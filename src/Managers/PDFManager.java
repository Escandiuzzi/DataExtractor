package Managers;

import Utils.TextPositionSequence;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFManager {
    private PDDocument pdDocument;
    private String filePath;

    public PDFManager() {

    }

    public String toText() throws IOException {

        File file = new File(filePath);
        PDFParser parser = new PDFParser(new RandomAccessFile(file, "r")); // update for PDFBox V 2.0

        parser.parse();
        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper pdfStripper = new PDFTextStripper();

        PDDocument pdDoc = new PDDocument(cosDoc);
        pdDoc.getNumberOfPages();

        pdfStripper.setStartPage(0);
        pdfStripper.setEndPage(pdDoc.getNumberOfPages());

        return pdfStripper.getText(pdDoc);
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void printSubWords(String searchTerm) throws IOException
    {
        System.out.printf("* Looking for '%s'\n", searchTerm);

        File file = new File(filePath);
        pdDocument = PDDocument.load(file);

        for (int page = 1; page <= pdDocument.getNumberOfPages(); page++)
        {
            List<TextPositionSequence> hits = findSubWords(page, searchTerm);
            for (TextPositionSequence hit : hits)
            {
                TextPosition lastPosition = hit.textPositionAt(hit.length() - 1);

                System.out.printf("Page %s at x: %s, y: %s with width %s and last letter '%s' at x: %s, y: %s\n",
                        page, hit.getX(), hit.getY(), hit.getWidth(),
                        lastPosition.getUnicode(), lastPosition.getXDirAdj(), lastPosition.getYDirAdj());
            }
        }
    }

    private List<TextPositionSequence> findSubWords(int page, String searchTerm) throws IOException
    {
        final List<TextPositionSequence> hits = new ArrayList<TextPositionSequence>();
        PDFTextStripper stripper = new PDFTextStripper()
        {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException
            {
                TextPositionSequence word = new TextPositionSequence(textPositions);
                String string = word.toString();

                int fromIndex = 0;
                int index;
                while ((index = string.indexOf(searchTerm, fromIndex)) > -1)
                {
                    hits.add(word.subSequence(index, index + searchTerm.length()));
                    fromIndex = index + 1;
                }
                super.writeString(text, textPositions);
            }
        };

        stripper.setSortByPosition(true);
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        stripper.getText(pdDocument);

        return hits;
    }
}