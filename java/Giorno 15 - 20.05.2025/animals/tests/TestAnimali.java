package animals.tests;

import animals.models.animals.*;
import animals.models.zoo.Zoo;

public class TestAnimali {

    public static void main(String[] args) {
        // Creazione dello zoo
        Zoo zoo = new Zoo(5);

        // Creazione degli animali
        Cane cane = new Cane("Fido", 5, "Golden Retriever");
        Uccello uccello = new Uccello("Twitter", 2, "Canarino");
        Pesce pesce = new Pesce("Nemo", 1, "Tonno");

        // Aggiunta degli animali allo zoo
        zoo.aggiungiAnimale(cane);
        zoo.aggiungiAnimale(uccello);
        zoo.aggiungiAnimale(pesce);

        // Mostra informazioni sugli animali --> metodi specifici utilizzati in zoo
        System.out.println("*******************************************");
        System.out.println(zoo.getAnimali());
        System.out.println("*******************************************");
        System.out.println(zoo.emettiVersiAnimali());
        System.out.println("*******************************************");
        System.out.println(zoo.muoviAnimali());
        System.out.println("*******************************************");
        System.out.println(zoo.mostraInfoAnimali());
        System.out.println("*******************************************");
    }

}