package com.xiebiao.tools.db;

import java.util.HashMap;
import java.util.Map;

public class DataType2Java {
    public static Map<String, String> dataTypeMap = new HashMap<String, String>();
    static {
	dataTypeMap.put("time", "Date");
	dataTypeMap.put("year", "Date");
	dataTypeMap.put("timestamp", "Date");
	dataTypeMap.put("date", "Date");
	dataTypeMap.put("datetime", "Date");
	dataTypeMap.put("double", "double");
	dataTypeMap.put("float", "float");
	dataTypeMap.put("char", "String");
	dataTypeMap.put("varchar", "String");
	dataTypeMap.put("int", "int");
	dataTypeMap.put("mediumint", "int");
	dataTypeMap.put("smallint", "int");
	dataTypeMap.put("tinyint", "int");
	dataTypeMap.put("smallint", "int");
	dataTypeMap.put("bigint", "int");
	dataTypeMap.put("text", "String");
	dataTypeMap.put("tinytext", "String");
	dataTypeMap.put("mediumtext", "String");
	dataTypeMap.put("longtext", "String");
	dataTypeMap.put("decimal", "double");
    }
}
