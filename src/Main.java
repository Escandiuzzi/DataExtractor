import exporters.DocumentExporter;
import extractors.PDFExtractor;
import gui.MainWindow;
import handlers.DataHandler;

import org.apache.pdfbox.pdmodel.PDDocument;
import persistence.ConfigPersistence;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String home = System.getProperty("user.home");
        String filepath = home + File.separator + "Documents" + File.separator + "document.pdf";

        ConfigPersistence configPersistence = new ConfigPersistence();
        MainWindow mainWindow = new MainWindow(configPersistence);

        PDFExtractor pdfExtractor = new PDFExtractor();

        DocumentExporter documentExporter = new DocumentExporter(configPersistence);
        DataHandler dataHandler = new DataHandler(documentExporter);

        try {
            PDDocument pdDocument = pdfExtractor.InitializeDocument(filepath);
            String pdfContent = pdfExtractor.getDocumentData();

            dataHandler.HandleData(pdfContent, pdDocument);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}