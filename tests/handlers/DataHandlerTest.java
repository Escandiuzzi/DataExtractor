package handlers;

import dtos.InvoiceDto;
import exporters.DocumentExporter;
import extractors.PDFExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import persistence.ConfigPersistence;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DataHandlerTest {

    final String filePath = Paths.get("tests", "resources").toAbsolutePath().toString();

    private DataHandler dataHandler;
    private PDFExtractor pdfManager;
    private PDDocument pdDocument;

    @Mock
    DocumentExporter documentExporter;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @Before
    public void setUp() {
        pdfManager = new PDFExtractor();
        dataHandler = new DataHandler(documentExporter, new ConfigPersistence());

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void onPrintData_WhenDataIsHandled_ShouldPrint() throws IOException {
        String pdfContent = "";
        pdDocument = pdfManager.InitializeDocument(filePath +  File.separator + "boleto.pdf");
        pdfContent = pdfManager.getDocumentData();
        dataHandler.HandleData(pdDocument);

        dataHandler.PrintData();

        String expectedOutput = "Document Code: 033998284.5 9890000048.4 7963520101.9 9 86140000165923\n" +
                "Beneficiary: FUNDAÇÃO UNIVERSIDADE DE CAXIAS DO SUL\n" +
                "Cnpj: 88.648.761/0001-03\n" +
                "Payer: Luiz Felipe Escandiuzzi\n" +
                "Cpf: 019.723.190-00\n" +
                "BeneficiaryCode: 2087.7 8284989\n" +
                "DueDate: 08/05/2021\n" +
                "OurNumber: 000004879635-2\n" +
                "DocumentPrice: 1659,23\n" +
                "DocumentNumber: 11928792\n" +
                "Addition: \n" +
                "ChargedValue: \n" +
                "DocumentDate: 03/05/2021\n" +
                "Discount: \n" +
                "Currency: R$\n" +
                "OtherDeductions: \n" +
                "Penalty: \n";
        String consoleOutput = outContent.toString();
        assertThat(consoleOutput, is(expectedOutput));
    }

    @Test
    public void onGetInvoiceDto_WhenDataWasHandled_ShouldFillInvoiceWithData() throws IOException {
        String pdfContent = "";

        pdDocument = pdfManager.InitializeDocument(filePath + File.separator + "boleto.pdf");
        pdfContent = pdfManager.getDocumentData();
        dataHandler.HandleData(pdDocument);

        InvoiceDto invoiceDto = dataHandler.getInvoiceDto();

        verify(documentExporter, times(1)).exportDocument(invoiceDto);

        assertEquals("88.648.761/0001-03", invoiceDto.cnpj);
        assertEquals("2087.7 8284989", invoiceDto.beneficiaryCode);
        assertEquals("033998284.5 9890000048.4 7963520101.9 9 86140000165923", invoiceDto.documentNumber);
        assertEquals("019.723.190-00", invoiceDto.cpf);
        assertEquals("08/05/2021", invoiceDto.dueDate);
        assertEquals("1659,23", invoiceDto.documentPrice);
        assertEquals("FUNDAÇÃO UNIVERSIDADE DE CAXIAS DO SUL", invoiceDto.beneficiary);
        assertEquals("Luiz Felipe Escandiuzzi", invoiceDto.payer);
    }
}