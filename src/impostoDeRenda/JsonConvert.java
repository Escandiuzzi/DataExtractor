package impostoDeRenda;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonConvert {

    Gson gson;
    ImpostoDeRenda imposto;

    public static String ImpostoPath = Paths.get("").toAbsolutePath().toString() + "/imposto.json";

    public ImpostoDeRenda ReadImposto() {
        gson = new Gson();
        imposto = new ImpostoDeRenda();

        try {
            File configFile = new File(ImpostoPath);
            configFile.createNewFile();
        } catch (IOException exception) {
            System.out.println("Could not create config file");
        }

        imposto = load();
        return imposto;

    }

    public ImpostoDeRenda load() {

        try {
            Gson gson = new Gson();

            Reader reader = Files.newBufferedReader(Paths.get(ImpostoPath));

            imposto = gson.fromJson(reader, ImpostoDeRenda.class);

            // System.out.println(imposto.sessoes[0].nome);
            reader.close();

            // if (imposto == null)
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

        return imposto;

    }

}
