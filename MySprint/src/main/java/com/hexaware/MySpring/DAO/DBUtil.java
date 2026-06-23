package com.hexaware.MySpring.DAO;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil { // Factory class

	public static Connection getDBConnection() throws SQLException {// Factory method


		

		String url = "jdbc:mysql://localhost:3306/testdb";

		String username = "root";

		String password = "Adhi@3129";

		//DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

		Connection conn = DriverManager.getConnection(url, username, password);

		return conn;

	}

}
