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
import serverInterface.server;
import utility.Utility;

public class ReCrawler {
	
	Dao dao = new Dao();
	
	//从table中选择limit a-b行，做成list
	private ArrayList<Request> selectFromTable(String password){
		ArrayList<Request> haveTickets = new ArrayList<Request>();
		
		try {
			ResultSet rs = dao.query("select * from last order by page ASC;", password);
			while(rs.next()){
				String name = rs.getString(1);
				int page = rs.getInt(2);
				int num = rs.getInt(3);
				String url = rs.getString(4);
				
				String requestUrl = Utility.requestUrl(url)+num;
				
				Request request = new Request();
				request.setName(name);
				request.setPage(page);
				request.setRequestUrl(requestUrl);
				
				haveTickets.add(request);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return haveTickets;
		
	}
	
	
	public void crawler(String password){
		int count = 0;
		int page = 0;
		ArrayList<Request> haveTickets = selectFromTable(password);
		
		for(int k=0;k<haveTickets.size();k++){	//这几页所有要爬的项目
			Request request = haveTickets.get(k);
			
			page = request.getPage();
			System.out.println("*********************"+page+"*********************");
			System.out.println(request.getName());
			String requestUrl = request.getRequestUrl();
			
//			try {
//				Document document = Jsoup.connect(requestUrl).timeout(5000).get();
//				
//				count++;
//				if (count==10) {
//					Thread.sleep(1000);
//					count=0;
//				}
//				
//				//summary
//				Element esm = document.select("h2[class=dark title]").first();
//				if (esm!=null) {
//					String summary = esm.text();
//					request.setSummary(summary);
//				}
//				
//				//labels
//				Element elb = document.select("label:containsOwn(Labels:)").first();
//				if (elb!=null) {
//					String labels = elb.parent().text();
//					request.setLabels(labels.substring(8));
//				}
//				
//				//priority
//				Element epo = document.select("label:containsOwn(Priority:)").first();
//				if (epo!=null) {
//					String priority = epo.parent().text();
//					request.setPriority(priority.substring(10));
//				}
//				
//				//createdTime
//				Element ect = document.select("label:containsOwn(Created:)").first();
//				if (ect!=null) {
//					String createdTime = ect.nextElementSibling().text();
//					request.setCreatedTime(createdTime);
//				}
//				
//				//status
//				Element est = document.select("label:containsOwn(Status:)").first();
//				if (est!=null) {
//					String status = est.nextElementSibling().text();
//					request.setStatus(status);
//				}
//				
//				//content
//				String content = "";
//				Element ectdiv = document.select("div[class=markdown_content]").first();
//				if (ectdiv!=null) {
//					Elements ectps = ectdiv.children();
//					Iterator<Element> ectpsIter = ectps.iterator();
//					while(ectpsIter.hasNext()) {
//						Element ectp = ectpsIter.next();
//						content = content + ectp.text() + "\n";
//					}
//					request.setContent(content);
//				}
//				
//				dao.updateRequest(request, password);
//					
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
		}//所有项目爬完
	}//方法结束
	
	
}
