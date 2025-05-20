package corsojava.veicolo;

public class TestVeicolo {

	public static void main(String[] args) { 

		Veicolo v = new Veicolo("Veicolo","Generico",1);
		
		Automobile a = new Automobile("Alfa","Duetto",2);
	
		
		
	    System.out.println(a);
	    
		Automobile b = new Automobile();
		
		System.out.println(b+" "+a);
		
		
		Object o = new Object();
	 
		
		String s;
		
		System.out.println(v);
		
		System.out.println(o);
		
		TestVeicolo t = new TestVeicolo();
		

		System.out.println(t);
		
	}	 
	

}
