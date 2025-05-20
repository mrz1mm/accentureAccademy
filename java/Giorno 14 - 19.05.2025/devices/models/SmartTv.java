package models;

import interfaces.*;

public class SmartTv extends ElectricalDevice implements Connettibile, Multimediale {
    private String screenType;

    public SmartTv() {
        super();
        this.screenType = "Unknown";
    }

    public SmartTv(String category, String brand, String model, String screenType) {
        super(category, brand, model);
        this.screenType = screenType;
    }


    // Getter
    public String getScreenType() {
        return screenType;
    }


    // Setter
    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }


    // Funzioni
    public void mostraInfo(SmartTv television) {
        super.mostraInfo();
        System.out.println("Screen type: " + this.screenType);
    }

    @Override
    public void accendi() {
        super.accendi();
        System.out.println("La televisione è accesa.");
    }

    @Override
    public void spegni() {
        super.spegni();
        System.out.println("La televisione è spenta.");
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
