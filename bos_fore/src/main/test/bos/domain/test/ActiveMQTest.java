package bos.domain.test;/*
* @author kynchen
* 
* @date 2018/8/2 23:20
*
* @version idea
*/

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

public class ActiveMQTest {
    @Test
    public void test1() throws Exception {
        //连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //创建session
        Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
        //创建队列
        Queue queue = session.createQueue("Hello ActiveMQ");
        //创建生产者
        MessageProducer producer = session.createProducer(queue);
        //发送消息
        for(int i=0;i<10;i++) {
            producer.send(session.createTextMessage("hi activeMQ"+i));
        }
        session.commit();
    }
}
