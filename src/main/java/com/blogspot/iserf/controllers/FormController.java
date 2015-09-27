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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.blogspot.iserf.model.Person;
import com.blogspot.iserf.utility.*;
/**
 * Handles requests for the application home page.
 */
@Controller
public class FormController {


	@Autowired
	private HttpServletRequest context;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView editUser(HttpServletRequest request, Model model) 
	{
		Person person = new Person();
    
		return new ModelAndView("form", "person", person);
	}
	

    
    @RequestMapping(value="/form", method=RequestMethod.POST)
    public String checkPersonInfo(@Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        return "redirect:/results";
    }
    

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/results", method = RequestMethod.GET)
	public String editUser() 
	{
    
		return "results";
	}


}
