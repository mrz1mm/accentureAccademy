package animals2.models;

import animals2.bases.Animale;

public class Mammifero extends Animale {

    private int numeroZampe;

    public Mammifero() {
        super();
        this.numeroZampe = 0;
    }

    public Mammifero(String nome, String nomeSpecie, int anni, int durataVitaMassimaAnni, int numeroZampe) {
        super(nome, nomeSpecie, anni, durataVitaMassimaAnni);
        this.numeroZampe = numeroZampe;
    }


    // Getters
    public int getNumeroZampe() { return numeroZampe;};


    // Setters
    public void setNumeroZampe(int numeroZampe) { this.numeroZampe = numeroZampe;};


    // Override dei metodi astratti
    @Override
    public String emettiVerso() { return "Il mammifero emette un verso."; };

    @Override
    public String muoviti() { return "Il mammifero si muove."; };


    // Override dei metodi concreti
    @Override
    public String mostraInfo() {
        StringBuilder info = new StringBuilder(super.mostraInfo());
        info.append("Numero Zampe: ").append(this.numeroZampe).append("\n");
        return info.toString();
    }


    // Metodi specifici dei mammiferi
    public String allatta() { return "Il mammifero allatta i suoi piccoli."; };
    public String allatta(int numeroPiccoli) { return "Il mammifero allatta " + numeroPiccoli + " piccoli."; };

    public String partorisce() { return "Il mammifero partorisce."; };
    public String partorisce(int numeroPiccoli) { return "Il mammifero partorisce " + numeroPiccoli + " piccoli."; };
    
    public String accudisce() { return "Il mammifero si prende cura dei suoi piccoli."; };
    public String accudisce(int numeroPiccoli) { return "Il mammifero si prende cura di " + numeroPiccoli + " piccoli."; };

}
