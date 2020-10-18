package com.bookmanage.util;

public class PageParam {

    //当前页编号
    protected Integer pageNo;

    //上一页编号
    protected Integer prePage;

    //下一页编号
    protected Integer nextPage;

    //总页数
    protected Integer totalPage;

    //总记录数
    protected Integer totalRows;

    //每页显示条数
    protected Integer pageSize;

    //每页偏移量
    protected Integer offset;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPrePage() {
        setPrePage();
        return prePage;
    }

    private void setPrePage() {
        if (this.pageNo<=1){
            this.prePage = 1;
        }else {
            this.prePage = this.pageNo-1;
        }
    }

    public Integer getNextPage() {
        setNextPage();
        return nextPage;
    }

    private void setNextPage() {
        if (this.pageSize>=this.totalPage){
            this.nextPage = this.totalPage;

        }
        this.nextPage = this.pageNo+1;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
