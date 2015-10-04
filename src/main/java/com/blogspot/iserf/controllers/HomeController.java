package com.blogspot.iserf.controllers;


import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.blogspot.iserf.model.DB.HomeDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.blogspot.iserf.model.Breadcrumbs;
import com.blogspot.iserf.model.Message;
import com.blogspot.iserf.model.User;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private HttpServletRequest context;

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main(Model model, @ModelAttribute("message") Message message) {


        ArrayList<User> userList = HomeDb.getUserList();

        model.addAttribute("pageTitle", "Home page");
        model.addAttribute("message", message);
        return new ModelAndView("home", "users", userList);
    }


    /**
     * contact page
     */
    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact(Locale locale, Model model) {

        Breadcrumbs breadcrumbs = new Breadcrumbs(context);
        breadcrumbs.add("contact");
        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("pageTitle", "Contact page");
        return "contact";
    }
}
