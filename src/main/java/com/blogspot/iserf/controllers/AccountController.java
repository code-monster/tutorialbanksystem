package com.blogspot.iserf.controllers;

import com.blogspot.iserf.model.DB.AccountDb;
import com.blogspot.iserf.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private HttpServletRequest context;


	/**
	 * create new account connected with user
	 */
	@RequestMapping(value = "/add-account", method = RequestMethod.GET)
	public ModelAndView addAccount(Locale locale, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		
		int newAccountId = AccountDb.addAccountToDb(new Integer(context.getParameter("user_id")));
		
		Message message = new Message("update", "New Account with id =" + newAccountId+ " was created");	
		redirectAttributes.addFlashAttribute("message", message);
		
		return new ModelAndView("redirect:user-profile?user_id="+context.getParameter("user_id"));
	}


	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/delete-account", method = RequestMethod.GET)
	public ModelAndView deleteAccount(Locale locale, Model model,
			RedirectAttributes redirectAttributes) {

		AccountDb.deleteAccount(new Integer(context.getParameter("account_id")));
		
		Message message = new Message("update", "Account with id ="+context.getParameter("account_id")+" is deleted");
		redirectAttributes.addFlashAttribute("message", message);
		return new ModelAndView("redirect:user-profile?user_id="+context.getParameter("user_id"));
	}
	
	
}
