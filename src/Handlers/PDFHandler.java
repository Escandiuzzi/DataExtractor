package Handlers;

import Utils.TextPositionSequence;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFHandler {
    private PDDocument pdDocument;
    private String filePath;

    public PDFHandler() {

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