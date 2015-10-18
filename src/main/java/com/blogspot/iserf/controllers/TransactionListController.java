package com.blogspot.iserf.controllers;

import com.blogspot.iserf.model.Breadcrumbs;
import com.blogspot.iserf.model.DB.TransactionDb;
import com.blogspot.iserf.model.DB.UserDb;
import com.blogspot.iserf.model.Message;
import com.blogspot.iserf.model.TransactionDisplay;
import com.blogspot.iserf.utility.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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


		int userId;
		if (context.getParameterMap().containsKey("showUserTransaction") == false) {
			userId = 0;
		}else{

			if (Validator.isInteger(context.getParameter("showUserTransaction"))==false) {

				Message errorMessage = new Message("error", "Error in showUserTransaction param!");
				model.addAttribute("message", errorMessage);
				model.addAttribute("pageTitle", "Error page");
				return new ModelAndView("error");
			}else{
				userId = new Integer(context.getParameter("showUserTransaction"));
			}

		}


		String startDateRangeSql = null;
		String endDateRangeSql = null;


		if (context.getParameterMap().containsKey("startDateRange") == true && context.getParameterMap().containsKey("endDateRange") == true) {

			if (Validator.isDate(context.getParameter("startDateRange"))==false || Validator.isDate(context.getParameter("endDateRange"))==false) {

				Message errorMessage = new Message("error", "Error in startDateRange or endDateRange param!");
				model.addAttribute("message", errorMessage);
				model.addAttribute("pageTitle", "Error page");
				return new ModelAndView("error");
			}else{

				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


					startDateRangeSql = context.getParameter("startDateRange");
					//startDateRangeSql = new java.sql.Date(startDateRange.getTime());

					endDateRangeSql = context.getParameter("endDateRange");
					//endDateRangeSql = new java.sql.Date(endDateRange.getTime());


			}

		}


		ArrayList<TransactionDisplay> transactionList = TransactionDb.getTransactionDisplayList(userId, startDateRangeSql, endDateRangeSql);

		Breadcrumbs breadcrumbs = new Breadcrumbs(context);
		breadcrumbs.add("Transactions list");
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("pageTitle", "Transactions list");
		model.addAttribute("message", message);
		return new ModelAndView("transaction-list", "transactionList", transactionList);
	}





}
