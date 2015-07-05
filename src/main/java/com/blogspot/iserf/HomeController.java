package com.blogspot.iserf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Statement;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView main(Model model) {

		DBConnection connect = new DBConnection("localhost", "root", "entersite", "java_bank");
		connect.initProperties();
		connect.init();
		ArrayList<User> citizenData = new ArrayList<User>();
        
		
	    try {

		Statement stmt = null;
		Connection connection    = connect.getConnection();
		
        // Execute SQL query
        stmt = (Statement) connection.createStatement();
        String sql;
        sql = "SELECT * FROM citizen";
        ResultSet rs = stmt.executeQuery(sql);

        // Extract data from result set
        while(rs.next()){
        	
    		User user = new User();
    		
    		user.setUserId(rs.getInt("id"));
    		user.setFirstname(rs.getString("firstname"));
    		user.setLastname(rs.getString("lastname"));
    		user.setAddress(rs.getString("address"));
    		user.setDob(rs.getDate("dob").toString());
    		       
        	citizenData.add(user);
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

		return new ModelAndView("home", "clients", citizenData);
	}
	

	/**
	 * contact page
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("pageTitle", "Contact page");
		return "contact";
	}
}
