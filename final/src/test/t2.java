package test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;

public class t2 {
	
	public static void main(String args[]) {
		String host = "183.221.245.207";
		String port = "80";
		SocketAddress addr = new InetSocketAddress(host, Integer.parseInt(port)); 
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
		try{
		    URL url = new URL("http://www.baidu.com");
		    URLConnection conn = url.openConnection(proxy);
		    conn.setConnectTimeout(5000);
		    conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)");
		    conn.getContent();
		}catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
