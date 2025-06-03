import java.util.HashMap;
import java.util.Map;

public class CloudStorage implements Storage {
    private Map<String, Document> cloudServer = new HashMap<>();

    @Override
    public void store(Document doc) {
        System.out.println("CloudStorage: Upload del documento '" + doc.getTitle() + "' sul cloud.");
        cloudServer.put(doc.getTitle(), doc);
    }

    @Override
    public Document retrieve(String title) {
        System.out.println("CloudStorage: Download del documento '" + title + "' dal cloud.");
        Document doc = cloudServer.get(title);
        if (doc == null) {
            System.out.println("CloudStorage: Documento '" + title + "' non trovato.");
        }
        return doc;
    }

}
