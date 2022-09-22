package handlers;

import dtos.InvoiceDto;
import exporters.DocumentExporter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import utils.TextPositionFinder;
import utils.TextPositionSequence;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import static handlers.invoice.InvoiceFields.*;

public class DataHandler {
    private PDDocument pdDocument;
    private PDFTextStripperByArea stripper;

    private Map<String, String> infos;

    private InvoiceDto invoiceDto;

    private boolean isSantander;

    DocumentExporter documentExporter;
    public DataHandler(DocumentExporter documentExporter) {
        this.documentExporter = documentExporter;
    }

    public InvoiceDto getInvoiceDto() { return invoiceDto; }

    public void PrintData() {
        /*PrintIfValid("CNPJ: ", invoiceDto.cnpj);
        PrintIfValid("Código Beneficiario: ", agencyCodePatternMatcher);
        PrintIfValid("Código Boleto: ", invoiceCodeMatcher);
        PrintIfValid("CPF: ", cpfMatcher);
        PrintIfValid("Data de vencimento: ", dateMatcher);
        PrintIfValid("Valor: ", priceMatcher);
        */
        PrintItemsFromDictionary();
    }

    public void HandleData(String text, PDDocument pdDocument) throws IOException
    {
        this.pdDocument = pdDocument;
        checkBankInstitution();

        stripper = CreatePDFStripperByArea();

        if (isSantander) {
            ExtractSantanderInvoiceData();
        } else {
            ExtractTextByArea();
        }

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

    private void ExtractSantanderInvoiceData() throws IOException {
        AddSantanderRects();
    }

    private void AddSantanderRects() throws IOException {
        TextPositionSequence position = null;

        /*
            public static final String BeneficiaryCode = "beneficiaryCode";
            public static final String ExpireAt = "expireAt";
            public static final String OurAt = "ourNumber";
            public static final String Price = "price";
            public static final String DocumentNumber = "documentNumber";
            public static final String Additions = "additions";
            public static final String ChargedValue = "chargedValue";
            public static final String ProcessDate = "processDate";
            public static final String Discount = "discount";
            public static final String Deductions = "deductions";
            public static final String fine = "fine";
            public static final String OtherAdditions = "otherAdditions";
        */

        position = findTerm("Beneficiário:");
        if(position != null) {
            Rectangle beneficiaryRect = new Rectangle((int)position.getX(), (int)position.getY() + 10, 270, 10);
            stripper.addRegion(Beneficiary, beneficiaryRect );

            Rectangle cnpjRect = new Rectangle((int)position.getX() + 30, (int)position.getY() + 20, 270, 10);
            stripper.addRegion(Cnpj, cnpjRect );
        }

        position = findTerm("Pagador:");
        if(position != null) {
            Rectangle payerRect = new Rectangle((int)position.getX(), (int)position.getY() + 20, 105, 10);
            stripper.addRegion( Payer, payerRect );

            Rectangle cpfRect = new Rectangle((int)position.getX() + 105, (int)position.getY() + 20, 105, 10);
            stripper.addRegion( Cpf, cpfRect );
        }


        if(infos.containsKey(Beneficiary))
            System.out.println(infos.get(Beneficiary).replace("\n", ""));

        if(infos.containsKey(Payer))
            System.out.println(infos.get(Payer).replace("\n", ""));


        if(regions.contains(Beneficiary))
            infos.put(Beneficiary, stripper.getTextForRegion( Beneficiary ));




        position = findTerm("Agência/Código Beneficiário:");
        if(position != null) {
            Rectangle beneficiaryRect = new Rectangle((int)position.getX(), (int)position.getY() + 10, 270, 10);
            stripper.addRegion( Beneficiary, beneficiaryRect );
        }

        position = findTerm("Agência/Código Beneficiário:");
        if(position != null) {
            Rectangle beneficiaryRect = new Rectangle((int)position.getX(), (int)position.getY() + 10, 270, 10);
            stripper.addRegion( Beneficiary, beneficiaryRect );
        }

    }


    private void exportData() {
        /*invoiceDto = new InvoiceDto();

        invoiceDto.cnpj = returnIfValid("CNPJ: ", cnpjMatcher);
        invoiceDto.agencyCode =  returnIfValid("Código Beneficiario: ", agencyCodePatternMatcher);
        invoiceDto.documentCode = returnIfValid("Código Boleto: ", invoiceCodeMatcher);
        invoiceDto.cpf = returnIfValid("CPF: ", cpfMatcher);
        invoiceDto.date = returnIfValid("Data de vencimento: ", dateMatcher);
        invoiceDto.price = returnIfValid("Valor: ", priceMatcher);
        String beneficiary = "";
        String payer = "";

        if(infos.containsKey(Beneficiary))
            beneficiary = infos.get(Beneficiary).replace("\n", "");

        if(infos.containsKey(Payer))
            payer = infos.get(Payer).replace("\n", "");

        invoiceDto.beneficiary = beneficiary;
        invoiceDto.payer = payer; */

        documentExporter.exportDocument(invoiceDto);
    }

    private void ExtractTextByArea() throws IOException {
        AddRects();

        PDPage firstPage = pdDocument.getPage(0);
        stripper.extractRegions( firstPage );

        CreateMap();
    }

    private PDFTextStripperByArea CreatePDFStripperByArea() throws IOException{
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition( true );

        return stripper;
    }

    private void AddRects() throws IOException {
        TextPositionSequence position = null;

        position = findTerm("Beneficiário");
        if(position != null) {
            Rectangle beneficiaryRect = new Rectangle((int)position.getX(), (int)position.getY() + 10, 270, 10);
            stripper.addRegion( Beneficiary, beneficiaryRect );
        }

        position = findTerm("Pagador:");
        if(position != null) {
            Rectangle payerRect = new Rectangle((int)position.getX(), (int)position.getY() + 20, 105, 10);
            stripper.addRegion( Payer, payerRect );
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

        if(regions.contains(Beneficiary))
            infos.put(Beneficiary, stripper.getTextForRegion( Beneficiary ));
        if(regions.contains(Payer))
            infos.put(Payer, stripper.getTextForRegion( Payer ));
    }

    private String returnIfValid(String prefix, Matcher matcher) {
        try {
            String value = matcher.group(0);
            return value;
        } catch (IllegalStateException ex) {
            System.out.println("Could not find " + prefix);
        }

        return "";
    }

    private void PrintItemsFromDictionary() {
        if(infos.containsKey(Beneficiary))
            System.out.println("Beneficiario: " + infos.get(Beneficiary));

        if(infos.containsKey(Payer))
            System.out.println("Pagador: " + infos.get(Payer));
    }
}
