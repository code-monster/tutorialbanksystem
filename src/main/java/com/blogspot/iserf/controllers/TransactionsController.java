package com.blogspot.iserf.controllers;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blogspot.iserf.model.Breadcrumbs;
import com.blogspot.iserf.model.Message;
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

		if(!model.containsAttribute("transactionAddMoney")) {
			Transaction newTransactionAddMoney = new Transaction(new Integer(context.getParameter("account_id")), true);
			model.addAttribute("transactionAddMoney", newTransactionAddMoney);
		}
		
		Breadcrumbs  breadcrumbs  = new Breadcrumbs(request);
		
		breadcrumbs.add("user-profile", "/user-profile?user_id="+userId);
		breadcrumbs.add("account-detail");

		
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("pageTitle", "View  transactions  in account id "+ context.getParameter("account_id"));
		return new ModelAndView("account-detail", "transactionList", transactionList);
	}



	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	@RequestMapping(value = "/add-transaction", method = RequestMethod.POST)
	public ModelAndView addTransaction(@ModelAttribute("transactionAddMoney")  @Valid Transaction transactionAddMoney, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {
        	
        	redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.transactionAddMoney", bindingResult);
        	redirectAttributes.addFlashAttribute("transactionAddMoney", transactionAddMoney);
        	
    		return new ModelAndView("redirect:/account-detail?account_id="+transactionAddMoney.getAccountId());
    		
        }
		
		
		int newTransactiontId = TransactionDb.createTransaction(transactionAddMoney);
		
		Message message = new Message("update", "New Transactiont with id =" + newTransactiontId+ " was created");	
		redirectAttributes.addFlashAttribute("message", message);

		return new ModelAndView("redirect:/account-detail?account_id="+transactionAddMoney.getAccountId());
			
	}
	



}
