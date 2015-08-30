package com.blogspot.iserf.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

	private String host;
	private String user;
	private String password;
	private String nameDb;
	private String url;
	
	private Connection mysqlConnections;
	
	
	public Connection getMysqlConnections() {
		return mysqlConnections;
	}


	public  DB(String host, String user, String password, String nameDb) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.nameDb = nameDb;

		
		init();
	}


	public void init() {
        url = "jdbc:mysql://" + host + "/" + nameDb+"?useUnicode=true&characterEncoding=utf-8";
        
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mysqlConnections = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
}
