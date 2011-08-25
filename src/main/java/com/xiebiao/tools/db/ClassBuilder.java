package com.xiebiao.tools.db;

public class ClassBuilder {
    protected void buildPackage() {

    }

    protected void buildImport() {

    }

    protected void buildAnnotate() {

    }

    protected void buildClassName() {

    }

    protected void buildField() {

    }

    protected void buildSetterGetter() {

    }

    protected void buildClassEnd() {

    }

    public final void build() {
	buildPackage();
	buildImport();
	buildAnnotate();
	buildClassName();
	buildField();
	buildSetterGetter();
	buildClassEnd();
    }
}
