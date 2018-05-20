package code;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import entity.Status;
import entity.Task;
import utility.Utility;

public class ListenerNode implements MessageListener {

	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//默认连接用户名
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认连接密码
    private static final String BROKEURL = Utility.activeIP;
    
	@Override
	public void onMessage(Message message) {
		TextMessage text = (TextMessage) message; 
		try {
        	String ip = text.getText();
            System.out.println("新节点："+ip);
            Task task = new Task(ip, Status.Node);
            Center.produce(task);
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
	}
	
	public static void main(String[] args) throws Exception {  
        ConnectionFactory connectionFactory =new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
        Connection conn = connectionFactory.createConnection();    
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        conn.start();  
        //消息目的地  
        Destination dest = session.createQueue("node");  
        //消息消费者  
        MessageConsumer consumer = session.createConsumer(dest);  
        consumer.setMessageListener(new ListenerNode());  
        //这里不能关闭连接，一旦关闭监听器也就关闭，那就接收不到消息了  
        System.out.println("success");
    }  

}
