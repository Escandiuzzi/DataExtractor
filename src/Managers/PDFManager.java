package Managers;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFManager {
    private PDDocument pdDocument;
    private String filePath;

    public PDFManager() {

    }

    public String toText() throws IOException {
        File file = new File(filePath);
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