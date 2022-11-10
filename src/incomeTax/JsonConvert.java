package incomeTax;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonConvert {

    Gson gson;
    IncomeTax incomeTax;

    public static String incomeTaxPath = Paths.get("").toAbsolutePath().toString() + "/imposto.json";

    public IncomeTax ReadIncomeTax() {
        gson = new Gson();
        incomeTax = new IncomeTax();

        try {
            File configFile = new File(incomeTaxPath);
            configFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("Could not create config file");
        }

        incomeTax = load();
        return incomeTax;

    }

    public IncomeTax load() {

        try {
            Gson gson = new Gson();

            Reader reader = Files.newBufferedReader(Paths.get(incomeTaxPath));

            incomeTax = gson.fromJson(reader, IncomeTax.class);

            // System.out.println(incomeTax.sections[0].name);
            reader.close();

            // if (incomeTax == null)
                // updateFileWithEmptyConfig();

            /*
                this.setFileType(config.fileTypeSelected);
                this.setFrequency(config.frequency);
                this.setInputFolderPath(config.inputFolderPath);
                this.setOutputFolderPath(config.outputFolderPath);
                this.setErrorFolderPath(config.errorFolderPath);

             */

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return incomeTax;

    }

}
