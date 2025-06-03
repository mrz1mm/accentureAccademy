package corsojava.threads;
 

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
public class Counter {
	
	private Integer counter=0; 
	
	Lock lockObj = new ReentrantLock(); 
	
	// sincronizzazione con blocco di Codice
	public void incrementCode() {	 
		// codice non sincronizzato
		synchronized (this) {
			// codice sincronizzato
			this.counter++;			
		}
		// codice non sincronizzato
	}
	
	
	// sincronizzaione diretta
	public synchronized  void incrementDirect() {
		// tutto il codice del metodo è sincronizzato
			this.counter++;		
	}

	// la sincronizzazione avviene esplicitamente	
	public  void increment() {	
		//codice non sincronizzato
		
		lockObj.lock();
		// da qui in poi il codice è sincronizzato
		this.counter++; 
		lockObj.unlock();
		// da qui in poi il codice non è sincronizzato		
	}
	
	
	
	public Integer getCounter() {
		return counter;
	}

}
