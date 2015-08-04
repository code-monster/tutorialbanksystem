package com.blogspot.iserf;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * Handles requests for the application home page.
 */
@Controller
public class TransactionsController {

	private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

	@Autowired
	private HttpServletRequest context;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/account-detail", method = RequestMethod.GET)
	public ModelAndView editUser(HttpServletRequest request, Model model) 
	{

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
			preparedStatement.setInt(1, new Integer(context.getParameter("account_id")));
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
		
		Money money = new Money(new Integer(context.getParameter("account_id")));
		model.addAttribute("money", money);
		
		
		Breadcrumbs  breadcrumbs  = new Breadcrumbs(request);
		
		breadcrumbs.add("user-profile", "/user-profile?user_id="+getUserIdByAccountId(new Integer(context.getParameter("account_id"))));
		breadcrumbs.add("account-detail");
		
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("pageTitle", "View  transactions  in account id "+ context.getParameter("account_id"));
		return new ModelAndView("account-detail", "transactionList", transactionList);
	}
	

	private int getUserIdByAccountId(int accountId){
		
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
			preparedStatement.setInt(1, new Integer(context.getParameter("account_id")));
			System.out.println(preparedStatement);
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
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/add-money", method = RequestMethod.POST)
	public ModelAndView addMoney(@ModelAttribute("money") Money money, Model model,
			RedirectAttributes redirectAttributes) throws Exception {


		ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");
		PreparedStatement preparedStatement = null;
		String updateSQL = "UPDATE `users_accounts` SET `balance` = `balance` + ?  WHERE `account_id`=?";

		
	//	UPDATE Orders SET Quantity = Quantity + 1 WHERE ...
		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(updateSQL);
			preparedStatement.setDouble(1, money.getPut());
			preparedStatement.setInt(2, money.getAccountId());

			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView("redirect:account-detail?account_id=" + money.getAccountId());

	}


}
