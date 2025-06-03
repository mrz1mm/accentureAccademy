package corsojava.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TestCounter {

	public static void main(String[] args) throws InterruptedException { 
		 
		final Counter counter = new Counter();
		
		final List<Integer> list = new Vector<>();
		
		final List<Integer> listNotSync = new ArrayList<>();
		
		// final
			
		Runnable r =  () -> {
			for (int i=0; i<1000;i++) {
				counter.increment();
			}			
		}; 
		
		Runnable runList =  () -> {
			for (Integer i=0; i<1000;i++) {				
					list.add(i);					
			}			
		}; 
		
		
		
		Thread[] t = new Thread[20];
		for (int i=0; i<t.length; i++) {
			t[i] = new Thread(runList);
			t[i].start();
		}
		for (int i=0; i<t.length; i++) {		
			t[i].join();
		}

		System.out.println("Count<List>: "+list.size());
		
		Thread t1 = new Thread( () -> {
			// accedono SOLO ALLE variabili FINAL dell'enclosing scope
			for (int i=0; i<10000;i++) {
				counter.increment();
			}
			
		}); 
		Thread t2 = new Thread( () -> {
			
			for (int i=0; i<10000;i++) {
				counter.increment();
			}
			
		});
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
	 	System.out.println("Count: "+counter.getCounter());

	}

}
