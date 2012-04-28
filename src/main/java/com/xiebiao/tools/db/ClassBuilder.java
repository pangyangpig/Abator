package com.xiebiao.tools.db;

public abstract class ClassBuilder {
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

	protected abstract void buildToString();

	protected void buildClassEnd() {

	}

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
}
