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
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Config {
	public static final String INFORMATION_SCHEMA = "information_schema";
	private Properties properties;
	private static final String CONFIG_FILE = "config.properties";
	private String _package;
	private String _extends;
	private String _domainSuffix = "";

	public Config() {
		if (System.getProperty("db.host") == null
				|| System.getProperty("db.port") == null
				|| System.getProperty("db.name") == null
				|| System.getProperty("db.user") == null
				|| System.getProperty("db.password") == null) {
			properties = new Properties();
			try {
				properties.load(new FileInputStream(
						new File(System.getProperty("user.dir")
								+ File.separator + CONFIG_FILE)));
				this._package = properties.getProperty("package");
				this._extends = properties.getProperty("extends");
				this._domainSuffix = properties.getProperty("domain.suffix");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String host = System.getProperty("db.host");
			String port = System.getProperty("db.port");
			String name = System.getProperty("db.name");
			String user = System.getProperty("db.user");
			String password = System.getProperty("db.password");
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

	public Config(String configFile) {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(configFile)));
			this._package = properties.getProperty("package");
			this._extends = properties.getProperty("extends");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Connection getConnection() {
		try {
			String jdbcUrl = "jdbc:mysql://"
					+ properties.getProperty("db.host") + ":"
					+ properties.getProperty("db.port") + "/"
					+ INFORMATION_SCHEMA + "?user="
					+ properties.getProperty("db.user") + "&password="
					+ properties.getProperty("db.password");
			Connection connection = DriverManager.getConnection(jdbcUrl);
			if (connection == null) {
				System.out.println("Can't get connection");
			}
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
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

	public String getPackage() {
		return this._package;
	}

	public String getExtends() {
		return this._extends;
	}

	public void setPackage(String _package) {
		this._package = _package;
	}

	public void setExtends(String _extends) {
		this._extends = _extends;
	}

	public List<Table> getTables() {
		Connection connection = this.getConnection();
		String sql = "select * from TABLES where TABLE_SCHEMA=?";
		List<Table> tables = null;
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.setString(1,
					this.properties.getProperty("db.name"));
			ResultSet rs = preparedStatement.executeQuery();
			tables = new ArrayList<Table>();
			while (rs.next()) {
				Table table = new Table();
				table.setName(rs.getString("TABLE_NAME").trim());
				table.setComment(rs.getString("TABLE_COMMENT").trim());
				table.setColumns(getColumns(table));
				tables.add(table);
			}
			close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tables;
	}

	public Set<Column> getColumns(Table table) {
		Connection connection = this.getConnection();
		String sql = "select * from COLUMNS where TABLE_SCHEMA=? and TABLE_NAME=?";
		Set<Column> columns = null;
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.setString(1,
					this.properties.getProperty("db.name"));
			preparedStatement.setString(2, table.getName());
			ResultSet rs = preparedStatement.executeQuery();
			columns = new HashSet<Column>();
			boolean hasPriKey = false;
			while (rs.next()) {
				Column column = new Column();
				column.setName(rs.getString("COLUMN_NAME").trim());
				column.setDataType(rs.getString("DATA_TYPE"));
				if (rs.getString("COLUMN_KEY").equalsIgnoreCase("PRI")) {
					column.setPrimaryKey(true);
					hasPriKey = true;
				}
				column.setComment(rs.getString("COLUMN_COMMENT").trim());
				columns.add(column);
			}
			if (!hasPriKey) {
				System.out.println("WARN: table(" + table.getName()
						+ ") must have PRIMARY KEY. ");
			}
			close(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columns;
	}

	public Properties getProperties() {
		return properties;
	}

	public String getDomainSuffix() {
		return _domainSuffix;
	}

	public void setDomainSuffix(String _domainSuffix) {
		this._domainSuffix = _domainSuffix;
	}
}
