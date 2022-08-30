package Handlers;

import Managers.PDFManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DataHandlerTest {

    final String filePath = Paths.get("tests","Resources").toAbsolutePath().toString();

    private DataHandler dataHandler;
    private PDFManager pdfManager;
    private PDDocument pdDocument;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUp() {
        pdfManager = new PDFManager();
        dataHandler = new DataHandler();

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void OnPrintData_WhenDataIsHandled_ShouldPrint() throws IOException {
        String pdfContent = "";

        pdDocument = pdfManager.InitializeDocument(filePath + "/document.pdf");
        pdfContent = pdfManager.toText();
        dataHandler.HandleData(pdfContent, pdDocument);
        dataHandler.PrintData();

        String expectedOutput = "CNPJ: 88.648.761/0001-03\nCódigo Beneficiario: 2087.7 8284989\nCódigo Boleto: 033998284.5 9890000048.4 7963520101.9 9 86140000165923\nCPF: 8614000016592\nData de vencimento: 08/05/2021\nValor: 1659,23\nBeneficiario: \n\nPagador: CPF: 019.\n\n";
        String consoleOutput = outContent.toString();
        assertThat(consoleOutput, is(expectedOutput));
    }
}