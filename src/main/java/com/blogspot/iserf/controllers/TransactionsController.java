package com.blogspot.iserf.controllers;

import com.blogspot.iserf.model.Breadcrumbs;
import com.blogspot.iserf.model.DB.AccountDb;
import com.blogspot.iserf.model.DB.TransactionDb;
import com.blogspot.iserf.model.Message;
import com.blogspot.iserf.model.SendMoney;
import com.blogspot.iserf.model.Transaction;
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

@Controller
public class TransactionsController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    @Autowired
    private HttpServletRequest context;

    @RequestMapping(value = "/account-detail", method = RequestMethod.GET)
    public ModelAndView editUser(HttpServletRequest request, Model model) {

        if (context.getParameterMap().containsKey("account_id") == false
                || Validator.isInteger(context.getParameter("account_id")) == false) {

            Message errorMessage = new Message(Message.ERROR, "Error in account_id param!");
            model.addAttribute(ATTRIBUTE_MESSAGE, errorMessage);
            model.addAttribute(ATTRIBUTE_PAGE_TITLE, "Error page");
            return new ModelAndView("error");
        }

        int userId = AccountDb.getUserIdByAccountId(new Integer(context.getParameter("account_id")));
        if (userId == 0) {
            Message errorMessage = new Message(Message.ERROR, "Error: account id is not connected for a user!");
            model.addAttribute(ATTRIBUTE_MESSAGE, errorMessage);
            model.addAttribute(ATTRIBUTE_PAGE_TITLE, "Error page");
            return new ModelAndView("error");

        }

        ArrayList<Transaction> transactionList = TransactionDb.getTransactionList(new Integer(context.getParameter("account_id")));

        if (!model.containsAttribute(ATTRIBUTE_TRANSACTION_SPEND_MONEY)) {
            Transaction newTransactionSpendMoney = new Transaction(new Integer(context.getParameter("account_id")), false);
            model.addAttribute(ATTRIBUTE_TRANSACTION_SPEND_MONEY, newTransactionSpendMoney);
        }

        if (!model.containsAttribute(ATTRIBUTE_TRANSACTION_ADD_MONEY)) {
            Transaction newTransactionAddMoney = new Transaction(new Integer(context.getParameter("account_id")), true);
            model.addAttribute(ATTRIBUTE_TRANSACTION_ADD_MONEY, newTransactionAddMoney);
        }

        if (!model.containsAttribute(ATTRIBUTE_TRANSACTION_SEND_MONEY)) {
            SendMoney newTransactionSendMoney = new SendMoney(new Integer(context.getParameter("account_id")));
            model.addAttribute(ATTRIBUTE_TRANSACTION_SEND_MONEY, newTransactionSendMoney);
        }

        Breadcrumbs breadcrumbs = new Breadcrumbs(request);

        breadcrumbs.add("user-profile", "/user-profile?user_id=" + userId);
        breadcrumbs.add("account-detail");

        model.addAttribute(ATTRIBUTE_BREADCRUMBS, breadcrumbs);
        model.addAttribute(ATTRIBUTE_PAGE_TITLE, "View  transactions  in account id " + context.getParameter("account_id"));
        return new ModelAndView("account-detail", "transactionList", transactionList);
    }

    @RequestMapping(value = "/add-transaction-add-money", method = RequestMethod.POST)
    public ModelAndView addTransactionAddMoney(
            @ModelAttribute(ATTRIBUTE_TRANSACTION_ADD_MONEY) @Valid Transaction transactionAddMoney,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute(ATTRIBUTE_ADD_ERROR_MASSAGE, bindingResult);
            redirectAttributes.addFlashAttribute(ATTRIBUTE_TRANSACTION_ADD_MONEY, transactionAddMoney);

            return new ModelAndView("redirect:/account-detail?account_id=" + transactionAddMoney.getAccountId() + "#add-money");

        }

        int newTransactiontId = TransactionDb.createTransaction(transactionAddMoney);

        Message message = new Message(Message.UPDATE, "New Transactiont with id =" + newTransactiontId + " was created");
        redirectAttributes.addFlashAttribute(ATTRIBUTE_MESSAGE, message);

        return new ModelAndView("redirect:/account-detail?account_id=" + transactionAddMoney.getAccountId());

    }

    @RequestMapping(value = "/add-transaction-spend-money", method = RequestMethod.POST)
    public ModelAndView addTransactionSpendMoney(@ModelAttribute(ATTRIBUTE_TRANSACTION_SPEND_MONEY) @Valid Transaction transactionSpendMoney, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute(ATTRIBUTE_SPEND_ERROR_MASSAGE, bindingResult);
            redirectAttributes.addFlashAttribute(ATTRIBUTE_TRANSACTION_SPEND_MONEY, transactionSpendMoney);

            return new ModelAndView("redirect:/account-detail?account_id=" + transactionSpendMoney.getAccountId() + "#spend-money");

        }

        int newTransactiontId = TransactionDb.createTransaction(transactionSpendMoney);

        Message message = new Message(Message.UPDATE, "New Transactiont with id =" + newTransactiontId + " was created");
        redirectAttributes.addFlashAttribute(ATTRIBUTE_MESSAGE, message);

        return new ModelAndView("redirect:/account-detail?account_id=" + transactionSpendMoney.getAccountId());

    }

    @RequestMapping(value = "/add-transaction-send-money", method = RequestMethod.POST)
    public ModelAndView addTransactionSendMoney(@ModelAttribute(ATTRIBUTE_TRANSACTION_SEND_MONEY) @Valid SendMoney transactionSendMoney, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute(ATTRIBUTE_SEND_ERROR_MASSAGE, bindingResult);
            redirectAttributes.addFlashAttribute(ATTRIBUTE_TRANSACTION_SEND_MONEY, transactionSendMoney);

            return new ModelAndView("redirect:/account-detail?account_id=" + transactionSendMoney.getAccountId() + "#send-money");

        }

        String sendMessage = TransactionDb.createSendTransaction(transactionSendMoney);

        String messageStatus = Message.ERROR;
        if (sendMessage.equals("ok")) {
            messageStatus = Message.UPDATE;
        }

        Message message = new Message(messageStatus, "Transactiont message =" + sendMessage);
        redirectAttributes.addFlashAttribute(ATTRIBUTE_MESSAGE, message);

        return new ModelAndView("redirect:/account-detail?account_id=" + transactionSendMoney.getAccountId());

    }

}
