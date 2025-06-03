package corsojava.threads;
 

public class MyThread extends Thread {

	String threadName;

	public MyThread(String threadName) {
		super();
		this.threadName = threadName;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	
	@Override
	public void run() {
	
		System.out.println(Thread.currentThread().getId() +" ["+this.threadName +"] Sto eseguendo il thread:");
		
		Long milliSec = Math.round(Math.random()*3000)+1000; // numeri fra 1000.0001 e 3999.99
		 
		
	 	System.out.println(this.getId() +" ["+this.threadName +"] pausa di "+milliSec+" millisecondi...");
		try {
			Thread t = null;
			if (milliSec > 1500 ) {
				t = new MyThread(this.threadName +".SottoThread"+milliSec);
				t.start();
			}
			sleep(milliSec); 
			if (t != null) {
				t.join(); 
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getId() +" ["+this.threadName +"] Ho finito il thread!");
		
	}
	
}
