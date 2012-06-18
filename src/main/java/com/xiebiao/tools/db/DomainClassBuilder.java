package com.xiebiao.tools.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.xiebiao.tools.util.Util;

/**
 * 生成表对应的Model
 * 
 * @author xiaog
 * 
 */
public class DomainClassBuilder extends ClassBuilder {

	public final static String PACKAGE = "domain.package";
	public final static String EXTENDS = "domain.extends";
	public final static String SUFFIX = "domain.suffix";
	private List<String> names = new ArrayList<String>();

	public DomainClassBuilder(String _package) {
		tab = "    ";
		if (_package == null || _package.equals("")) {
			System.out.println("WARN: package must be setting. ");
		}
		File outputDir = new File(OUTPUT);
		if (!outputDir.exists()) {
			outputDir.mkdir();
		} else {
			FileUtils.deleteDirectory(OUTPUT);
		}
	}

	public DomainClassBuilder(Config config) {
		this(config.getProperties().getProperty(PACKAGE));
		this.config = config;
	}

	public String build() {
		if (sb == null) {
			throw new java.lang.InstantiationError();
		}
		doBuild();
		String _package = this.config.getProperties().getProperty(PACKAGE);
		String dirPath = _package == null ? "" : _package;
		if (_package != null && !_package.equals("")) {
			dirPath = dirPath.replace(".", File.separator);
			File dirs = new File(OUTPUT + File.separator + dirPath);
			if (!dirs.exists()) {
				dirs.mkdirs();
			}
		}
		String modelClassName = Util.getModelClassName(table.getName())
				+ this.config.getProperties().getProperty(SUFFIX);
		String fileName = OUTPUT + File.separator + dirPath + File.separator
				+ modelClassName + ".java";
		File modelFile = new File(fileName);
		try {
			if (!modelFile.exists()) {
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
		String full_name = _package + "." + modelClassName + ".java";
		System.out.println(full_name + " ... build success!");
		return full_name;
	}

	public void buildStructure() {
		sb.append("\n");
		sb.append(tab
				+ "public "
				+ Util.getModelClassName(table.getName()
						+ this.config.getProperties().getProperty(SUFFIX))
				+ "() {\n");
		sb.append(tab + "}\n");
	}

	public DomainClassBuilder from(Table table) {
		sb = new StringBuffer();
		if (table == null || table.getName().equals("")) {
			throw new java.lang.IllegalArgumentException();
		}
		this.table = table;
		return this;
	}

	protected void buildPackage() {
		if (this.config.getProperties().getProperty(PACKAGE) != null
				&& !this.config.getProperties().getProperty(PACKAGE).equals("")) {
			sb.append("package "
					+ this.config.getProperties().getProperty(PACKAGE) + ";");
			sb.append("\n");
		}
	}

	protected void buildImport() {
		if (sb.length() != 0) {
			sb.append("\n");
		}
		if (table.getColumns() == null) {
			return;
		} else {
			for (Column c : table.getColumns()) {
				if (DataType2Java.dataTypeMap.get(c.getDataType()).equals(
						"Date")) {
					sb.append("import java.util.Date;\n");
					break;
				}
			}
		}
		sb.append("\n");
	}

	protected void buildAnnotate() {
		sb.append("\n");
		sb.append("/**\n");
		sb.append(" * " + table.getComment() + "\n");
		sb.append(" */");
		sb.append("\n");
	}

	protected void buildClassName() {
		sb.append("\n");
		if (config.getProperties().getProperty("dao.suffix") != null) {
			sb.append("public class "
					+ Util.getModelClassName(table.getName()
							+ config.getProperties().getProperty(SUFFIX))
					+ " extends " + config.getProperties().getProperty(EXTENDS)
					+ " {\n");
		} else {
			sb.append("public class "
					+ Util.getModelClassName(table.getName()
							+ config.getProperties().getProperty(SUFFIX))
					+ " {\n");
		}
	}

	protected void buildField() {
		sb.append("\n");
		sb.append(tab + "private static final long serialVersionUID = 1L;\n");
		Set<Column> columns = table.getColumns();
		if (columns == null) {
			System.err.println("WARN: Table=" + table.getName()
					+ " has no column.");
			return;
		} else {
			for (Column c : columns) {
				String name = Util.getCamelName(Util.getCamelName(c.getName()));
				if (JavaKeyWord.isJavaKeyWord(name)) {
					name = "_" + name;
				}
				if (c.isPrimaryKey()) {
					sb.append(tab + "//primary key \n");
				}
				sb.append(tab + "protected "
						+ DataType2Java.dataTypeMap.get(c.getDataType()) + " "
						+ name + ";\n");
				names.add(name);
			}
		}

	}

	protected void buildSetterGetter() {
		sb.append("\n");
		Set<Column> columns = table.getColumns();
		for (Column c : columns) {
			String field = Util.getCamelName(c.getName());
			String _field = field;
			if (JavaKeyWord.isJavaKeyWord(field)) {
				_field = "_" + field;
			}
			sb.append("\n");
			sb.append(
					tab + "public void set"
							+ field.substring(0, 1).toUpperCase()
							+ field.substring(1, field.length()) + "("
							+ DataType2Java.dataTypeMap.get(c.getDataType())
							+ " " + _field + ")")
					.append(" {\n")
					.append(tab + tab + "this." + _field + " = " + _field
							+ ";\n").append(tab + "}\n");
			sb.append("\n");
			sb.append(tab + "/**\n");
			sb.append(tab + " * " + c.getComment() + "\n");
			sb.append(tab + " */\n");
			sb.append(
					tab + "public "
							+ DataType2Java.dataTypeMap.get(c.getDataType())
							+ " get" + field.substring(0, 1).toUpperCase()
							+ field.substring(1, field.length()) + "()")
					.append(" {\n").append(tab)
					.append(tab + "return this." + _field + ";\n")
					.append(tab + "}\n");
		}
	}

	@Override
	protected void buildToString() {
		sb.append("\n");
		sb.append(tab + "public String toString(){\n");
		sb.append(tab + tab + "StringBuilder sb = new StringBuilder();\n");
		for (String name : names) {
			sb.append(tab + tab + "sb.append(\"" + name + ":\"+this." + name
					+ "+\" \");\n");
		}
		sb.append(tab + tab + "return sb.toString();\n");
		sb.append(tab + "}");
		this.names.clear();
	}
}
