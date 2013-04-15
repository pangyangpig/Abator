package com.github.abator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.junit.Before;
import org.junit.Test;

import com.github.abator.schema.Column;
import com.github.abator.schema.SqlMapperBuilder;
import com.github.abator.schema.Table;

public class SqlMapperBuilderTest {
	private org.hsqldb.Server db;
	private String DATABASE_NAME = "generator";

	@Before
	public void setUp() {
		db = new org.hsqldb.Server();
		db.setDatabaseName(0, DATABASE_NAME);
		db.start();
	}

	private Connection getConnection() {

		try {
			Class.forName("org.hsqldb.jdbcDriver");
			Connection connection = DriverManager.getConnection(
					"jdbc:hsqldb:mem: " + DATABASE_NAME, "sa", "");
			return connection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成测试表
	 * 
	 * @return
	 */
	private Table getTableFromDatabase() {
		Table table = null;
		try {
			Connection connection = this.getConnection();
			ResultSet rs = null;
			String tableName = "product";
			// 创建表
			String sql = "CREATE TABLE " + tableName
					+ "(id INTEGER,name VARCHAR(50),mobile CHAR(12))";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.executeUpdate();
			// 查询表
			ps = connection.prepareStatement("SELECT * FROM " + tableName);
			rs = ps.executeQuery();
			table = new Table(rs.getMetaData().getTableName(1).toLowerCase());
			int count = rs.getMetaData().getColumnCount();
			for (; count > 0; --count) {
				Column column = new Column(rs.getMetaData().getColumnLabel(
						count));
				table.addColumn(column);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}

	@Test
	public void test_build() throws Exception {
		Table table = getTableFromDatabase();
		String mapper = SqlMapperBuilder.from(table, "").build();
		String resource = "MapperConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		XMLConfigBuilder configBuilder = new XMLConfigBuilder(inputStream);
		Configuration configuration = configBuilder.parse();
		File f = new File(mapper);
		FileInputStream in = new FileInputStream(f);
		// InputStream inputStream = Resources.getResourceAsStream(mapper);
		XMLMapperBuilder builder = new XMLMapperBuilder(in, configuration,
				mapper, configuration.getSqlFragments());
		builder.parse();
	}
}
