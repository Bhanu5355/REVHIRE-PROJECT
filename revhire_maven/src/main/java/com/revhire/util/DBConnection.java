	package com.revhire.util;
	
	import java.sql.Connection;
	import java.sql.DriverManager;
	
	public class DBConnection {
	
	    public static Connection getConnection() {
	        Connection con = null;
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            con = DriverManager.getConnection(
	                "jdbc:oracle:thin:@localhost:1522:XE",
	                "revhire",
	                "revhire"
	            );
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return con;
	    }
	}
	
