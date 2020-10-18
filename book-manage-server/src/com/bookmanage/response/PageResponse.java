package com.bookmanage.response;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class PageResponse <T>{

    private Integer total;

    private List<T> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
