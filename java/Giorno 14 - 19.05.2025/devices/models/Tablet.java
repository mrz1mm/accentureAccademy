package models;

import interfaces.Connettibile;
import interfaces.Multimediale;

public class Tablet extends ElectricalDevice implements Connettibile, Multimediale {

    private String pollici;

    public Tablet() {
        super();
        this.pollici = "N/A";
    }

    public Tablet(String category, String brand, String model, String pollici) {
        super(category, brand, model);
        this.pollici = pollici;
    }


    // Getter
    public String getPollici() {
        return pollici;
    }


    // Setter
    public void setPollici(String pollici) {
        this.pollici = pollici;
    }


    // Funzioni
    @Override
    public void mostraInfo() {
        super.mostraInfo();
        System.out.println("Pollici: " + this.pollici);
    }
    
    // Funzioni - Connettibile
    @Override
    public void connettiAInternet() {
        System.out.println("Connessione a Internet stabilita.");
    }

    @Override
    public void disconnettiDaInternet() {
        System.out.println("Disconnessione da Internet.");
    }

    // Funzioni - Multimediale
    @Override
    public void riproduciMedia() {
        System.out.println("Riproduzione di un file multimediale.");
    }

    @Override
    public void fermaRiproduzione() {
        System.out.println("Riproduzione fermata.");
    }
}
