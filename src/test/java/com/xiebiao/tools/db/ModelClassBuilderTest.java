package com.xiebiao.tools.db;

public class ModelClassBuilderTest extends BaseTestCase {
    private ModelClassBuilder modelClassBuilder;

    protected void setUp() throws Exception {
	super.setUp();
	modelClassBuilder = new ModelClassBuilder(new DbConfig());
    }

    public void testBuild() throws Exception {
	modelClassBuilder.build();
    }
}
