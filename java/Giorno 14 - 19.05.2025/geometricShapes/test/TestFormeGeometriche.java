public class TestFormeGeometriche {

    public static void main(String[] args) {

        // utilizzo del polimorfismo per gestire gli oggetti tramite riferimenti di tipo FormaGeometrica
        FormaGeometrica[] forme = new FormaGeometrica[3];

        try {
            forme[0] = new Cerchio("Cerchione", "Rosso", 5.0);
            forme[1] = new Rettangolo("Rettangolone", "Verde", 4.0, 7.0);
            forme[2] = new Triangolo("Triangolone", "Blu", 3.0, 4.0, 5.0);
        } catch (IllegalArgumentException e) {
            System.err.println("Errore durante la creazione di una forma: " + e.getMessage());
            return;
        }

        System.out.println("===== INIZIO TEST =====");
        for (FormaGeometrica forma : forme) {
            System.out.println("\n-----------------------------------");
            // casting 
            if (forma instanceof Figura) {
                Figura figuraConcreta = (Figura) forma;
                figuraConcreta.mostraInfo();
            } else {
                System.out.println("Errore, forma non riconosciuta come Figura.");
            }
        }
        System.out.println("\n-----------------------------------");
        System.out.println("===== FINE TEST =====");
    }
}