package com.xiebiao.tools.db;

import junit.framework.TestCase;

public class BaseTestCase extends TestCase {
    protected void setUp() throws Exception {
	super.setUp();
	System.setProperty("db.host", "localhost");
	System.setProperty("db.port", "3306");
	System.setProperty("db.name", "aa");
	System.setProperty("db.user", "root");
	System.setProperty("db.password", "root");
    }
}
