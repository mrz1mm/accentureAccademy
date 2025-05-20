package animals.models.zoo;

import animals.bases.AbstractAnimale;

public class Zoo {
    private AbstractAnimale[] animali;
    private int numeroAnimali;
    private static final int CAPACITA_DEFAULT = 10;

    public Zoo() {
        this.animali = null;
        this.numeroAnimali = 0;
    }

    public Zoo(int capacita) {
        if (capacita <= 0) {
            System.err.println("Errore: La capacità dello zoo deve essere positiva. Impostata capacità di default settata a " + CAPACITA_DEFAULT + ".");
            this.animali = new AbstractAnimale[CAPACITA_DEFAULT];
        } else {
            this.animali = new AbstractAnimale[capacita];
        }
        this.numeroAnimali = 0;
    }


    // Getters
    public AbstractAnimale[] getAnimali() {
        return animali;
    }

    public int getNumeroAnimali() {
        return numeroAnimali;
    }


    // Metodi
    public void aggiungiAnimale(AbstractAnimale animale) {
        if (animale == null) {
            System.err.println("Errore: Impossibile aggiungere un animale nullo.");
            return;
        }
        if (numeroAnimali < animali.length) {
            animali[numeroAnimali] = animale;
            numeroAnimali++;
        } else {
            System.err.println("Errore: Lo zoo è pieno! Impossibile aggiungere " + animale.getNome() + ".");
        }
    }

    public String emettiVersiAnimali() {
        if (numeroAnimali == 0) {
            return "Lo zoo è silenzioso, non ci sono animali.";
        }
        StringBuilder output = new StringBuilder("--- Voci dallo Zoo ---\n");
        for (int i = 0; i < numeroAnimali; i++) {
            output.append(animali[i].emettiVerso())
                  .append("\n");
        }
        return output.toString();
    }

    public String muoviAnimali() {
        if (numeroAnimali == 0) {
            return "Nessun animale da far muovere nello zoo.";
        }
        StringBuilder output = new StringBuilder("--- Movimenti nello Zoo ---\n");
        for (int i = 0; i < numeroAnimali; i++) {
            output.append(animali[i].muoviti())
                  .append("\n");
        }
        return output.toString();
    }

    public String mostraInfoAnimali() {
        if (numeroAnimali == 0) {
            return "Lo zoo è attualmente vuoto.";
        }
        StringBuilder output = new StringBuilder("--- Registro Animali dello Zoo ---\n\n");
        for (int i = 0; i < numeroAnimali; i++) {
            output.append(animali[i].mostraInfo());
        }
        return output.toString();
    }

    public void svuotaZoo() {
        this.animali = null;
        this.numeroAnimali = 0;
    }

}
