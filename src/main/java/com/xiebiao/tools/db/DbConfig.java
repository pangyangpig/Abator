package com.xiebiao.tools.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DbConfig {
    public static final String INFORMATION_SCHEMA = "information_schema";
    private String host;
    private int port;
    private String name;
    private String user;
    private String password;
    private Properties properties;

    public DbConfig() {
	if (System.getProperty("db.host") == null
		&& System.getProperty("db.port") == null
		&& System.getProperty("db.name") == null
		&& System.getProperty("db.user") == null
		&& System.getProperty("db.password") == null) {
	    properties = new Properties();
	    try {
		properties.load(new FileInputStream(new File("")));		
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	} else {
	    host = System.getProperty("db.host");
	    port = Integer.valueOf(System.getProperty("db.port"));
	    name = System.getProperty("db.name");
	    user = System.getProperty("db.user");
	    password = System.getProperty("db.password");
	    properties = new Properties();
	    properties.put("db.host", host);
	    properties.put("db.port", port);
	    properties.put("db.name", name);
	    properties.put("db.user", user);
	    properties.put("db.password", password);
	    properties.put("package", "");
	}
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
    }

    private Connection getConnection() {
	try {
	    Connection connection = DriverManager.getConnection("jdbc:mysql://"
		    + host + ":" + port + "/" + INFORMATION_SCHEMA + "?user="
		    + user + "&password=" + password);
	    return connection;
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }

    private void close(Connection connection) {
	try {
	    connection.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public List<Table> getTables() {
	Connection connection = this.getConnection();
	String sql = "select * from TABLES where TABLE_SCHEMA=?";
	List<Table> tables = null;
	try {
	    PreparedStatement preparedStatement = connection
		    .prepareStatement(sql);
	    preparedStatement.setString(1, name);
	    ResultSet rs = preparedStatement.executeQuery();
	    tables = new ArrayList<Table>();
	    while (rs.next()) {
		Table table = new Table();
		table.setName(rs.getString("TABLE_NAME"));
		table.setColumns(getColumns(table));
		tables.add(table);
	    }
	    close(connection);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return tables;
    }

    public List<Column> getColumns(Table table) {
	Connection connection = this.getConnection();
	String sql = "select * from COLUMNS where TABLE_SCHEMA=? and TABLE_NAME=?";
	List<Column> columns = null;
	try {
	    PreparedStatement preparedStatement = connection
		    .prepareStatement(sql);
	    preparedStatement.setString(1, name);
	    preparedStatement.setString(2, table.getName());
	    ResultSet rs = preparedStatement.executeQuery();
	    columns = new ArrayList<Column>();
	    while (rs.next()) {
		Column column = new Column();
		column.setName(rs.getString("COLUMN_NAME"));
		columns.add(column);
	    }
	    close(connection);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return columns;
    }
}
