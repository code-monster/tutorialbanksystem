package com.blogspot.iserf.model.DB;

import com.blogspot.iserf.model.SendMoney;
import com.blogspot.iserf.model.Transaction;
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
		int newTransactiontId = 0;
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
			newTransactiontId = rs.getInt(1);
			}
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return	newTransactiontId;
	}

	public static String createSendTransaction( SendMoney transaction){

		if(transaction.getSendToAccountId() == transaction.getAccountId()) {
			return	"error - you cannot send money to this account Id = " + transaction.getSendToAccountId() ;
		}


		// send money to another account
		if(createTransaction(new Transaction(transaction))>0) {


			ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
			DB connect = (DB) contextBean.getBean("DB");
			PreparedStatement preparedStatement = null;

			String insertSQL = "INSERT INTO `transactions` (`account_id`, `operation`, `date`, `money`)"
					+ "VALUES (?,?,?,?)";
			int newTransactiontId = 0;
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
					newTransactiontId = rs.getInt(1);
				}
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return	"ok";
		}
			return	"error";

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

	public static ArrayList<Transaction> getAllTransactionList() {

		ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
		DB connect = (DB) contextBean.getBean("DB");

		ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

		try {

			Statement stmt = null;
			Connection connection = connect.getMysqlConnections();

			// Execute SQL query
			stmt = (Statement) connection.createStatement();
			String sql;
			sql = "SELECT * FROM `transactions`";



			ResultSet rs = stmt.executeQuery(sql);

			// Extract data from result set
			while (rs.next()) {

				Transaction transaction = new Transaction();

				transaction.setTransactionId(rs.getInt("transactions_id"));
				transaction.setAccountId(rs.getInt("account_id"));
				transaction.setOperation(rs.getString("operation"));
				transaction.setDate(rs.getDate("date").toString());
				transaction.setMoney(rs.getDouble("money"));

				transactionList.add(transaction);
			}

			// Clean-up environment
			rs.close();
			stmt.close();

			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return transactionList;
	}

}
