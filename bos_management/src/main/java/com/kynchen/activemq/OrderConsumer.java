package com.kynchen.activemq;/*
 * @author kynchen
 *
 * @date 2018/8/8 11:54
 *
 * @version idea
 */

import bos.domain.take_delivery.Order;
import com.kynchen.service.take_delivery.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Service("orderConsumer")
public class OrderConsumer implements MessageListener {

    @Autowired
    private OrderService orderService;

    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            //接收订单
            Order order = (Order) objectMessage.getObject();
            System.out.println(order);
            orderService.save(order);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
