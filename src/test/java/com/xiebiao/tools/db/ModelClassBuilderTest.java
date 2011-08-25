package com.xiebiao.tools.db;

import java.util.HashSet;
import java.util.Set;

public class ModelClassBuilderTest extends BaseTestCase {
    private ModelClassBuilder modelClassBuilder;

    protected void setUp() throws Exception {
	// super.setUp();
	modelClassBuilder = new ModelClassBuilder("com.xiebiao.db");
    }

    public void testCreate() throws Exception {
	Column name = new Column();
	name.setName("name");
	name.setDataType(DataType.VARCHAR);

	Column year = new Column();
	year.setName("date");
	year.setDataType(DataType.YEAR);

	Column datetime = new Column();
	datetime.setName("date");
	datetime.setDataType(DataType.DATETIME);

	Column _float = new Column();
	_float.setName("float");
	_float.setDataType(DataType.FLOAT);

	Set<Column> columns = new HashSet<Column>();
	columns.add(name);
	columns.add(year);
	columns.add(datetime);
	columns.add(_float);
	Table table = new Table();
	table.setName("AA");
	table.setColumns(columns);
	modelClassBuilder.from(table).create();
    }
}
