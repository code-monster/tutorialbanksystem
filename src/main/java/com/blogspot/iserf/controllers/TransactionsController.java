package com.blogspot.iserf.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.blogspot.iserf.model.Account;
import com.blogspot.iserf.model.Breadcrumbs;
import com.blogspot.iserf.model.Message;
import com.blogspot.iserf.model.Person;
import com.blogspot.iserf.model.Transaction;
import com.blogspot.iserf.model.DB.AccountDb;
import com.blogspot.iserf.model.DB.TransactionDb;
import com.blogspot.iserf.utility.*;
/**
 * Handles requests for the application home page.
 */
@Controller
public class TransactionsController {

	private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

	@Autowired
	private HttpServletRequest context;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/account-detail", method = RequestMethod.GET)
	public ModelAndView editUser(HttpServletRequest request, Model model) 
	{
		
	//	https://gerrydevstory.com/2013/07/11/preserving-validation-error-messages-on-spring-mvc-form-post-redirect-get/
			
	//		http://stackoverflow.com/questions/2543797/spring-redirect-after-post-even-with-validation-errors
		
        if (context.getParameterMap().containsKey("account_id") == false  
        		|| Validator.isInteger(context.getParameter("account_id"))==false) {
            
    		Message errorMessage = new Message("error", "Error in account_id param!");
    		model.addAttribute("message", errorMessage);
    		model.addAttribute("pageTitle", "Error page");	
    		return new ModelAndView("error");
        }
        
        int userId = AccountDb.getUserIdByAccountId(new Integer(context.getParameter("account_id")));
		if(userId == 0){
    		Message errorMessage = new Message("error", "Error: account id is not connected for a user!");
    		model.addAttribute("message", errorMessage);
    		model.addAttribute("pageTitle", "Error page");	
    		return new ModelAndView("error");	
			
		}
		

		ArrayList<Transaction> transactionList = TransactionDb.getTransactionList(new Integer(context.getParameter("account_id")));
		
		
		Transaction newTransaction = new Transaction(new Integer(context.getParameter("account_id")));
		model.addAttribute("transaction", newTransaction);
		
		
		Breadcrumbs  breadcrumbs  = new Breadcrumbs(request);
		
		breadcrumbs.add("user-profile", "/user-profile?user_id="+userId);
		breadcrumbs.add("account-detail");
		
	//	   if (!model.containsAttribute("transaction")) {
		//        model.addAttribute("transaction", new Register());
		//    }
		
		
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("pageTitle", "View  transactions  in account id "+ context.getParameter("account_id"));
		return new ModelAndView("account-detail", "transactionList", transactionList);
	}
	

	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/add-transaction-spend", method = RequestMethod.POST)
	public ModelAndView addTransactionSpend(@ModelAttribute("transaction") Transaction transaction, Model model,
			RedirectAttributes redirectAttributes) throws Exception {

		int newTransactiontId = TransactionDb.createTransaction(transaction);
		
		Message message = new Message("update", "New Transactiont with id =" + newTransactiontId+ " was created");	
		redirectAttributes.addFlashAttribute("message", message);
		
		return new ModelAndView("redirect:/account-detail?account_id="+transaction.getAccountId());
			
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	@RequestMapping(value = "/add-transaction", method = RequestMethod.POST)
	public ModelAndView addTransaction(@ModelAttribute("transaction")  @Valid Transaction transaction, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {
        	
        	redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.transaction", bindingResult);
        	redirectAttributes.addFlashAttribute("transaction", transaction);
        	
    		return new ModelAndView("redirect:/account-detail?account_id="+transaction.getAccountId());
    		
        }
		
		
		int newTransactiontId = TransactionDb.createTransaction(transaction);
		
		Message message = new Message("update", "New Transactiont with id =" + newTransactiontId+ " was created");	
		redirectAttributes.addFlashAttribute("message", message);
		
		return new ModelAndView("redirect:/");
			
	}
	
	
	
    /*
    @RequestMapping(value="/add-transaction", method=RequestMethod.POST)
    public String addTransaction(@Valid Transaction transaction, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "account-detail?account_id=51";
     //       return new ModelAndView("redirect:/account-detail?account_id="+transaction.getAccountId());
        }
	    int newTransactiontId = TransactionDb.createTransaction(transaction);
		
	//	Message message = new Message("update", "New Transactiont with id =" + newTransactiontId+ " was created");	
	//	redirectAttributes.addFlashAttribute("message", message);
		
	//	return new ModelAndView("redirect:/account-detail?account_id="+transaction.getAccountId());
    }
    */
	
    /*
    @RequestMapping(value="/account-detail", method=RequestMethod.POST)
    public String checkPersonInfo(@Valid Transaction transaction, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
      //     return "account-detail";
       //     return "redirect:/account-detail?account_id=51";
         
           
           attr.addFlashAttribute("org.springframework.validation.BindingResult.register", binding);
           attr.addFlashAttribute("register", register);
           return "redirect:/register/create";
           
           
        }
        return "redirect:/home";
    }
    
	*/



}
