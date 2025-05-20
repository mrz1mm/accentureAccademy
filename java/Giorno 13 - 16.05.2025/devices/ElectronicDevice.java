package devices;

public class ElectronicDevice {
    private String category; // Frullatore
    private String brand; // Philips
    private String model; // HR1863/00
    private boolean isOn = false;

    // Costruttore vuoto
    public ElectronicDevice() {
        super();
        this.category = "N/A";
        this.brand = "N/A";
        this.model = "N/A";
    }

    // Costruttore con parametri
    public ElectronicDevice(String category, String brand, String model) {
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

    // Metodo per accendere/spegnere il dispositivo
    public void toggleIsOn() {
        isOn = !isOn;
        if (isOn) {
            System.out.println("Il dispositivo " + category + " è acceso.");
        } else {
            System.out.println("Il dispositivo " + category + " è spento.");
        }
    }

    // Metodo per mostrare le informazioni del dispositivo
    public String showInfo() {
        return this.toString();
    }

    // Metodo toString
    @Override
    public String toString() {
        return "ElectronicDevice [category=" + category + ", brand=" + brand + ", model=" + model + "]";
    }
}

