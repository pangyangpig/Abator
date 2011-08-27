package com.xiebiao.tools.db;

import java.util.Set;

public class Table {
    private String name;
    private Set<Column> columns;
    private String comment;

    public Table() {
	comment = "";
    }

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

    public Set<Column> getColumns() {
        return columns;
    }

    public void setColumns(Set<Column> columns) {
        this.columns = columns;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("[").append("name=" + name).append(columns.toString())
		.append("]");
	return sb.toString();
    }
}
