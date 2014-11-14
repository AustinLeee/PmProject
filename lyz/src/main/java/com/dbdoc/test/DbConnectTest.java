package com.dbdoc.test;

import com.dbdoc.db.datasource.DataSourceProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectTest {

	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/classloader_test?useUnicode=true&characterEncoding=UTF-8";
	private static final String user = "root";
	private static final String password = "141421";

	public static void main(String args[]) {
		if (DataSourceProvider.getConnection() != null) {
		} else {
		}
	}

	public static Connection getConnnection() {

		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			if (!conn.isClosed())
				System.out.println("连接被关闭..");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				if (null != conn)
					conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConnection(Connection conn) {
		try {
			if (null != conn) {
				conn.close();
				System.out.println("关闭连接失败...");
			} else {
				System.out.println("���ݿ����Ӳ�����...");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
