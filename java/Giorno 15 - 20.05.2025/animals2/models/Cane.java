package animals2.models;

import animals2.interfaces.Nuotatore;
import animals2.interfaces.Riportatore;


public class Cane extends Mammifero implements Nuotatore, Riportatore {

    private String razza;

    public Cane() {
        super();
        this.razza = "Sconosciuta";
    }

    public Cane(String nome, String nomeSpecie, int anni, int durataVitaMassimaAnni, int numeroZampe, String razza) {
        super(nome, nomeSpecie, anni, durataVitaMassimaAnni, numeroZampe);
        this.razza = razza;
    }


    // Getters
    public String getRazza() { return razza; };


    // Setters
    public void setRazza(String razza) { this.razza = razza; };


    // Override dei metodi astratti
    @Override
    public String emettiVerso() { return "Il cane abbaia."; };

    @Override
    public String muoviti() { return "Il cane corre."; };


    // Override dei metodi concreti
    @Override
    public String mostraInfo() {
        StringBuilder info = new StringBuilder(super.mostraInfo());
        info.append("Razza: ").append(this.razza).append("\n");
        return info.toString();
    }


    // Override dei metodi dell'interfaccia Nuotatore
    @Override
    public String nuota() { return "Il cane nuota."; };

    @Override
    public String nuota(int distanza) { return "Il cane nuota per " + distanza + " metri."; };


    // Override dei metodi dell'interfaccia Riportatore
    @Override
    public String riportaOggetto() { return "Il cane riporta l'oggetto."; };

    @Override
    public String riportaOggetto(String oggetto) { return "Il cane riporta " + oggetto + "."; };

    @Override
    public String riportaOggetto(String oggetto, String luogo) { return "Il cane riporta " + oggetto + " al " + luogo + "."; };

}
