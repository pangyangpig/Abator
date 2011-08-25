package com.xiebiao.tools.db;

public enum DataType {
    TIME, YEAR, TIMESTAMP, DATETIME, DOUBLE, FLOAT, CHAR, VARCHAR, INT, TINYINT, BIGINT, TEXT, TINYTEXT, MEDIUMTEXT, LONGTEXT;
    public static DataType to(String name) {
	if (name.toUpperCase().equals(TIME.name())) {
	    return TIME;
	} else if (name.toUpperCase().equals(YEAR.name())) {
	    return YEAR;
	} else if (name.toUpperCase().equals(TIMESTAMP.name())) {
	    return TIMESTAMP;
	} else if (name.toUpperCase().equals(DATETIME.name())) {
	    return DATETIME;
	} else if (name.toUpperCase().equals(DOUBLE.name())) {
	    return DOUBLE;
	} else if (name.toUpperCase().equals(FLOAT.name())) {
	    return FLOAT;
	} else if (name.toUpperCase().equals(CHAR.name())) {
	    return CHAR;
	} else if (name.toUpperCase().equals(VARCHAR.name())) {
	    return VARCHAR;
	} else if (name.toUpperCase().equals(INT.name())) {
	    return INT;
	} else if (name.toUpperCase().equals(TINYINT.name())) {
	    return TINYINT;
	} else if (name.toUpperCase().equals(BIGINT.name())) {
	    return BIGINT;
	} else if (name.toUpperCase().equals(TEXT.name())) {
	    return TEXT;
	} else if (name.toUpperCase().equals(TINYTEXT.name())) {
	    return TINYTEXT;
	} else if (name.toUpperCase().equals(MEDIUMTEXT.name())) {
	    return MEDIUMTEXT;
	} else if (name.toUpperCase().equals(LONGTEXT.name())) {
	    return LONGTEXT;
	} else {
	    System.err.println("ERROR: column=" + name + "");
	    return VARCHAR;
	}
    }
}
