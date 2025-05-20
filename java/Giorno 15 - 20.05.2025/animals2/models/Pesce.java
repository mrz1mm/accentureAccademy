package animals2.models;

import animals2.bases.Animale;
import animals2.interfaces.Nuotatore;

public class Pesce extends Animale implements Nuotatore {

    private String tipoAcquaHabitat;

    public Pesce() {
        super();
        this.tipoAcquaHabitat = "Sconosciuto";
    }

    public Pesce(String nome, String nomeSpecie, int anni, int durataVitaMassimaAnni, String tipoAcquaHabitat) {
        super(nome, nomeSpecie, anni, durataVitaMassimaAnni);
        this.tipoAcquaHabitat = tipoAcquaHabitat;
    }

    
    // Getters
    public String getTipoAcquaHabitat() { return tipoAcquaHabitat; };


    // Setters
    public void setTipoAcquaHabitat(String tipoAcquaHabitat) { this.tipoAcquaHabitat = tipoAcquaHabitat; };


    // Override dei metodi astratti
    @Override
    public String emettiVerso() { return "Il pesce non emette suoni."; };

    @Override
    public String muoviti() { return "Il pesce nuota."; };


    // Override dei metodi concreti
    @Override
    public String mostraInfo() {
        StringBuilder info = new StringBuilder(super.mostraInfo());
        info.append("Tipo Acqua Habitat: ").append(this.tipoAcquaHabitat).append("\n");
        return info.toString();
    }


    // Override dei metodi dell'interfaccia Nuotatore
    @Override
    public String nuota() { return "Il pesce nuota."; };

    @Override
    public String nuota(int distanza) { return "Il pesce nuota per " + distanza + " metri."; };

}
