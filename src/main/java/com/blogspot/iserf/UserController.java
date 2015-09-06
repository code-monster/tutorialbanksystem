package com.blogspot.iserf;

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
import com.blogspot.iserf.utility.*;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private HttpServletRequest context;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/user-profile", method = RequestMethod.GET)
	public ModelAndView editUser(Locale locale, Model model,
			 @ModelAttribute("tmpUser") User tmpUser,
			 @ModelAttribute("message") Message message) {


        if (context.getParameterMap().containsKey("user_id") == false  || Validator.isInteger(context.getParameter("user_id"))==false ) {
         
    		Message errorMessage = new Message("error", "Error in user_id param!");
    		model.addAttribute("message", errorMessage);
    		model.addAttribute("pageTitle", "Error page");	
    		return new ModelAndView("error");
        }
        
		
		
		
		if (tmpUser.getUserId()>0){
			  model.addAttribute("message", message);
			  model.addAttribute("pageTitle", "Edit User");
			  model.addAttribute("accountList", getUserAccounts(tmpUser.getUserId()));
			  return new ModelAndView("user-profile", "user", tmpUser);	  
		 }
		


    	ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");

		PreparedStatement preparedStatement = null;
		
		String selectSQL = "SELECT users.*, users_accounts.account_id, "
				+ "(SELECT SUM( money ) "
				+ "FROM transactions "
				+ "WHERE account_id = users_accounts.account_id) AS balance "
				+ "FROM users "
				+ "LEFT JOIN users_accounts "
				+ "ON users.id=users_accounts.user_id "
				+ "WHERE id = ? ";
		

		User user = new User();
		ArrayList<Account> accountList = new ArrayList<Account>();
		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, new Integer(context.getParameter("user_id")));
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
		
		if(user.getUserId()==0){
    		Message errorMessage = new Message("error", "User with id "+ context.getParameter("user_id") +" is not found!");
    		model.addAttribute("message", errorMessage);
    		model.addAttribute("pageTitle", "Error page");	
    		return new ModelAndView("error");
			
		}

		Breadcrumbs  breadcrumbs  = new Breadcrumbs(context);	
		breadcrumbs.add("user-profile");
		model.addAttribute("breadcrumbs", breadcrumbs);
		
		model.addAttribute("message", message);
		model.addAttribute("pageTitle", "Edit User");
		model.addAttribute("accountList", accountList);
		return new ModelAndView("user-profile", "user", user);
	}

	private ArrayList<Account> getUserAccounts(int userId){
		
		ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");

		PreparedStatement preparedStatement = null;
		
		String selectSQL = "SELECT users.*, users_accounts.account_id, "
				+ "(SELECT SUM( money ) "
				+ "FROM transactions "
				+ "WHERE account_id = users_accounts.account_id) AS balance "
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
				
		
		return accountList;
	}

	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/change-user", method = RequestMethod.POST)
	public ModelAndView changeUser(@ModelAttribute("user") User user, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		Message message;
		String validReturn = Validator.checkUser(user);
		
		if(validReturn.equals("OK")==false){
			
		message = new Message("error", validReturn);

		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("tmpUser", user);
		
		
		return new ModelAndView("redirect:user-profile?user_id=" + user.getUserId());
		}

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
		message = new Message("update", "Data is update");
		redirectAttributes.addFlashAttribute("message", message);
		return new ModelAndView("redirect:user-profile?user_id=" + user.getUserId());

	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addUser(Locale locale, Model model,
			 @ModelAttribute("tmpUser") User user,
			 @ModelAttribute("message") Message message) {

		Breadcrumbs  breadcrumbs  = new Breadcrumbs(context);	
		breadcrumbs.add("add user");
		model.addAttribute("breadcrumbs", breadcrumbs);
		
		model.addAttribute("message", message);  
		model.addAttribute("pageTitle", "Add new user");
		return new ModelAndView("add-user", "user", user);
	}
	

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/delete-user", method = RequestMethod.GET)
	public ModelAndView deleteUser(Locale locale, Model model,
			RedirectAttributes redirectAttributes) {

		
    	ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");
		PreparedStatement preparedStatement = null;
		
		String deleteSQL = "DELETE FROM  `users` WHERE `id`=?";	

		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, new Integer(context.getParameter("user_id")));
			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Message message = new Message("update", "User is deleted");
		redirectAttributes.addFlashAttribute("message", message);
		return new ModelAndView("redirect:/");
	}
	
	

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/save-new-user", method = RequestMethod.POST)
	public ModelAndView saveNewUser(@ModelAttribute("user") User user, Model model,
			RedirectAttributes redirectAttributes) throws Exception {

		String validReturn = Validator.checkUser(user);
		
		if(validReturn.equals("OK")==false){
			
		Message message = new Message("error", validReturn);

		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("tmpUser", user);
		
		
		return new ModelAndView("redirect:add");
		}
		
		
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

		return new ModelAndView("redirect:user-profile?user_id=" + newUserId);

	}
	
}
