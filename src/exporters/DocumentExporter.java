package exporters;

import com.google.gson.Gson;
import dtos.InvoiceDto;
import persistence.ConfigPersistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;

public class DocumentExporter {

    private ConfigPersistence configPersistence;

    private Gson gson;

    public DocumentExporter(ConfigPersistence configPersistence) {
        this.configPersistence = configPersistence;
        gson = new Gson();
    }

    public void exportDocument(InvoiceDto invoice) {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        try {
            Files.writeString(Path.of(configPersistence.getOutputFolderPath() + "/document-" + date
                            + ".json")
                    ,gson.toJson(invoice), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.print(exception.getMessage());
        }
    }
}
