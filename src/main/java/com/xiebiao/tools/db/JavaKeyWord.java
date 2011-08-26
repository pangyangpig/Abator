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

    public static String toString(JavaKeyWord javaKeyWord) {
	for (JavaKeyWord jkw : JavaKeyWord.values()) {
	    if (javaKeyWord.equals(jkw)) {
		if (javaKeyWord.equals(STRING)) {
		    return STRING.name().substring(0, 1).toUpperCase()
			    + STRING.name()
				    .substring(1, STRING.name().length());
		} else {
		    return javaKeyWord.name().toLowerCase();
		}
	    }
	}
	return null;
    }
}
