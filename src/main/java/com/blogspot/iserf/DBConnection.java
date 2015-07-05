package com.blogspot.iserf;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {


	
	private String host;
	private String root;
	private String password;
	private String nameDb;
	private String url;
	
	private Properties properties = new Properties();
	private Connection mysqlConnections;
	
	
	public DBConnection (String host, String root, String password,  String nameDb)
	{
		this.host = host;
		this.root = root;
		this.password = password;
		this.nameDb = nameDb;
		
		initProperties();
		init();
		
	}
	
	public void initProperties(){
		
		url = "jdbc:mysql://"+host+"/"+nameDb;
		
		properties.setProperty("user", root);
		properties.setProperty("password", password);
		properties.setProperty("characterEncoding", "UTF-8");
		properties.setProperty("useUnicode", "true");
		
	//	System.out.println(url);
		
	}
	
	public void init(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mysqlConnections = DriverManager.getConnection(url, root, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Connection getConnection(){
		
     return mysqlConnections;
		
	}
	
	
	
	
}
