public class PdfDocumentFactory implements DocumentFactory {

    @Override
    public Document createDocument(String title, String content) {
        return new PdfDocument(title, content);
    }

}