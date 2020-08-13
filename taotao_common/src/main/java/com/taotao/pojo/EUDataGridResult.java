package com.taotao.pojo;

import org.apache.log4j.pattern.LineSeparatorPatternConverter;

import java.util.List;

public class EUDataGridResult {

    private long total;
    private List<?> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
