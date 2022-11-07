package IncomeTax;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import dtos.InvoiceDto;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.List;

public class GetIncomeTax {

    public static void main(String[] args) {

        /*
        Serialized
        IncomeTax incomeTax = new IncomeTax();
        Gson gson = new Gson();
        String json = gson.toJson(incomeTax);
        System.out.println(json);

        */



        /*
        No fim adiciona: Para Salvar Gson no arquivo
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        try {
            Files.writeString(Path.of( "C:\\document-" + date + ".json"), gson.toJson(incomeTaxTeste), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.print(exception.getMessage());
        }
         */



        //Gson gson = new Gson();
        //String jsonTeste = gson.toJson(incomeTaxTeste);
        //System.out.println(jsonTeste);


        Gson gson = new Gson();
        IncomeTax incomeTaxTeste = new IncomeTax();
        JsonElement jsonElement = gson.toJsonTree(incomeTaxTeste);
        //jsonElement.getAsJsonObject().addProperty("url_to_user", "teste");
        //String jsonTeste = gson.toJson(jsonElement);
        //System.out.println(jsonTeste);



        // Desserialized

        JsonConvert json = new JsonConvert();

        IncomeTax incomeTax = json.ReadIncomeTax();

        ExtractData document = new ExtractData();

        if (incomeTax == null) {
            return;
        }

        try {
            document.openDocument("C:\\imposto.pdf");

            for (int i = 0; i<incomeTax.sections.length; i++){

                Section section = incomeTax.sections[i];

                try {
                    List<ExtractData.Result> nextResult = null;
                    List<ExtractData.Result> result = document.getSubwords(section.name, 0, 0, 5000, 0);

                    int sum = 0;
                    int lastPage = 0;

                    for(ExtractData.Result res: result){
                        if (res == null){
                            System.out.println("Não encontrou posicao do termo " + section.name);
                        } else {
                            System.out.println("RESULT: " + section.name + " X: " + res.getPosition().getX() + " Y: " + res.getPosition().getY() + " PAGE: " + res.getPage());

                            if ((i + 1) < incomeTax.sections.length){
                                nextResult = document.getSubwords(incomeTax.sections[i+1].name, 0, 0, 5000, 0);
                            }

                            if (sum > 0 && nextResult != null){

                                if(lastPage == res.getPage() || (res.getPage() - lastPage) > 1){
                                    break;
                                }

                            }

                            Gson gsonSection = new Gson();
                            JsonElement jsonElementSection = gsonSection.toJsonTree(incomeTaxTeste);

                            for (int j = 0; j< section.fields.length; j++){

                                section.fields[j].getContentField(section, document, res, nextResult);
                                section.fields[j].getObjectFromField(section.fields[j], jsonElementSection);

                            }

                            String jsonSection = gsonSection.toJson(jsonElementSection);
                            jsonElement.getAsJsonObject().addProperty(section.name, jsonSection);

                            ++sum;
                            lastPage = res.getPage();
                        }
                    }

                } catch (IOException dfg) {
                    System.out.println(dfg);
                }

            }

        } catch (IOException error) {
            System.out.println(error);
        }

        String jsonTeste = gson.toJson(jsonElement);
        System.out.println(jsonTeste);

        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        try {
            Files.writeString(Path.of( "C:\\Users\\Hobuu\\Documents\\document-" + date + ".json"), gson.toJson(jsonElement), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.print("ERRO: " + exception.getMessage());
        }

    }

}
