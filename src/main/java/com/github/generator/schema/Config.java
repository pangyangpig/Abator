package com.github.generator.schema;

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
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public final class Config extends Properties {
	private static final long serialVersionUID = -6815576977124926101L;
	private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(this
			.getClass());
	public static final String INFORMATION_SCHEMA = "information_schema";

	private static final String CONFIG_FILE = "config.properties";
	private static final String COLUMNS_SQL = "select * from COLUMNS where TABLE_SCHEMA=? and TABLE_NAME=?";

	public Config() throws Exception {
		if (System.getProperty("db.host") == null
				|| System.getProperty("db.port") == null
				|| System.getProperty("db.name") == null
				|| System.getProperty("db.user") == null
				|| System.getProperty("db.password") == null) {
			File f = new File(".." + File.separator + "conf" + File.separator
					+ CONFIG_FILE);
			this.load(new FileInputStream(f));
			LOG.debug("加载配置文件:" + f.getAbsoluteFile());
		} else {
			String host = System.getProperty("db.host");
			String port = System.getProperty("db.port");
			String name = System.getProperty("db.name");
			String user = System.getProperty("db.user");
			String password = System.getProperty("db.password");
			this.put("db.host", host);
			this.put("db.port", port);
			this.put("db.name", name);
			this.put("db.user", user);
			this.put("db.password", password);
			this.put("package", "");
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Config(String configFile) {
		try {
			this.load(new FileInputStream(new File(configFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Connection getConnection() {
		try {
			String jdbcUrl = "jdbc:mysql://" + this.getProperty("db.host")
					+ ":" + this.getProperty("db.port") + "/"
					+ INFORMATION_SCHEMA + "?user="
					+ this.getProperty("db.user") + "&password="
					+ this.getProperty("db.password");
			Connection connection = DriverManager.getConnection(jdbcUrl);
			if (connection == null) {
				LOG.error("Can't get connection");
			}
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			LOG.error("", e);
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
			preparedStatement.setString(1, this.getProperty("db.name"));
			ResultSet rs = preparedStatement.executeQuery();
			tables = new ArrayList<Table>();
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME").trim();
				Table table = new Table(tableName);
				table.setComment(rs.getString("TABLE_COMMENT").trim());
				table.setColumns(getColumns(table));
				table.setPriKey(getKey(table));
				tables.add(table);
			}
			close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tables;
	}

	private String getKey(Table table) {
		Connection connection = this.getConnection();
		String key = "";
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(COLUMNS_SQL);
			preparedStatement.setString(1, this.getProperty("db.name"));
			preparedStatement.setString(2, table.getName());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				if (rs.getString("COLUMN_KEY").equalsIgnoreCase("PRI")) {
					key = rs.getString("COLUMN_NAME").trim();
				}
			}
			close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return key;
	}

	public Set<Column> getColumns(Table table) {
		Connection connection = this.getConnection();
		Set<Column> columns = null;
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(COLUMNS_SQL);
			preparedStatement.setString(1, this.getProperty("db.name"));
			preparedStatement.setString(2, table.getName());
			ResultSet rs = preparedStatement.executeQuery();
			columns = new HashSet<Column>();
			boolean hasPriKey = false;
			while (rs.next()) {
				Column column = new Column();
				column.setName(rs.getString("COLUMN_NAME").trim());
				column.setDataType(rs.getString("DATA_TYPE"));
				column.setPrecision(rs.getInt("NUMERIC_PRECISION"));
				column.setMaxLength(rs.getLong("CHARACTER_MAXIMUM_LENGTH"));
				if (rs.getString("COLUMN_KEY").equalsIgnoreCase("PRI")) {
					column.setPrimaryKey(true);
					hasPriKey = true;
				}
				column.setComment(rs.getString("COLUMN_COMMENT").trim());
				columns.add(column);
			}
			if (!hasPriKey) {
				LOG.warn("table(" + table.getName()
						+ ") must have PRIMARY KEY. ");
			}
			close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columns;
	}

	public Properties getProperties() {
		return this;
	}

}
