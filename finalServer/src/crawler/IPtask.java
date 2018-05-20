package crawler;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.TimerTask;

public class IPtask extends TimerTask{
	
	boolean useProxy = false;
	Proxy proxy = null;
	
    @Override
    public void run() {
//    	if (useProxy==false) {
//    		useProxy();
//    		useProxy = true;
//		}
//    	else {
//			proxy = null;
//			useProxy = false;
//		}
    }
    
    private void useProxy() {
    	String ip = "221.237.155.64";
    	int port = 9797;
    	proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
    }
    
    public Proxy getProxy() {
    	return proxy;
    }
    
}
