package com.xiebiao.tools.db;

import java.util.List;

/**
 * 生成表对应的Model
 * 
 * @author xiaog
 * 
 */
public class ModelClassBuilder {
    private DbConfig dbConfig;

    public ModelClassBuilder(DbConfig dbConfig) {
	this.dbConfig = dbConfig;
    }

    public void build() {
	List<Table> tables = this.dbConfig.getTables();
	for (Table table : tables) {
	    System.out.println(table.toString());
	}

    }
}
