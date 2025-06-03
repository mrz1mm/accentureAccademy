public class ExcelDocumentFactory implements DocumentFactory {

    @Override
    public Document createDocument(String title, String content) {
        return new ExcelDocument(title, content);
    }

}