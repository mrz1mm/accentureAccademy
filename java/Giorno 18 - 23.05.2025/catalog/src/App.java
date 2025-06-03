public class App {
    
    public static void main(String[] args) {
        // Carica o crea un nuovo catalogo
        ProductCatalog catalog = ProductCatalog.loadCatalog();
        
        // Aggiungi alcuni prodotti
        catalog.addProduct(new Product(1, "Smartphone", "Elettronica"));
        catalog.addProduct(new Product(2, "Laptop", "Elettronica"));
        catalog.addProduct(new Product(3, "T-shirt", "Abbigliamento"));
        catalog.addProduct(new Product(4, "Jeans", "Abbigliamento"));
        catalog.addProduct(new Product(5, "Pasta", "Alimentari"));
        
        // Visualizza tutti i prodotti
        System.out.println("\nTutti i prodotti:");
        System.out.println(catalog.showAllProductsInfo());
        
        // Cerca prodotti per categoria
        System.out.println("\nProdotti nella categoria Elettronica:");
        for (Product product : catalog.findAllProductsByCategory("Elettronica")) {
            System.out.println(product.showInfo());
        }
        
        // Cerca un prodotto per ID
        System.out.println("\nDettagli del prodotto con ID 3:");
        System.out.println(catalog.showProductInfo(3));
        
        // Aggiorna un prodotto
        System.out.println("\nAggiornamento prodotto con ID 3:");
        catalog.updateProduct(3, new Product(3, "Maglietta sportiva", "Abbigliamento sportivo"));
        System.out.println(catalog.showProductInfo(3));
        
        // Rimuovi un prodotto
        System.out.println("\nRimozione prodotto con ID 5:");
        catalog.removeProduct(5);
        
        // Visualizza tutti i prodotti dopo le modifiche
        System.out.println("\nProdotti dopo le modifiche:");
        System.out.println(catalog.showAllProductsInfo());
        
        // Salva il catalogo
        catalog.saveCatalog();

    }

}


/*
 * VERSIONE NELLA QUALE SI SPACCANO I RIFERIMENTI CON SCANNER
 * 
import java.util.Scanner;
import java.util.Set;

import static javax.xml.catalog.CatalogManager.catalog;

public class App {
    
    public static void main(String[] args) {
        // Carica o crea un nuovo catalogo
        ProductCatalog catalog = ProductCatalog.loadCatalog();
        boolean running = true;
        Scanner scanner = new Scanner(System.in);


        // Aggiungi alcuni prodotti
        if (catalog.getProductsById().isEmpty()) {
        catalog.addProduct(new Product(1, "Smartphone", "Elettronica"));
        catalog.addProduct(new Product(2, "Laptop", "Elettronica"));
        catalog.addProduct(new Product(3, "T-shirt", "Abbigliamento"));
        catalog.addProduct(new Product(4, "Jeans", "Abbigliamento"));
        catalog.addProduct(new Product(5, "Pasta", "Alimentari"));
        }

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addProduct(); break;
                case "2": viewAllProducts(); break;
                case "3": getProductsByCategory(); break;
                case "4": viewProductDetails(); break;
                case "5": updateProduct(); break;
                case "6": removeProduct(); break;
                case "7": 
                    catalog.saveCatalog();
                    System.out.println("Catalogo salvato. Uscita dal'app'.");
                    scanner.close();
                    return;
                case "8":
                    System.out.println("Uscita senza salvare. Applicazione terminata.");
                    scanner.close();
                    return;
                default: System.out.println("Opzione non valida. Riprova.");
            }
            scanner.close();
            System.out.println("Applicazione terminata.");
        }
    }


        private static void printMenu() {
        System.out.println("\n--- Menu Gestione Catalogo Prodotti ---");
        System.out.println("1. Aggiungi un nuovo prodotto");
        System.out.println("2. Visualizza tutti i prodotti");
        System.out.println("3. Cerca prodotti per categoria");
        System.out.println("4. Visualizza dettagli prodotto per ID");
        System.out.println("5. Aggiorna un prodotto");
        System.out.println("6. Rimuovi un prodotto");
        System.out.println("7. Salva e Esci");
        System.out.print("Scegli un'opzione: ");
        System.out.println("8. Esci (senza salvare)");
    }

    private static void addProduct() {
        System.out.println("\n--- Aggiungi Nuovo Prodotto ---");
        try {
            System.out.print("ID Prodotto: ");
            int id = Integer.parseInt(scanner.nextLine());
            if (catalog.findProductById(id) != null) {
                System.out.println("ERRORE: Un prodotto con ID " + id + " esiste già. Scegli un ID diverso.");
                return;
            }
            System.out.print("Nome Prodotto: ");
            String name = scanner.nextLine();
            System.out.print("Categoria Prodotto: ");
            String category = scanner.nextLine();
            if (name.trim().isEmpty() || category.trim().isEmpty()) {
                System.out.println("ERRORE: Nome e categoria non possono essere vuoti.");
                return;
            }
            catalog.addProduct(new Product(id, name, category));
        } catch (NumberFormatException e) {
            System.out.println("ERRORE: ID non valido. Inserisci un numero.");
        }
    }

    private static void viewAllProducts() {
        System.out.println("\n--- Tutti i Prodotti ---");
        String allProductsInfo = catalog.showAllProductsInfo();
        if (allProductsInfo.isEmpty()) {
            System.out.println("Nessun prodotto nel catalogo.");
        } else {
            System.out.print(allProductsInfo);
        }
    }

    private static void getProductsByCategory() {
        System.out.println("\n--- Cerca Prodotti per Categoria ---");
        System.out.print("Inserisci la categoria da cercare: ");
        String category = scanner.nextLine();
        Set<Product> products = catalog.findAllProductsByCategory(category);

        if (products.isEmpty()) {
            System.out.println("Nessun prodotto trovato nella categoria: " + category);
        } else {
            System.out.println("Prodotti nella categoria '" + category + "':");
            for (Product product : products) {
                System.out.print(product.showInfo());
            }
        }
    }

    private static void viewProductDetails() {
        System.out.println("\n--- Dettagli Prodotto per ID ---");
        try {
            System.out.print("Inserisci l'ID del prodotto: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.println(catalog.showProductInfo(id));
        } catch (NumberFormatException e) {
            System.out.println("ERRORE: ID non valido. Inserisci un numero.");
        }
    }

    private static void updateProduct() {
        System.out.println("\n--- Aggiorna Prodotto ---");
        try {
            System.out.print("Inserisci l'ID del prodotto da aggiornare: ");
            int id = Integer.parseInt(scanner.nextLine());

            Product existingProduct = catalog.findProductById(id);
            if (existingProduct == null) {
                System.out.println("ERRORE: Prodotto con ID " + id + " non trovato.");
                return;
            }

            System.out.println("Prodotto attuale: \n" + existingProduct.showInfo());

            System.out.print("Nuovo Nome Prodotto (lascia vuoto per non modificare '" + existingProduct.getName() + "'): ");
            String name = scanner.nextLine();
            System.out.print("Nuova Categoria Prodotto (lascia vuoto per non modificare '" + existingProduct.getCategory() + "'): ");
            String category = scanner.nextLine();

            String newName = name.trim().isEmpty() ? existingProduct.getName() : name;
            String newCategory = category.trim().isEmpty() ? existingProduct.getCategory() : category;

            if (newName.trim().isEmpty() || newCategory.trim().isEmpty()){
                 System.out.println("ERRORE: Il nuovo nome o la nuova categoria non possono essere vuoti se modificati.");
                 return;
            }

            Product updatedProductData = new Product(id, newName, newCategory);
            catalog.updateProduct(id, updatedProductData);
            System.out.println("Prodotto con ID " + id + " aggiornato.");
            System.out.println("Nuovi dettagli: \n" + catalog.showProductInfo(id));

        } catch (NumberFormatException e) {
            System.out.println("ERRORE: ID non valido. Inserisci un numero.");
        }
    }

    private static void removeProduct() {
        System.out.println("\n--- Rimuovi Prodotto ---");
        try {
            System.out.print("Inserisci l'ID del prodotto da rimuovere: ");
            int id = Integer.parseInt(scanner.nextLine());
            catalog.removeProduct(id);
        } catch (NumberFormatException e) {
            System.out.println("ERRORE: ID non valido. Inserisci un numero.");
        }
    }

}
 */