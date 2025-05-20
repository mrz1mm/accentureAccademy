package animals2.models;

import animals2.bases.Animale;
import animals2.interfaces.Volante;

public class Uccello extends Animale implements Volante {

    private double aperturaAlareCm;

    public Uccello() {
        super();
        this.aperturaAlareCm = 0;
    }

    public Uccello(String nome, String nomeSpecie, int anni, int durataVitaMassimaAnni, double aperturaAlareCm) {
        super(nome, nomeSpecie, anni, durataVitaMassimaAnni);
        this.aperturaAlareCm = aperturaAlareCm;
    }


    // Getters
    public double getAperturaAlareCm() { return aperturaAlareCm; };


    // Setters
    public void setAperturaAlareCm(double aperturaAlareCm) { this.aperturaAlareCm = aperturaAlareCm; };


    // Override dei metodi astratti
    @Override
    public String emettiVerso() { return "L'uccello cinguetta."; };

    @Override
    public String muoviti() { return "L'uccello vola."; };


    // Override dei metodi concreti
    @Override
    public String mostraInfo() {
        StringBuilder info = new StringBuilder(super.mostraInfo());
        info.append("Apertura Alare: ").append(this.aperturaAlareCm).append(" cm").append("\n");
        return info.toString();
    }


    // Override dei metodi dell'interfaccia Volante
    @Override
    public String vola() { return "L'uccello vola."; };
    @Override
    public String vola(int distanza) { return "L'uccello vola per " + distanza + " metri."; };

    @Override
    public String atterra() { return "L'uccello atterra."; };
    @Override
    public String atterra(String luogo) { return "L'uccello atterra a " + luogo + " metri di distanza."; };

    @Override
    public String decolla() { return "L'uccello decolla."; };
    @Override
    public String decolla(String luogo) { return "L'uccello decolla da " + luogo + "."; };

    @Override
    public String plana() { return "L'uccello plana."; };
    @Override
    public String plana(int distanza) { return "L'uccello plana per " + distanza + " metri."; };

}
