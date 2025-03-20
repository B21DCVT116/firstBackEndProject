package com.javaweb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private static String URL_DB = "jdbc:mysql://127.0.0.1:3306/quanlydaotao";
	private static String USER_NAME = "root";
	private static String PASS_WORD = "123456";
	private static Connection con;
	
	public static Connection getConnect() {
		con = null;
		try {
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		con = (Connection) DriverManager.getConnection(URL_DB, USER_NAME, PASS_WORD);
		}
		catch(SQLException e){
			System.out.print(e);
		}
		return con;
	}
	public static void main(String [] args) {
		try {
			Connection c = getConnect();
			if(c!=null) System.out.print("ok");
			else System.out.print("no");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
