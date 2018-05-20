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
import utility.Utility;

public class Tickets {

	Dao dao = new Dao();

	public static void main(String args[]) {
		Tickets c = new Tickets();
		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);
		String z = args[2];
		c.tickets(x, y, z); // 第x~(y-1)页
	}

	// 从table中选择limit a-b行，做成list
	private ArrayList<Item> selectFromTable(int begin, int end, String password) {
		ArrayList<Item> items = new ArrayList<Item>();

		try {
			ResultSet rs = dao.query(
					"select * from reTickets2 where page >=" + begin + " and page <" + end + " order by page ASC;",
					password);
			while (rs.next()) {
				String name = rs.getString(1);
				int page = rs.getInt(2);
				String url = rs.getString(3);

				Item i = new Item();
				i.setName(name);
				i.setPage(page);
				i.setUrl(url);

				items.add(i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;

	}

	private void tickets(int begin, int end, String password) {
		int count = 0;
		int page = begin;
		ArrayList<Item> items = selectFromTable(begin, end, password);
		System.out.println("*********************" + page + "*********************");

		for (int k = 0; k < items.size(); k++) {
			Item i = items.get(k);

			if (page < i.getPage()) {
				page = i.getPage();
				System.out.println("*********************" + page + "*********************");
			}

			try {
				count++;
				if (count == 10) {
					Thread.sleep(1000);
					count = 0;
				}

				if (haveFR(i, password)) {
					String requestStatsUrl = Utility.requestUrl(i) + "stats/";

					// tickets的打印名字在try里面，因为只打印有fr的
					System.out.println(i.getName());

					Document document = Jsoup.connect(requestStatsUrl).timeout(5000).get();

					Element edt = document.select("h2[class=dark title]").first();
					if (edt != null) {
						Elements ticketsDetails = edt.nextElementSibling().children();
						Iterator<Element> ticketsIter = ticketsDetails.iterator();

						// tickets
						String tickets = ticketsIter.next().text();
						i.setTickets(Integer.parseInt(tickets.substring(11)));

						dao.updateTickets(i, password);
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dao.reTickets(i, password);
				System.out.println("retickets2");
			}
		}

	}

	private boolean haveFR(Item i, String password) {
		try {
			String featureRequestUrl = Utility.requestUrl(i) + "stats/";

			if (Utility.checkUrl(featureRequestUrl)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// dao.reTickets(i, password);
			// System.out.println("retickets");
		}
		return false;
	}

}
