package com.blogspot.iserf.controllers;

import com.blogspot.iserf.model.*;
import com.blogspot.iserf.model.DB.AccountDb;
import com.blogspot.iserf.model.DB.TransactionDb;
import com.blogspot.iserf.model.DB.UserDb;
import com.blogspot.iserf.utility.Validator;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Handles requests for the application home page.
 */
@Controller
public class TransactionListController {

	private static final Logger logger = LoggerFactory.getLogger(TransactionListController.class);

	@Autowired
	private HttpServletRequest context;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/transaction-list", method = RequestMethod.GET)
	public ModelAndView editUser(Model model, @ModelAttribute("message") Message message)
	{


		model.addAttribute("userList", UserDb.getUserList());

		if (context.getParameterMap().containsKey("showUserTransaction") == false
				|| Validator.isInteger(context.getParameter("showUserTransaction"))==false
				|| context.getParameter("showUserTransaction")=="0") {


			ArrayList<TransactionDisplay> transactionList = TransactionDb.getTransactionDisplayList(0);

			model.addAttribute("pageTitle", "Transactions list");
			model.addAttribute("message", message);
			return new ModelAndView("transaction-list", "transactionList", transactionList);

		}


		ArrayList<TransactionDisplay> transactionList = TransactionDb.getTransactionDisplayList(new Integer(context.getParameter("showUserTransaction")));

		model.addAttribute("pageTitle", "Transactions list");
		model.addAttribute("message", message);
		return new ModelAndView("transaction-list", "transactionList", transactionList);
	}





}