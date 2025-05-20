package animals2.tests;

import animals2.models.Zoo;
import animals2.models.Cane;
import animals2.models.Mammifero;
import animals2.models.Pesce;
import animals2.models.Uccello;

public class TestAnimali {

    public static void main(String[] args) {
        // Creazione dello zoo
        Zoo zoo = new Zoo(4);

        // Creazione degli animali
        Mammifero mammifero = new Mammifero("Leo", "Felino", 3, 15, 4);
        Cane cane = new Cane("Fido", "Mammifero", 5, 12, 4, "Golden Retriever");
        Uccello uccello = new Uccello("Twitter", "Canarino", 2, 5, 2);
        Pesce pesce = new Pesce("Nemo", "Tonno", 1, 3, "Acqua Salata");

        // Aggiunta degli animali allo zoo
        zoo.aggiungiAnimale(null);
        zoo.aggiungiAnimale(mammifero);
        zoo.aggiungiAnimale(cane);
        zoo.aggiungiAnimale(uccello);
        zoo.aggiungiAnimale(pesce);

        // Mostra informazioni sugli animali --> metodi specifici utilizzati in zoo
        System.out.println("*******************************************");
        System.out.println(zoo.emettiVersiAnimali());
        System.out.println("*******************************************");
        System.out.println(zoo.muoviAnimali());
        System.out.println("*******************************************");
        System.out.println(zoo.mostraInfoAnimali());
        System.out.println("*******************************************");
        System.out.println(zoo.getAnimalePiuLongevo());
        System.out.println("*******************************************");

        // Test per verificare l'eccezione di capacità dello zoo
        Mammifero mammifero2 = new Mammifero("Max", "Felino", 4, 10, 4);
        zoo.aggiungiAnimale(mammifero2);
        System.out.println("*******************************************");


        // Invecchiamento degli animali
        mammifero.invecchia();
        cane.invecchia(2);
        uccello.invecchia(3);
        pesce.invecchia(1);
        System.out.println("*******************************************");


        // Mostra informazioni sugli animali dopo l'invecchiamento
        System.out.println(zoo.mostraInfoAnimali());
        System.out.println("*******************************************");


        // Test per verificare l'eccezione per l'invecchiamento di un animale oltre la durata della vita massima
        /* 
        try {
            mammifero.invecchia(20);
        } catch (IllegalArgumentException e) {
            System.out.println("Eccezione: " + e.getMessage());
        }
        */

        // Test per verificare che lo zoo si svuoti correttamente
        zoo.svuotaZoo();
        System.out.println(zoo.mostraInfoAnimali());
        System.out.println("*******************************************");
    }

}