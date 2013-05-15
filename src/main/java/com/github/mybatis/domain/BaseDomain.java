package com.github.mybatis.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * <ul>
 * <li>1. 分页查询中计算 LIMIT start,end.</li>
 * <li>2. 排序.</li>
 * </ul>
 * </p>
 * @author xiebiao
 */
public abstract class BaseDomain implements java.io.Serializable {

    private static final long     serialVersionUID = 1L;

    /** 动态字段. 在ibatis文件中可用“dynamicFields.xxx”方式读取动态字段值 */
    protected Map<String, Object> dynamicFields    = new HashMap<String, Object>();

    /** 查询最大行数. */
    // private int MAX_ROWS = 9999999;
    /**
     * 返回结果
     */
    private Integer               result;

    private int                   start;

    /**
     * 查询数据条数
     */
    private int                   rows;

    /**
     * DESC|ASC
     */
    private String                sort;

    /**
     * 排序字段
     */
    private String                orderBy;

    public BaseDomain() {}

    public void setStart(int start) {
        this.start = start;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    /**
     * 设置动态字段值.
     * @param fieldName 字段名称
     * @param value 字段值
     */
    public void setField(String fieldName, Object value) {
        dynamicFields.put(fieldName, value);
    }

    /**
     * 返回动态字段值.
     * @param fieldName 字段名称
     * @return 对象
     */
    public Object getField(String fieldName) {
        if (dynamicFields == null) {
            return null;
        }
        return dynamicFields.get(fieldName);
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getStart() {
        return start;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

}
