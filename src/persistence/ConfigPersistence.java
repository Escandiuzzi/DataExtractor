package persistence;

import dtos.ConfigDto;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigPersistence {

    public static String Invoice = "Boleto";
    public static String IR = "IR";

    private static String filePath;

    private Gson gson;

    private ConfigDto config;

    public ConfigPersistence() {
        this(Paths.get("").toAbsolutePath().toString() + "/config.json");
    }

    public ConfigPersistence(String filePath)
    {
        gson = new Gson();
        config = new ConfigDto();

        this.filePath = filePath;

        try {
            File configFile = new File(this.filePath);
            configFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("Could not create config file");
        }

        load();
    }

    public void setFileType(String fileTypeSelected) {
        config.fileTypeSelected = fileTypeSelected;
    }

    public String getFileType() {
        return config.fileTypeSelected;
    }

    public void setFrequency(String frequency) {
        config.frequency = frequency;
    }

    public String getFrequency() {
        return config.frequency;
    }

    public void setInputFolderPath(String inputFolderPath) {
        config.inputFolderPath = inputFolderPath;
    }

    public String getInputFolderPath() {
        return config.inputFolderPath;
    }

    public void setOutputFolderPath(String outputFolderPath) {
        config.outputFolderPath = outputFolderPath;
    }

    public String getOutputFolderPath() {
        return config.outputFolderPath;
    }

    public void setErrorFolderPath(String errorFolderPath) {
        config.errorFolderPath = errorFolderPath;
    }

    public String getErrorFolderPath() {
        return config.errorFolderPath;
    }

    public void save()
    {
        try {
            Files.writeString(Path.of(filePath), gson.toJson(config), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.print("Invalid Path");
        }
    }

    public void load() {

        try {
            Gson gson = new Gson();

            Reader reader = Files.newBufferedReader(Paths.get(filePath));

            config = gson.fromJson(reader, ConfigDto.class);
            reader.close();

            if (config == null)
                updateFileWithEmptyConfig();

            this.setFileType(config.fileTypeSelected);
            this.setFrequency(config.frequency);
            this.setInputFolderPath(config.inputFolderPath);
            this.setOutputFolderPath(config.outputFolderPath);
            this.setErrorFolderPath(config.errorFolderPath);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateFileWithEmptyConfig() {
        config = new ConfigDto();
        save();
    }
}
