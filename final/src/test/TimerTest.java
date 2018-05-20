package test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
	
	Timer timer = new Timer();
    Task task = new Task();
	
	public static void main(String[] args) {
		TimerTest test = new TimerTest();
		test.run();
    }
	
	public void run() {
		changeIP();
		for (int i = 0; i < 5; i++) {
			aaa();
		}
		timer.cancel();
	}
	
	public void aaa() {
		System.out.println(task.getI());
		try {
			Thread.sleep(1100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void changeIP() {
		timer.schedule(task, 0, 1000);
//		System.out.println("change!");
	}
}





class Task extends TimerTask{
	
	int i = 1;
	
	public int getI() {
		return i;
	}

    @Override
    public void run() {
        if (i==1) {
			i=0;
		}
        else {
			i=1;
		}
    }
}
