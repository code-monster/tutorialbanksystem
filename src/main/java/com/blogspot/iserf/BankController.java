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

import com.mysql.jdbc.Statement;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BankController {
	
	private static final Logger logger = LoggerFactory.getLogger(BankController.class);
	
	@Autowired
	private HttpServletRequest context;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/bank", method = RequestMethod.GET)
	public ModelAndView bank(Locale locale, Model model) {
//	logger.info("Welcome home! The client locale is {}.", locale);
		
   if(context.getParameter("user_id")== "") {
		 return new ModelAndView("bank", "user_id", context.getParameter("user_id"));
   }
		
		
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
            sql = "SELECT * "
        		+ "FROM  `citizen` "
        		+ "WHERE id = "+context.getParameter("user_id");
      //  WHERE Country='Mexico';
        ResultSet rs = stmt.executeQuery(sql);

        HashMap<String, String> hm; 
        
        // Extract data from result set
        while(rs.next()){
        	
        	hm = new HashMap<String, String>();
        	

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
		user.setName(citizenData.get(0).get("firstname"));
		user.setUserId(new Integer(citizenData.get(0).get("id")));
		user.setPassword("sunset00222");
		user.setCitizenData(citizenData);
		
		return new ModelAndView("bank", "user", user);
		
	
	}
	
	

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/change-user", method = RequestMethod.POST)
	public ModelAndView changeUser(@ModelAttribute("user") User user)  throws Exception {
 
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
                    "SET firstname ='"+ user.getName().toString()+"'"
                    		+ "WHERE id='"+ user.getUserId()+"';";
       
    //    UPDATE citizen SET firstname =  'Alfred Schmidt'
    //    UPDATE citizen SET firstname =`plutto`
        
        System.out.println(sql);
        stmt.executeUpdate(sql);
        
        
   
        stmt.close();
    
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return new ModelAndView("redirect:bank?user_id="+user.getUserId());
 
	}		

	
}
