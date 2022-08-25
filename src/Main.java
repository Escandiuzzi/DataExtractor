import Managers.PDFManager;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String home = System.getProperty("user.home");
        String filepath = home + File.separator + "Documents" + File.separator + "document.pdf";

        PDFManager pdfManager = new PDFManager();
        pdfManager.setFilePath(filepath);
        try {
            String text = pdfManager.toText();
            System.out.println(text);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}