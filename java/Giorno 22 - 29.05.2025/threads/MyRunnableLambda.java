package corsojava.threads;

public class MyRunnableLambda implements Runnable{

	@Override
	public void run() { 
		System.out.println(Thread.currentThread().getId() +" [LAMBDA*] Sto eseguendo il thread da Runnable:");    		
		Long milliSec = Math.round(Math.random()*3000)+1000; // numeri fra 1000.0001 e 3999.99
		 
	 	System.out.println(Thread.currentThread().getId() +" [LAMBDA*] pausa di "+milliSec+" millisecondi...");
		try {
			Thread.sleep(milliSec); 
			 
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		System.out.println(Thread.currentThread().getId() +" [LAMBDA*] Ho finito il thread da Runnable!");
	}

}
