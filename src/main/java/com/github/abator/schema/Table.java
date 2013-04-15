package com.github.abator.schema;

import java.util.HashSet;
import java.util.Set;

import com.github.abator.utils.Util;

public class Table {
	private String name;
	private Set<Column> columns;
	private String comment;
	private String priKey;
	private String alias;

	public Table() {
		comment = "";
		columns = new HashSet<Column>();
	}

	public Table(String tableName) {
		this();
		this.name = tableName;
		this.alias = Util.getModelClassName(tableName);
	}

	public void addColumn(Column column) {
		columns.add(column);
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

	public String getPriKey() {
		return priKey;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setPriKey(String key) {
		this.priKey = key;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append("name=" + name).append(columns.toString())
				.append("]");
		return sb.toString();
	}
}
