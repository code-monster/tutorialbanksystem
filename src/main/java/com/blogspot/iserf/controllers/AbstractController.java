package com.blogspot.iserf.controllers;

/**
 *
 * @author alex
 */
public class AbstractController {

    protected final String ATTRIBUTE_MESSAGE = "message";
    protected final String ATTRIBUTE_PAGE_TITLE = "pageTitle";
    protected final String ATTRIBUTE_TRANSACTION_SPEND_MONEY = "transactionSpendMoney";
    protected final String ATTRIBUTE_TRANSACTION_ADD_MONEY = "transactionAddMoney";
    protected final String ATTRIBUTE_TRANSACTION_SEND_MONEY = "transactionSendMoney";
    protected final String ATTRIBUTE_BREADCRUMBS = "breadcrumbs";
    protected final String ATTRIBUTE_SEND_ERROR_MASSAGE = "org.springframework.validation.BindingResult.transactionSendMoney";
    protected final String ATTRIBUTE_ADD_ERROR_MASSAGE = "org.springframework.validation.BindingResult.transactionAddMoney";
    protected final String ATTRIBUTE_SPEND_ERROR_MASSAGE = "org.springframework.validation.BindingResult.transactionSpendMoney";

    
}
