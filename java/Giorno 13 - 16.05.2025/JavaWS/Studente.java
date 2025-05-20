package corsojava;

public class Studente {   // come se fosse un nuovo tipo di dati
	 
	// costante
	// public static final String nomeScuola = "ITI Vittorio Emanuele III";
	public static String nomeScuola = "ITI Vittorio Emanuele III";
	
	private String nome;
	private String cognome; 
	private int eta; 
	
/*	
	La prassi è creare almeno 2 costruttori:
	
	1 - costruttore senza parametri
	2 - costruttore con tutti i parametri	
*/	
	public Studente() {  // costruttore
		this.eta = -1;
		this.nome = null;
		this.cognome = null;
	}
	 

	public Studente(String nome, String cognome, int eta) { 
		this.nome = nome;
		this.cognome = cognome;
		this.eta = -1;
		this.setEta(eta);		
	}





/* ------------------ altri costruttori precedenti */
	Studente(int p_eta, String p_nome) {  // costruttore
		this.nome = p_nome;
		this.eta = -1;
		this.setEta(p_eta);		
	
	} 

	
	
	Studente(String p_nome, int p_eta) {  // costruttore
		this.nome = p_nome;
		this.eta = -1;
		this.setEta(p_eta);		
	
	} 
	
	public void mostraInfo() { 		
		System.out.println( nomeCompleto() + ", Età: " + (this.eta>0 ?  this.eta:"non inserita")); 
	}
	
	public String testo(String testoIniziale) {
		String s = testoIniziale + ": " + this.nome +" di "+ this.eta +" anni"; 
		return s;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getEta() {
		return eta;
	}

	public void setEta(int eta) {
		if (eta >= 18) { 
			this.eta = eta;
		}
	}
	
	 
	static int somma (int a, int b) {
		
		return a + b;
		
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String nomeCompleto() {
		return "[" + nomeScuola + "] " + getNome() + " " + this.cognome;
	}
	
	
	// si tende a evitare
	/*
public static void main(String[] args) { 
		
	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	System.out.println("Studente!!!!");
	
	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	
		System.out.println(Math.max(2, 5));
		
		
		System.out.println(Studente.somma(2, 4));
		
		
		
		Studente s1;
		
		Studente[] arr = new Studente[10];
		System.out.println("------------------------");
		System.out.println("Studente[]:");
		for (Studente s : arr ) {
			System.out.println(s);
			// s.mostraInfo();
		}
		
		
		System.out.println("------------------------");
		System.out.println("String[]:");
	    String[] arrS = new String[10];
		
		for (String s : arrS ) {
			System.out.println(s);
		}
		
		s1 = new Studente("Mario Rossi", 16);


		
		//s1.nome = "Mario";
		
		s1.setEta(22);
		
		s1.mostraInfo(); 
		
		
		// Studente s2 = s1;   copia il riferimento all'oggetto non l'oggetto 
		Studente s2 = new Studente("Luigi Bianchi", 23); 				

		s2.setNome("Cosimino");
	 
		s2.mostraInfo(); 

		System.out.println("------------------------");		
		System.out.println("s1.nomescuola: " + s1.nomeScuola);
		System.out.println("s2.nomescuola: " + s1.nomeScuola);
		
		Studente.nomeScuola = "Mamiani";
		System.out.println("------------------------");
		System.out.println("s1.nomescuola: " + s1.nomeScuola);
		System.out.println("s2.nomescuola: " + s2.nomeScuola);
		
	
		
		
		s2.testo("Ecco lo studente");

		String s = s2.testo("Ecco lo studente");
		
		System.out.println(s);
		
		
		
		
		
		
		
	}
	
*/	
  }
