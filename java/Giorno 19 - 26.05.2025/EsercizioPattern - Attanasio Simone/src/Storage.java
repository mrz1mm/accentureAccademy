public interface Storage {

    void store(Document doc);
    Document retrieve(String title);

}
