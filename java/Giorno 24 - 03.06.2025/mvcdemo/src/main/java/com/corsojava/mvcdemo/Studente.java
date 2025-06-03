package com.corsojava.mvcdemo;

public class Studente  {   // come se fosse un nuovo tipo di dati
	 
	// costante
	//public static final String nomeScuola = "ITI Vittorio Emanuele III";
	 public static String nomeScuola = "ITI Vittorio Emanuele III";
	
	private String matricola; 
	private String nome;
	private String cognome; 
	private Integer eta; 
	
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
 

	public Studente(String nome, String cognome, int eta, String matricola) { 
		this.nome = nome;
		this.cognome = cognome;
		this.eta = -1;
		this.setEta(eta);		
		this.matricola = matricola;
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
		System.out.println( toString() + ", Età: " + (this.eta>0 ?  this.eta:"non inserita")); 
	}
	
	public void mostraInfo (String testoIniziale) { 		
		System.out.println( testoIniziale +": ");
		mostraInfo(); 
	}
	
	 

	public String getMatricola() {
		return matricola;
	}


	public void setMatricola(String matricola) {
		this.matricola = matricola;
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
	
 

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String toString() {
		return getNome() + " " + getCognome()+ " ["+getEta()+"]";
	}
 

  
  }
