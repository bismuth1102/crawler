package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Jdbc {

	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/crawler?useSSL=false";
	String user = "root";
	// String password = "1129009";
	// String password = "951102ljc";
	// String password = "Mylinux123*";

	public Connection connect(String password) {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			if (!con.isClosed())
				System.out.println("Database!");
		} catch (Exception e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		}
		return con;
	}

	public ResultSet query(Connection con, String sql) {
		ResultSet rs = null;
		try {
			Statement statement = con.createStatement();
			rs = statement.executeQuery(sql);
		} catch (Exception e) {
			System.out.println("execute failed!");
			e.printStackTrace();
		}
		return rs;
	}

}
