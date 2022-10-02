package IncomeTax;

import utils.TextPositionSequence;

import java.io.IOException;

public class Field {

    public String name;
    public String content;
    public String type;
    public String pattern;
    public int x;
    public int y;
    public int height;
    public int width;
    public boolean table;

    public void getContentField(Section section, ExtractData document, ExtractData.Result posSection, ExtractData.Result nextPosSection){

        try {

            ExtractData.Result campoResult = document.getSubwords(this.name, posSection.getPage(), (int)posSection.getPosition().getY(), nextPosSection == null ? 0 : (int)nextPosSection.getPosition().getY(), nextPosSection == null ? posSection.getPage() + 1 :nextPosSection.getPage());

            if (campoResult == null){
                System.out.println("Não encontrou posicao do termo " + this.name + " page: " + posSection.getPage());
                return;
            }

            TextPositionSequence position = campoResult.getPosition();

            if (this.table){
                this.content = document.getTextByArea((int)position.getX() - 25, (int)position.getY() + (this.y > 0 ? this.y : this.height) , (int)position.getWidth(), this.height, this, campoResult.getPage() - 1);
            } else {
                this.content = document.getTextByArea((int)position.getX() + (int)position.getWidth() + 10, (int)position.getY(), (int)position.getWidth() + 200, this.height, this, campoResult.getPage() - 1);
            }

            System.out.println( "Field: " + this.name + ": " + this.content);

            if (section.table){

                String content2 = "a";

                int y = (int) position.getY() + section.numberOfLines;

                System.out.println("Section is table !!!");

                while (!content2.trim().isEmpty()) {

                    if (nextPosSection != null){
                        if (nextPosSection.getPosition().getY() < y && nextPosSection.getPage() == posSection.getPage() ){
                            break;
                        }
                    }

                    y += section.numberOfLines;

                    System.out.println("Position Get Y " + y);

                    if (this.table) {
                        content2 = document.getTextByArea((int) position.getX() - 25, y, (int) position.getWidth(), this.height, this, campoResult.getPage() - 1);
                    } else {
                        content2 = document.getTextByArea((int) position.getX() + (int) position.getWidth() + 10, y, (int) position.getWidth() + 200, this.height, this, campoResult.getPage() - 1);
                    }

                    if (!content2.trim().isEmpty()){
                        System.out.println("Field 2: " + this.name + ": " + content2);
                    }

                }

            }

        } catch (IOException dfg) {
            System.out.println(dfg);
        }
    }
}
