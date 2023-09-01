package ar.com.jsuper.dto.reportes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DataReportGeneric {

    private ArrayList<ColumnGeneric> columnGenerics;
    private List data;
    private String title;
    private ObjectMapper mapper = new ObjectMapper();
    private List<Class> converters;

    public DataReportGeneric(List data, ArrayList<ColumnGeneric> columnGenerics, String title) {
        this.columnGenerics = columnGenerics;
        this.data = data;
        this.title = title;
    }

    public DataReportGeneric(List data, ArrayList<ColumnGeneric> columnGenerics, String title, List<Class> converters) {
        this.columnGenerics = columnGenerics;
        this.data = data;
        this.title = title;
        this.converters = converters;
    }

    public List<String> getValues() {
        List<String> resp = new ArrayList<>();
        List<Map<String, Object>> data = mapper.convertValue(this.data, new TypeReference<List<Map<String, Object>>>() {
        });
        data.stream().forEach(x -> {
            this.columnGenerics.forEach(y -> {
                resp.add(this.getValue(y.getNameField().split("\\."), x));
            });
        });
        return resp;
    }

    public List<List<String>> getValuesRow() {
        List<List<String>> resp = new ArrayList<>();
        List<Map<String, Object>> data = mapper.convertValue(this.data, new TypeReference<List<Map<String, Object>>>() {
        });
        data.stream().forEach(x -> {
            List<String> row = new ArrayList<>();
            this.columnGenerics.forEach(y -> {
                row.add(this.getValue(y.getNameField().split("\\."), x));
            });
            resp.add(row);
        });
        return resp;
    }

    private String getValue(String[] keysValue, Map<String, Object> data) {
        if (keysValue.length > 0) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (keysValue[0].equals(entry.getKey())) {
                    if (keysValue.length == 1) {
                        Object value = entry.getValue();
                        String valueString = "";
                        if (Objects.nonNull(value)) {
                            if (value instanceof Boolean) {
                                Boolean valueBoolean = (Boolean) value;
                                valueString = valueBoolean ? "Si" : "No";
                            } else {
                                valueString = entry.getValue().toString();
                            }
                        }
                        return (valueString);
                    } else {
                        return this.getValue(Arrays.copyOfRange(keysValue, 1, keysValue.length), (Map<String, Object>) entry.getValue());
                    }
                }
            }
        }
        return "";
    }

    public float[] getWidths() {
        float[] resp = new float[this.columnGenerics.size()];
        AtomicInteger i = new AtomicInteger(0);
        this.columnGenerics.stream().map(x -> (new Integer(x.getWidth())).floatValue()).forEach(y -> {
            resp[i.getAndIncrement()] = y;
        });
        return resp;
    }

    public ArrayList<ColumnGeneric> getColumnGenerics() {
        return columnGenerics;
    }

    public void setColumnGenerics(ArrayList<ColumnGeneric> columnGenerics) {
        this.columnGenerics = columnGenerics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
