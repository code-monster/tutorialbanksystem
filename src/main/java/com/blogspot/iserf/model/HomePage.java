package com.blogspot.iserf.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blogspot.iserf.utility.DB;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class HomePage {
	
	public static ArrayList<User> getUserList() {

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
        
        
        sql = "SELECT users.*, SUM(transactions.money) AS total "
        	   + "FROM jbank.users "
        	   + "LEFT JOIN  jbank.users_accounts "
        	   + "ON (users_accounts.user_id = users.id) "
        	   + "LEFT JOIN jbank.transactions "
        	   + "ON (transactions.account_id = users_accounts.account_id) "
        	   + "GROUP BY users.firstname;";
        
        ResultSet rs = stmt.executeQuery(sql);

        // Extract data from result set
        while(rs.next()){
        	
    		User user = new User();
    		
    		user.setUserId(rs.getInt("id"));
    		user.setFirstname(rs.getString("firstname"));
    		user.setLastname(rs.getString("lastname"));
    		user.setAddress(rs.getString("address"));
    		user.setDob(rs.getDate("dob").toString());
    		user.setTotalMoney(rs.getDouble("total"));
    		       
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
	    
			
		return userList;
	}
	

}
