package corsojava.threads;
 

public class MyRunnable implements Runnable {

	String threadName;

	public MyRunnable(String threadName) {
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
	
		System.out.println(Thread.currentThread().getId() +"  ["+this.threadName +"] Sto eseguendo il thread da Runnable:");
		
		Long milliSec = Math.round(Math.random()*3000)+1000; // numeri fra 1000.0001 e 3999.99
		 
		
	 	System.out.println(Thread.currentThread().getId() +" ["+this.threadName +"] pausa di "+milliSec+" millisecondi...");
		try {
			Thread.sleep(milliSec); 
			 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		System.out.println(Thread.currentThread().getId() +" ["+this.threadName +"] Ho finito il thread da Runnable!");
		
	}
	
}
