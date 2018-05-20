package code;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import entity.Task;

public class Center{   
   
	static ExecutorService service = Executors.newCachedThreadPool();
	
	public static void produce(Task task) {
		Connector connector = new Connector(task);
		service.execute(connector);
	}
	   
}