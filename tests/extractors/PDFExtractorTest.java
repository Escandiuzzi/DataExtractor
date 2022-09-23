package extractors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PDFExtractorTest {
    final String filePath = Paths.get("tests", "resources").toAbsolutePath().toString();
    private PDFExtractor pdfManager;

    @Before
    public void setup(){
        pdfManager = new PDFExtractor();
    }

    @Test
    public void onGetDocumentData_WhenParsingAFile_ShouldReturnPDFToString() {
        String result = "";
        try {
            pdfManager.InitializeDocument(filePath +  File.separator + "test.pdf");
            result = pdfManager.getDocumentData();
        } catch (IOException ex) {
            assertNotNull(ex);
        }

        String expectedOutput = "Sample PDF generated for JUnit DataExtraction test \n";
        assertThat(result, is(expectedOutput));
    }

    @Test
    public void onDocumentInitialize_WhenAValidFilePathIsSet_ShouldReturnANewDocument() {
        PDDocument pdDocument = null;
        try {
            pdDocument = pdfManager.InitializeDocument(filePath +  File.separator + "document.pdf");
        } catch (IOException ex) {
            assertNotNull(ex);
        }

        assertNotNull(pdDocument);
    }
}