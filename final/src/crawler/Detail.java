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

public class Detail {

	Dao dao = new Dao();

	public static void main(String args[]) {
		Detail c = new Detail();
		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);
		String z = args[2];
		c.detail(x, y, z); // 第x~(y-1)页
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

	private void detail(int begin, int end, String password) {
//		int count = 0;
		int page = begin;
		ArrayList<Item> items = selectFromTable(begin, end, password);
		System.out.println("*********************" + page + "*********************");

		for (int k = 0; k < items.size(); k++) {
			Item i = items.get(k);
			String url = i.getUrl();

			if (page < i.getPage()) {
				page = i.getPage();
				System.out.println("*********************" + page + "*********************");
			}
			System.out.println(i.getName());

			try {
//				count++;
//				if (count == 10) {
//					Thread.sleep(1000);
//					count = 0;
//				}

				Document document = Jsoup.connect(url).timeout(5000).get();

				// weekDownload
				Element ewd = document.select("a[title=Downloads This Week]").first();
				if (ewd != null) {
					String weekDownload = ewd.text();
					i.setWeekDownload(weekDownload);
				}

				// update
				Element eud = document.select("time[class=dateUpdated]").first();
				if (eud != null) {
					String update = eud.text();
					i.setUpdate(update);
				}

				// reviewNum
				Element ern = document.select("a[title=Reviews]").first();
				if (ern != null) {
					String reviewNum = ern.text();
					i.setReviewNum(reviewNum);
				}

				// description
				Element edc = document.select("p[itemprop=description]").first();
				if (edc != null) {
					String description = edc.text();
					i.setDescription(description);
				}

				// feature
				Element efh3 = document.select("h3:containsOwn(Features)").first();
				if (efh3 != null) {
					Element eful = efh3.nextElementSibling();
					String features = "";
					Elements efuis = eful.children();
					Iterator<Element> efuisIter = efuis.iterator();
					while (efuisIter.hasNext()) {
						features = features + efuisIter.next().text() + "\n";
					}
					i.setFeatures(features);
				}

				// review
				Element erv = document.select("span[itemprop=ratingValue]").first();
				if (erv != null) {
					String review = erv.text();
					i.setReview(review);
				}

				// review5
				Element er5 = document.select("div[class=stars-5]>div[class=rating-label]").first();
				if (er5 != null) {
					String review5 = er5.text();
					i.setReview5(review5);
				}

				// homePage
				Element ehp = document.select("a[id=homePage]").first();
				if (ehp != null) {
					String homePage = ehp.attr("abs:href");
					i.setHomePage(homePage);
				}

				// catalog
				Element lastLi = document.select("nav[id=breadcrumbs]>ul>li").last();
				if (lastLi != null) {
					Element last2Lia = lastLi.previousElementSibling().select("a").first();
					String catalog = last2Lia.attr("href");
					i.setCatalog(catalog);
				}

				// audience
				String audiences = projectInfo("Intended Audience", document);
				i.setAudiences(audiences);

				// interface
				String interfaces = projectInfo("User Interface", document);
				i.setInterfaces(interfaces);

				// language
				String languages = projectInfo("Programming Language", document);
				i.setLanguages(languages);

				// register
				Element header = document.select("h4:containsOwn(Registered)").first().parent();
				String register = header.nextElementSibling().text();
				i.setRegister(register);

//				dao.saveDetail(i, password);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
//				dao.reDetail(i, password);
//				System.out.println("redetail");
			}
		}

	}

	private String projectInfo(String header, Document document) {

		String strs = "";
		try {
			Element h4 = document.select("h4:containsOwn(" + header + ")").first();
			if (h4 != null) {
				Elements Ele = h4.siblingElements();
				Iterator<Element> liIter = Ele.iterator();

				while (liIter.hasNext()) {
					String str = liIter.next().text();
					strs = strs + "," + str;
				}
				strs = strs.substring(1);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return strs;
	}

}
