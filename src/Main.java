import GUI.MainWindow;

public class Main {
    public static void main(String[] args) {

        /*
        String home = System.getProperty("user.home");
        String filepath = home + File.separator + "Documents" + File.separator + "document.pdf";

        DataHandler dataHandler = new DataHandler();
        PDFManager pdfManager = new PDFManager();

        try {
            PDDocument pdDocument = pdfManager.InitializeDocument(filepath);
            String pdfContent = pdfManager.toText();

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