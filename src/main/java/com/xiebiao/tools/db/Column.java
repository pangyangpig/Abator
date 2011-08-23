package com.xiebiao.tools.db;

public class Column {
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("[").append("name=" + name).append("]");
	return sb.toString();
    }

    private String name;

}
