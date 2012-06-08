package com.xiebiao.tools.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SqlMapBuilder {
	protected StringBuffer sb;
	protected Table table;
	protected String tab = "    ";
	protected String namespace;

	private SqlMapBuilder() {
	}

	public static SqlMapBuilder from(Table table, String namespace) {
		SqlMapBuilder sqlMap = new SqlMapBuilder();
		sqlMap.sb = new StringBuffer();
		sqlMap.namespace = namespace;
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
		sb.append(tab);
		sb.append("<sql id=\"table_name\">" + table.getName() + "</sql>");
		sb.append("\n");
		return sb.toString();
	}

	public String buildDefault() {
		sb.append(tab);
		String tmp = "<sql id=\"field_list\">\n";
		for (Column column : table.getColumns()) {
			tmp = tmp + tab + tab;
			tmp = tmp + column.getName() + ",\n";
		}
		tmp = tmp.substring(0, tmp.length() - 2);
		tmp = tmp + "\n" + tab + "</sql>\n";
		sb.append(tmp);
		// build condition
		sb.append(tab);
		sb.append("<sql id=\"condition\">\n");
		sb.append(tab + tab + "<where>\n");
		for (Column column : table.getColumns()) {
			sb.append(tab + tab + tab + "<if test=\"" + column.getName()
					+ " != null\">\n");
			sb.append(tab + tab + tab + tab + column.getName() + "=#{"
					+ column.getName() + "}\n");
			sb.append(tab + tab + tab + "</if>\n");
		}
		sb.append(tab + tab + "</where>\n");
		sb.append(tab + "</sql>\n");
		// build count
		sb.append(tab
				+ "<select id=\"count\" resultType=\"int\" parameterType=\""
				+ table.getAlias() + "\">\n");
		sb.append(tab + tab + "select count(*) as value from \n");
		sb.append(tab + tab + "<include refid=\"table_name\" />\n");
		sb.append(tab + tab + "<include refid=\"condition\" />\n");
		sb.append(tab + "</select>\n");
		// build find
		sb.append(tab
				+ "<select id=\"find\" parameterType=\"string\" resultType=\""
				+ table.getAlias() + "\">\n");
		sb.append(tab + tab + "SELECT * FROM \n");
		sb.append(tab + tab + "<include refid=\"table_name\" /> \n");
		sb.append(tab + tab + "WHERE " + table.getPriKey() + " = #{"
				+ table.getPriKey() + "} \n");
		sb.append(tab + "</select>\n");
		// build update
		sb.append(tab + "<update id=\"update\"> \n");
		sb.append(tab + "UPDATE \n");
		sb.append(tab + "<include refid=\"table_name\" />\n");
		sb.append(tab + "SET \n");
		tmp = "";
		for (Column column : table.getColumns()) {
			tmp = tmp + tab + tab;
			tmp = tmp + column.getName() + "= #{" + column.getName() + "},\n";
		}
		tmp = tmp.substring(0, tmp.length() - 2);
		sb.append(tmp + "\n");
		sb.append(tab + tab + "WHERE " + table.getPriKey() + "=#{"
				+ table.getPriKey() + "} \n");

		sb.append(tab + "</update> \n");
		// build insert
		sb.append(tab + "<insert id=\"insert\"> \n");
		sb.append(tab + tab + "INSERT INTO \n");
		sb.append(tab + tab + "<include refid=\"table_name\" /> \n");
		sb.append(tab + tab + "( \n");
		tmp = "";
		for (Column column : table.getColumns()) {
			tmp = tmp + tab + tab;
			tmp = tmp + tab + column.getName() + ",\n";
		}
		tmp = tmp.substring(0, tmp.length() - 2);
		sb.append(tmp + "\n");

		sb.append(tab + tab + ") \n");
		sb.append(tab + tab + "VALUES \n");
		sb.append(tab + tab + "( \n");

		tmp = "";
		for (Column column : table.getColumns()) {
			tmp = tmp + tab + tab;
			tmp = tmp + tab + "#{" + column.getName() + "},\n";
		}
		tmp = tmp.substring(0, tmp.length() - 2);
		sb.append(tmp + "\n");
		sb.append(tab + tab + ") \n");
		sb.append(tab + tab + "</insert> \n");
		// build list

		sb.append(tab + "<select id=\"list\" parameterType=\""
				+ table.getAlias() + "\" resultType=\"" + table.getAlias()
				+ "\"> \n");
		sb.append(tab + "SELECT * FROM \n");
		sb.append(tab + "<include refid=\"table_name\" /> \n");
		sb.append(tab + "<include refid=\"condition\" /> \n");
		sb.append(tab + "<if test=\"order != null\"> \n");
		sb.append(tab + tab + "order by ${order} \n");
		sb.append(tab + "</if> \n");
		sb.append(tab + "<if test=\"sort != null\"> \n");
		sb.append(tab + tab + "${sort} \n");
		sb.append(tab + "</if> \n");

		sb.append(tab + "<if test=\"rows != 0\"> \n");
		sb.append(tab + tab + "LIMIT #{start},#{rows} \n");
		sb.append(tab + "</if> \n");
		sb.append(tab + "</select> \n");

		return sb.toString();
	}

	public String buildBodyHeader() {
		sb.append("<mapper namespace=\"" + this.namespace + "\">");
		sb.append("\n");
		return sb.toString();
	}

	public String buildEnd() {
		sb.append("</mapper>");
		return sb.toString();
	}

	public final void doBuild() {
		buildXmlHeader();
		buildDtd();
		buildBodyHeader();
		buildTableName();
		buildDefault();
		buildEnd();
	}

	public String build() {
		doBuild();
		String dirPath = ClassBuilder.OUTPUT + File.separator + "mybatis";
		File dir = new File(dirPath);
		if (dir.exists() == false) {
			dir.mkdirs();
		}
		String fileName = dirPath + File.separator + table.getName()
				+ "SQL.xml";
		File modelFile = new File(fileName);
		try {
			if (!modelFile.exists()) {
				// modelFile.mkdirs();
				modelFile.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(modelFile);
			out.write(sb.toString().getBytes());
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(table.getName() + "SQL.xml" + " ... build success!");
		return fileName;
	}
}
