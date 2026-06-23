package com.hexaware.springEMS.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class DBUtil { // Factory class

	public static Connection getDBConnection() throws SQLException {// Factory method

		String url = "jdbc:mysql://localhost:3306/ems";

		String username = "root";

		String password = "Adhi@3129";

		//DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

		Connection conn = DriverManager.getConnection(url, username, password);

		return conn;

	}
}
