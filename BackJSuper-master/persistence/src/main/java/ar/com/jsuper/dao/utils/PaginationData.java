package ar.com.jsuper.dao.utils;

import java.util.List;

public class PaginationData<T, V> {

	private List<T> data;
	private V dataAditional;
	private int total;
	private int page;
	private int pageSize;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public V getDataAditional() {
		return dataAditional;
	}

	public void setDataAditional(V dataAditional) {
		this.dataAditional = dataAditional;
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
