import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductCatalog {

    private Map<Integer, Product> productsById; // Mappa primaria per gli ID
    private Map<String, Set<Product>> productsByCategory; // Mappa secondaria per le categorie
    private static final String DEFAULT_CATALOG_FILE = "catalog_data.dat"; // Nome del file di default per il catalogo

    public ProductCatalog() {
        super();
        this.productsById = new HashMap<>();
        this.productsByCategory = new HashMap<>();
    }

    private ProductCatalog(Map<Integer, Product> loadedProductsById) {
        this.productsById = new HashMap<>(loadedProductsById); // Crea una nuova mappa per sicurezza
        this.productsByCategory = new HashMap<>();
        // Ricostruisce la mappa productsByCategory basandosi su productsById caricata
        if (this.productsById != null) {
            for (Product product : this.productsById.values()) {
                this.productsByCategory
                    .computeIfAbsent(product.getCategory(), k -> new HashSet<>())
                    .add(product);
            }
        }
    }


    // Getters - facciamo restituire copie non modificabili
    public Map<Integer, Product> getProductsById() { 
        return Collections.unmodifiableMap(productsById); 
    }

    public Map<String, Set<Product>> getProductsByCategory() {
        return Collections.unmodifiableMap(productsByCategory);
    }


    // --- METODI DI GESTIONE DEL CATALOGO ---
    // Cercare un prodotto per ID
    public Product findProductById(int productId) {
        return productsById.get(productId);
    }

    // Cercare tutti i prodotti in una categoria
    public Set<Product> findAllProductsByCategory(String category) {
        return new HashSet<>(productsByCategory.getOrDefault(category, new HashSet<>()));
    }

    // Aggiungere un prodotto al catalogo
    public void addProduct(Product product) {
        if (product == null) {
            System.out.println("ERROR: Impossibile aggiungere un prodotto nullo.");
            return;
        }
        if (productsById.containsKey(product.getId())) {
            System.out.println("ERROR: Prodotto con ID " + product.getId() + " già esistente.");
            return;
        }
        productsById.put(product.getId(), product);
        productsByCategory
            .computeIfAbsent(product.getCategory(), k -> new HashSet<>())
            .add(product);
        System.out.println("Prodotto aggiunto: " + product.showInfo());
    }

    // Modificare un prodotto esistente
    public void updateProduct(int productId, Product updatedProduct) {
        
        Product productToUpdate = productsById.get(productId);

        if (productToUpdate == null) {
            System.out.println("ERROR: Prodotto con ID " + productId + " non trovato.");
            return;
        }

        if (updatedProduct == null) {
            System.out.println("ERROR: Impossibile aggiornare un prodotto nullo.");
            return;
        }

        // Rimuovi dalla mappa delle categorie prima
        String oldCategory = productToUpdate.getCategory();
        Set<Product> categoryProducts = productsByCategory.get(oldCategory);
        if (categoryProducts != null) {
            categoryProducts.remove(productToUpdate);
            // Se la categoria è vuota, rimuovi la chiave
            if (categoryProducts.isEmpty()) {
                productsByCategory.remove(oldCategory);
            }
        }

        // Aggiorna i dati del prodotto
        productToUpdate.setName(updatedProduct.getName());
        productToUpdate.setCategory(updatedProduct.getCategory());

        // Aggiungi alla nuova categoria nella mappa
        productsByCategory
            .computeIfAbsent(productToUpdate.getCategory(), k -> new HashSet<>())
            .add(productToUpdate);
    }

    // Rimuovere un prodotto dal catalogo
    public void removeProduct(int productId) {
        Product productToRemove = productsById.get(productId);

        if (productToRemove == null) {
            System.out.println("ERROR: Prodotto con ID " + productId + " non trovato.");
            return;
        }

        // Rimuovi dalla mappa principale
        productsById.remove(productId);

        // Rimuovi dalla mappa delle categorie
        String category = productToRemove.getCategory();
        Set<Product> categoryProducts = productsByCategory.get(category);
        if (categoryProducts != null) {
            categoryProducts.remove(productToRemove);
            // Se la categoria è vuota, rimuovi la chiave
            if (categoryProducts.isEmpty()) {
                productsByCategory.remove(category);
            }
        }

        System.out.println("Prodotto rimosso: " + productToRemove.showInfo());
    }

    // Mostrare le informazioni di un prodotto
    public String showProductInfo(int productId) {
        Product product = productsById.get(productId);
        if (product == null) {
            return "ERROR: Prodotto con ID " + productId + " non trovato.";
        }
        return product.showInfo();
    }

    // Mostrare le informazioni di tutti i prodotti
    public String showAllProductsInfo() {
        StringBuilder sb = new StringBuilder();
        for (Product product : productsById.values()) {
            sb.append(product.showInfo()).append("\n");
        }
        return sb.toString();
    }

    // --- METODI DI PERSISTENZA SU DISCO ---
    // Salva il catalogo su disco
    public void saveCatalog() {
        saveCatalog(DEFAULT_CATALOG_FILE);
    }

    public void saveCatalog(String filename) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(filename), productsById);
            System.out.println("Catalogo salvato con successo in: " + filename);
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio del catalogo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Carica il catalogo da disco
    public static ProductCatalog loadCatalog() {
        return loadCatalog(DEFAULT_CATALOG_FILE);
    }

    public static ProductCatalog loadCatalog(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("File catalogo non trovato. Creazione di un nuovo catalogo.");
                return new ProductCatalog();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            Map<Integer, Product> loadedProducts = objectMapper.readValue(
                file, 
                new TypeReference<HashMap<Integer, Product>>() {}
            );
            
            System.out.println("Catalogo caricato con successo da: " + filename);
            return new ProductCatalog(loadedProducts);
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento del catalogo: " + e.getMessage());
            e.printStackTrace();
            return new ProductCatalog();
        }
    }

}
