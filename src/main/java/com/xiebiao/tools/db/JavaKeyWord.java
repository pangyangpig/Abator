package com.xiebiao.tools.db;

public enum JavaKeyWord {
    BYTE, INT, FLOAT, DOUBLE, BOOLEAN, STRING, LONG;
    public static boolean isJavaKeyWord(String kw) {
	if (kw.toUpperCase().equals(BYTE.name())) {
	    return true;
	} else if (kw.toUpperCase().equals(INT.name())) {
	    return true;
	} else if (kw.toUpperCase().equals(FLOAT.name())) {
	    return true;
	} else if (kw.toUpperCase().equals(DOUBLE.name())) {
	    return true;
	} else if (kw.toUpperCase().equals(BOOLEAN.name())) {
	    return true;
	} else if (kw.toUpperCase().equals(STRING.name())) {
	    return true;
	} else if (kw.toUpperCase().equals(LONG.name())) {
	    return true;
	}
	return false;
    }
}
