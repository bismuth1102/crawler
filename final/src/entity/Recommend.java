package entity;

public class Recommend {
	String host;
	String url;
	int page;
	String r1name;
	String r1url;
	String r2name;
	String r2url;
	String r3name;
	String r3url;

	public Recommend() {
		this.host = "";
		this.url = "";
		this.page = 0;
		this.r1name = "";
		this.r1url = "";
		this.r2name = "";
		this.r2url = "";
		this.r3name = "";
		this.r3url = "";
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getR1name() {
		return r1name;
	}

	public void setR1name(String r1name) {
		this.r1name = r1name;
	}

	public String getR1url() {
		return r1url;
	}

	public void setR1url(String r1url) {
		this.r1url = r1url;
	}

	public String getR2name() {
		return r2name;
	}

	public void setR2name(String r2name) {
		this.r2name = r2name;
	}

	public String getR2url() {
		return r2url;
	}

	public void setR2url(String r2url) {
		this.r2url = r2url;
	}

	public String getR3name() {
		return r3name;
	}

	public void setR3name(String r3name) {
		this.r3name = r3name;
	}

	public String getR3url() {
		return r3url;
	}

	public void setR3url(String r3url) {
		this.r3url = r3url;
	}

}
