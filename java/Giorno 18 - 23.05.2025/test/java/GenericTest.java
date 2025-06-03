import java.util.ArrayList;
import java.util.List;

public class GenericTest {

    public static void main(String[] args) {

        // 1. INFORMAZIONI A COMPILE-TIME
        List<String> listString = new ArrayList<>();
        listString.add("Ciao");
        // listString.add(123); // A compile-time, sa che listString deve contenere solo String --> mi da errore

        List<Integer> listInteger = new ArrayList<>();
        listInteger.add(123);
        // listInteger.add("Ciao"); // A compile-time, il compilatore sa che listInteger deve contenere solo Integer --> mi da errore

        
        // 2. INFORMAZIONI A RUNTIME
        System.out.println("--- INFORMAZIONI A RUNTIME ---");
        
        try {
            System.out.println("listString: " + listString.getClass());
            System.out.println("listInteger: " + listInteger.getClass());

            if (listString.getClass() == listInteger.getClass()) {
                    System.out.println("=> Sono istanze della stessa classe: " + 
                    listString.getClass().getSimpleName());
            } else {
                    System.out.println("=> Sono istante di classi diverse: " + 
                    listString.getClass().getSimpleName() + " e " + 
                    listInteger.getClass().getSimpleName());
            }
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    
    }

}
