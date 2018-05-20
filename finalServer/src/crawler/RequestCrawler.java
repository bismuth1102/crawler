package crawler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import database.Dao;
import entity.Item;
import entity.Request;
import serverInterface.server;
import utility.Utility;

public class RequestCrawler {
	
	Dao dao = new Dao();
	Timer timer = new Timer();
    IPtask task = new IPtask();
	
	//从table中选择limit a-b行，做成list
	private ArrayList<Item> selectFromTable(int begin, String password){
		ArrayList<Item> haveTickets = new ArrayList<Item>();
		int end = begin+1;
		
		try {
			ResultSet rs = dao.query("select * from item where page >=" + begin + " and page <" + end + " and tickets>0 order by page ASC;", password);
			while(rs.next()){
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
	
	
	private Document getDocument(String href) {
		Proxy proxy = task.getProxy();
		Document doc = null;
		
		try {		
			URL url = new URL(href);  
			
			if (proxy==null) {
//				System.out.println("no proxy");
				Connection con = Jsoup.connect(href);
				con = Utility.setHead(con);
				doc = Jsoup.connect(href).get();
			}
	    	else {
//	    		System.out.println("have proxy");
	    		HttpsURLConnection urlcon = (HttpsURLConnection)url.openConnection(proxy);
	    		urlcon = Utility.setHead(urlcon);
		    	urlcon.connect();         //获取连接  
		    	InputStream is = urlcon.getInputStream();  
		    	BufferedReader buffer = new BufferedReader(new InputStreamReader(is));  
		    	StringBuffer bs = new StringBuffer();  
		    	String l = null;  
		    	while((l=buffer.readLine())!=null){  
			    	bs.append(l);  
		    	}  
		    	doc = Jsoup.parse(bs.toString());
			}
	    	
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	
	public void crawler(int begin, String password, server server){
		timer.schedule(task, 0, 30000);
		
		int page = 0;
		ArrayList<Item> haveTickets = selectFromTable(begin, password);
		
		for(int k=0;k<haveTickets.size();k++){	//这几页所有要爬的项目
			Item i = haveTickets.get(k);
			int tickets = i.getTickets();
			
			page = i.getPage();
			System.out.println("*********************"+page+"*********************");
			System.out.println(i.getName());
			
			for(int m=1;m<=tickets;m++){	//每个项目的tickets
				String requestUrl = Utility.requestUrl(i) + m + "/";
				
				System.out.println(m);
				
				Request request = new Request();
				request.setName(i.getName());
				request.setPage(i.getPage());
				request.setNum(m);
				
				try {
					Document document = getDocument(requestUrl);
					
//					//summary
//					Element esm = document.select("h2[class=dark title]").first();
//					if (esm!=null) {
//						String summary = esm.text();
//						request.setSummary(summary);
//					}
//					
//					//labels
//					Element elb = document.select("label:containsOwn(Labels:)").first();
//					if (elb!=null) {
//						String labels = elb.parent().text();
//						request.setLabels(labels.substring(8));
//					}
//					
//					//priority
//					Element epo = document.select("label:containsOwn(Priority:)").first();
//					if (epo!=null) {
//						String priority = epo.parent().text();
//						request.setPriority(priority.substring(10));
//					}
//					
//					//createdTime
//					Element ect = document.select("label:containsOwn(Created:)").first();
//					if (ect!=null) {
//						String createdTime = ect.nextElementSibling().text();
//						request.setCreatedTime(createdTime);
//					}
//					
//					//status
//					Element est = document.select("label:containsOwn(Status:)").first();
//					if (est!=null) {
//						String status = est.nextElementSibling().text();
//						request.setStatus(status);
//					}
//					
//					//content
//					String content = "";
//					Element ectdiv = document.select("div[class=markdown_content]").first();
//					if (ectdiv!=null) {
//						Elements ectps = ectdiv.children();
//						Iterator<Element> ectpsIter = ectps.iterator();
//						while(ectpsIter.hasNext()) {
//							Element ectp = ectpsIter.next();
//							content = content + ectp.text() + "\n";
//						}
//						request.setContent(content);
//					}
					
//					dao.updateRequest(request, password);
						
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
//					dao.reRequest(request, password);
//					System.out.println("redo");
				}
			}//一个项目的tickets爬完
		}//所有项目爬完
		timer.cancel();
		ReCrawler reCrawler = new ReCrawler();
		reCrawler.crawler(password);
		server.sendback();
	}//方法结束
	
	
}
