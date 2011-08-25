package com.xiebiao.tools.db;

public class Column {
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("[").append("name=" + name)
		.append(",dataType=" + dataType + "]");
	return sb.toString();
    }

    public String getDataType() {
	return dataType;
    }

    public void setDataType(String dataType) {
	this.dataType = dataType;
    }

    private String name;
    private String dataType;
    private String key;
    private String comment;

}
