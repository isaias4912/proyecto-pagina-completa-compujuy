package ar.com.jsuper.dao.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Pagination<T> {

    private List<T> data;
    private Set<T> dataSet;
    private Map extraData;
    private int total;
    private int page;
    private int pageSize;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Set<T> getDataSet() {
        return dataSet;
    }

    public void setDataSet(Set<T> dataSet) {
        this.dataSet = dataSet;
    }

    public Map getExtraData() {
        return extraData;
    }

    public void setExtraData(Map extraData) {
        this.extraData = extraData;
    }

}
