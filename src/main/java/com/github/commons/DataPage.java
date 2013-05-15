package com.github.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页封装
 * @author xiebiao
 * @param <E>
 */
public final class DataPage<E> extends ArrayList<E> {

    private static final long serialVersionUID = 1L;
    private final static int  MAX_PAGE_SIZE    = 50;
    private int               totalRecord;
    private int               totalPage;
    /** 默认每页记录数 */
    private int               pageSize         = 20;
    private int               pageIndex;
    private int               startRecord;

    /**
     * @param totalRecord 总记录数
     * @param pageIndex 当前页
     * @param pageSize 每页记录数
     */
    public DataPage(int totalRecord, int pageIndex, int pageSize) {
        this.totalRecord = totalRecord;
        this.pageIndex = pageIndex == 0 ? 1 : pageIndex;
        this.pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
        int remainder = this.totalRecord % this.pageSize;
        int totalPage = this.totalRecord / this.pageSize;
        this.totalPage = remainder == 0 ? totalPage : totalPage + 1;

    }

    public int getStartRecord() {
        this.startRecord = (this.pageIndex - 1) * pageSize;
        return this.startRecord;
    }

    public int getEndRecord() {

        return this.pageSize;
    }

    /**
     * 填充数据
     * @param list
     */
    public void addAll(List<E> list) {
        super.addAll(list);
    }

    /**
     * 是否还有上一页
     * @return
     */
    public boolean hasForwardPage() {
        if (pageIndex <= 1) {
            return false;
        }
        return true;
    }

    /**
     * 是否还有下一页
     * @return
     */
    public boolean hasNextPage() {
        if (pageIndex >= totalPage) {
            return false;
        }
        return true;
    }

    /**
     * 获取下一页
     * @return
     */
    public int getNextPage() {
        int nextPage = this.pageIndex + 1;
        return nextPage = nextPage > this.totalPage ? this.totalPage : nextPage;
    }

    /**
     * 获取前一页
     * @return
     */
    public int getForwardPage() {
        int forwardPage = this.pageIndex - 1;
        return forwardPage <= 0 ? 1 : forwardPage;
    }

    /**
     * 总页数
     * @return
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 每页记录数
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("nextPage=" + this.getNextPage() + ",dataSize=" + this.size() + ",totalPage=" + this.totalPage
                + ",totalRecord=" + this.totalRecord + ",pageIndex=" + this.pageIndex + ",pageSize=" + this.pageSize);
        return sb.toString();
    }
}
