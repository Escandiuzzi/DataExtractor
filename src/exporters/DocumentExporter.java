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

    public void createErrorLog(String message)
    {
        String date = getDate();

        try {
            Files.writeString(
                    Path.of(configPersistence.getErrorFolderPath() + "/error-" + date + ".txt"),
                    message,
                    StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.print(exception.getMessage());
        }
    }

    public void exportDocument(InvoiceDto invoice) {
        String json = gson.toJson(invoice);
        export(json);
    }

    public void exportDocument(String json) {
        export(json);
    }

    private void export(String json) {
        String date = getDate();

        try {
            Files.writeString(
                    Path.of(configPersistence.getOutputFolderPath() + "/document-" + date + ".json"),
                    json,
                    StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.print(exception.getMessage());
        }
    }

    private String getDate() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
    }
}
