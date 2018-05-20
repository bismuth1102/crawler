package code;

import entity.Status;
import entity.Task;

public class Connector implements Runnable {

	static int i = 0;
	Task task;
	
	public Connector(Task task) {
		this.task = task;
	}
	
	public void run() {
		String ip = task.getIp();
		Status status = task.getStatus();
		JmsClient client = new JmsClient();
		
		if (status==Status.Return) {
			i++;
			if(i!=0&&i<=5) {
				client.connect(ip, i);
				System.out.println("已连接"+ip);
			}
		}
		else {
			client.connect(ip, 0);
			System.out.println("新"+ip);
		}
		
	}

}
