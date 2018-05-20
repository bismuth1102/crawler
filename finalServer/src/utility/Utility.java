package utility;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Connection;

import entity.Item;

public class Utility {
	
	public static String activeIP = "tcp://140.143.194.177:61616";
	
	public static String requestUrl(Item i){
		String urlStr = i.getUrl();
		String requestUrl = urlStr.substring(0, 25) + "/" + urlStr.substring(33) + "feature-requests/";
		return requestUrl;
	}
	
	public static String requestUrl(String urlStr) {
		String requestUrl = urlStr.substring(0, 25) + "/" + urlStr.substring(33) + "feature-requests/";
		return requestUrl;
	}
	
	public static boolean checkUrl(String urlStr){
		URL url;
		int state = 0;
		try {
			url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();  
		    state = con.getResponseCode(); 
		    if (state==200) {
				return true;
			}
		    else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static HttpsURLConnection setHead(HttpsURLConnection urlcon) {
		urlcon.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
    	urlcon.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
    	urlcon.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
    	urlcon.setRequestProperty("Connection", "keep-alive");
    	urlcon.setRequestProperty("Host", "sourceforge.net");
    	urlcon.setRequestProperty("Upgrade-Insecure-Requests", "1");
    	urlcon.setRequestProperty("User-Agent",
				"Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
    	urlcon.setConnectTimeout(5000);	
    	return urlcon;
	}
	
	public static Connection setHead(Connection con) {
		con.header("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		con.header("Accept-Encoding", "gzip, deflate, br");
		con.header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
		con.header("Connection", "keep-alive");
		con.header("Host", "sourceforge.net");
		con.header("Upgrade-Insecure-Requests", "1");
		con.header("User-Agent",
				"Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		con.timeout(5000);
		return con;
	}
	
}
