package com.xiebiao.tools.db;

public final class Creator {

    /**
     * @param args
     */
    public static void main(String[] args) {
	Config c = new Config();
	DomainClassBuilder modelClassBuilder = new DomainClassBuilder(c);
	for (Table table : c.getTables()) {
	    modelClassBuilder.from(table).build();
	}
    }

}
