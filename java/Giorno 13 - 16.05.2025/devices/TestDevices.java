package devices;
public class TestDevices {

    public static void main(String[] args) {
        ElectronicDevice electronicDevice = new ElectronicDevice("Device", "Generic", "ModelX");
        Television television = new Television("Television", "LG", "OLED55CXPUA", "OLED");
        Smartphone smartphone = new Smartphone("Smartphone", "Samsung", "Galaxy S21", "Android");
        Iphone iphone = new Iphone("Smartphone", "Apple", "iPhone 13", "iOS");


        // Stampa di ElectronicDevice e dei suoi metodi getter e setter
        System.out.println(electronicDevice);

        System.out.println("Category: " + electronicDevice.getCategory());
        System.out.println("Brand: " + electronicDevice.getBrand());
        System.out.println("Model: " + electronicDevice.getModel());

        electronicDevice.setCategory("Updated Device");
        electronicDevice.setBrand("Updated Brand");
        electronicDevice.setModel("Updated Model");
        electronicDevice.toggleIsOn();
        System.out.println(electronicDevice);


        // Stampa di Television e dei suoi metodi getter e setter
        System.out.println(television);

        System.out.println("Category: " + television.getCategory());
        System.out.println("Brand: " + television.getBrand());
        System.out.println("Model: " + television.getModel());
        System.out.println("Screen Type: " + television.getScreenType());

        television.setCategory("Updated Television");
        television.setBrand("Updated Brand");
        television.setModel("Updated Model");
        television.setScreenType("Updated Screen Type");
        television.toggleIsOn();
        System.out.println(television);


        // Stampa di Smartphone e dei suoi metodi getter e setter
        System.out.println(smartphone);

        System.out.println("Category: " + smartphone.getCategory());
        System.out.println("Brand: " + smartphone.getBrand());
        System.out.println("Model: " + smartphone.getModel());
        System.out.println("Operating System: " + smartphone.getOperatingSystem());

        smartphone.setCategory("Updated Smartphone");
        smartphone.setBrand("Updated Brand");
        smartphone.setModel("Updated Model");
        smartphone.setOperatingSystem("Updated Operating System");
        smartphone.toggleIsOn();
        System.out.println(smartphone);


        // Stampa di Iphone e dei suoi metodi getter e setter
        System.out.println(iphone);

        System.out.println("Category: " + iphone.getCategory());
        System.out.println("Brand: " + iphone.getBrand());
        System.out.println("Model: " + iphone.getModel());
        System.out.println("Operating System: " + iphone.getOperatingSystem());
        System.out.println("Iphone Model: " + iphone.getModel());

        iphone.setCategory("Updated Iphone");
        iphone.setBrand("Updated Brand");
        iphone.setModel("Updated Model");
        iphone.setOperatingSystem("Updated Operating System");
        iphone.setModel("Updated Iphone Model");
        iphone.toggleIsOn();
        System.out.println(iphone);
    }
}
