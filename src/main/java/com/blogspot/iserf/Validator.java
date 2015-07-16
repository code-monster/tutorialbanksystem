package com.blogspot.iserf;

public class Validator {

	public static String checkUser(User user) {
		
		if (user.getFirstname().length() < 2) {
			return "Field Firstname should have more than 1 letters";

		} else if (user.getLastname().length() < 2) {
			return "Field Lastname should have more than 1 letters";
			
		} else if (user.getAddress().length() < 2) {
			return "Field Address should have more than 1 letters";
			
		} else if (user.getDob().length() != 10) {
			return "Field  date of birth is not valid";
			
		} 

		return "OK";

	}

}
