package serverB; 
  
import javax.jms.Connection;  
import javax.jms.ConnectionFactory;  
import javax.jms.Destination;  
import javax.jms.JMSException;  
import javax.jms.Message;  
import javax.jms.MessageConsumer;  
import javax.jms.MessageListener;  
import javax.jms.Session;  
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import crawler.RequestCrawler;
import utility.Utility;  
  
  
public class BListener implements MessageListener{  
	
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//默认连接用户名
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认连接密码
    private static final String BROKEURL = Utility.activeIP;

      
    @Override  
    public void onMessage(Message message) {  
    	 TextMessage text = (TextMessage) message;  
         try {  
        	String s = text.getText();
            System.out.println("接收消息："+s); 
            operate(s);
        } catch (JMSException e) {  
            e.printStackTrace();  
        }  
    } 
    
    private void operate(String s) {
    	int begin = Integer.parseInt(s);
    	
    	if (begin!=0) {
    		RequestCrawler crawler = new RequestCrawler();
        	BSendback server = new BSendback();
        	String password = "Mylinux123*";
        	crawler.crawler(begin, password, server);
		}
    	else {
			BSendback sendback = new BSendback();
			sendback.sendback();
		}
    	
    }
      
    public static void main(String[] args) throws Exception {  
        ConnectionFactory connectionFactory =new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
        Connection conn = connectionFactory.createConnection();    
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);	//是否支持事务；签收模式
        conn.start();  
        //消息目的地  
        Destination dest = session.createQueue("b");
        //消息消费者  
        MessageConsumer consumer = session.createConsumer(dest);  
        consumer.setMessageListener(new BListener());  
        //这里不能关闭连接，一旦关闭监听器也就关闭，那就接收不到消息了 
        System.out.println("success");
    }  
  
}  