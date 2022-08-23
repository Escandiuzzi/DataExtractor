import Managers.PDFManager;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        PDFManager pdfManager = new PDFManager();

        String home = System.getProperty("user.home");
        pdfManager.setFilePath(home + File.separator + "Documents" + File.separator + "definition.pdf");

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