package bos.test;/*
* @author kynchen
* 
* @date 2018/8/3 0:01
*
* @version idea
*/

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

public class ConsumeTest {
    @Test
    public void Consumes() throws Exception {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

        Connection connection = connectionFactory.createConnection();
        
        connection.start();

        //第一个参数 是否开启事务，如果开启事务，需要在操作队列消息之后，进行commit()
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue("Hello ActiveMQ");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        while (true){

        }
    }
}
