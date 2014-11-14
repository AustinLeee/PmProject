package com.dbdoc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;

public class Test {
	public static void main(String[] arg) throws ParseException, SQLException {
		// 加载驱动
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		// 获得连接
		Connection conn = DriverManager.getConnection("jdbc:mysql://10.28.53.52:3306/test", "root", "root");
		// 创建存储过程的对象
		CallableStatement c = conn.prepareCall("{call HANDLECOUNT(?,?,?,?,?,?,?,?,?,?,?,?)}");
		c.setInt(1, 1);
		c.setString(2, "1");
		c.setString(3, "1");
		c.setString(4, "1.1.1.1");
		c.setString(5, "0");
		c.setDate(6, null);
		c.setString(7, "2");
		c.setDate(8, null);
		c.setString(9, "zengjia");
		c.setString(10, "jjj");
		c.setString(11, "5000");

		c.registerOutParameter(12, java.sql.Types.INTEGER);
		// 执行存储过程
		c.execute();
		// 得到存储过程的输出参数值
		System.out.println(c.getString(12));
		conn.close();

	}
}
