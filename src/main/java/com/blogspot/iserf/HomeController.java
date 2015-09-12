package com.blogspot.iserf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.blogspot.iserf.utility.*;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private HttpServletRequest context;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView main(Model model, @ModelAttribute("message") Message message) {

		
    	ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");

		ArrayList<User> userList = new ArrayList<User>();

	    try {
	    	
		Statement stmt = null;
		Connection connection    = connect.getMysqlConnections();
		
        // Execute SQL query
        stmt = (Statement) connection.createStatement();
        String sql;
        sql = "SELECT * FROM `users`";
        ResultSet rs = stmt.executeQuery(sql);

        // Extract data from result set
        while(rs.next()){
        	
    		User user = new User();
    		
    		user.setUserId(rs.getInt("id"));
    		user.setFirstname(rs.getString("firstname"));
    		user.setLastname(rs.getString("lastname"));
    		user.setAddress(rs.getString("address"));
    		user.setDob(rs.getDate("dob").toString());
    		       
    		userList.add(user);
        }

        // Clean-up environment
        rs.close();
        stmt.close();
    
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		model.addAttribute("pageTitle", "Home page");
		model.addAttribute("message", message);
		return new ModelAndView("home", "users", userList);
	}
	
	
	private double getUserTotalMoney(int userId){
		
		ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");
        double	totalMoney =  0;

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
		
		ArrayList<Account> accountList = new ArrayList<Account>();
		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, userId);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
		
				if(rs.getInt("account_id")>0){
				Account account  = new Account();
				account.setAccountId(rs.getInt("account_id"));
				account.setBalance(rs.getDouble("balance"));
				account.setNumberOfTransaction(rs.getInt("number_of_transaction"));
				accountList.add(account);
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
				
		
		return totalMoney;
	}

	

	/**
	 * contact page
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		Breadcrumbs  breadcrumbs  = new Breadcrumbs(context);	
		breadcrumbs.add("contact");
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("pageTitle", "Contact page");
		return "contact";
	}
}
