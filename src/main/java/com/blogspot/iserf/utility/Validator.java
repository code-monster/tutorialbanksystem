package com.blogspot.iserf.utility;

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
	
	
	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c <= '/' || c >= ':') {
				return false;
			}
		}
		return true;
	}
	
	/*
	public static String checkTransactionAdd (Transaction transaction) {
		
		transaction.getMoney()
		
		
		
		if (transaction.getMoney() < 1) {
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
	*/

}
