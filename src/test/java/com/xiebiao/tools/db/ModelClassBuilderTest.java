package com.xiebiao.tools.db;

public class ModelClassBuilderTest extends BaseTestCase {
    private ModelClassBuilder modelClassBuilder;
    private Config c;

    protected void setUp() throws Exception {
	// super.setUp();
	c = new Config();
	modelClassBuilder = new ModelClassBuilder("com.xiebiao.db");
    }

    public void testCreate() throws Exception {
	for (Table table : c.getTables()) {
	    modelClassBuilder.from(table).create();
	}

    }
}
