package Managers;

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
    public void toText() {
        pdfManager.setFilePath(filePath + "/test.pdf");
        String result = "";

        try {
           result = pdfManager.toText();
           String result2 = "";
        } catch (IOException ex) {
            assertNotNull(ex);
        }

        String expectedOutput = "Sample PDF generated for JUnit DataExtraction test \n";
        assertThat(result, is(expectedOutput));
    }
}