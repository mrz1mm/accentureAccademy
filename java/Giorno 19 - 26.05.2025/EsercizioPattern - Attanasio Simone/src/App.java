public class App {

    public static void main(String[] args) {
        printSeparator();
        System.out.println("### Inizio Scenario di Gestione Documenti ###");
        printSeparator();

        // 1. PDF su Storage Locale
        printSubSection("Parte 1: PDF su Storage Locale");
        DocumentFactory pdfFactory = new PdfDocumentFactory();
        Storage localStorage = new LocalStorage();

        Document pdfDoc = pdfFactory.createDocument("CompanyReport.pdf", "Questo è il report annuale dell'azienda.");
        pdfDoc.view();

        DocumentHandler localHandler = new DocumentHandlerImpl(localStorage);
        localHandler.saveDocument(pdfDoc);
        System.out.println("Documento PDF dopo il salvataggio interno e lo storage:");
        pdfDoc.view();
        System.out.println();

        // 2. Word su Storage Cloud
        printSubSection("Parte 2: Word su Storage Cloud");
        DocumentFactory wordFactory = new WordDocumentFactory();
        Storage cloudStorage = new CloudStorage();

        Document wordDoc = wordFactory.createDocument("MeetingNotes.docx", "Appunti della riunione del Q3.");
        wordDoc.view();

        DocumentHandler cloudHandler = new DocumentHandlerImpl(cloudStorage);
        cloudHandler.saveDocument(wordDoc);
        System.out.println("Documento Word dopo il salvataggio interno e lo storage:");
        wordDoc.view();
        System.out.println();

        // 3. Apertura PDF da Storage Locale
        printSubSection("Parte 3: Apertura PDF da Storage Locale");
        Document retrievedPdf = localHandler.openDocument("CompanyReport.pdf");
        if (retrievedPdf != null) {
            System.out.println("Visualizzazione del PDF recuperato:");
            retrievedPdf.view(); 
        }
        System.out.println();

        // 4. Excel su Storage Locale (Creazione, Salvataggio, Apertura)
        printSubSection("Parte 4: Excel su Storage Locale (Creazione, Salvataggio, Apertura)");
        DocumentFactory excelFactory = new ExcelDocumentFactory();
        Document excelDoc = excelFactory.createDocument("FinancialData.xlsx", "Dati finanziari: Q1: 1000, Q2: 1500");
        excelDoc.view();

        System.out.println("Salvataggio del documento Excel su storage locale...");
        localHandler.saveDocument(excelDoc);
        System.out.println("Documento Excel dopo il salvataggio interno e lo storage:");
        excelDoc.view();
        System.out.println();

        System.out.println("Ora apriamo il documento Excel 'FinancialData.xlsx' dallo storage locale:");
        Document retrievedExcel = localHandler.openDocument("FinancialData.xlsx");
        if (retrievedExcel != null) {
            System.out.println("Visualizzazione del documento Excel recuperato:");
            retrievedExcel.view();
        }
        System.out.println();

        System.out.println("Ora apriamo il documento Excel 'Budget2024.xlsx' (che non esiste) dallo storage locale:");
        Document nonExistentExcel = localHandler.openDocument("Budget2024.xlsx");
        if (nonExistentExcel == null) {
             System.out.println("Conferma: Budget2024.xlsx non trovato come previsto.");
        }
        System.out.println();

        System.out.println("Ora apriamo il documento Word 'MeetingNotes.docx' dal Cloud Storage:");
        Document retrievedWord = cloudHandler.openDocument("MeetingNotes.docx");
        if(retrievedWord != null) {
            System.out.println("Visualizzazione del documento Word recuperato dal cloud:");
            retrievedWord.view();
        }

        printSeparator();
        System.out.println("### Fine Scenario di Gestione Documenti ###");
        printSeparator();
    }

    // 🔧 Metodi helper per i separatori
    private static void printSeparator() {
        System.out.println("**************************************************");
    }

    private static void printSubSection(String title) {
        printSeparator();
        System.out.println("--- " + title + " ---");
        printSeparator();
    }
}
