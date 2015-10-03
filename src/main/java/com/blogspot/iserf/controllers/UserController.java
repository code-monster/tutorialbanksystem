package com.blogspot.iserf.controllers;

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
import com.blogspot.iserf.model.Account;
import com.blogspot.iserf.model.AccountList;
import com.blogspot.iserf.model.Breadcrumbs;
import com.blogspot.iserf.model.Message;
import com.blogspot.iserf.model.User;
import com.blogspot.iserf.model.DB.UserDb;
import com.blogspot.iserf.utility.*;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private HttpServletRequest context;
	
	private double totalMoney;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/user-profile", method = RequestMethod.GET)
	public ModelAndView editUser(Locale locale, Model model,
			 @ModelAttribute("tmpUser") User tmpUser,
			 @ModelAttribute("message") Message message) {


        if (context.getParameterMap().containsKey("user_id") == false  || Validator.isInteger(context.getParameter("user_id"))==false ) {
         
    		Message errorMessage = new Message("error", "Error in user_id param!");
    		model.addAttribute("message", errorMessage);
    		model.addAttribute("pageTitle", "Error page");	
    		return new ModelAndView("error");
        }

		
		if (tmpUser.getUserId()>0){
			  model.addAttribute("message", message);
			  model.addAttribute("pageTitle", "Edit User");
			  AccountList<Account> tmpUserAccounts = (AccountList<Account>) UserDb.getUser(tmpUser.getUserId()).getAccountList();
			  model.addAttribute("accountList", tmpUserAccounts);
			  tmpUser.setTotalMoney(tmpUserAccounts.getTotalMoney());
			  return new ModelAndView("user-profile", "user", tmpUser);	  
		 }
		
		
		User user  = UserDb.getUser(new Integer(context.getParameter("user_id")));
		AccountList<Account> accountList = user.getAccountList();
		
		if(user.getUserId()==0){
    		Message errorMessage = new Message("error", "User with id "+ context.getParameter("user_id") +" is not found!");
    		model.addAttribute("message", errorMessage);
    		model.addAttribute("pageTitle", "Error page");	
    		return new ModelAndView("error");
			
		}

		user.setTotalMoney(accountList.getTotalMoney());
		Breadcrumbs  breadcrumbs  = new Breadcrumbs(context);	
		breadcrumbs.add("user-profile");
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("message", message);
		model.addAttribute("pageTitle", "Edit User");
		model.addAttribute("accountList", accountList);
		return new ModelAndView("user-profile", "user", user);
	}

	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/change-user", method = RequestMethod.POST)
	public ModelAndView changeUser(@ModelAttribute("user") User user, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		Message message;
		String validReturn = Validator.checkUser(user);
		
		if(validReturn.equals("OK")==false){
			
		message = new Message("error", validReturn);

		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("tmpUser", user);
		
		
		return new ModelAndView("redirect:user-profile?user_id=" + user.getUserId());
		}

		UserDb.changeUser(user);

		message = new Message("update", "Data is update");
		redirectAttributes.addFlashAttribute("message", message);
		return new ModelAndView("redirect:user-profile?user_id=" + user.getUserId());

	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addUser(Locale locale, Model model,
			 @ModelAttribute("tmpUser") User user,
			 @ModelAttribute("message") Message message) {

		Breadcrumbs  breadcrumbs  = new Breadcrumbs(context);	
		breadcrumbs.add("add user");
		model.addAttribute("breadcrumbs", breadcrumbs);
		
		model.addAttribute("message", message);  
		model.addAttribute("pageTitle", "Add new user");
		return new ModelAndView("add-user", "user", user);
	}
	

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@RequestMapping(value = "/delete-user", method = RequestMethod.GET)
	public ModelAndView deleteUser(Locale locale, Model model,
			RedirectAttributes redirectAttributes) throws NumberFormatException, Exception {

		UserDb.deleteUser(new Integer(context.getParameter("user_id")));
		
		Message message = new Message("update", "User is deleted");
		redirectAttributes.addFlashAttribute("message", message);
		return new ModelAndView("redirect:/");
	}
	
	

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/save-new-user", method = RequestMethod.POST)
	public ModelAndView saveNewUser(@ModelAttribute("user") User user, Model model,
			RedirectAttributes redirectAttributes) throws Exception {

		String validReturn = Validator.checkUser(user);
		
		if(validReturn.equals("OK")==false){
			
		Message message = new Message("error", validReturn);

		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("tmpUser", user);
		
		
		return new ModelAndView("redirect:add");
		}
		
		int newUserId = UserDb.addNewUserToDb(user);        

		return new ModelAndView("redirect:user-profile?user_id=" + newUserId);

	}
	
}
