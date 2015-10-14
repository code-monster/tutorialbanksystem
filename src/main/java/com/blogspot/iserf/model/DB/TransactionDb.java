package com.blogspot.iserf.model.DB;

import com.blogspot.iserf.model.SendMoney;
import com.blogspot.iserf.model.Transaction;
import com.blogspot.iserf.model.TransactionDisplay;
import com.blogspot.iserf.model.User;
import com.blogspot.iserf.utility.DB;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class TransactionDb {



	public static int createTransaction( Transaction transaction){
		
    	ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO `transactions` (`account_id`, `operation`, `date`, `money`)"
				+ "VALUES (?,?,?,?)";	
		int newTransactionId = 0;
        ResultSet rs = null;
		try {
			double money;
			String comment;
			if(transaction.isAdd()){

				money = transaction.getMoney();
				comment  = "Add money: " + transaction.getOperation();
			}else{
				//spend
				money = -transaction.getMoney();
				comment  = "Spend money: " + transaction.getOperation();
			}


			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setInt(1, transaction.getAccountId());
			preparedStatement.setString(2, comment);
			
			Date date = new Date();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			preparedStatement.setDate(3, sqlDate);
			preparedStatement.setDouble(4, money);

	
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if(rs != null && rs.next()){
			newTransactionId = rs.getInt(1);
			}
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return	newTransactionId;
	}

	public static String createSendTransaction( SendMoney transaction){

		if(transaction.getSendToAccountId() == transaction.getAccountId()) {
			return	"error, you cannot send money to this account Id = " + transaction.getSendToAccountId() ;
		}


		// send money to another account
		if(createTransaction(new Transaction(transaction))>0) {


			ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
			DB connect = (DB) contextBean.getBean("DB");
			PreparedStatement preparedStatement = null;

			String insertSQL = "INSERT INTO `transactions` (`account_id`, `operation`, `date`, `money`)"
					+ "VALUES (?,?,?,?)";
			int newTransactionId = 0;
			ResultSet rs = null;
			try {

				double money = -transaction.getMoney();
				String comment = "Send money to account Id= " + transaction.getSendToAccountId() + " Comment: " + transaction.getOperation();

				Connection connection = connect.getMysqlConnections();
				preparedStatement = (PreparedStatement) connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

				preparedStatement.setInt(1, transaction.getAccountId());
				preparedStatement.setString(2, comment);

				Date date = new Date();
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				preparedStatement.setDate(3, sqlDate);
				preparedStatement.setDouble(4, money);


				preparedStatement.executeUpdate();
				rs = preparedStatement.getGeneratedKeys();
				if (rs != null && rs.next()) {
					newTransactionId = rs.getInt(1);
				}
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return	"ok";
		}
			return	"error, recipient account (Id "+ + transaction.getSendToAccountId()+") is not found";

	}
	
	
	public static ArrayList<Transaction> getTransactionList( int accountId){
		
    	ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");

		PreparedStatement preparedStatement = null;
		
		String selectSQL = "SELECT * "
				+ "FROM transactions "
				+ "WHERE `account_id` = ? ";
		

		Transaction transaction;
		ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, accountId);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				transaction = new Transaction();
				transaction.setTransactionId(rs.getInt("account_id"));
				transaction.setAccountId(rs.getInt("transactions_id"));
				transaction.setMoney(rs.getDouble("money"));
				transaction.setOperation(rs.getString("operation"));
				transaction.setDate(rs.getDate("date").toString());	
				transactionList.add(transaction);
			}

			// Clean-up environment
			rs.close();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		return	transactionList;
	}

	public static ArrayList<TransactionDisplay> getTransactionDisplayList(int userId) {

		ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
		DB connect = (DB) contextBean.getBean("DB");

		ArrayList<TransactionDisplay> transactionList = new ArrayList<TransactionDisplay>();

		try {

			Connection connection = connect.getMysqlConnections();

			// Execute SQL query
			String sql;
			sql =   " SELECT " +
					" transactions.*" +
					", users.id as user_id " +
					", users.firstname" +
					", users.lastname" +
					" FROM" +
					" transactions" +
					" INNER JOIN users_accounts" +
					" ON (transactions.account_id = users_accounts.account_id)" +
					" INNER JOIN users" +
					" ON (users_accounts.user_id = users.id)";


			PreparedStatement preparedStatement;

			if (userId>0){
				sql+= " WHERE users.id = ?";
				preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
				preparedStatement.setInt(1, userId);
			}else{
				preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			}


			ResultSet rs = preparedStatement.executeQuery();

			// Extract data from result set
			while (rs.next()) {

				TransactionDisplay transaction = new TransactionDisplay();

				transaction.setTransactionId(rs.getInt("transactions_id"));
				transaction.setAccountId(rs.getInt("account_id"));
				transaction.setOperation(rs.getString("operation"));
				transaction.setDate(rs.getDate("date").toString());
				transaction.setMoney(rs.getDouble("money"));
				transaction.setAccountOwnerFirstname(rs.getString("firstname"));
				transaction.setAccountOwnerLastname(rs.getString("lastname"));
				transaction.setUserId(rs.getInt("user_id"));

				transactionList.add(transaction);
			}

			// Clean-up environment
			rs.close();

			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return transactionList;
	}

	public static ArrayList<User> getUserListByTransactionList(ArrayList<TransactionDisplay> transactionDisplayArrayList) {

		ArrayList<User> userList = new ArrayList<User>();
		for(int i=0; i<transactionDisplayArrayList.size(); i++){
			TransactionDisplay transactionDisplay = transactionDisplayArrayList.get(i);

			User user = new User();

			user.setUserId(transactionDisplay.getUserId());
			user.setFirstname(transactionDisplay.getAccountOwnerFirstname());
			user.setLastname(transactionDisplay.getAccountOwnerLastname());

			userList.add(user);

		}

		return  userList;
	}

}
