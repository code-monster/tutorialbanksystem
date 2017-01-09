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
import java.util.ArrayList;

@Controller
public class TransactionListController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionListController.class);

    @Autowired
    private HttpServletRequest context;
    
    protected String pageTitle = "Transactions list";

    @RequestMapping(value = "/transaction-list", method = RequestMethod.GET)
    public ModelAndView editUser(Model model, @ModelAttribute(ATTRIBUTE_MESSAGE) Message message) {

        model.addAttribute("userList", UserDb.getUserList());

        int userId;
        if (context.getParameterMap().containsKey("showUserTransaction") == false) {
            userId = 0;
        } else {

            if (Validator.isInteger(context.getParameter("showUserTransaction")) == false) {

                Message errorMessage = new Message(Message.ERROR, "Error in showUserTransaction param!");
                model.addAttribute(ATTRIBUTE_MESSAGE, errorMessage);
                model.addAttribute(ATTRIBUTE_PAGE_TITLE, "Error page");
                return new ModelAndView("error");
            } else {
                userId = new Integer(context.getParameter("showUserTransaction"));
            }

        }

        String startDateRangeSql = null;
        String endDateRangeSql = null;

        if (context.getParameterMap().containsKey("startDateRange") == true && context.getParameterMap().containsKey("endDateRange") == true) {

            if (Validator.isDate(context.getParameter("startDateRange")) == false || Validator.isDate(context.getParameter("endDateRange")) == false) {

                Message errorMessage = new Message(Message.ERROR, "Error in startDateRange or endDateRange param!");
                model.addAttribute(ATTRIBUTE_MESSAGE, errorMessage);
                model.addAttribute(ATTRIBUTE_PAGE_TITLE, "Error page");
                return new ModelAndView("error");
            } else {

                startDateRangeSql = context.getParameter("startDateRange");
                endDateRangeSql = context.getParameter("endDateRange");
            }

        }

        ArrayList<TransactionDisplay> transactionList = TransactionDb.getTransactionDisplayList(userId, startDateRangeSql, endDateRangeSql);

        Breadcrumbs breadcrumbs = new Breadcrumbs(context);
        breadcrumbs.add(pageTitle);
        model.addAttribute(ATTRIBUTE_BREADCRUMBS, breadcrumbs);
        model.addAttribute(ATTRIBUTE_PAGE_TITLE, pageTitle);
        model.addAttribute(ATTRIBUTE_MESSAGE, message);
        return new ModelAndView("transaction-list", "transactionList", transactionList);
    }

}
