package com.github.generator;

import com.github.generator.schema.Config;
import com.github.generator.schema.DaoClassBuilder;
import com.github.generator.schema.DaoImplClassBuilder;
import com.github.generator.schema.DomainClassBuilder;
import com.github.generator.schema.SqlMapperBuilder;
import com.github.generator.schema.Table;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Config c = null;
		try {
			c = new Config();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		DomainClassBuilder modelClassBuilder = new DomainClassBuilder(c);
		DaoClassBuilder daoClassBuilder = new DaoClassBuilder(c);
		DaoImplClassBuilder daoImplClassBuilder = new DaoImplClassBuilder(c);
		for (Table table : c.getTables()) {
			modelClassBuilder.from(table).build();
			String dao = daoClassBuilder.from(table).build();
			Config pro = daoImplClassBuilder.getConfig();
			pro.put("dao.impl.implements", dao);
			String daoImpl = daoImplClassBuilder.from(table).build();
			SqlMapperBuilder.from(table, daoImpl).build();
		}
	}
}
