package com.sms.activemq;/*
* @author kynchen
* 
* @date 2018/8/3 11:37
*
* @version idea
*/

import com.sms.utils.MailUtils;
import org.springframework.stereotype.Service;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@Service("mailConsumer")
public class MailConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            MailUtils.sendMail(mapMessage.getString("subject"),mapMessage.getString("content"),mapMessage.getString("email"));
            System.out.println("发送成功...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
