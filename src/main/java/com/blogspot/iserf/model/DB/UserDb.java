package com.blogspot.iserf.model.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blogspot.iserf.model.Account;
import com.blogspot.iserf.model.AccountList;
import com.blogspot.iserf.model.User;
import com.blogspot.iserf.utility.DB;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class UserDb {

	public UserDb() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static int addNewUserToDb(User user) throws Exception {
		
    	ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO `users` (`firstname`, `lastname`, `address`, `dob`)"
				+ "VALUES (?,?,?,?)";	
		int newUserId = 0;
        ResultSet rs = null;
		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(insertSQL,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getFirstname());
			preparedStatement.setString(2, user.getLastname());
			preparedStatement.setString(3, user.getAddress());
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(user.getDob());
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			preparedStatement.setDate(4, sqlDate);
			
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if(rs != null && rs.next()){
			newUserId = rs.getInt(1);
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newUserId;
	}
	
	
	public static void deleteUser(int userId) throws Exception {
		
		
    	ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");
		PreparedStatement preparedStatement = null;
		
		String deleteSQL = "DELETE FROM  `users` WHERE `id`=?";	

		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, userId);
			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void changeUser(User user) throws Exception {
		
		ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");
		PreparedStatement preparedStatement = null;
		String updateSQL = "UPDATE `users` SET `firstname` =?, `lastname` = ?, `address` = ?, `dob` = ? WHERE `id`=?";

		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(updateSQL);
			preparedStatement.setString(1, user.getFirstname());
			preparedStatement.setString(2, user.getLastname());
			preparedStatement.setString(3, user.getAddress());
			
			System.out.println(user.getFirstname());
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(user.getDob());
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			preparedStatement.setDate(4, sqlDate);
			
			preparedStatement.setInt(5, new Integer(user.getUserId()));
			// System.out.println(preparedStatement);
			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	public static  User getUser(int userId){
		
	
		ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");
    	double totalMoney =  0;

		PreparedStatement preparedStatement = null;
		
		String selectSQL = "SELECT users.*, users_accounts.account_id, "
				+ "(SELECT SUM( money ) "
				+ "FROM transactions "
				+ "WHERE account_id = users_accounts.account_id) AS balance, "
				+ "(SELECT COUNT( * ) "
				+ "FROM transactions "
				+ "WHERE account_id = users_accounts.account_id) AS number_of_transaction "
				+ "FROM users "
				+ "LEFT JOIN users_accounts "
				+ "ON users.id=users_accounts.user_id "
				+ "WHERE id = ? ";
		

		User user = new User();
		AccountList accountList = new AccountList();
		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, userId);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				user.setUserId(rs.getInt("id"));
				user.setFirstname(rs.getString("firstname"));
				user.setLastname(rs.getString("lastname"));
				user.setAddress(rs.getString("address"));
				user.setDob(rs.getDate("dob").toString());
				
				if(rs.getInt("account_id")>0){
				Account account  = new Account();
				account.setAccountId(rs.getInt("account_id"));
				account.setBalance(rs.getDouble("balance"));
				account.setNumberOfTransaction(rs.getInt("number_of_transaction"));
				accountList.add(account);
				totalMoney +=  rs.getDouble("balance");
				}
		
			}

			// Clean-up environment
			rs.close();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		accountList.setTotalMoney(totalMoney);
		user.setAccountList(accountList);
		
		return user;
	}
	

}
