import Managers.PDFManager;
import com.groupdocs.parser.Parser;
import com.groupdocs.parser.data.*;
import com.groupdocs.parser.templates.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // Create Template to Parse Data from Invoice using Java
        // First create Template Items
        TemplateItem[] templateItems = new TemplateItem[]
        {
            new TemplateField(new TemplateFixedPosition(new Rectangle(new Point(35, 135), new Size(100, 10))), "FromCompany"),
            new TemplateField(new TemplateFixedPosition(new Rectangle(new Point(35, 150), new Size(100, 35))), "FromAddress"),
            new TemplateField(new TemplateFixedPosition(new Rectangle(new Point(35, 190), new Size(150, 2))), "FromEmail"),
            new TemplateField(new TemplateFixedPosition(new Rectangle(new Point(35, 250), new Size(100, 2))), "ToCompany"),
            new TemplateField(new TemplateFixedPosition(new Rectangle(new Point(35, 260), new Size(100, 15))), "ToAddress"),
            new TemplateField(new TemplateFixedPosition(new Rectangle(new Point(35, 290), new Size(150, 2))), "ToEmail"),
            new TemplateField(new TemplateRegexPosition("Invoice Number"), "InvoiceNumber"),
            new TemplateField(new TemplateLinkedPosition(
                    "InvoiceNumber",
                    new Size(200, 15),
                    new TemplateLinkedPositionEdges(false, false, true, false)),
                    "InvoiceNumberValue"),
            new TemplateField(new TemplateRegexPosition("Order Number"), "InvoiceOrder"),
            new TemplateField(new TemplateLinkedPosition(
                    "InvoiceOrder",
                    new Size(200, 15),
                    new TemplateLinkedPositionEdges(false, false, true, false)),
                    "InvoiceOrderValue"),
            new TemplateField(new TemplateRegexPosition("Invoice Date"), "InvoiceDate"),
            new TemplateField(new TemplateLinkedPosition(
                    "InvoiceDate",
                    new Size(200, 15),
                    new TemplateLinkedPositionEdges(false, false, true, false)),
                    "InvoiceDateValue"),
            new TemplateField(new TemplateRegexPosition("Due Date"), "DueDate"),
            new TemplateField(new TemplateLinkedPosition(
                    "DueDate",
                    new Size(200, 15),
                    new TemplateLinkedPositionEdges(false, false, true, false)),
                    "DueDateValue"),
            new TemplateField(new TemplateRegexPosition("Total Due"), "TotalDue"),
            new TemplateField(new TemplateLinkedPosition(
                    "TotalDue",
                    new Size(200, 15),
                    new TemplateLinkedPositionEdges(false, false, true, false)),
                    "TotalDueValue")
        };

        // Transform into template
        Template template = new Template(Arrays.asList(templateItems));

        String home = System.getProperty("user.home");
        String filepath = home + File.separator + "Documents" + File.separator + "document.pdf";

        // Parse the PDF Invoice using the defined Template in Java
        Parser parser = new Parser(filepath);
        DocumentData data = parser.parseByTemplate(template);

        // Print the extracted data
        for (int i = 0; i < data.getCount(); i++) {
            // Printing Field Name
            System.out.print(data.get(i).getName() + ": ");
            // Cast PageArea property value to PageTextArea
            // as we have defined only text fields in the template
            PageTextArea area = data.get(i).getPageArea() instanceof PageTextArea
                    ? (PageTextArea) data.get(i).getPageArea()
                    : null;
            System.out.println(area == null ? "Not a template field" : area.getText());
        }

        /*
            PDFManager pdfManager = new PDFManager();

            pdfManager.setFilePath(home + File.separator + "Documents" + File.separator + "document.pdf");

            try {
                String text = pdfManager.toText();
                System.out.println(text);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        */
    }
}