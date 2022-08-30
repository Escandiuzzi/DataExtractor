package Managers;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void OnExtractTextByArea_WhenParsingAFile_ShouldReturnMapWithFields() {
        pdfManager.setFilePath(filePath + "/boleto.pdf");
        Map<String,String> result = new HashMap<>();

        try {
            result = pdfManager.ExtractTextByArea();
        } catch (IOException ex) {
            assertNotNull(ex);
        }

        assertThat(result.get("beneficiary"), is("FUNDAÇÃO UNIVERSIDADE DE CAXIAS DO SUL\n"));
        assertThat(result.get("payer"), is("Luiz Felipe Escandiuzzi\n"));
    }
}