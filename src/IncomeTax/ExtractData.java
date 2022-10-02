package IncomeTax;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import utils.TextPositionSequence;
import java.awt.Rectangle;

public class ExtractData {

    private PDDocument document = null;

    public void openDocument(String path) throws IOException {
        document = PDDocument.load(new File(path));
    }

    final class Result {
        public int page;
        public TextPositionSequence position;

        public Result(int page, TextPositionSequence position){
            this.page = page;
            this.position = position;
        }

        public int getPage(){
            return this.page;
        }

        public TextPositionSequence getPosition(){
            return this.position;
        }
    }

    List<TextPositionSequence> findSubwords(int page, String searchTerm, int yIni, int yFinal, int nextPage) throws IOException
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
                     TextPositionSequence hit = word.subSequence(index, index + searchTerm.length());

                      if (nextPage != page || (hit.getY() > yIni && (hit.getY() < yFinal || yFinal == 0))){
                        hits.add(hit);
                      }

                      fromIndex = index + 1;
                }
                super.writeString(text, textPositions);
            }
        };

        stripper.setSortByPosition(true);
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        stripper.getText(document);
        return hits;
    }


    public Result getSubwords(String searchTerm, int page, int yIni, int yFinal, int nextPage) throws IOException
    {
        int lastPage = page;

        if (page == 0) {
            lastPage = document.getNumberOfPages();
        }

        List<TextPositionSequence> hits = new ArrayList<>();

        for (; page <= lastPage; page++)
        {
            List<TextPositionSequence> results = findSubwords(page, searchTerm, yIni, yFinal, nextPage);
            for (TextPositionSequence result : results)
            {
                hits.add(result);
            }

            if (hits.size() > 0){
                break;
            }
        }

        if (hits.size() == 0){
            return null;
        }

        return new Result(page, hits.get(0));
    }

    public String getTextByArea(int x, int y, int width, int height, Field field, int page ) throws IOException {

        if (field.table){
            height +=20;
        }

        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition( true );
        Rectangle rect = new Rectangle( field.x > 0 ? field.x : x, y, field.width > 0 ? field.width : width, height);
        stripper.addRegion( field.name, rect );
        stripper.extractRegions( document.getPage(page) );
        // System.out.println( "Text in the area:" + rect );

        return stripper.getTextForRegion( field.name );
    }
}
