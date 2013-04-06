package com.github.generator.schema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.github.generator.utils.Util;

public class DaoImplClassBuilder extends DaoClassBuilder {
	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());
	public final static String PACKAGE = "dao.impl.package";
	public final static String EXTENDS = "dao.impl.extends";
	public final static String IMPLEMENTS = "dao.impl.implements";
	public final static String SUFFIX = "dao.impl.suffix";
	private String tab = "  ";

	public DaoImplClassBuilder(Config config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

	protected void buildField() {
		sb.append("\n");
		sb.append(tab + tab
				+ "public final String NAME_SPACE = this.getClass().getName();");
	}

	protected void buildImport() {
		if (sb.length() != 0) {
			sb.append("\n");
		}
		sb.append("import com.xiebiao.mybatis.dao.BaseDao;\n");
		sb.append("\n");
	}

	protected void buildPackage() {
		if (this.config.getProperties().getProperty(PACKAGE) != null
				&& !this.config.getProperties().getProperty(PACKAGE).equals("")) {
			sb.append("package "
					+ this.config.getProperties().getProperty(PACKAGE) + ";");
			sb.append("\n");
		}
	}

	protected void buildToString() {
		sb.append("\n");
		sb.append(tab + tab + "public String getNameSpace() {\n");
		sb.append(tab + tab + tab + tab + "return NAME_SPACE;\n");
		sb.append(tab + tab + "}\n");
	}

	protected void buildClassName() {
		sb.append("\n");
		String _implements = config.getProperties().getProperty(IMPLEMENTS);
		if (config.getProperties().getProperty("dao.suffix") != null) {
			sb.append("public class "
					+ Util.getModelClassName(table.getName()
							+ config.getProperties().getProperty(SUFFIX))
					+ " extends " + config.getProperties().getProperty(EXTENDS)
					+ " implements " + _implements + " {\n");
		} else {
			sb.append("public class "
					+ Util.getModelClassName(table.getName()
							+ config.getProperties().getProperty(SUFFIX))
					+ " {\n");
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
		String full_name = _package + "." + modelClassName;
		LOG.debug(full_name + " ... build success!");
		return full_name;
	}

}
