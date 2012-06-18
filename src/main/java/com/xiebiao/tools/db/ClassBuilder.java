package com.xiebiao.tools.db;

import java.io.File;

public abstract class ClassBuilder {
	protected String tab;
	protected Table table;
	protected Config config;
	protected StringBuffer sb;
	public static final String OUTPUT = System.getProperty("user.dir")
			+ File.separator + "output";

	public ClassBuilder() {
	}

	public ClassBuilder(String _package) {
		if (sb == null) {
			sb = new StringBuffer();
		}
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

	protected void buildPackage() {

	}

	protected void buildImport() {

	}

	protected void buildAnnotate() {

	}

	protected void buildStructure() {
	}

	protected void buildClassName() {

	}

	protected void buildField() {

	}

	protected void buildSetterGetter() {

	}

	protected void buildClassEnd() {
		sb.append("\n}");
	}

	


	protected abstract void buildToString();

	public final void doBuild() {
		buildPackage();
		buildImport();
		buildAnnotate();
		buildClassName();
		buildField();
		buildStructure();
		buildSetterGetter();
		buildToString();
		buildClassEnd();
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}
}
