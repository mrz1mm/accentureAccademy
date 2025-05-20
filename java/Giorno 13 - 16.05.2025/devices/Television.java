package devices;
public class Television extends ElectronicDevice {
    private String screenType;

    public Television() {
        super();
        this.screenType = "Unknown";
    }

    public Television(String category, String brand, String model, String screenType) {
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

    // Override del metodo toString per includere le informazioni specifiche della televisione
    @Override
    public String toString() {
        return "Television [category=" + getCategory() + ", brand=" + getBrand() + ", model=" + getModel()
                + ", screenType=" + screenType + "]";
    }    
}
