package animals.models.animals;

import animals.bases.AbstractAnimale;
import animals.interfaces.Volante;

public class Uccello extends AbstractAnimale implements Volante {

    private String specie;

    public Uccello() {
        super();
        this.specie = "Uccello";
    }

    public Uccello(String nome, int eta, String specie) {
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
        return "L'uccello cinguetta.";
    }

    @Override
    public String muoviti() {
        return "L'uccello vola.";
    }

    @Override
    public String mostraInfo() {
        StringBuilder info = new StringBuilder(super.mostraInfo());
        info.append("Razza: ").append(this.specie).append("\n");
        info.append("----------------------------------------\n");
        return info.toString();
    }

    // Implementazione Volante
    @Override
    public String vola() {
        return "L'uccello vola.";
    }

    @Override
    public String decolla() {
        return "L'uccello decolla.";
    }

    @Override
    public String atterra() {
        return "L'uccello atterra.";
    }

    @Override
    public String plana() {
        return "L'uccello plana.";
    }

}
