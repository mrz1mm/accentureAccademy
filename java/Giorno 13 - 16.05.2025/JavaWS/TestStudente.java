package corsojava;

public class TestStudente {

	public static void main(String[] args) { 
		
		
		

		System.out.println("nomescuola: " + Studente.nomeScuola);
		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println("TestStudente!!!!");	
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
		
		
			
		Studente s3 = new Studente();
		
		
		Studente s4 = new Studente(20,"Luigi");
		
		s4.mostraInfo();
		
		s3.mostraInfo();
		
		
		Studente s5 = new Studente("Vincenzo","Errante",28);
		
		s5.mostraInfo();
		

		System.out.println("s5.nomescuola: " + s5.nomeScuola);
	}

}
