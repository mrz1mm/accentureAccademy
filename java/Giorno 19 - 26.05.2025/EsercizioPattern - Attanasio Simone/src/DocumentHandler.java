public abstract class DocumentHandler {

    protected Storage storage;

    public DocumentHandler(Storage storage) {
        this.storage = storage;
    }

    public abstract void saveDocument(Document doc);
    public abstract Document openDocument(String docName);

}


