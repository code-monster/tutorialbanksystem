package com.blogspot.iserf;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private String host;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	private String user;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNameDb() {
		return nameDb;
	}

	public void setNameDb(String nameDb) {
		this.nameDb = nameDb;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	private String password;
	private String nameDb;
	private String url;

	private Properties properties = new Properties();
	private Connection mysqlConnections;

	public DBConnection(String host, String user, String password, String nameDb) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.nameDb = nameDb;

		initProperties();
		init();

	}

	public void initProperties() {

		url = "jdbc:mysql://" + host + "/" + nameDb;

		properties.setProperty("user", user);
		properties.setProperty("password", password);
		properties.setProperty("characterEncoding", "UTF-8");
		properties.setProperty("useUnicode", "true");

		// System.out.println(url);

	}

	public void init() {

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

	public Connection getConnection() {

		return mysqlConnections;

	}

}
