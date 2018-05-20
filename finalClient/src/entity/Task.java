package entity;

public class Task {
	String ip;
	Status status;
	
	public Task(String ip, Status status) {
		super();
		this.ip = ip;
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
}
