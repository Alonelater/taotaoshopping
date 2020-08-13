package com.taotao.search.pojo;

import java.util.List;

/**
 *
 * 查询的总记录数
 */

public class SearchResult {

    //查询的商品列表
    private List<Item> itemList;
    //总记录数
    private long recordCount;
    //总页码
    private long pageCount;
    //当前页
    private long curPage;

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getCurPage() {
        return curPage;
    }

    public void setCurPage(long curPage) {
        this.curPage = curPage;
    }
}
