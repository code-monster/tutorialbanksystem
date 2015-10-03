package com.blogspot.iserf.model.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blogspot.iserf.model.Transaction;
import com.blogspot.iserf.utility.DB;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class AccountDb {

	public AccountDb() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static int addAccountToDb(int userId) {

    	ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO `users_accounts` (`user_id`)"
				+ "VALUES (?)";	
		int newAccountId = 0;
        ResultSet rs = null;
		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(insertSQL,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, userId);

			
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if(rs != null && rs.next()){
			newAccountId = rs.getInt(1);
			}
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contextBean.close();
			
		return newAccountId;
	}
	

	public static void deleteAccount(int accountId) {

		
    	ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");
		PreparedStatement preparedStatement = null;
		
		String deleteSQL = "DELETE FROM  `users_accounts` WHERE `account_id`=?";	

		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, accountId);
			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static int getUserIdByAccountId(int accountId){
		
		ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");

		PreparedStatement preparedStatement = null;
		
		String selectSQL = "SELECT user_id "
				+ "FROM `users_accounts` "
				+ "WHERE `account_id` = ? "
				+ "LIMIT 1";
		

		Transaction transaction;
        int userId = 0;
		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, accountId);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				userId = rs.getInt("user_id");
			}

			// Clean-up environment
			rs.close();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userId;
	}

}
