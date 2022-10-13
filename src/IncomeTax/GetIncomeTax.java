package IncomeTax;

import java.io.IOException;
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

                            for (int j = 0; j< section.fields.length; j++){

                                section.fields[j].getContentField(section, document, res, nextResult);

                            }

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

    }
}
