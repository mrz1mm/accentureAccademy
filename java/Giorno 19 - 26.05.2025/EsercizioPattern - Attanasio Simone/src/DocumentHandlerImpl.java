public class DocumentHandlerImpl extends DocumentHandler {

    public DocumentHandlerImpl(Storage storage) {
        super(storage);
    }

    @Override
    public void saveDocument(Document doc) {
        System.out.println("DocumentHandler: Inizio processo di salvataggio per '" + doc.getTitle() + "' usando " + storage.getClass().getSimpleName());
        doc.save();
        storage.store(doc);
        System.out.println("DocumentHandler: Documento '" + doc.getTitle() + "' salvato con successo su " + storage.getClass().getSimpleName() + ".");
    }

    @Override
    public Document openDocument(String docName) {
        System.out.println("DocumentHandler: Inizio processo di apertura per '" + docName + "' usando " + storage.getClass().getSimpleName());
        Document doc = storage.retrieve(docName);
        if (doc != null) {
            doc.open();
            System.out.println("DocumentHandler: Documento '" + docName + "' aperto con successo.");
        } else {
            System.out.println("DocumentHandler: Impossibile aprire il documento '" + docName + "', non trovato su " + storage.getClass().getSimpleName() + ".");
        }
        return doc;
    }
}