package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 工具类 DBUtil 获取数据库 Connection
 */

public class DBUtil {
	static final String database = "db/data.db";
	
	static Connection Connection = null;

	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		if (Connection == null) {
			String url = String.format("jdbc:sqlite:%s", database);
			return DriverManager.getConnection(url);
		}
		return Connection;
	}

	public static void main(String[] args) {
		String url = String.format("jdbc:sqlite:%s", "db/data.db");
		try {
			DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}