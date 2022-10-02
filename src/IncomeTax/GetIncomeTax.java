package IncomeTax;

import java.io.IOException;

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
                    ExtractData.Result nextResult = null;
                    ExtractData.Result result = document.getSubwords(section.name, 0, 0, 5000, 0);

                    if (result == null){
                        System.out.println("Não encontrou posicao do termo " + section.name);
                        continue;
                    }

                    if ((i + 1) < incomeTax.sections.length){
                        nextResult = document.getSubwords(incomeTax.sections[i+1].name, 0, 0, 5000, 0);
                    }

                    System.out.println("RESULT: " + section.name + " X: " + result.getPosition().getX() + " Y: " + result.getPosition().getY() + " PAGE: " + result.getPage());

                    for (int j = 0; j< section.fields.length; j++){

                        section.fields[j].getContentField(section, document, result, nextResult);

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
