package Managers;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PDFManager {
    final String Beneficiary = "beneficiary";
    final String Payer = "payer";

    private PDDocument pdDocument;
    private PDFParser parser;
    private COSDocument cosDocument;
    private PDFTextStripper pdfStripper;
    private String filePath;
    private File file;
    private Map<String, String> infos;

    public PDFManager() {

    }

    public Map<String, String> ExtractTextByArea() throws IOException {
        InitializeDocument();
        PDFTextStripperByArea stripper = CreatePDFStripperByArea();

        AddRects(stripper);

        PDPage firstPage = pdDocument.getPage(0);
        stripper.extractRegions( firstPage );

        CreateMap(stripper);
        return infos;
    }

    public String toText() throws IOException {
        InitializeDocument();
        return pdfStripper.getText(pdDocument);
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private void InitializeDocument() throws IOException {
        file = new File(filePath);
        parser = new PDFParser(new RandomAccessFile(file, "r")); // update for PDFBox V 2.0

        parser.parse();
        cosDocument = parser.getDocument();
        pdfStripper = new PDFTextStripper();
        pdfStripper.setSortByPosition(true);

        pdDocument = new PDDocument(cosDocument);

        pdDocument.getNumberOfPages();

        pdfStripper.setStartPage(0);
        pdfStripper.setEndPage(pdDocument.getNumberOfPages());
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
}