package com.fore.web.action;/*
* @author kynchen
* 
* @date 2018/8/8 11:06
*
* @version idea
*/

import bos.domain.base.Area;
import bos.domain.take_delivery.Order;
import com.crm.domain.Customer;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import javax.jms.*;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order>{

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    private String sendAreaInfo;
    private String recAreaInfo;

    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }

    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    @Action(value = "order_add",results = {@Result(name = "success",type = "redirect",location = "index.html")})
    public String order_add(){

        //寄件人省市信息
        Area sendArea = new Area();
        sendArea.setProvince(sendAreaInfo.split("/")[0]);
        sendArea.setCity(sendAreaInfo.split("/")[1]);
        sendArea.setDistrict(sendAreaInfo.split("/")[2]);
        model.setSendArea(sendArea);
        //收件人省市信息
        Area recArea = new Area();
        recArea.setProvince(recAreaInfo.split("/")[0]);
        recArea.setCity(recAreaInfo.split("/")[1]);
        recArea.setDistrict(recAreaInfo.split("/")[2]);
        model.setRecArea(recArea);

        Customer customer = (Customer) ServletActionContext.getRequest().getSession().getAttribute("customer");
        model.setCustomer_id(customer.getId());
        final Order order=model;
        jmsTemplate.send("order",new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
            ObjectMessage message = session.createObjectMessage(order);
            return message;
            }
        });

        return SUCCESS;
    }
}
