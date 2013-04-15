package com.github.abator;

import com.github.abator.schema.Config;
import com.github.abator.schema.DaoClassBuilder;
import com.github.abator.schema.DaoImplClassBuilder;
import com.github.abator.schema.DomainClassBuilder;
import com.github.abator.schema.SqlMapperBuilder;
import com.github.abator.schema.Table;

/**
 * 工具入口
 * 
 * @author xiebiao[谢彪]
 */
public class Main {
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
