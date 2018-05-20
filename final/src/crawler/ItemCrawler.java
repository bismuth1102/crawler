package crawler;

import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import database.Dao;
import entity.Item;

public class ItemCrawler {

	Dao dao = new Dao();

	public static void main(String args[]) {
		ItemCrawler c = new ItemCrawler();
		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);
		String z = args[2];
		c.item(x, y, z); // 第x~(y-1)页
	}

	private void item(int begin, int end, String password) {

		for (int page = begin; page < end; page++) {
			ArrayList<Item> items = new ArrayList<Item>();
			String pageUrl = "https://sourceforge.net/directory/?page=" + page;
			System.out.println("***************** " + page + " *****************");
			try {
				Connection con = Jsoup.connect(pageUrl);
				con.header("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
				con.header("Accept-Encoding", "gzip, deflate, br");
				con.header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
				con.header("Connection", "keep-alive");
				con.header("Host", "sourceforge.net");
				con.header("Upgrade-Insecure-Requests", "1");
				con.header("User-Agent",
						"Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
				Document document = con.timeout(5000).get();
				
//				Thread.sleep(1000);

				Element ul = document.select("ul[class=projects]").first();

				Elements lis = ul.select("li[itemtype=http://schema.org/SoftwareApplication]");
				Iterator<Element> liIter = lis.iterator();

				while (liIter.hasNext()) {
					Element li = liIter.next();
					Item i = new Item();
					i.setPage(page);

					// name
					String name = li.select("div[class=result-heading-texts]>a>h2").first().text();
					i.setName(name);
					System.out.println(name);

					// url
					String url = li.select("a.project-icon").first().attr("abs:href");
					url = url.substring(0, url.length() - 17);
					i.setUrl(url);

					items.add(i);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if (page < end) {
					item(page, end, password);
				}
			}

//			dao.saveItems(items, password);
		}

	}

}
