package com.core.pagination;

import java.util.Collections;
import java.util.List;


public class Page<E> {
    private boolean hasPre;//是否首页
    private boolean hasNext;//是否尾页
    private List<E> items;//当前页包含的记录列表
    private int index;//当前页页码(起始为1)
    private IPageContext<E> context;
    private int pageNo;
    private Long totalCount;
    private int pageSize;
    
    public Page(){}
    
    public Page(int index, Long totalCount, int pageSize,List<E> items) {
		super();
		this.items = items;
		this.index = index;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public IPageContext<E> getContext() {
        return this.context;
    }
    public void setContext(IPageContext<E> context) {
        this.context = context;
    }
    public int getIndex() {
        return this.index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public boolean isHasPre() {
        return this.hasPre;
    }
    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }
    public boolean isHasNext() {
        return this.hasNext;
    }
    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
    public List<E> getItems() {
        return this.items == null ? Collections.<E>emptyList() : this.items;
    }
    public void setItems(List<E> items) {
        this.items = items;
    }
}