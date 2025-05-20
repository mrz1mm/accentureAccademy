package devices;
public class Smartphone extends ElectronicDevice {
    private String operatingSystem;

    public Smartphone() {
        super();
        this.operatingSystem = "Unknown";
    }

    public Smartphone(String category, String brand, String model, String operatingSystem) {
        super(category, brand, model);
        this.operatingSystem = operatingSystem;
    }

    // Getter
    public String getOperatingSystem() {
        return operatingSystem;
    }

    // Setter
    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    // Override del metodo toString per includere le informazioni specifiche dello smartphone
    @Override
    public String toString() {
        return "Smartphone [category=" + getCategory() + ", brand=" + getBrand() + ", model=" + getModel()
                + ", operatingSystem=" + operatingSystem + "]";
    }    
}
