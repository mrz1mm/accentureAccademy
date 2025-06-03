import java.util.HashMap;
import java.util.Map;

public class LocalStorage implements Storage {
    private Map<String, Document> localDisk = new HashMap<>();

    @Override
    public void store(Document doc) {
        System.out.println("LocalStorage: Salvataggio del documento '" + doc.getTitle() + "' su disco locale.");
        localDisk.put(doc.getTitle(), doc);
    }

    @Override
    public Document retrieve(String title) {
        System.out.println("LocalStorage: Recupero del documento '" + title + "' da disco locale.");
        Document doc = localDisk.get(title);
        if (doc == null) {
            System.out.println("LocalStorage: Documento '" + title + "' non trovato.");
        }
        return doc;
    }
}
