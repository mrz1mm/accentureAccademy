public abstract class Document {

    protected String title;
    protected String content;

    public Document() {
        super();
        this.title = "Untitled";
        this.content = "";
    }

    public Document(String title, String content) {
        super();
        this.title = title;
        this.content = content;
    }


    // Getters 
    public String getTitle() { return title; }
    public String getContent() { return content; }


    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }


    // Metodi astratti
    public abstract void open();
    public abstract void save();
    public abstract void view();


    // Metodi concreti
    @Override
    public String toString() { return "Document [title=" + title + ", content=" + content + "]"; }
}