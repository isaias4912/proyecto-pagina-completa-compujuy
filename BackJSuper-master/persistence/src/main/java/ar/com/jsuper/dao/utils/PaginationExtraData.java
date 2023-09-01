package ar.com.jsuper.dao.utils;

import java.util.List;

public class PaginationExtraData<T, G> {

    private List<T> data;
    private G extraData;
    private int total;
    private int page;
    private int pageSize;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public G getExtraData() {
        return extraData;
    }

    public void setExtraData(G extraData) {
        this.extraData = extraData;
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
}
