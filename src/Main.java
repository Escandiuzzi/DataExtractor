import Handlers.DataHandler;
import Handlers.PDFHandler;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String home = System.getProperty("user.home");
        String filepath = home + File.separator + "Documents" + File.separator + "document.pdf";

        PDFHandler pdfHandler = new PDFHandler();
        pdfHandler.setFilePath(filepath);

        try {
            DataHandler dataHandler = new DataHandler();
            dataHandler.ShowData(pdfHandler.toText());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}