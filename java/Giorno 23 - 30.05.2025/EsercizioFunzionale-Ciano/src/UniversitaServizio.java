import java.util.List;
import java.util.Map;

public interface UniversitaServizio {
    List<Studente> caricaDatiStudenti();
    List<Studente> filtraEOrdinaStudentiPerCorsoEMedia(List<Studente> studenti, String corsoSeguito, double sogliaMediaStudente);
    Map<String, Double> calcolaMediaVotiPerCorsoGlobale(List<Studente> studenti);
}