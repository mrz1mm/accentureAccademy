package animals2.models;

import animals2.bases.Animale;

public class Zoo {

    private Animale[] animali;
    private int numeroAnimali;
    private static final int CAPACITA_DEFAULT = 10;

    public Zoo() {
        this(CAPACITA_DEFAULT);
    }

    public Zoo(int capacita) {
        if (capacita <= 0) {
            System.err.println("Errore: La capacità dello zoo deve essere positiva. Impostata capacità di default settata a " + CAPACITA_DEFAULT + ".");
            this.animali = new Animale[CAPACITA_DEFAULT];
        } else {
            this.animali = new Animale[capacita];
        }
        this.numeroAnimali = 0;
    }


    // Getters
    public Animale[] getAnimali() { return animali; };
    public int getNumeroAnimali() { return numeroAnimali; };
    public int getCapacita() { return animali.length; };


    // Metodi concreti
    public void aggiungiAnimale(Animale animale) {
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
            return "Lo zoo è vuoto, non ci sono animali.";
        }
        StringBuilder output = new StringBuilder("--- Animali in Movimento ---\n");
        for (int i = 0; i < numeroAnimali; i++) {
            output.append(animali[i].muoviti())
                  .append("\n");
        }
        return output.toString();
    }

    public String mostraInfoAnimali() {
        if (numeroAnimali == 0) {
            return "Lo zoo è vuoto, non ci sono animali.";
        }
        StringBuilder output = new StringBuilder("--- Info Animali nello Zoo ---\n");
        for (int i = 0; i < numeroAnimali; i++) {
            output.append(animali[i].mostraInfo())
                  .append("\n");
        }
        return output.toString();
    }

    public void svuotaZoo() {
        for (int i = 0; i < numeroAnimali; i++) {
                animali[i] = null; // Aiuta il garbage collector
            }
        this.numeroAnimali = 0;
    }

    public String getAnimalePiuLongevo() {
        if (numeroAnimali == 0) {
            return "Lo zoo è vuoto, non ci sono animali.";
        }

        Animale piuLongevo = animali[0];
        for (int i = 1; i < numeroAnimali; i++) {
            if (animali[i].getDurataVitaMassimaAnni() > piuLongevo.getDurataVitaMassimaAnni()) {
                piuLongevo = animali[i];
            }
        }        
        return "L'animale più longevo è: " + piuLongevo.getNome() + ", di specie " + piuLongevo.getNomeSpecie() + ", con " + piuLongevo.getAnni() + " anni.";
    }   

}
