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
/*import javax.validation.Valid;*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;


import com.blogspot.iserf.utility.*;
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
		
        if (context.getParameterMap().containsKey("account_id") == false  
        		|| Validator.isInteger(context.getParameter("account_id"))==false) {
            
    		Message errorMessage = new Message("error", "Error in account_id param!");
    		model.addAttribute("message", errorMessage);
    		model.addAttribute("pageTitle", "Error page");	
    		return new ModelAndView("error");
        }
        
        int userId = getUserIdByAccountId(new Integer(context.getParameter("account_id")));
		if(userId == 0){
    		Message errorMessage = new Message("error", "Error: account id is not connected for a user!");
    		model.addAttribute("message", errorMessage);
    		model.addAttribute("pageTitle", "Error page");	
    		return new ModelAndView("error");	
			
		}
		
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
		
		Transaction newTransaction = new Transaction(new Integer(context.getParameter("account_id")));
		model.addAttribute("transaction", newTransaction);
		
		
		Breadcrumbs  breadcrumbs  = new Breadcrumbs(request);
		
		breadcrumbs.add("user-profile", "/user-profile?user_id="+userId);
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
	@RequestMapping(value = "/add-transaction-spend", method = RequestMethod.POST)
	public ModelAndView addTransactionSpend(@ModelAttribute("transaction") Transaction transaction, Model model,
			RedirectAttributes redirectAttributes) throws Exception {

	/*	String validReturn = Validator.checkUser(user);
		
		if(validReturn.equals("OK")==false){
			
		message = new Message("error", validReturn);

		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("tmpUser", user);
		
		
		return new ModelAndView("redirect:user-profile?user_id=" + user.getUserId());
		}
		*/
		
		int newTransactiontId = createTransaction(transaction);
		
		Message message = new Message("update", "New Transactiont with id =" + newTransactiontId+ " was created");	
		redirectAttributes.addFlashAttribute("message", message);
		
		return new ModelAndView("redirect:/account-detail?account_id="+transaction.getAccountId());
			
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/add-transaction", method = RequestMethod.POST)
	public ModelAndView addTransaction(@ModelAttribute("transaction") Transaction transaction, Model model,
			RedirectAttributes redirectAttributes) throws Exception {

		
		int newTransactiontId = createTransaction(transaction);
		
		Message message = new Message("update", "New Transactiont with id =" + newTransactiontId+ " was created");	
		redirectAttributes.addFlashAttribute("message", message);
		
		return new ModelAndView("redirect:/account-detail?account_id="+transaction.getAccountId());
			
	}
	
	
	private int createTransaction( Transaction transaction){
		
    	ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO `transactions` (`account_id`, `operation`, `date`, `money`)"
				+ "VALUES (?,?,?,?)";	
		int newTransactiontId = 0;
        ResultSet rs = null;
		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setInt(1, transaction.getAccountId());
			preparedStatement.setString(2, transaction.getOperation());
			
			Date date = new Date();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			preparedStatement.setDate(3, sqlDate);
					
			preparedStatement.setDouble(4, transaction.getMoney());
	
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
	
	
/*    @RequestMapping(value="/", method=RequestMethod.GET)
    public String showForm(Person person) {
        return "form";
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String checkPersonInfo(@Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        return "redirect:/results";
    }*/


}
