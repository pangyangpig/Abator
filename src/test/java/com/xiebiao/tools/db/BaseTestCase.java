package com.xiebiao.tools.db;

import junit.framework.TestCase;

public class BaseTestCase extends TestCase {
    protected void setUp() throws Exception {
	super.setUp();
	System.setProperty("db.host", "10.10.101.21");
	System.setProperty("db.port", "3306");
	System.setProperty("db.name", "leku_live");
	System.setProperty("db.user", "root");
	System.setProperty("db.password", "weare13G");
    }
}
