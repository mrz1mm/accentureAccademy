package corsojava.veicolo;

public class Automobile extends Veicolo {

	private int numPorte;
	 

	public Automobile() { 
		super();
		setNumRuote(4);
		this.numPorte = -1;
	}

	public Automobile(String marca, String modello,  int numPorte) { 
		super(marca,modello,4);
		// super();
		this.numPorte = numPorte;
	}

	public int getNumPorte() {
		return numPorte;
	}

	public void setNumPorte(int numPorte) {
		this.numPorte = numPorte;
	}
  
	
    @Override
	public String toString() {
    	String s1 = ", numPorte: "+numPorte;
		return super.toString() + s1;
	}
	
	
	
	
	
	
}
