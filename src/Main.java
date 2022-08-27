import Handlers.DataHandler;
import Handlers.PDFManager;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String home = System.getProperty("user.home");
        String filepath = home + File.separator + "Documents" + File.separator + "document.pdf";

        DataHandler dataHandler = new DataHandler();
        PDFManager pdfManager = new PDFManager();
        pdfManager.setFilePath(filepath);

        try {
            dataHandler.ShowData(pdfManager.toText());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}