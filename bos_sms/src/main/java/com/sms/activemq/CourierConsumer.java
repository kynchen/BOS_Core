package com.sms.activemq;/*
* @author kynchen
* 
* @date 2018/8/8 19:05
*
* @version idea
*/

import org.springframework.stereotype.Service;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@Service("courierConsumer")
public class CourierConsumer implements MessageListener {
    /** 发短信给快递员
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage=(MapMessage) message;
        try {
            //AliSendCourier.SendCourier(mapMessage.getString("telephone"),mapMessage.getString("name"),mapMessage.getString("sendPhone"));
            System.out.println("发送短信给快递员成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
