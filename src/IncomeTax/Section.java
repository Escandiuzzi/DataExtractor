package IncomeTax;

public class Section {

    public String name;
    public boolean table;
    public int numberOfLines;
    public int heightOfLines;
    public Field[] fields;

    public void setTable(boolean table) {
        this.table = table;
    }
}
