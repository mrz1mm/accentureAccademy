package corsojava.threads;

public class TestThread {

	public static void main(String[] args) {

		Thread t1 = new MyThread("Thread UNO!");
		Thread t2 = new MyThread("Thread DUE!");

		Thread t3 = new Thread(new MyRunnable("RUN!"));
		
		
		
        //################################################################
        //################################################################
		
        // così facendo, abbiamo creato una classe che:
		// 1. non estende nulla 
		// 2. implementa l'interfaccia Runnable
		// 3. ha il codice del metodo run() già implementato  
		// 4. la istanzio 
		// 5. ritorno l'istanza a runLambda
        Runnable runLambda = () -> {

    		System.out.println(Thread.currentThread().getId() + "[LAMBDA] Sto eseguendo il thread da Runnable:");    		
    		Long milliSec = Math.round(Math.random()*3000)+1000; // numeri fra 1000.0001 e 3999.99
    		 
    	 	System.out.println(Thread.currentThread().getId() +"[LAMBDA] pausa di "+milliSec+" millisecondi...");
    		try {
    			Thread.sleep(milliSec); 
    			 
    		} catch (InterruptedException e) {
    			System.out.println(Thread.currentThread().getId() +"[LAMBDA] interrotto!");
    			return;
    		} 
    		if (milliSec > 2000) Thread.currentThread().interrupt();
    		
    		System.out.println(Thread.currentThread().getId() +"[LAMBDA] Ho finito il thread da Runnable!");
        };
        Thread t4 = new Thread(runLambda);
        
        //################################################################
        
        //                           EQUIVALE A:                 
        
        //################################################################
         
        
        // - aver creato la classe MyRunnableLambda
        // - ed averla istanziate
        Runnable myRunLambda = new MyRunnableLambda();        
        Thread t5 = new Thread(myRunLambda);
        
        //################################################################
        //################################################################
		 
        /* oppure senza creare variabili  */
        
        Thread t6 = new Thread( () -> {
    		System.out.println(Thread.currentThread().getId() +" [LAMBDA6] Sto eseguendo il thread da Runnable:");    		
    		Long milliSec = Math.round(Math.random()*3000)+1000; // numeri fra 1000.0001 e 3999.99
    		 
    	 	System.out.println(Thread.currentThread().getId() +" [LAMBDA6] pausa di "+milliSec+" millisecondi...");
    		try {
    			Thread.sleep(milliSec); 
    			 
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		} 
    		
    		
    		
    		System.out.println(Thread.currentThread().getId() + " [LAMBDA6] Ho finito il thread da Runnable!");
        });
	    // }).start();
         
          
        Long startTime = System.currentTimeMillis();
        
		t1.start();
		
		t2.start();
		
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		 
		try {

			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
			t6.join();
			
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
		Long stopTime = System.currentTimeMillis();
		System.out.println( "Ho finito il processo principale!, tempo impiegato: " +(stopTime-startTime)+" millisecondi.");
	}

}
