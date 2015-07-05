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
	@RequestMapping(value = "/edit-user", method = RequestMethod.GET)
	public ModelAndView bank(Locale locale, Model model) {

   if(context.getParameter("user_id")== "") {
		 return new ModelAndView("edit-user", "user_id", context.getParameter("user_id"));
   }

		DBConnection connect = new DBConnection("localhost", "root", "entersite", "java_bank");

		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT *  FROM `citizen` WHERE id = ?";
		
		User user = new User();
	    try {
	    	
		Connection connection    = connect.getConnection();
		preparedStatement = (PreparedStatement) connection.prepareStatement(selectSQL);
		preparedStatement.setInt(1, new Integer(context.getParameter("user_id")));
		ResultSet rs = preparedStatement.executeQuery();
	
        while(rs.next()){

    		user.setUserId(rs.getInt("id"));
    		user.setFirstname(rs.getString("firstname"));
    		user.setLastname(rs.getString("lastname"));
    		user.setAddress(rs.getString("address"));
    		user.setDob(rs.getDate("dob").toString());
       }

        // Clean-up environment
        rs.close();
    
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		model.addAttribute("pageTitle", "Edit User");	
		return new ModelAndView("edit-user", "user", user);
	}
	
	

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/change-user", method = RequestMethod.POST)
	public ModelAndView changeUser(@ModelAttribute("user") User user, Model model)  throws Exception {
 
		DBConnection connect = new DBConnection("localhost", "root", "entersite", "java_bank");
		connect.initProperties();
		connect.init();

	    try {

		Statement stmt = null;
		Connection connection    = connect.getConnection();
		
        // Execute SQL query
        stmt = (Statement) connection.createStatement();
        String sql;
            
        sql = "UPDATE citizen " +
                    "SET firstname ='"+ user.getFirstname().toString()+"', "
                    + "lastname ='"+ user.getLastname().toString()+"', "
                    + "address ='"+ user.getAddress().toString()+"' "
                 //   + "dob ='"+ user.getDob().to+"' "
                    		+ "WHERE id='"+ user.getUserId()+"';";
       
        stmt.executeUpdate(sql);
        stmt.close();
    
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("pageMessage", "update");
		return new ModelAndView("redirect:edit-user?user_id="+user.getUserId());
 
	}		

	
}
