package corsojava.threads;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks. Lock;

public class Risorsa { 
	
	private final Lock lock ;
	
	public String name;
	
	public Risorsa(String name) {
		super();
		this.name = name;
		lock = new ReentrantLock();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void lock() {
		lock.lock();		
		// basta aggiungere un overhead nel lock per far sì che
		// TestDeadLock vada in lock
		 try {
			Thread.sleep(1);
		} catch (InterruptedException e) {  
		}
	}
	public void unlock() {
		lock.unlock();		
	}
	public boolean tryLock() {
		boolean b = lock.tryLock();		
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
		// prova a bloccare se ce la fa ritorna true else false
		return b;
	}
	
	public void execWork(String operazione, int decimi) {
		System.out.println(""+operazione+" - "+ name + " per " + decimi +" decimi di sec.");
		try {
			Thread.sleep(decimi*100);
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
	}
}