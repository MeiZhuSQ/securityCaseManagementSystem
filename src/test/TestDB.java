package test;

import java.sql.*;
//import org.sqlite.JDBC;

public class TestDB {
	/**
	 * 这是个非常简单的SQLite的Java程序， 程序中创建数据库、创建表、然后插入数据， 最后读出数据显示出来
	 */
	public static void main(String[] args) {
		try {
			// 连接SQLite的JDBC
			Class.forName("org.sqlite.JDBC");
			// 建立一个数据库名zieckey.db的连接，如果不存在就在当前目录下创建之
			Connection conn = DriverManager.getConnection("jdbc:sqlite:db/data.db");
			Statement stat = conn.createStatement();
			int i = stat.executeUpdate("select * from sqlite_master  WHERE type = 'table';");
			System.out.println(i);
			
			stat.executeUpdate("drop table tbl1");// 创建一个表，两列
			stat.executeUpdate("create table tbl1(name varchar(20), salary int);");// 创建一个表，两列
			stat.executeUpdate("insert into tbl1 values('ZhangSan',8000);");// 插入数据
			stat.executeUpdate("insert into tbl1 values('LiSi',7800);");
			stat.executeUpdate("insert into tbl1 values('WangWu',5800);");
			stat.executeUpdate("insert into tbl1 values('ZhaoLiu',9100);");
			ResultSet rs = stat.executeQuery("select * from tbl1;");// 查询数据
			while (rs.next()) {// 将查询到的数据打印出来
				System.out.print("name = " + rs.getString("name") + " ");// 列属性一
				System.out.println("salary = " + rs.getString("salary"));// 列属性二
			}
			rs.close();
			conn.close();// 结束数据库的连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
