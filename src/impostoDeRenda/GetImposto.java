package impostoDeRenda;

import utils.TextPositionSequence;

import java.io.IOException;

public class GetImposto {

    public static void main(String[] args) {

        /*
        Serialized
        ImpostoDeRenda imposto = new ImpostoDeRenda();
        Gson gson = new Gson();
        String json = gson.toJson(imposto);
        System.out.println(json);

        */


        // Desserialized

        JsonConvert json = new JsonConvert();

        ImpostoDeRenda imposto = json.ReadImposto();

        ExtractData teste = new ExtractData();

        try {
            teste.openDocument("C:\\imposto.pdf");

            if (imposto != null) {
                for (int i=0; i<imposto.sessoes.length; i++){

                    Sessao sessao = imposto.sessoes[i];

                    try {
                        ExtractData.Result nextResult = null;
                        ExtractData.Result result = teste.getSubwords(sessao.nome, 0, 0, 5000, 0);

                        if (result == null){
                            System.out.println("Não encontrou posicao do termo " + sessao.nome);
                            continue;
                        }

                        if ((i + 1) < imposto.sessoes.length){
                            nextResult = teste.getSubwords(imposto.sessoes[i+1].nome, 0, 0, 5000, 0);

                            /*
                                if (nextResult == null){
                                    continue;
                                }

                             */

                        }

                        System.out.println("RESULT: " + sessao.nome + " X: " + result.getPosition().getX() + " Y: " + result.getPosition().getY() + " PAGE: " + result.getPage());

                        int heightTable = 0;

                        for (int j=0; j<sessao.campos.length; j++){

                            Campo campo = sessao.campos[j];

                            try {

                                ExtractData.Result campoResult = teste.getSubwords(campo.nome, result.getPage(), (int)result.getPosition().getY(), nextResult == null ? 0 : (int)nextResult.getPosition().getY(), nextResult == null ? result.getPage() + 1 :nextResult.getPage());

                                if (campoResult == null){
                                    System.out.println("Não encontrou posicao do termo " + campo.nome + " page: " + result.getPage());
                                    continue;
                                }

                                TextPositionSequence position = campoResult.getPosition();

                                String conteudo = "";

                                if (campo.tabela){
                                    conteudo = teste.getTextByArea((int)position.getX() - 25, (int)position.getY() + campo.altura, (int)position.getWidth(), campo.altura, campo, campoResult.getPage() - 1);
                                } else {
                                    heightTable += campo.altura;
                                    conteudo = teste.getTextByArea((int)position.getX() + (int)position.getWidth() + 10, (int)position.getY(), (int)position.getWidth() + 200, campo.altura, campo, campoResult.getPage() - 1);
                                }

                                System.out.println( "Campo: " + campo.nome + ": " + conteudo);

                                // System.out.println("RESULT CAMPO: " + campo.nome + " X: " + position.getX() + " Y: " + position.getY() + " PAGE: " + campoResult.getPage());


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
