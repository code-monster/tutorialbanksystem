package com.blogspot.iserf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

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
	public ModelAndView editUser(Locale locale, Model model) {

		if (context.getParameter("user_id") == "") {
			return new ModelAndView("edit-user", "user_id", context.getParameter("user_id"));
		}

		DBConnection connect = new DBConnection("localhost", "root", "entersite", "java_bank");

		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT *  FROM `citizen` WHERE id = ?";

		User user = new User();
		try {

			Connection connection = connect.getConnection();
			preparedStatement = (PreparedStatement) connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, new Integer(context.getParameter("user_id")));
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				user.setUserId(rs.getInt("id"));
				user.setFirstname(rs.getString("firstname"));
				user.setLastname(rs.getString("lastname"));
				user.setAddress(rs.getString("address"));
				user.setDob(rs.getDate("dob").toString());
			}

			// Clean-up environment
			rs.close();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("pageTitle", "Edit User");
		return new ModelAndView("user-profile", "user", user);
	}


	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/change-user", method = RequestMethod.POST)
	public ModelAndView changeUser(@ModelAttribute("user") User user, Model model) throws Exception {

		DBConnection connect = new DBConnection("localhost", "root", "entersite", "java_bank");
		PreparedStatement preparedStatement = null;
		String updateSQL = "UPDATE `citizen` SET `firstname` =?, `lastname` = ?, `address` = ?, `dob` = ? WHERE `id`=?";

		try {

			Connection connection = connect.getConnection();
			preparedStatement = (PreparedStatement) connection.prepareStatement(updateSQL);
			preparedStatement.setString(1, user.getFirstname());
			preparedStatement.setString(2, user.getLastname());
			preparedStatement.setString(3, user.getAddress());
			
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

		model.addAttribute("pageMessage", "update");
		return new ModelAndView("redirect:user-profile?user_id=" + user.getUserId());

	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addUser(Locale locale, Model model) {

		User user = new User();

		model.addAttribute("pageTitle", "Add new client");
		return new ModelAndView("add-user", "user", user);
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/save-new-user", method = RequestMethod.POST)
	public ModelAndView saveNewUser(@ModelAttribute("user") User user, Model model) throws Exception {

		DBConnection connect = new DBConnection("localhost", "root", "entersite", "java_bank");
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO `citizen` (`firstname`, `lastname`, `address`, `dob`)"
				+ "VALUES (?,?,?,?)";	
		int newUserId = 0;
        ResultSet rs = null;
		try {

			Connection connection = connect.getConnection();
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

		model.addAttribute("pageMessage", "update");
		return new ModelAndView("redirect:user-profile?user_id=" + newUserId);

	}
	
}
