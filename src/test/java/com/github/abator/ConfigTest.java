package com.github.abator;

import com.github.abator.schema.Config;

import junit.framework.TestCase;

public class ConfigTest extends TestCase {
	private Config dbConfig;

	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("db.host", "localhost");
		System.setProperty("db.port", "3306");
		System.setProperty("db.name", "mybatis_t");
		System.setProperty("db.user", "root");
		System.setProperty("db.password", "wangzhu");
		dbConfig = new Config();
	}

	public void testGetTables() {
		org.junit.Assert.assertNotNull(dbConfig.getTables());
	}
}
