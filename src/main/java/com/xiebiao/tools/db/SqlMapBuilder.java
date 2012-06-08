package com.xiebiao.tools.db;

public class SqlMapBuilder {
	protected StringBuffer sb;
	protected Table table;

	private SqlMapBuilder() {
	}

	public static SqlMapBuilder from(Table table) {
		SqlMapBuilder sqlMap = new SqlMapBuilder();
		sqlMap.sb = new StringBuffer();
		if (table == null || table.getName().equals("")) {
			throw new java.lang.IllegalArgumentException();
		}
		sqlMap.table = table;
		return sqlMap;
	}

	public String buildXmlHeader() {
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("\n");
		return sb.toString();
	}

	public String buildDtd() {
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN \"\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
		sb.append("\n");
		return sb.toString();
	}

	public String buildTableName() {
		sb.append("<sql id=\"table_name\">" + table.getName() + "</sql>");
		sb.append("\n");
		return sb.toString();
	}

	public String buildBodyHeader() {
		sb.append("<mapper namespace=\"com.adson.dao.impl.AttCodeDaoImpl\">");
		sb.append("\n");
		return sb.toString();
	}

	public String build() {
		this.buildXmlHeader();
		this.buildDtd();
		this.buildBodyHeader();
		this.buildTableName();
		return sb.toString();
	}
}
