package animals.models.animals;

import animals.bases.AbstractAnimale;
import animals.interfaces.Nuotatore;

public class Pesce extends AbstractAnimale implements Nuotatore {

    private String specie;

    public Pesce() {
        super();
        this.specie = "Pesce";
    }

    public Pesce(String nome, int eta, String specie) {
        super(nome, eta);
        this.specie = specie;
    }


    // Getters
    public String getSpecie() {
        return specie;
    }


    // Setters
    public void setSpecie(String specie) {
        this.specie = specie;
    }


    // Metodi
    @Override
    public String emettiVerso() {
        return "Il pesce non emette suoni.";
    }

    @Override
    public String muoviti() {
        return "Il pesce nuota.";
    }

    @Override
    public String mostraInfo() {
        StringBuilder info = new StringBuilder(super.mostraInfo());
        info.append("Razza: ").append(this.specie).append("\n");
        info.append("----------------------------------------\n");
        return info.toString();
    }

    // Implementazione Nuotatore
    @Override
    public String nuota() {
        return "Il pesce nuota.";
    }

}
