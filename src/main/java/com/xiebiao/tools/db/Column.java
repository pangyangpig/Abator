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

    public int hashCode() {
	int hashCode = 0;
	char[] cc = this.name.toCharArray();
	for (char c : cc) {
	    hashCode = hashCode + c;
	}
	return hashCode;

    }

    public boolean equals(Object obj) {
	if (obj instanceof Column) {
	    Column c = (Column) obj;
	    if (c.getName().equals(name)) {
		return true;
	    }
	}
	return false;
    }

    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("[").append("name=" + name)
		.append(",dataType=" + dataType + "]");
	return sb.toString();
    }

    public DataType getDataType() {
	return dataType;
    }

    public void setDataType(DataType dataType) {
	this.dataType = dataType;
    }

    public Column() {
	comment = "";
	key = "";
    }

    private String name;
    private DataType dataType;
    private String key;
    private String comment;

}
