package models;
import bases.DispositivoIntelligente;

public class ElectricalDevice extends DispositivoIntelligente {
    private String category; // Frullatore
    private String brand; // Philips
    private String model; // HR1863/00
    private boolean isOn = false;

    // Costruttore vuoto
    public ElectricalDevice() {
        super();
        this.category = "N/A";
        this.brand = "N/A";
        this.model = "N/A";
    }

    // Costruttore con parametri
    public ElectricalDevice(String category, String brand, String model) {
        super();
        this.category = category;
        this.brand = brand;
        this.model = model;
    }

    
    // Getter
    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }


    // Setter
    public void setCategory(String category) {
        this.category = category;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }


    // Funzioni
    public void accendi() {
        isOn = true;
        System.out.println("Il dispositivo " + category + " è acceso.");
    }

    public void spegni() {
        isOn = false;
        System.out.println("Il dispositivo " + category + " è spento.");
    }


    public void mostraInfo() {
        System.out.println("Sistema Operativo: " + getSistemaOperativo());
        System.out.println("Categoria: " + category);
        System.out.println("Marca: " + brand);
        System.out.println("Modello: " + model);
        System.out.println("Stato: " + (isOn ? "Acceso" : "Spento"));
    }



}

