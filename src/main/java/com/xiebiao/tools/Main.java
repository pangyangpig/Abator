package com.xiebiao.tools;

import java.util.Properties;

import com.xiebiao.tools.db.Config;
import com.xiebiao.tools.db.DaoClassBuilder;
import com.xiebiao.tools.db.DaoImplClassBuilder;
import com.xiebiao.tools.db.DomainClassBuilder;
import com.xiebiao.tools.db.SqlMapperBuilder;
import com.xiebiao.tools.db.Table;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Config c = new Config();
		DomainClassBuilder modelClassBuilder = new DomainClassBuilder(c);
		DaoClassBuilder daoClassBuilder = new DaoClassBuilder(c);
		DaoImplClassBuilder daoImplClassBuilder = new DaoImplClassBuilder(c);
		for (Table table : c.getTables()) {
			modelClassBuilder.from(table).build();
			String dao = daoClassBuilder.from(table).build();
			Properties pro = daoImplClassBuilder.getConfig().getProperties();
			pro.put("dao.impl.implements", dao);
			String daoImpl = daoImplClassBuilder.from(table).build();
			SqlMapperBuilder.from(table, daoImpl).build();
		}
	}
}
