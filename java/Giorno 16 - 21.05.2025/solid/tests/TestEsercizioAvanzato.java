public class TestEsercizioAvanzato {

    public static void main(String[] args) {
        // Creazione di un GestoreForme
        GestoreForme gestoreForme = new GestoreForme(4);

        // Creazione delle forme geometriche
        Cerchio cerchio = new Cerchio("Cerchio1", "Verde", 5);
        Triangolo triangolo = new Triangolo("Triangolo1", "Rosso", 3, 4, 5);
        Quadrato quadrato = new Quadrato("Quadrato1", "Blu", 4);
        Rettangolo rettangolo = new Rettangolo("Rettangolo1", "Giallo", 4, 6);

        // Aggiunta delle forme al gestore
        gestoreForme.aggiungiFigura(cerchio);
        gestoreForme.aggiungiFigura(triangolo);
        gestoreForme.aggiungiFigura(quadrato);
        gestoreForme.aggiungiFigura(rettangolo);

        // Mostra informazioni su tutte le forme --> metodi specifici utilizzati in GestoreForme
        System.out.println("*******************************************");
        System.out.println(gestoreForme.getNumeroFigure());
        System.out.println("*******************************************");
        System.out.println(gestoreForme.mostraTutteLeFigure());
        System.out.println("*******************************************");
        System.out.println(gestoreForme.calcolaAreaTotale());
        System.out.println("*******************************************");
        System.out.println(gestoreForme.trovaFiguraMassima().mostraInfo());
        System.out.println("*******************************************");

        // Test per verificare l'eccezione di capacità di GestoreForme
        Cerchio quintaFigura = new Cerchio("Cerchio2", "Blu", 3);
        try {
            gestoreForme.aggiungiFigura(quintaFigura);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Errore: " + e.getMessage());
        }

        // Test per verificare che lo zoo si svuoti correttamente
        System.out.println("*******************************************");
        gestoreForme.svuotaGestore();
        System.out.println(gestoreForme.mostraTutteLeFigure());
        System.out.println("*******************************************");
        
        // Test raggio negativo su Cerchio
        try {
            Cerchio cerchioNegativo = new Cerchio("Cerchio3", "Blu", -3);
            System.out.println(cerchioNegativo.mostraInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        }

        // Test lato negativo su Triangolo
        try {
            Triangolo triangoloNegativo = new Triangolo("Triangolo2", "Blu", -3, 4, 5);
            System.out.println(triangoloNegativo.mostraInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        }

        // Test lato negativo su Quadrato
        try {
            Quadrato quadratoNegativo = new Quadrato("Quadrato2", "Blu", -3);
            System.out.println(quadratoNegativo.mostraInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        }

        // Test lato negativo su Rettangolo
        try {
            Rettangolo rettangoloNegativo = new Rettangolo("Rettangolo2", "Blu", -3, 4);
            System.out.println(rettangoloNegativo.mostraInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        }

        // Test raggio uguale a zero su Cerchio
        try {
            Cerchio cerchioUguale = new Cerchio("Cerchio4", "Blu", 0);
            System.out.println(cerchioUguale.mostraInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        }

        // Test lato uguale a zero su Triangolo
        try {
            Triangolo triangoloUguale = new Triangolo("Triangolo4", "Blu", 0, 4, 5);
            System.out.println(triangoloUguale.mostraInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        }

        // Test lato uguale su Quadrato
        try {
            Quadrato quadratoUguale = new Quadrato("Quadrato3", "Blu", 0);
            System.out.println(quadratoUguale.mostraInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        }

        // Test lato uguale su Rettangolo
        try {
            Rettangolo rettangoloUguale = new Rettangolo("Rettangolo3", "Blu", 0, 4);
            System.out.println(rettangoloUguale.mostraInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        }

        // Test lato uguale su Triangolo
        try {
            Triangolo triangoloUguale = new Triangolo("Triangolo3", "Blu", 3, 3, 5);
            System.out.println(triangoloUguale.mostraInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        }

        // Test disuguaglianza triangolare su Triangolo
        try {
            Triangolo triangoloDisuguaglianza = new Triangolo("Triangolo5", "Blu", 1, 2, 3);
            System.out.println(triangoloDisuguaglianza.mostraInfo());
        } catch (IllegalArgumentException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

}