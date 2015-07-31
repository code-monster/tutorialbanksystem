package com.blogspot.iserf;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validator {

	public static String checkUser(User user) {
		
		if (user.getFirstname().length() < 1) {
			return "Field Firstname should have more than 1 letters";

		} else if (user.getLastname().length() < 1) {
			return "Field Lastname should have more than 1 letters";
			
		} else if (user.getAddress().length() < 1) {
			return "Field Address should have more than 1 letters";
			
		}

	     try {
	            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	            Date result =  df.parse(user.getDob());
	        } catch (ParseException pe) {
	        	
	        	return "Field  date of birth is not valid";
	        }
		
		

		return "OK";

	}

}
