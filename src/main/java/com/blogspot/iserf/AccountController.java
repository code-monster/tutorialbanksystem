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

/**
 * Handles requests for the application home page.
 */
@Controller
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private HttpServletRequest context;


	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/add-account", method = RequestMethod.GET)
	public ModelAndView addAccount(Locale locale, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {


		
    	ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO `users_accounts` (`user_id`, `balance`)"
				+ "VALUES (?,?)";	
		int newAccountId = 0;
        ResultSet rs = null;
		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(insertSQL,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, new Integer(context.getParameter("user_id")));
			preparedStatement.setInt(2, 0);

		
			
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
		
		Message message = new Message("update", "New Account with id =" + newAccountId+ " was created");	
		redirectAttributes.addFlashAttribute("message", message);
		
		return new ModelAndView("redirect:user-profile?user_id="+context.getParameter("user_id"));
	}



	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/delete-account", method = RequestMethod.GET)
	public ModelAndView deleteAccount(Locale locale, Model model,
			RedirectAttributes redirectAttributes) {

		
    	ClassPathXmlApplicationContext contextBean = new ClassPathXmlApplicationContext("app-beans.xml");
    	DB connect = (DB)contextBean.getBean("DB");
		PreparedStatement preparedStatement = null;
		
		String deleteSQL = "DELETE FROM  `users_accounts` WHERE `account_id`=?";	

		try {

			Connection connection = connect.getMysqlConnections();
			preparedStatement = (PreparedStatement) connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, new Integer(context.getParameter("account_id")));
			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Message message = new Message("update", "Account with id ="+context.getParameter("account_id")+" is deleted");
		redirectAttributes.addFlashAttribute("message", message);
		return new ModelAndView("redirect:user-profile?user_id="+context.getParameter("user_id"));
	}
	
	
}
