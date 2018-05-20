package utility;

import java.net.HttpURLConnection;
import java.net.URL;

import entity.Item;

public class Utility {

	public static String requestUrl(Item i) {
		String urlStr = i.getUrl();
		String requestUrl = urlStr.substring(0, 25) + "/" + urlStr.substring(33) + "feature-requests/";
		return requestUrl;
	}

	public static String ticketsUrl(Item i) { // 暂时用不到
		String urlStr = i.getUrl();
		String requestUrl = urlStr.substring(0, 25) + "/" + urlStr.substring(33) + "tickets/";
		return requestUrl;
	}

	public static boolean checkUrl(String urlStr) {
		URL url;
		int state = 0;
		try {
			url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			state = con.getResponseCode();
			if (state == 200) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
