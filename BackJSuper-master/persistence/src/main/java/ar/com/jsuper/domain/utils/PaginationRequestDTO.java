package ar.com.jsuper.domain.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class PaginationRequestDTO {
    @NotNull
    private Integer page;
    @NotNull
    private Integer pageSize;
    @NotNull
    private String order;
    @NotNull
    private Boolean reverse;

    public PaginationRequestDTO(Integer page, Integer pageSize, String order, Boolean reverse) {
        this.page = page;
        this.pageSize = pageSize;
        this.order = order;
        this.reverse = reverse;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrder() {
        return order.trim();
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Boolean getReverse() {
        return reverse;
    }

    public void setReverse(Boolean reverse) {
        this.reverse = reverse;
    }
}
