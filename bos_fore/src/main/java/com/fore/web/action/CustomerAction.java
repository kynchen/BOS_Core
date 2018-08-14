package com.fore.web.action;/*
 * @author kynchen
 *
 * @date 2018/8/2 9:11
 *
 * @version idea
 */

import bos.domain.constans.Constans;
import com.crm.domain.Customer;
import com.fore.utils.JsonUtils;
import com.fore.utils.MailUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class CustomerAction extends BaseAction<Customer> {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 发送手机验证码
     *
     * @return
     * @throws Exception
     */
    @Action(value = "customer_sendSms")
    public String customer_sendSms() throws Exception {
        final String code = RandomStringUtils.randomNumeric(6);
        //将code存入redis
        redisTemplate.opsForValue().set("code", code, 5, TimeUnit.MINUTES);
//        redisTemplate.boundValueOps("code").set(code);
//        redisTemplate.boundValueOps("code").expire(5,TimeUnit.MINUTES);
        //将发送短信请求消息发送给MQ队列
        jmsTemplate.send("bos_sms", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("telephone", model.getTelephone());
                mapMessage.setString("code", code);
                return mapMessage;
            }
        });
        System.out.println(code);
        return NONE;
    }

    private String checkCode;

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    /**
     * 用户注册 验证码校验 邮件发送
     *
     * @return
     */
    @Action(value = "customer_regist", results = {
            @Result(name = "success", type = "redirect", location = "signup-success.html"),
            @Result(name = "input", type = "redirect", location = "signup.html")
    })
    public String customer_regist() {
        String code = redisTemplate.opsForValue().get("code");
        System.out.println(checkCode);
        System.out.println(code);
        if (code == null || !code.equals(checkCode)) {
            System.out.println("注册失败");
            return INPUT;
        }

        //WebClient.create("http://localhost:8081/services/customerService/customer").type(MediaType.APPLICATION_JSON).post(model);
        //生成激活码
        String activeCode = RandomStringUtils.randomNumeric(32);
        //存入redis中
        redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 24, TimeUnit.HOURS);

        final String content = "尊敬的会员，请点击以下链接进行激活:<br />" +
                "<a href='" + MailUtils.activeUrl + "?telephone=" + model.getTelephone() + "&activeCode=" + activeCode + "'>kynchen信息技术公司----点击这里进行账号激活</a>";

        jmsTemplate.send("bos_mail", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("subject", "kynchen信息技术公司");
                mapMessage.setString("content", content);
                mapMessage.setString("email", model.getEmail());
                return mapMessage;
            }
        });
        WebClient.create(Constans.CRM_MANAGEMENT_URL + "/crm_management/services/customerService/customer").type(MediaType.APPLICATION_JSON).post(model);
        //发送邮件
        //MailUtils.sendMail("kynchen信息技术公司",content,model.getEmail());

        return SUCCESS;
    }

    //http://localhost:8082/customer_activeMail?telephone=18695774575&activeCode=35385528245986667851286097427383

    private String activeCode;

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    @Action(value = "customer_activeMail")
    public String customer_activeMail() {

        String activeRedisCode = redisTemplate.opsForValue().get(model.getTelephone());
        if (activeCode == null || !activeCode.equals(activeRedisCode)) {
            JsonUtils.write("激活码失效，请重新激活");
        } else {
            Customer customer = WebClient.create(Constans.CRM_MANAGEMENT_URL + "/services/customerService/customer/telephone/" + model.getTelephone()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
            if (customer.getType() == null || customer.getType() != 1) {
                WebClient.create(Constans.CRM_MANAGEMENT_URL + "/services/customerService/customer/updatetype/" + model.getTelephone()).get();
                JsonUtils.write("邮箱绑定成功");
            } else {
                JsonUtils.write("邮箱已绑定，无需重复绑定");
            }
        }
        redisTemplate.delete(model.getTelephone());
        return NONE;
    }

    @Action(value = "customer_login", results = {@Result(name = "success", location = "index.html#/myhome", type = "redirect"),
    @Result(name = "input",location = "login.html",type = "redirect")})
    public String customer_login() {
        Customer customer=WebClient.create(Constans.CRM_MANAGEMENT_URL + "/services/customerService/customer/login?telephone=" + model.getTelephone() +
                "&password=" + model.getPassword()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
        if(customer!=null) {
            ServletActionContext.getRequest().getSession().setAttribute("customer",customer);
            return SUCCESS;
        }else{
            return INPUT;
        }
    }

}
