package test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class t1 {

	public static void main(String args[]) {
		System.getProperties().setProperty("proxySet", "true");
		System.getProperties().setProperty("http.proxyHost", "218.73.142.102");
		System.getProperties().setProperty("http.proxyPort", "30278");
		
		Connection connection = Jsoup.connect("http://ip.chinaz.com/getip.aspx");
		connection.timeout(2000);
		
		try {
			Document document = connection.get();
			System.out.println(document);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
