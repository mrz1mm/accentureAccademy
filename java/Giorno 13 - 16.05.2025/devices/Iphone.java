package devices;
public class Iphone extends Smartphone {
    private String model;

    public Iphone() {
        super();
        this.model = "Unknown";
    }

    public Iphone(String category, String brand, String model, String operatingSystem) {
        super(category, brand, model, operatingSystem);
        this.model = model;
    }

    // Getter
    public String getModel() {
        return model;
    }

    // Setter
    public void setModel(String model) {
        this.model = model;
    }

    // Override del metodo toString per includere le informazioni specifiche dell'iPhone
    @Override
    public String toString() {
        return "Iphone [category=" + getCategory() + ", brand=" + getBrand() + ", model=" + getModel()
                + ", operatingSystem=" + getOperatingSystem() + ", model=" + model + "]";
    }
}
