public class WordDocument extends Document {

    public WordDocument() {
        super();
    }

    public WordDocument(String title, String content) {
        super(title, content);
    }


    // Getters
    @Override
    public String getTitle() { return super.getTitle(); }

    @Override
    public String getContent() { return super.getContent(); }


    // Setters
    @Override
    public void setTitle(String title) { super.setTitle(title); }

    @Override
    public void setContent(String content) { super.setContent(content); }


    // Metodi astratti
    @Override
    public void open() { System.out.println("Apertura di un documento Word: " + getTitle()); }

    @Override
    public void save() { System.out.println("Salvataggio di un documento Word: " + getTitle()); }

    @Override
    public void view() { 
        System.out.println("Visualizzazione del documento Word: " + getTitle());
        System.out.println("Contenuto del documento Word: " + getContent());
    }
    

    // Metodi concreti
    @Override
    public String toString() { return "Documento Word [titolo=" + getTitle() + ", contenuto=" + getContent() + "]"; }
    
}