package exporters;

import com.google.gson.Gson;
import dtos.InvoiceDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import persistence.ConfigPersistence;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DocumentExporterTest {

    final String filePath = Paths.get("tests","exporters", "files").toAbsolutePath().toString();

    private DocumentExporter documentExporter;

    @Mock
    private ConfigPersistence configPersistence;

    @Before
    public void setup() {
        configPersistence = mock(ConfigPersistence.class);
        documentExporter = new DocumentExporter(configPersistence);
    }

    @After
    public void tearDown() {
        Arrays.stream(new File(filePath).listFiles()).forEach(File::delete);
    }

    @Test
    public void onExportDocument_WhenMethodCalled_ShouldExportDataAsJson() {
        when(configPersistence.getOutputFolderPath()).thenReturn(filePath);

        InvoiceDto invoiceDto = getSimpleInvoiceDto();

        try {
            documentExporter.exportDocument(invoiceDto);
        } catch(Exception ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void onExportDocument_WhenJsonIsCreated_ShouldContainInvoiceInfo() {
        when(configPersistence.getOutputFolderPath()).thenReturn(filePath);

        InvoiceDto loadedInvoice = null;

        InvoiceDto invoiceDto = getSimpleInvoiceDto();
        documentExporter.exportDocument(invoiceDto);

        try {
            Gson gson = new Gson();

            String path = Arrays.stream(new File(filePath).listFiles()).findFirst().get().getAbsolutePath();
            Reader reader = Files.newBufferedReader(Paths.get(path));

            loadedInvoice = gson.fromJson(reader, InvoiceDto.class);
            reader.close();

        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }

        Assert.assertTrue(new ReflectionEquals(invoiceDto).matches(loadedInvoice));
    }

    private InvoiceDto getSimpleInvoiceDto() {
        InvoiceDto invoiceDto = new InvoiceDto();

        invoiceDto.cnpj = "88.648.761/0001-03";
        invoiceDto.agencyCode = "2087.7 8284989";
        invoiceDto.documentCode = "33998284.5 9890000048.4 7963520101.9 9 86140000165923";
        invoiceDto.cpf = "648.846.048-06";
        invoiceDto.date = "08/05/2021";
        invoiceDto.money = "1659,23";
        invoiceDto.beneficiary = "FUNDAÇÃO UNIVERSIDADE DE CAXIAS DO SUL";
        invoiceDto.payer = "Luiz Felipe Escandiuzzi";

        return invoiceDto;
    }
}