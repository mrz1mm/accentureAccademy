package models;

import interfaces.*;

public class Smartphone extends ElectricalDevice implements Connettibile, Multimediale {
    private String display;

    public Smartphone() {
        super();
        this.display = "Unknown";
    }

    public Smartphone(String category, String brand, String model, String display) {
        super(category, brand, model);
        this.display = display;
    }


    // Getter
    public String getDisplay() {
        return display;
    }


    // Setter
    public void setDisplay(String display) {
        this.display = display;
    }


    // Funzioni
    public void mostraInfo(Smartphone smartphone) {
        super.mostraInfo();
        System.out.println("Display: " + this.display);
    }

    @Override
    public void accendi() {
        super.accendi();
        System.out.println("Lo smartphone è acceso.");
    }

    @Override
    public void spegni() {
        super.spegni();
        System.out.println("Lo smartphone è spento.");
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
