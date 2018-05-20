package crawler;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import database.Dao;
import entity.Item;
import entity.Request;
import utility.Utility;

public class ReqeustCrawler {

	Dao dao = new Dao();

	public static void main(String args[]) {
		ReqeustCrawler c = new ReqeustCrawler();
		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);
		String z = args[2];
		c.request(x, y, z); // 第x~(y-1)页
	}

	// 从table中选择limit a-b行，做成list
	private ArrayList<Item> selectFromTable(int begin, int end, String password) {
		ArrayList<Item> haveTickets = new ArrayList<Item>();

		try {
			ResultSet rs = dao.query("select * from item where page >=" + begin + " and page <" + end + " and tickets>0 order by page ASC;", password);
			while (rs.next()) {
				String name = rs.getString(1);
				int page = rs.getInt(2);
				String url = rs.getString(3);
				int tickets = rs.getInt(4);

				Item i = new Item();
				i.setName(name);
				i.setPage(page);
				i.setUrl(url);
				i.setTickets(tickets);

				haveTickets.add(i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return haveTickets;

	}

	private void request(int begin, int end, String password) {
		int count = 0;
		int page = begin;
		ArrayList<Item> haveTickets = selectFromTable(begin, end, password);
		System.out.println("*********************" + page + "*********************");

		for (int k = 0; k < haveTickets.size(); k++) {
			Item i = haveTickets.get(k);
			int tickets = i.getTickets();

			page = i.getPage();
			System.out.println("*********************" + page + "*********************");
			System.out.println(i.getName());

			for (int m = 1; m <= tickets; m++) {
				// String requestUrl = i.getFrUrl() + m + "/";
				String requestUrl = Utility.requestUrl(i) + m + "/";

				System.out.println(m);

				Request request = new Request();
				request.setName(i.getName());
				request.setPage(i.getPage());
				request.setRequestUrl(requestUrl);
				request.setNum(m);

				try {
					Document document = Jsoup.connect(requestUrl).timeout(5000).get();

					count++;
					if (count == 10) {
						Thread.sleep(1000);
						count = 0;
					}

					// labels
					Element elb = document.select("label:containsOwn(Labels:)").first();
					if (elb != null) {
						String labels = elb.parent().text();
						request.setLabels(labels.substring(8));
					}

					// priority
					Element epo = document.select("label:containsOwn(Priority:)").first();
					if (epo != null) {
						String priority = epo.parent().text();
						request.setPriority(priority.substring(10));
					}

					// createdTime
					Element ect = document.select("label:containsOwn(Created:)").first();
					if (ect != null) {
						String createdTime = ect.nextElementSibling().text();
						request.setCreatedTime(createdTime);
					}

					// status
					Element est = document.select("label:containsOwn(Status:)").first();
					if (est != null) {
						String status = est.nextElementSibling().text();
						request.setStatus(status);
					}

					// content
					String content = "";
					Element ectdiv = document.select("div[class=markdown_content]").first();
					if (ectdiv != null) {
						Elements ectps = ectdiv.children();
						Iterator<Element> ectpsIter = ectps.iterator();
						while (ectpsIter.hasNext()) {
							Element ectp = ectpsIter.next();
							content = content + ectp.text() + "\n";
						}
						request.setContent(content);
					}

					dao.saveRequest(request, password);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					dao.reRequest(request, password);
					System.out.println("rerequest");
				}
			}
		}

	}

}
