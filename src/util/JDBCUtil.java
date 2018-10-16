package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 工具类 DBUtil 获取数据库 Connection
 */

public class JDBCUtil {
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

    /**
     * 关闭连接
     */
    public static void free(ResultSet rs, Statement sta, Connection con) {
        try {
            if (null != rs) {
                rs.close();
                rs = null;
            }

            if (null != sta) {
                sta.close();
                sta = null;
            }

            if (null != con) {
                con.close();
                con = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = String.format("jdbc:sqlite:%s", "db/data.db");
        try {
            DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}