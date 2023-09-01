package ar.com.jsuper.dto.reportes;

import java.util.ArrayList;

public class ColumnGeneric {
    private String labelHeader;
    private String nameField;
    private Integer width;

    public ColumnGeneric() {
    }

    public ColumnGeneric(String nameField, Integer width) {
        this.nameField = nameField;
        this.width = width;
    }

    public static ArrayList<ColumnGeneric> getColumns(String[] headers, String[] nameFields, int[] widths) {
        ArrayList<ColumnGeneric> resp = new ArrayList<>();
        for (int i=0; i< nameFields.length; i++){
            ColumnGeneric columnGeneric = new ColumnGeneric();
            columnGeneric.setLabelHeader(headers[i]);
            columnGeneric.setNameField(nameFields[i]);
            columnGeneric.setWidth(widths[i]);
            resp.add(columnGeneric);
        }
        return resp;
    }

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getLabelHeader() {
        return labelHeader;
    }

    public void setLabelHeader(String labelHeader) {
        this.labelHeader = labelHeader;
    }
}
