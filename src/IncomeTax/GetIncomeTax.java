package IncomeTax;

import utils.TextPositionSequence;

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

        try {
            document.openDocument("C:\\imposto.pdf");

            if (incomeTax != null) {
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

                            /*
                                if (nextResult == null){
                                    continue;
                                }

                             */

                        }

                        System.out.println("RESULT: " + section.name + " X: " + result.getPosition().getX() + " Y: " + result.getPosition().getY() + " PAGE: " + result.getPage());

                        for (int j = 0; j< section.fields.length; j++){

                            Field field = section.fields[j];

                            try {

                                ExtractData.Result campoResult = document.getSubwords(field.name, result.getPage(), (int)result.getPosition().getY(), nextResult == null ? 0 : (int)nextResult.getPosition().getY(), nextResult == null ? result.getPage() + 1 :nextResult.getPage());

                                if (campoResult == null){
                                    System.out.println("Não encontrou posicao do termo " + field.name + " page: " + result.getPage());
                                    continue;
                                }

                                TextPositionSequence position = campoResult.getPosition();

                                String content = "";

                                if (field.table){
                                    content = document.getTextByArea((int)position.getX() - 25, (int)position.getY() + (field.y > 0 ? field.y : field.height) , (int)position.getWidth(), field.height, field, campoResult.getPage() - 1);
                                } else {
                                    content = document.getTextByArea((int)position.getX() + (int)position.getWidth() + 10, (int)position.getY(), (int)position.getWidth() + 200, field.height, field, campoResult.getPage() - 1);
                                }

                                System.out.println( "Field: " + field.name + ": " + content);

                                if (section.table){

                                    String content2 = "a";

                                    int y = (int) position.getY() + section.numberOfLines;

                                    System.out.println("Section is table !!!");

                                    while (!content2.trim().isEmpty()) {

                                        if (nextResult != null){
                                            if (nextResult.getPosition().getY() < y && nextResult.getPage() == result.getPage() ){
                                                break;
                                            }
                                        }
                                        content2 = "";

                                        y += section.numberOfLines;

                                        System.out.println("Position Get Y " + y);

                                        if (field.table) {
                                            content2 = document.getTextByArea((int) position.getX() - 25, y, (int) position.getWidth(), field.height, field, campoResult.getPage() - 1);
                                        } else {
                                            content2 = document.getTextByArea((int) position.getX() + (int) position.getWidth() + 10, y, (int) position.getWidth() + 200, field.height, field, campoResult.getPage() - 1);
                                        }

                                        if (!content2.trim().isEmpty()){
                                            System.out.println("Field 2: " + field.name + ": " + content2);
                                        }

                                    }

                                }

                                // System.out.println("RESULT CAMPO: " + field.name + " X: " + position.getX() + " Y: " + position.getY() + " PAGE: " + campoResult.getPage());


                            } catch (IOException dfg) {
                                System.out.println(dfg);
                            }

                        }


                    } catch (IOException dfg) {
                        System.out.println(dfg);
                    }

                }
            }

        } catch (IOException error) {
            System.out.println(error);
        }

    }
}
