package handlers;

import dtos.InvoiceDto;
import exporters.DocumentExporter;
import handlers.invoice.InvoiceField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import utils.TextPositionFinder;
import utils.TextPositionSequence;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static handlers.invoice.InvoiceFields.*;

public class DataHandler {
    private PDDocument pdDocument;

    private PDFTextStripperByArea stripper;

    private Map<String, String> infos;

    private InvoiceDto invoiceDto;

    private boolean isSantander;

    private List<InvoiceField> invoiceFields;

    DocumentExporter documentExporter;

    public DataHandler(DocumentExporter documentExporter) {
        this.documentExporter = documentExporter;
    }

    public InvoiceDto getInvoiceDto() { return invoiceDto; }

    public void PrintData() {
        for (String field : invoiceFieldsKeys) {
            if (infos.containsKey(field)) {
                System.out.println(field + ": " + infos.get(field).trim());
            }
        }
    }

    public void HandleData(String text, PDDocument pdDocument) throws IOException
    {
        this.pdDocument = pdDocument;

        checkBankInstitution();

        CreatePDFStripperByArea();

        extractTextByArea();

        exportData();
    }

    private void checkBankInstitution() {
        TextPositionSequence position = null;

        try {
            position = findTerm("Santander");
        } catch (IOException e) {
            System.out.println(e);
        }

        isSantander = position != null;
    }

    private void CreatePDFStripperByArea() throws IOException{
        stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition( true );
    }

    private void extractTextByArea() throws IOException {

        if (isSantander) {
            invoiceFields = invoiceFieldsSantander;
        }

        AddRects();

        PDPage firstPage = pdDocument.getPage(0);
        stripper.extractRegions( firstPage );

        CreateMap();
    }

    private void AddRects() throws IOException {
        TextPositionSequence position = null;

        for (InvoiceField field: invoiceFields) {
            position = findTerm(field.searchTerm());
            if( position == null) {
                System.out.println("Could not find term " + field.searchTerm());
                continue;
            }
            Rectangle rect = new Rectangle((int)position.getX() + field.offsetX(), (int)position.getY() + field.offsetY(), field.width(), field.height());
            stripper.addRegion(field.name(), rect);
        }
    }

    private TextPositionSequence findTerm(String term) throws IOException {
        List<TextPositionSequence> positions = TextPositionFinder.getTermPosition(pdDocument, 1, term.toLowerCase());

        if(positions.isEmpty()) return null;

        return positions.get(0);
    }

    private void CreateMap() {
        infos = new HashMap<String, String>();

        List<String> regions = stripper.getRegions();

        for (InvoiceField field: invoiceFields) {
            if(regions.contains(field.name())) {
                infos.put(field.name(),  stripper.getTextForRegion( field.name() ).trim());
            }
        }
    }

    private void exportData() {
        invoiceDto = new InvoiceDto();

        invoiceDto.beneficiary = returnIfValid(Beneficiary);
        invoiceDto.payer = returnIfValid(Payer);
        invoiceDto.cnpj = returnIfValid(Cnpj);
        invoiceDto.agencyCode = returnIfValid(BeneficiaryCode);
        invoiceDto.documentCode = returnIfValid(DocumentNumber);
        invoiceDto.cpf = returnIfValid(Cpf);
        invoiceDto.date = returnIfValid(DueDate);
        invoiceDto.price = returnIfValid(DocumentPrice);

        documentExporter.exportDocument(invoiceDto);
    }

    private String returnIfValid(String field) {
        if (infos.containsKey(field)) {
           return infos.get(field);
        }

        return "";
    }
}
