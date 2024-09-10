package org.notice.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DBconfig {
	private static Connection con;
	private static PreparedStatement stmt;
	private static ResultSet rs;
	private static DBconfig db = null;

	private DBconfig() {
		try {

			Properties p = new Properties();
			p.load(PathHelper.fin);
			String driverClassName = p.getProperty("driver.classname");
			String username = p.getProperty("db.username");
			String password = p.getProperty("db.password");
			String url = p.getProperty("db.url");
			Class.forName(driverClassName);
			con = DriverManager.getConnection(url, username, password);
			if (con != null) {
				System.out.println("db connected.............");

			} else {
				System.out.println("db is not connected");
			}
		} catch (Exception ex) {
			System.out.println("error is" + ex);
		}
	}

	public static DBconfig getDBInstanace() {
		if (db == null) {
			db = new DBconfig();
		}
		return db;
	}

	public static Connection getConnection() {
		return con;
	}

	public static PreparedStatement getStatement() {
		return stmt;
	}

	public static ResultSet getResultSet() {
		return rs;
	}

}