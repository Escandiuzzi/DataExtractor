package incomeTax;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import utils.TextPositionSequence;

import java.io.IOException;
import java.util.List;

public class Field {

    public String name;
    public String content;
    public Field[] content2; //Peço desculpa a todos que possam se sentir ofendidos
    public int x;
    public int y;
    public int height;
    public int width;
    public boolean table;
    public boolean hasNoContent;

    public void getContentField(Section section, ExtractData document, ExtractData.Result posSection, List<ExtractData.Result> nextPosSection){

        try {

            if (this.hasNoContent){

                System.out.println("Sem conteudo: " + this.name);
                section.setTable(this.table);

                for(int i=0; i<this.content2.length; i++){
                    this.content2[i].getContentField(section, document, posSection, nextPosSection);
                }

                return;
            }

            // System.out.println("Page: " + posSection.getPage() + " Y: " + (int)posSection.getPosition().getY() + " Next y: " + nextPosSection == null ? 0 : (int)nextPosSection.get(0).getPosition().getY() + " NextPage: " + nextPosSection == null ? posSection.getPage() + 1 : nextPosSection.get(0).getPage());

            List<ExtractData.Result> listCampoResult = document.getSubwords(this.name, posSection.getPage(), (int)posSection.getPosition().getY(), nextPosSection == null ? 0 : (int)nextPosSection.get(0).getPosition().getY(), nextPosSection == null ? posSection.getPage() + 1 : nextPosSection.get(0).getPage());

            if (listCampoResult == null){
                System.out.println("Não encontrou posicao do termo " + this.name + " page: " + posSection.getPage());
                return;
            }

            ExtractData.Result campoResult = listCampoResult.get(0);

            TextPositionSequence position = campoResult.getPosition();

            int height = this.height > 0 ? this.height : 5;
            int posY = this.table ? (int)position.getY() + (this.y > 0 ? this.y : height) : (int)position.getY();

            if (this.table){
                this.content = document.getTextByArea((int)position.getX() - 25, posY , (int)position.getWidth(), height, this, campoResult.getPage() - 1);
                //System.out.println( "Field: " + this.name + ": " + this.content + " Position: " + posY);
            } else {
                this.content = document.getTextByArea((int)position.getX() + (int)position.getWidth() + 10, posY, (int)position.getWidth() + 200, height, this, campoResult.getPage() - 1);
                //System.out.println( "Field: " + this.name + ": " + this.content + " Position: " + posY);
            }

            if (section.table && this.height > 0){

                String content2 = "a";

                int y = posY;

                //System.out.println("Section is table !!! Get POSITIOn : " + (int) position.getY() + " Y: " + y);

                while (!content2.trim().isEmpty()) {

                    y += (section.numberOfLines * section.heightOfLines);

                    if (y > 739){
                        break;
                        //campoResult.setPage(campoResult.getPage() + 1);
                        //y = 50;
                    }

                    if (nextPosSection != null){
                        if ((nextPosSection.get(0).getPosition().getY() < y && nextPosSection.get(0).getPage() == campoResult.getPage()) ||
                            (nextPosSection.get(0).getPosition().getY() < (y + section.numberOfLines * section.heightOfLines) && nextPosSection.get(0).getPage() == campoResult.getPage())){
                            //System.out.println("Break");
                            break;
                        }
                    }

                    //System.out.println("Position Get Y " + y);

                    if (this.table) {
                        content2 = document.getTextByArea((int) position.getX() - 25, y, (int) position.getWidth(), this.height, this, campoResult.getPage() - 1);
                    } else {
                        content2 = document.getTextByArea((int) position.getX() + (int) position.getWidth() + 10, y, (int) position.getWidth() + 200, this.height, this, campoResult.getPage() - 1);
                    }

                    if (!content2.trim().isEmpty()){
                        //System.out.println("Field 2: " + this.name + ": " + content2);
                    }

                    //y += (section.numberOfLines * section.heightOfLines);

                }

            }

        } catch (IOException dfg) {
            System.out.println(dfg);
        }
    }

    public void getObjectFromField(Field field, JsonElement jsonElement){

        if (field.content2 != null){

            Gson gsonSection = new Gson();
            JsonElement jsonElementSection = gsonSection.toJsonTree(new Object());

            for (Field item: field.content2){
                this.getObjectFromField(item, jsonElementSection);
            }

            jsonElement.getAsJsonObject().add(field.name, jsonElementSection);

        } else {
            jsonElement.getAsJsonObject().addProperty(field.name, field.content.trim());
        }


    }
}
