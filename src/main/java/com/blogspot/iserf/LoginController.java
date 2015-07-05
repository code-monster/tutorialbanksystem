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
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView main() {

		DBConnection connect = new DBConnection("localhost", "root", "entersite", "java_bank");
		connect.initProperties();
		connect.init();
		ArrayList<HashMap<String, String>> citizenData = new ArrayList<HashMap<String,String>>();
        
		
	    try {
	 //   Connection conn = null;
		Statement stmt = null;
		Connection connection    = connect.getConnection();
		
        // Execute SQL query
        stmt = (Statement) connection.createStatement();
        String sql;
        sql = "SELECT * FROM citizen";
        ResultSet rs = stmt.executeQuery(sql);

        // Extract data from result set
        while(rs.next()){
        	
        	HashMap<String, String> hm = new HashMap<String, String>();
        	

           hm.put("id", new Integer(rs.getInt("id")).toString());
           hm.put("firstname", rs.getString("firstname"));
           hm.put("lastname", rs.getString("lastname"));
           hm.put("address", rs.getString("address"));
           hm.put("dob", rs.getDate("dob").toString());
           
       	citizenData.add(hm);
        }


        // Clean-up environment
        rs.close();
        stmt.close();
    
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		User user = new User();
		user.setName("Kubra");
		user.setPassword("sunset00");
		user.setCitizenData(citizenData);
		
		return new ModelAndView("login", "user", user);
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/check-user", method = RequestMethod.POST)
	public ModelAndView checkUser(@ModelAttribute("user") User user) {

	 return new ModelAndView("main", "user", user);
	}
}
