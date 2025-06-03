public class PdfDocument extends Document {

    public PdfDocument() {
        super();
    }

    public PdfDocument(String title, String content) {
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
    public void open() { System.out.println("Apertura di un documento PDF: " + getTitle()); }

    @Override
    public void save() { System.out.println("Salvataggio di un documento PDF: " + getTitle()); }

    @Override
    public void view() { 
        System.out.println("Visualizzazione del documento PDF: " + getTitle());
        System.out.println("Contenuto del documento PDF: " + getContent());
    }
    

    // Metodi concreti
    @Override
    public String toString() { return "Documento PDF [titolo=" + getTitle() + ", contenuto=" + getContent() + "]"; }
    
}