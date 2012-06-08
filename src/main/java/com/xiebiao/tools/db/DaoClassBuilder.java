package com.xiebiao.tools.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 生成DAO接口
 * 
 * @author xiaog
 * 
 */
public class DaoClassBuilder extends ClassBuilder {
	public final static String PACKAGE = "dao.package";
	public final static String EXTENDS = "dao.extends";
	public final static String SUFFIX = "dao.suffix";

	public DaoClassBuilder from(Table table) {
		sb = new StringBuffer();
		if (table == null || table.getName().equals("")) {
			throw new java.lang.IllegalArgumentException();
		}
		this.table = table;
		return this;
	}

	public DaoClassBuilder(Config config) {
		if (this.sb == null) {
			this.sb = new StringBuffer();
		}
		if (config != null) {
			this.config = config;
		}
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
		String modelClassName = getModelClassName(table.getName())
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
		String full_name = _package + "." + modelClassName;
		System.out.println(full_name + " ... build success!");
		return full_name;
	}

	protected void buildPackage() {
		if (this.config.getProperties().getProperty(PACKAGE) != null
				&& !this.config.getProperties().getProperty(PACKAGE).equals("")) {
			sb.append("package "
					+ this.config.getProperties().getProperty(PACKAGE) + ";");
			sb.append("\n");
		}
	}

	protected void buildClassName() {
		sb.append("\n");
		String _extends = config.getProperties().getProperty(EXTENDS);
		if (config.getProperties().getProperty("dao.suffix") != null) {
			sb.append("public interface "
					+ this.getModelClassName(table.getName()
							+ config.getProperties().getProperty(SUFFIX))
					+ " extends " + _extends + " {\n");
		} else {
			sb.append("public class "
					+ this.getModelClassName(table.getName()
							+ config.getProperties().getProperty(SUFFIX))
					+ " {\n");
		}
	}

	// protected void buildImport() {
	// if (sb.length() != 0) {
	// sb.append("\n");
	// }
	// sb.append("import com.xiebiao.mybatis.dao.IDao;\n");
	// sb.append("\n");
	// }

	protected void buildToString() {
		// TODO Auto-generated method stub

	}

}
