public class WordDocumentFactory implements DocumentFactory {

    @Override
    public Document createDocument(String title, String content) {
        return new WordDocument(title, content);
    }

}