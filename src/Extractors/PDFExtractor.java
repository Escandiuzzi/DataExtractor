package Extractors;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFExtractor {
    private PDDocument pdDocument;
    private PDFParser parser;
    private COSDocument cosDocument;
    private PDFTextStripper pdfStripper;
    private File file;

    public PDFExtractor() {

    }

    public PDDocument InitializeDocument(String filePath) throws IOException {
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

        return pdDocument;
    }

    public String toText() throws IOException {
        return pdfStripper.getText(pdDocument);
    }
}