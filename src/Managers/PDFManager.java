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
    private PDDocument pdDocument;
    private String filePath;
    private File file;
    private Map<String, String> infos;

    public PDFManager() {

    }

    public Map<String, String> ExtractTextByArea() throws IOException {
        try
        {
            file = new File(filePath);
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r")); // update for PDFBox V 2.0

            parser.parse();
            COSDocument cosDocument = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setSortByPosition(true);

            pdDocument = new PDDocument(cosDocument);
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition( true );

            Rectangle beneficiaryRect = new Rectangle( 10, 60, 275, 10 );
            stripper.addRegion( "beneficiary", beneficiaryRect );

            Rectangle payerRect = new Rectangle( 10, 110, 120, 10 );
            stripper.addRegion( "payer", payerRect );


            PDPage firstPage = pdDocument.getPage(0);
            stripper.extractRegions( firstPage );

            infos = new HashMap<String, String>();

            infos.put("beneficiary", stripper.getTextForRegion( "beneficiary" ));
            infos.put("payer", stripper.getTextForRegion( "payer" ));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return infos;
    }

    public String toText() throws IOException {
        file = new File(filePath);
        PDFParser parser = new PDFParser(new RandomAccessFile(file, "r")); // update for PDFBox V 2.0

        parser.parse();
        COSDocument cosDocument = parser.getDocument();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        pdfStripper.setSortByPosition(true);

        pdDocument = new PDDocument(cosDocument);
        pdDocument.getNumberOfPages();

        pdfStripper.setStartPage(0);
        pdfStripper.setEndPage(pdDocument.getNumberOfPages());

        return pdfStripper.getText(pdDocument);
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}