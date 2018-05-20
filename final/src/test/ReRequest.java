package test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import database.Dao;
import entity.Request;

public class ReRequest {

	Dao dao = new Dao();

	public static void main(String args[]) {
		ReRequest re = new ReRequest();
		re.request("951102ljc");
	}

	private ArrayList<Request> selectFromTable(String password) {
		ArrayList<Request> array = new ArrayList<Request>();

		try {
			ResultSet rs = dao.query("select * from reRequest2;", password);
			while (rs.next()) {
				String name = rs.getString(1);
				int page = rs.getInt(2);
				int num = rs.getInt(3);
				String requestUrl = rs.getString(4);

				entity.Request request = new Request();
				request.setName(name);
				request.setNum(num);
				request.setPage(page);
				request.setRequestUrl(requestUrl);

				array.add(request);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;

	}

	private void request(String password) {
		int count = 0;
		ArrayList<Request> array = selectFromTable(password);

		for (int k = 0; k < array.size(); k++) {
			Request request = array.get(k);

			System.out.println("*********************" + request.getPage() + "*********************");
			System.out.println(request.getName() + " " + request.getNum());
			String requestUrl = request.getRequestUrl();

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
				e.printStackTrace();
			}

		}
	}

}
