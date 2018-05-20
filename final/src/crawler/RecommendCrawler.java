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
import entity.Recommend;

public class RecommendCrawler {
	ArrayList<Item> items = new ArrayList<Item>();
	Dao dao = new Dao();

	public static void main(String args[]) {
		RecommendCrawler c = new RecommendCrawler();
		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);
		String z = args[2];
		c.recommend(x, y, z); // 第x~(y-1)页
	}

	// 从table中选择limit a-b行，做成list
	private ArrayList<Item> selectFromTable(int begin, int end, String password) {
		ArrayList<Item> items = new ArrayList<Item>();

		try {
			ResultSet rs = dao.query(
					"select * from item where page >=" + begin + " and page <" + end + " order by page ASC;", password);
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

	private void recommend(int begin, int end, String password) {
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
			System.out.println(i.getName());

			Recommend r = new Recommend();
			r.setHost(i.getName());
			r.setPage(i.getPage());
			r.setUrl(i.getUrl());

			try {
				Document document = Jsoup.connect(r.getUrl()).timeout(10000).get();

				count++;
				if (count == 10) {
					Thread.sleep(1000);
					count = 0;
				}

				Elements rcms = document.select("div[class=pinfo-content recommended]>a");
				Iterator<Element> rcmIter = rcms.iterator();

				Element r1 = rcmIter.next();
				String r1name = r1.text();
				String r1url = r1.attr("abs:href");
				r.setR1name(r1name);
				r.setR1url(r1url);

				Element r2 = rcmIter.next();
				String r2name = r2.text();
				String r2url = r2.attr("abs:href");
				r.setR2name(r2name);
				r.setR2url(r2url);

				Element r3 = rcmIter.next();
				String r3name = r3.text();
				String r3url = r3.attr("abs:href");
				r.setR3name(r3name);
				r.setR3url(r3url);

				dao.saveRecommend(r, password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dao.reRecommend(i, password);
				System.out.println("rerecommend");
			}
		}

	}

}
