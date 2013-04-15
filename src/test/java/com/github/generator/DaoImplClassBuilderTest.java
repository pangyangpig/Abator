package com.github.generator;

import java.util.HashSet;
import java.util.Set;

import com.github.abator.schema.Column;
import com.github.abator.schema.Config;
import com.github.abator.schema.DaoImplClassBuilder;
import com.github.abator.schema.Table;

public class DaoImplClassBuilderTest extends BaseTestCase {
	private DaoImplClassBuilder daoImplClassBuilder;
	private Config config;

	public void test_build() {
		try {
			config = new Config();
		} catch (Exception e) {
			e.printStackTrace();
		}
		config.getProperties().put("dao.impl.package", "com.xiebiao.dao.impl");
		daoImplClassBuilder = new DaoImplClassBuilder(config);
		Column name = new Column();
		name.setName("name");
		name.setDataType("varchar");

		Column year = new Column();
		year.setName("date");
		year.setDataType("year");

		Column datetime = new Column();
		datetime.setName("date");
		datetime.setDataType("datetime");

		Column _float = new Column();
		_float.setName("float");
		_float.setDataType("float");
		_float.setComment("字段说明");

		Set<Column> columns = new HashSet<Column>();
		columns.add(name);
		columns.add(year);
		columns.add(datetime);
		columns.add(_float);
		Table table = new Table();
		table.setName("Person");
		table.setColumns(columns);
		table.setComment("这是表说明");
		daoImplClassBuilder.from(table).build();
	}
}
