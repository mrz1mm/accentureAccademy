package animals.models.animals;

import animals.bases.AbstractAnimale;
import animals.interfaces.Nuotatore;
import animals.interfaces.Riportatore;

public class Cane extends AbstractAnimale implements Nuotatore, Riportatore {

    private String razza;

    public Cane() {
        super();
        this.razza = "Cane";
    }

    public Cane(String nome, int eta, String razza) {
        super(nome, eta);
        this.razza = razza;
    }


    // Getters
    public String getRazza() {
        return razza;
    }


    // Setters
    public void setRazza(String razza) {
        this.razza = razza;
    }


    // Metodi
    @Override
    public String emettiVerso() {
        return "Il cane abbaia.";
    }

    @Override
    public String muoviti() {
        return "Il cane corre.";
    }

    @Override
    public String mostraInfo() {
        StringBuilder info = new StringBuilder(super.mostraInfo());
        info.append("Razza: ").append(this.razza).append("\n");
        info.append("----------------------------------------\n");
        return info.toString();
    }
    
    // Implementazione Nuotatore
    @Override
    public String nuota() {
        return "Il cane nuota.";
    }

    // Implementazione Riportatore
    @Override
    public String riportaOggetto() {
        return "Il cane riporta l'oggetto.";
    }

    @Override
    public String riportaOggetto(String oggetto) {
        return "Il cane riporta l'oggetto: " + oggetto;
    }

    @Override
    public String riportaOggetto(String oggetto, String luogo) {
        return "Il cane riporta l'oggetto: " + oggetto + " al luogo: " + luogo;
    }



}
