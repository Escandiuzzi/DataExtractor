package Handlers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataHandler {
    final String Beneficiary = "beneficiary";
    final String Payer = "payer";

    final Pattern cnpjPattern = Pattern.compile("([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})\n");
    final Pattern agencyCodePattern = Pattern.compile("\\d{4}(\\.\\d{1}) \\d{7}");
    final Pattern documentCodePattern = Pattern.compile("(\\d{9}\\.\\d{1} \\d{10}\\.\\d{1} \\d{10}\\.\\d{1} \\d \\d{14})");
    final Pattern cpfPattern = Pattern.compile("\\d{3}.\\d{3}.\\d{3}-?\\d{2}" );
    final Pattern datePattern = Pattern.compile("([0-9]{2})/([0-9]{2})/([0-9]{4})");
    final Pattern moneyPattern = Pattern.compile("\\d{1,4}(\\.\\d{3})*,\\d{2}");

    private Matcher cnpjMatcher;
    private Matcher agencyCodePatternMatcher;
    private Matcher documentCodeMatcher;
    private Matcher cpfMatcher;
    private Matcher dateMatcher;
    private Matcher moneyMatcher;

    private Map<String, String> infos;

    public void PrintData() {

        PrintIfValid("CNPJ: ", cnpjMatcher);
        PrintIfValid("Código Beneficiario: ", agencyCodePatternMatcher);
        PrintIfValid("Código Boleto: ", documentCodeMatcher);
        PrintIfValid("CPF: ", cpfMatcher);
        PrintIfValid("Data de vencimento: ", dateMatcher);
        PrintIfValid("Valor: ", moneyMatcher);
        PrintItemsFromDictionary();
    }

    public void HandleData(String text, PDDocument pdDocument) throws IOException
    {
        cnpjMatcher = cnpjPattern.matcher(text);
        cnpjMatcher.find();

        cnpjMatcher = cnpjPattern.matcher(text);
        cnpjMatcher.find();

        agencyCodePatternMatcher = agencyCodePattern.matcher(text);
        agencyCodePatternMatcher.find();

        documentCodeMatcher = documentCodePattern.matcher(text);
        documentCodeMatcher.find();

        cpfMatcher = cpfPattern.matcher(text);
        cpfMatcher.find();

        dateMatcher = datePattern.matcher(text);
        dateMatcher.find();

        moneyMatcher = moneyPattern.matcher(text);
        moneyMatcher.find();

        ExtractTextByArea(pdDocument);
    }

    private void ExtractTextByArea(PDDocument pdDocument) throws IOException {
        PDFTextStripperByArea stripper = CreatePDFStripperByArea();
        AddRects(stripper);
        PDPage firstPage = pdDocument.getPage(0);
        stripper.extractRegions( firstPage );

        CreateMap(stripper);
    }

    private PDFTextStripperByArea CreatePDFStripperByArea() throws IOException{
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition( true );

        return stripper;
    }

    private void AddRects(PDFTextStripperByArea stripper) {
        Rectangle beneficiaryRect = new Rectangle( 10, 60, 275, 10 );
        stripper.addRegion( Beneficiary, beneficiaryRect );

        Rectangle payerRect = new Rectangle( 10, 110, 120, 10 );
        stripper.addRegion( Payer, payerRect );
    }

    private void CreateMap(PDFTextStripperByArea stripper) {
        infos = new HashMap<String, String>();
        infos.put(Beneficiary, stripper.getTextForRegion( Beneficiary ));
        infos.put(Payer, stripper.getTextForRegion( Payer ));
    }

    private void PrintIfValid(String prefix, Matcher matcher) {
        try {
            System.out.println(prefix + matcher.group(0));
        } catch (IllegalStateException ex) {
            System.out.println("Could not find " + prefix);
        }
    }
    private void PrintItemsFromDictionary() {
        if(infos.containsKey(Beneficiary))
            System.out.println("Beneficiario: " + infos.get(Beneficiary));

        if(infos.containsKey(Payer))
            System.out.println("Pagador: " + infos.get(Payer));
    }
}
