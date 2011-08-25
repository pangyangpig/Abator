package com.xiebiao.tools.db;

public final class Creator {

    /**
     * @param args
     */
    public static void main(String[] args) {
	Config c = new Config();
	ModelClassBuilder modelClassBuilder = new ModelClassBuilder(c
		.getProperties().getProperty("package"));
	for (Table table : c.getTables()) {
	    modelClassBuilder.from(table).create();
	}
    }

}