public class Esame {

    private final String nomeCorso;
    private final int voto;


    private static final int VOTO_MINIMO = 0;
    private static final int VOTO_MASSIMO = 31;

    public Esame() {
        super();
        this.nomeCorso = "N/A";
        this.voto = 0;

    }

    public Esame(String nomeCorso, int voto) {
        super();
        if (nomeCorso == null || nomeCorso.isEmpty()) {
            throw new IllegalArgumentException("Il nome del corso non può essere nullo o vuoto.");
        }

        if (voto < VOTO_MINIMO || voto > VOTO_MASSIMO) {
            throw new IllegalArgumentException("Il voto deve essere compreso tra 0 e 31 dove 31 rappresenta il 30 e lode.");
        }
        this.nomeCorso = nomeCorso;
        this.voto = voto;
    }


    // Getters
    public String getNomeCorso() { return nomeCorso; }
    public int getVoto() { return voto; }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Esame: ").append(nomeCorso)
          .append(", Voto: ").append(voto);
        return sb.toString();
    }

}
