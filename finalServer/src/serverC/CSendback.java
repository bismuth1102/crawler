package serverC;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import serverInterface.server;
import utility.Utility;


public class CSendback implements server {

	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKEURL = Utility.activeIP;
	

	public void sendback() {

		//连接工厂
        ConnectionFactory connectionFactory;
        //连接
        Connection connection = null;
        //会话 接受或者发送消息的线程
        Session session;
        //消息的目的地
        Destination destination;
        //消息生产者
        MessageProducer messageProducer;
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);

        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建一个名称为HelloWorld的消息队列
            destination = session.createQueue("return");
            //创建消息生产者
            messageProducer = session.createProducer(destination);
            //设置为不持久化
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);  
            //发送消息
            sendMessage(session, messageProducer);

            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }
	
    public static void sendMessage(Session session,MessageProducer messageProducer) throws Exception{
        //创建一条文本消息 
        TextMessage message = session.createTextMessage("c");
        System.out.println("return");
        //通过消息生产者发出消息 
        messageProducer.send(message);
    }
    
}
