package com.github.abator;

public enum DatabaseType {
	MySQL("com.mysql.jdbc.Driver");
	private String driver;

	private DatabaseType(String driver) {
		this.driver = driver;
	}

	public String getDriver() {
		return this.driver;
	}
}
