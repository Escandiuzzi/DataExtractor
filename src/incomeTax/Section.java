package incomeTax;

public class Section {

    public String name;
    public boolean table;
    public int numberOfLines;
    public int heightOfLines;
    public Field[] fields;

    public Section(String name, boolean table, int numberOfLines, int heightOfLines, Field[] fields){
        this.name = name;
        this.table = table;
        this.numberOfLines = numberOfLines;
        this.heightOfLines = heightOfLines;
        this.fields = fields;
    }

    public void setTable(boolean table) {
        this.table = table;
    }
}
