import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UniversitaServizioImpl implements UniversitaServizio {

    @Override
    public List<Studente> caricaDatiStudenti() {
        return Arrays.asList(
            new Studente("Anna", 21, Arrays.asList(
                new Esame("Matematica", 28),
                new Esame("Fisica", 30),
                new Esame("Chimica", 26)  // Media Anna: (28+30+26)/3 = 84/3 = 28.0
            )),
            new Studente("Luca", 22, Arrays.asList(
                new Esame("Matematica", 25),
                new Esame("Fisica", 22)   // Media Luca: (25+22)/2 = 47/2 = 23.5
            )),
            new Studente("Marco", 23, Arrays.asList(
                new Esame("Matematica", 30),
                new Esame("Fisica", 29),
                new Esame("Informatica", 30) // Media Marco: (30+29+30)/3 = 89/3 = 29.67
            )),
            new Studente("Giulia", 20, Arrays.asList(
                new Esame("Informatica", 27),
                new Esame("Fisica", 28)   // Media Giulia: (27+28)/2 = 55/2 = 27.5
            )),
            new Studente("Paolo", 24, Arrays.asList( // Studente che non segue "Matematica"
                new Esame("Storia", 25),
                new Esame("Filosofia", 28)
            ))
        );
    }

    @Override
    public List<Studente> filtraEOrdinaStudentiPerCorsoEMedia(List<Studente> studenti, String corsoSeguito, double sogliaVoto) {
        if (studenti == null)
            throw new IllegalArgumentException("La lista degli studenti non può essere nulla.");
        
        if (corsoSeguito == null || corsoSeguito.isEmpty())
            throw new IllegalArgumentException("Il nome del corso non può essere nullo o vuoto.");
        
        if (sogliaVoto < 0 || sogliaVoto > 30)
            throw new IllegalArgumentException("La soglia del voto deve essere compresa tra 0 e 30.");
        
        return studenti.stream()
            .filter(studente -> studente.segueCorso(corsoSeguito))
            .filter(studente -> studente.calcolaMediaEsami() >= sogliaVoto)
            .sorted(Comparator.comparingDouble(Studente::calcolaMediaEsami).reversed())
            .collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> calcolaMediaVotiPerCorsoGlobale(List<Studente> studenti) {
        if (studenti == null) 
            throw new IllegalArgumentException("La lista degli studenti non può essere nulla.");
        
        if (studenti.isEmpty()) 
            throw new IllegalArgumentException("La lista degli studenti non può essere vuota.");
        
        return studenti.stream()
            .flatMap(studente -> studente.getEsami().stream())
            .collect(Collectors.groupingBy(
                Esame::getNomeCorso,
                Collectors.averagingInt(Esame::getVoto)
            ));
    }

}