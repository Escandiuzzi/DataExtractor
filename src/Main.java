import Extractors.PDFExtractor;
import GUI.MainWindow;
import Handlers.DataHandler;
import Persistence.Config;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        /*

        String home = System.getProperty("user.home");
        String filepath = home + File.separator + "Documents" + File.separator + "document.pdf";

        DataHandler dataHandler = new DataHandler();
        PDFExtractor pdfExtractor = new PDFExtractor();

        try {
            PDDocument pdDocument = pdfExtractor.InitializeDocument(filepath);
            String pdfContent = pdfExtractor.toText();

            dataHandler.HandleData(pdfContent, pdDocument);
            dataHandler.PrintData();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        */

        MainWindow mainWindow = new MainWindow();
    }
}