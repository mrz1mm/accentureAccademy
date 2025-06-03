import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        UniversitaServizio servizio = new UniversitaServizioImpl();

        // 1. Carica studenti
        List<Studente> tuttiStudenti = servizio.caricaDatiStudenti();
        System.out.println("--- Tutti gli Studenti Caricati ---");
        tuttiStudenti.forEach(s -> System.out.println(s.getNome() + " - Media: " + String.format("%.2f", s.calcolaMediaEsami()) +
                                                      " - Esami: " + s.getEsami()));
        System.out.println("\n--------------------------------------\n");

        // 2. Filtra studenti per corso e media, ordina
        String corsoDaFiltrare = "Matematica";
        double sogliaMedia = 25.0;

        System.out.println("--- Studenti che seguono '" + corsoDaFiltrare +
                           "' con media >= " + sogliaMedia +
                           ", ordinati per media decrescente ---");

        List<Studente> studentiFiltratiOrdinati = servizio.filtraEOrdinaStudentiPerCorsoEMedia(
            tuttiStudenti, corsoDaFiltrare, sogliaMedia
        );

        if (studentiFiltratiOrdinati.isEmpty()) {
            System.out.println("Nessuno studente trovato con i criteri specificati.");
        } else {
            studentiFiltratiOrdinati.forEach(stud ->
                    System.out.println(stud.getNome() + " - Media Generale: " + String.format("%.2f", stud.calcolaMediaEsami()))
            );
        }
        System.out.println("\n--------------------------------------\n");



        // 3. Crea mappa corso -> media complessiva voti
        System.out.println("--- Media Complessiva dei Voti per Ogni Corso ---");
        Map<String, Double> mediePerCorso = servizio.calcolaMediaVotiPerCorsoGlobale(tuttiStudenti);

        if (mediePerCorso.isEmpty()) {
            System.out.println("Nessun corso trovato o nessun esame registrato.");
        } else {
            mediePerCorso.forEach((corso, media) ->
                    System.out.println("Corso: " + corso + " - Media Voti del Corso: " + String.format("%.2f", media))
            );
        }
        System.out.println("\n--------------------------------------\n");
    }
}