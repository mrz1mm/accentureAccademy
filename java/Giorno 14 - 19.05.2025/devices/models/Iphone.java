package models;

public class Iphone extends Smartphone {
    private String iosVersion;

    public Iphone() {
        super();
        this.iosVersion = "N/A";
    }

    public Iphone(String category, String brand, String model, String display, String iosVersion) {
        super(category, brand, model, display);
        this.iosVersion = iosVersion;
    }


    // Getter
    public String getiosVersion() {
        return iosVersion;
    }


    // Setter
    public void setiosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }


    // Funzioni
    public void mostraInfo(Iphone iphone) {
        super.mostraInfo();
        System.out.println("iOS version: " + this.iosVersion);
    }

    @Override
    public void accendi() {
        super.accendi();
        System.out.println("L'iPhone è acceso.");
    }

    @Override
    public void spegni() {
        super.spegni();
        System.out.println("L'iPhone è spento.");
    }

}
