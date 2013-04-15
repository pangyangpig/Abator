package com.github.generator;

import java.util.HashSet;
import java.util.Set;

import com.github.abator.schema.Column;
import com.github.abator.schema.Config;
import com.github.abator.schema.DaoClassBuilder;
import com.github.abator.schema.Table;

public class DaoClassBuilderTest extends BaseTestCase {
	private DaoClassBuilder daoClassBuilder;
	private Config config;

	protected void setUp() throws Exception {
		// super.setUp();
		config = new Config();
		config.getProperties().put("domain.package", "com.xiebiao.domain");
		config.getProperties().put("dao.package", "com.xiebiao.dao");
		daoClassBuilder = new DaoClassBuilder(config);
	}

	public void test_build() {
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
		daoClassBuilder.from(table).build();
	}
}
