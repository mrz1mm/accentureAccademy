package tests;
import models.*;

public class TestDevices {
    
    public static void main(String[] args) {
        ElectricalDevice[] devices = new ElectricalDevice[4];

        try {
            devices[0] = new SmartTv("TV", "Samsung", "QLED", "OLED");
            devices[1] = new Tablet("Tablet", "Samsung", "Galaxy Tab S8", "11 inches");
            devices[2] = new Smartphone("Smartphone", "Apple", "iPhone 14", "6.1 inches");
            devices[3] = new Iphone("Smartphone", "Apple", "iPhone 14 Pro", "6.7 inches", "16.0");
        } catch (Exception e) {
            System.out.println("Error creating devices: " + e.getMessage());
            return;
        }

        System.out.println("===== INIZIO TEST =====");
        System.out.println("\n-----------------------------------");
        
        for (ElectricalDevice device : devices) {
            String deviceType = "";
            
            if (device instanceof SmartTv) deviceType = "SmartTv";
            else if (device instanceof Iphone) deviceType = "Iphone";
            else if (device instanceof Smartphone) deviceType = "Smartphone";
            else if (device instanceof Tablet) deviceType = "Tablet";
            
            switch (deviceType) {
                case "SmartTv":
                    System.out.println("========== TEST SMART TV ==========");
                    ((SmartTv) device).mostraInfo();
                    ((SmartTv) device).accendi();
                    break;
                    
                case "Tablet":
                    System.out.println("========== TEST TABLET ==========");
                    ((Tablet) device).mostraInfo();
                    ((Tablet) device).accendi();
                    break;
                    
                case "Smartphone":
                    System.out.println("========== TEST SMARTPHONE ==========");
                    ((Smartphone) device).mostraInfo();
                    ((Smartphone) device).accendi();
                    break;
                    
                case "Iphone":
                    System.out.println("========== TEST IPHONE ==========");
                    ((Iphone) device).mostraInfo();
                    ((Iphone) device).accendi();
                    break;
                    
                default:
                    System.out.println("Dispositivo non riconosciuto");
                    break;
            }
        }
        
        System.out.println("\n-----------------------------------");
        System.out.println("===== FINE TEST =====");
    }
}