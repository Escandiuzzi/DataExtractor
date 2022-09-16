package Persistence;

import com.google.gson.Gson;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {

    private String fileTypeSelected;

    private String frequency;

    private String inputFolderPath;

    private String outputFolderPath;

    private String errorFolderPath;


    public Config() {
    }

    public void setFileType(String fileTypeSelected) {
        this.fileTypeSelected = fileTypeSelected;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setInputFolderPath(String inputFolderPath) {
        this.inputFolderPath = inputFolderPath;
    }

    public void setOutputFolderPath(String outputFolderPath) {
        this.outputFolderPath = outputFolderPath;
    }

    public void setErrorFolderPath(String errorFolderPath) {
        this.errorFolderPath = errorFolderPath;
    }

    public void save()
    {
        Gson gson = new Gson();
        String json = gson.toJson(this);

        try {

            Path currentRelativePath = Paths.get("");
            String path = currentRelativePath.toAbsolutePath().toString();
            Files.writeString(Path.of(path + "/config.json"), json,
                    StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.print("Invalid Path");
        }

    }
}
