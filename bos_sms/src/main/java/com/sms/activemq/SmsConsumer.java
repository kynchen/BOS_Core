package com.sms.activemq;/*
* @author kynchen
* 
* @date 2018/8/3 10:19
*
* @version idea
*/

import org.springframework.stereotype.Service;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@Service("smsConsumer")
public class SmsConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            //发送手机验证码
            //AliSendSms.SendSms(mapMessage.getString("telephone"),mapMessage.getString("code"));
            System.out.println("手机号："+mapMessage.getString("telephone")+",验证码："+mapMessage.getString("code"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
