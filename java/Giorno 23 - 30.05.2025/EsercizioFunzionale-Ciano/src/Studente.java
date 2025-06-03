
import java.util.List;
import java.util.OptionalDouble;

public class Studente {

    private String nome;
    private int eta;
    private List<Esame> esami;

    public Studente() {
        super();
        this.nome = "N/A";
        this.eta = 0;
        this.esami = null;
    }

    public Studente(String nome, int eta, List<Esame> esami) {
        super();
        if (nome == null) {
            throw new IllegalArgumentException("Il nome non può essere nullo.");
        }

        if (nome.isEmpty()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto.");
        }

        if (eta <= 0) {
            throw new IllegalArgumentException("L'età non può essere negativa.");
        }
        this.nome = nome;
        this.eta = eta;
        this.esami = esami;          
    }


    // Getters
    public String getNome() { return nome; }
    public int getEta() { return eta; }
    public List<Esame> getEsami() { return esami; }


    // Setters
    public void setNome(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Il nome non può essere nullo.");
        }
        if (nome.isEmpty()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto.");
        }
        this.nome = nome;
    }

    public void setEta(int eta) {
        if (eta <= 0) {
            throw new IllegalArgumentException("L'età non può essere negativa.");
        }
        this.eta = eta;
    }

    public void setEsami(List<Esame> esami) {
        this.esami = esami;
    }

    public boolean segueCorso(String nomeCorso) {
        if (nomeCorso == null) {
            throw new IllegalArgumentException("Il nome del corso non può essere nullo.");
        }

        if (nomeCorso.isEmpty()) {
            throw new IllegalArgumentException("Il nome del corso non può essere vuoto.");
        }

        if (esami == null || esami.isEmpty()) {
            throw new IllegalArgumentException("La lista degli esami non può essere nulla o vuota.");
        }

        return esami.stream()
                .anyMatch(esame -> esame.getNomeCorso().equalsIgnoreCase(nomeCorso));
    }

    public double calcolaMediaEsami() {
        if (esami == null) {
            throw new IllegalArgumentException("La lista degli esami non può essere nulla.");
        }

        if (esami.isEmpty()) {
            throw new IllegalArgumentException("La lista degli esami non può essere vuota.");
        }

        OptionalDouble average = esami.stream()
                .mapToInt(Esame::getVoto)
                .average();
        
        return average.orElse(0.0);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Studente: ").append(nome)
          .append(", Età: ").append(eta)
          .append(", Esami: ");

        if (esami == null || esami.isEmpty()) {
            sb.append("Nessun esame registrato.");
        } else {
            for (Esame esame : esami) {
                sb.append("\n  - ").append(esame.getNomeCorso())
                  .append(" (Voto: ").append(esame.getVoto()).append(")");
            }
        }
        return sb.toString();
    }

}
