package corsojava.threads;

public class TestTryLock {


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
			while (true) {
				if (resA.tryLock()) { 
					System.out.println("Run1 - ResA bloccata");
					if (resB.tryLock()) {
					    System.out.println("Run1 - ResB bloccata");
						resA.execWork("Run1", 2);			
						resB.execWork("Run1", 2);
						resA.unlock(); System.out.println("Run1 - ResA sbloccata");
						resB.unlock(); System.out.println("Run1 - ResB sbloccata");
						break;
					} else {

						resA.unlock(); System.out.println("Run1 - ResB Impossibile bloccare, ResA sbloccata");
						 
					}					
				}
			//	try { Thread.sleep( 5); } catch (InterruptedException e) {}
			}
			System.out.println("Run1 - Fine Run");
		};
		
		
		Runnable r2 = () -> {
			System.out.println("Run2 - Inizio Run");
			while (true) {
				if (resB.tryLock()) { 
					System.out.println("Run2 - ResB bloccata");
					if (resA.tryLock()) {
					    System.out.println("Run2 - ResA bloccata");
						resA.execWork("Run2", 2);			
						resB.execWork("Run2", 2);
						resA.unlock(); System.out.println("Run2 - ResA sbloccata");
						resB.unlock(); System.out.println("Run2 - ResB sbloccata");
						break;
					} else {

						resB.unlock(); System.out.println("Run2 - ResA Impossibile bloccare, ResB sbloccata"); 
					}					
				}
			//	try { Thread.sleep( 4 ); } catch (InterruptedException e) {}
			}
			System.out.println("Run2 - Fine Run");
		};
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		System.out.println("Fine programma!");
		
	}
}
