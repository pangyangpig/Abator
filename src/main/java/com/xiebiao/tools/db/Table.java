package com.xiebiao.tools.db;

import java.util.List;

public class Table {
    private String name;
    private List<Column> columns;
    private String comment;

    public String getName() {
	return name;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	comment = comment == null ? "" : comment;
	this.comment = comment;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<Column> getColumns() {
	return columns;
    }

    public void setColumns(List<Column> columns) {
	this.columns = columns;
    }

    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("[").append("name=" + name).append(columns.toString())
		.append("]");
	return sb.toString();
    }
}
