package Managers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PDFManagerTest {
    final String filePath = Paths.get("tests","Resources").toAbsolutePath().toString();
    private PDFManager pdfManager;

    @Before
    public void setup(){
        pdfManager = new PDFManager();
    }

    @Test
    public void OnToText_WhenParsingAFile_ShouldReturnPDFToString() {
        String result = "";
        try {
            pdfManager.InitializeDocument(filePath + "/test.pdf");
            result = pdfManager.toText();
        } catch (IOException ex) {
            assertNotNull(ex);
        }

        String expectedOutput = "Sample PDF generated for JUnit DataExtraction test \n";
        assertThat(result, is(expectedOutput));
    }

    @Test
    public void OnDocumentInitialize_WhenAValidFilePathIsSet_ShouldReturnANewDocument() {
        PDDocument pdDocument = null;
        try {
            pdDocument = pdfManager.InitializeDocument(filePath + "/document.pdf");
        } catch (IOException ex) {
            assertNotNull(ex);
        }

        assertNotNull(pdDocument);
    }
}