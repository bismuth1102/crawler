package entity;

public class Item {
	String name;
	int page;
	String description;
	String features;
	String url;
	String review;
	String review5;
	String reviewNum;
	String weekDownload;
	String totalDownload;
	String update;
	String homePage;
	String catalog;
	String recommand;	//这一项不在itemCrawler里
	String register;
	String audiences;
	String interfaces;
	String languages;
	int tickets;
	String frUrl;	//没有stats
	

	public Item(){
		this.name = "";
		this.page = 0;
		this.description = "";
		this.features = "";
		this.url = "";
		this.review = "";
		this.review5 = "";
		this.reviewNum = "";
		this.weekDownload = "";
		this.totalDownload = "";
		this.update = "";
		this.homePage = "";
		this.catalog = "";
		this.recommand = "";
		this.register = "";
		this.audiences = "";
		this.interfaces = "";
		this.languages = "";
		this.tickets = 0;
		this.frUrl = "";
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTickets() {
		return tickets;
	}
	public void setTickets(int tickets) {
		this.tickets = tickets;
	}
	public String getLanguages() {
		return languages;
	}
	public void setLanguages(String languages) {
		this.languages = languages;
	}
	public String getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(String interfaces) {
		this.interfaces = interfaces;
	}
	public String getAudiences() {
		return audiences;
	}
	public void setAudiences(String audiences) {
		this.audiences = audiences;
	}
	public String getRegister() {
		return register;
	}
	public void setRegister(String register) {
		this.register = register;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getRecommand() {
		return recommand;
	}
	public void setRecommand(String recommand) {
		this.recommand = recommand;
	}
	public String getHomePage() {
		return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	public String getReview5() {
		return review5;
	}
	public void setReview5(String review5) {
		this.review5 = review5;
	}	
	public String getReviewNum() {
		return reviewNum;
	}
	public void setReviewNum(String reviewNum) {
		this.reviewNum = reviewNum;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getWeekDownload() {
		return weekDownload;
	}
	public void setWeekDownload(String weekDownload) {
		this.weekDownload = weekDownload;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getTotalDownload() {
		return totalDownload;
	}
	public void setTotalDownload(String totalDownload) {
		this.totalDownload = totalDownload;
	}
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String feature) {
		this.features = feature;
	}
	public String getFrUrl() {
		return frUrl;
	}
	public void setFrUrl(String frUrl) {
		this.frUrl = frUrl;
	}
	
}
