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
    @Mock
    ConfigPersistence configPersistence;


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;


    @Before
    public void setUp() {
        pdfManager = new PDFExtractor();
        dataHandler = new DataHandler(documentExporter, configPersistence);

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
        pdDocument = pdfManager.InitializeDocument(filePath +  File.separator + "boleto.pdf");
        dataHandler.HandleData(pdDocument);

        dataHandler.PrintData();

        String expectedOutput = "InvoiceCode: 033998284.5 9890000048.4 7963520101.9 9 86140000165923\r\n" +
                "Beneficiary: FUNDAÇÃO UNIVERSIDADE DE CAXIAS DO SUL\r\n" +
                "Cnpj: 88.648.761/0001-03\r\n" +
                "Payer: Luiz Felipe Escandiuzzi\r\n" +
                "Cpf: 019.723.190-00\r\n" +
                "BeneficiaryCode: 2087.7 8284989\r\n" +
                "DueDate: 08/05/2021\r\n" +
                "OurNumber: 000004879635-2\r\n" +
                "DocumentPrice: 1659,23\r\n" +
                "DocumentNumber: 11928792\r\n" +
                "Addition: \r\n" +
                "ChargedValue: \r\n" +
                "DocumentDate: 03/05/2021\r\n" +
                "Discount: \r\n" +
                "Currency: R$\r\n" +
                "OtherDeductions: \r\n" +
                "Penalty: \r\n" +
                "InvoiceCode: 033998284.5 9890000048.4 7963520101.9 9 86140000165923\r\n" +
                "Beneficiary: FUNDAÇÃO UNIVERSIDADE DE CAXIAS DO SUL\r\n" +
                "Cnpj: 88.648.761/0001-03\r\n" +
                "Payer: Luiz Felipe Escandiuzzi\r\n" +
                "Cpf: 019.723.190-00\r\n" +
                "BeneficiaryCode: 2087.7 8284989\r\n" +
                "DueDate: 08/05/2021\r\n" +
                "OurNumber: 000004879635-2\r\n" +
                "DocumentPrice: 1659,23\r\n" +
                "DocumentNumber: 11928792\r\n" +
                "Addition: \r\n" +
                "ChargedValue: \r\n" +
                "DocumentDate: 03/05/2021\r\n" +
                "Discount: \r\n" +
                "Currency: R$\r\n" +
                "OtherDeductions: \r\n" +
                "Penalty: \r\n";
        String consoleOutput = outContent.toString();
        assertThat(consoleOutput, is(expectedOutput));
    }

    @Test
    public void onGetInvoiceDto_WhenDataWasHandled_ShouldFillInvoiceWithData() throws IOException {
        pdDocument = pdfManager.InitializeDocument(filePath + File.separator + "santander.pdf");

        when(configPersistence.getBeneficiaryConfig()).thenReturn(true);
        when(configPersistence.getPayerConfig()).thenReturn(true);
        when(configPersistence.getCnpjConfig()).thenReturn(true);
        when(configPersistence.getBeneficiaryCodeConfig()).thenReturn(true);
        when(configPersistence.getDocumentNumberConfig()).thenReturn(true);
        when(configPersistence.getCpfConfig()).thenReturn(true);
        when(configPersistence.getDueDateConfig()).thenReturn(true);
        when(configPersistence.getDocumentPriceConfig()).thenReturn(true);

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