import Handlers.PDFHandler;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String home = System.getProperty("user.home");
        //String filepath = home + File.separator + "Documents" + File.separator + "document.pdf";

        String filepath = "C:\\document.pdf";

        PDFHandler pdfHandler = new PDFHandler();
        pdfHandler.setFilePath(filepath);

        try {
            String text = pdfHandler.toText();

            Pattern cnpjPattern = Pattern.compile("([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})\n");
            Matcher cnpjMatcher = cnpjPattern.matcher(text);
            cnpjMatcher.find();

            Pattern documentCodePattern = Pattern.compile("/(([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([+-]?\\d+))?( ([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([+-]?\\d+))?)+)/g");
            Matcher documentCodeMatcher = documentCodePattern.matcher(text);
            documentCodeMatcher.find();

            System.out.println("CPF: " + cnpjMatcher.group(0));
            System.out.println("Codigo: " + documentCodeMatcher.group(0));

            //pdfHandler.printSubWords("CPF");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}