package corsojava.veicolo;

// QUALSIASI CLASSE ESTENDE Object	
public class Veicolo  {
	private String marca;
	private String modello;
	private int numRuote; 
 
	public Veicolo() { 
		super();
		this.marca = "N/A";
		this.modello = "N/A";
		this.numRuote = 0;
	}  
	public Veicolo(String marca, String modello, int numRuote) {
		super();
		this.marca = marca;
		this.modello = modello;
		this.numRuote = numRuote;
	} 
	
	
	
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public int getNumRuote() {
		return numRuote;
	}
	public void setNumRuote(int numRuote) {
		this.numRuote = numRuote;
	}
 
	@Override
	public String toString() {
		return "marca=" + marca + ", modello=" + modello + ", numRuote=" + numRuote;
	}
	
	
	
	
	
}
