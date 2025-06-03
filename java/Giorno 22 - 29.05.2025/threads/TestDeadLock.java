package corsojava.threads;

public class TestDeadLock {


	public static void main(String[] args) throws InterruptedException { 
/*
 
         resA, resB
  		
  		T1 - blocca resA   		
  		T2 - blocca resB
  		T1 - aspetta rilascio resB da T2
  		T2 - aspetta rilascio resA da T1
 */
		
		final Risorsa resA = new Risorsa("ResA");
		final Risorsa resB = new Risorsa("ResB");
		
		Runnable r1 = () -> {
			System.out.println("Run1 - Inizio Run");
			resA.lock(); System.out.println("Run1 - ResA bloccata");
			resB.lock(); System.out.println("Run1 - ResB bloccata");
			resA.execWork("[Run1] - 5 dec", 20);			
			resB.execWork("[Run1] - 5 ec", 20);
			resA.unlock(); System.out.println("Run1 - ResA sbloccata");;
			resB.unlock(); System.out.println("Run1 - ResB sbloccata");;
			System.out.println("Run1 - Fine Run");
		};
		
		
		Runnable r2 = () -> {
			System.out.println("Run2 - Inizio Run");
			resB.lock(); System.out.println("Run2 - ResB bloccata");			
			resA.lock(); System.out.println("Run2 - ResA bloccata");
			
			resA.execWork("[Run2] - 5 dec", 20);			
			resB.execWork("[Run2] - 5 ec", 20);
			resA.unlock(); System.out.println("Run2 - ResA sbloccata");;
			resB.unlock(); System.out.println("Run2 - ResB sbloccata");; 
			
			System.out.println("Run2 - Fine Run");
		};
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		System.out.println("F");
		
	}
}
