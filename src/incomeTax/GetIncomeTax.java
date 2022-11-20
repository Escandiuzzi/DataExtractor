package incomeTax;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import exporters.DocumentExporter;
import persistence.ConfigPersistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetIncomeTax {

    private ConfigPersistence configPersistence;
    private DocumentExporter documentExporter;

    public GetIncomeTax(ConfigPersistence configPersistence) {
        this.configPersistence = configPersistence;
        this.documentExporter = new DocumentExporter(this.configPersistence);
    }

    public void process(String filepath) {
        Gson gson = new Gson();

        IncomeTax incomeTaxJson = new IncomeTax();
        JsonElement jsonElement = gson.toJsonTree(incomeTaxJson);

        JsonConvert json = new JsonConvert(configPersistence.getConfigFolderPath());
        IncomeTax incomeTax = json.ReadIncomeTax();

        ExtractData document = new ExtractData();

        if (incomeTax == null) {
            return;
        }

        try {
            document.openDocument(filepath);

            for (int i = 0; i<incomeTax.sections.length; i++){

                Section section = incomeTax.sections[i];

                try {
                    List<ExtractData.Result> nextResult = null;
                    List<ExtractData.Result> result = document.getSubwords(section.name, 0, 0, 5000, 0);

                    int sum = 0;
                    int lastPage = 0;

                    if(result != null) {
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

                                ArrayList<Field> fields = new ArrayList<>();

                                for (int j = 0; j< section.fields.length; j++){

                                    Field field = section.fields[j].getContentField(section, document, res, nextResult);

                                    if (field != null){
                                        fields.add(field);
                                    }
                                }

                                Gson gsonSection = new Gson();
                                JsonElement jsonElementSection = gsonSection.toJsonTree(incomeTaxJson);

                                if (section.table && fields.size() > 0 && fields.get(0).content != null && fields.get(0).content.size() > 1){

                                    JsonArray list = new JsonArray();

                                    for (int k=0; k<fields.get(0).content.size(); k++){

                                        Gson gsonArray = new Gson();
                                        JsonElement jsonElementArray = gsonArray.toJsonTree(new Object());

                                        for(Field field: fields) {
                                            if (field.content != null && field.content.size() > k && field.content.size() > 0){

                                                System.out.println("Add array " + field.name + " " +  field.content.get(k).trim() );
                                                jsonElementArray.getAsJsonObject().addProperty(field.name, field.content.get(k).trim());

                                            }
                                        }

                                        list.add(jsonElementArray);
                                    }

                                    jsonElement.getAsJsonObject().add(section.name, list);

                                } else {

                                    if (fields.size() > 0){

                                        for(Field field: fields) {
                                            field.getObjectFromField(field, jsonElementSection);
                                        }

                                        //String jsonSection = gsonSection.toJson(jsonElementSection);
                                        jsonElement.getAsJsonObject().add(section.name, jsonElementSection);

                                    }

                                }

                                ++sum;
                                lastPage = res.getPage();
                            }
                        }
                    }

                } catch (IOException dfg) {
                    System.out.println(dfg);
                }
            }

        } catch (IOException error) {
            System.out.println(error);
        }

        String outputJson = gson.toJson(jsonElement);
        System.out.println(outputJson);

        String jsonData = gson.toJson(jsonElement);

        documentExporter.exportDocument(jsonData);
    }
}
