package com.blogspot.iserf.controllers;


import com.blogspot.iserf.model.Breadcrumbs;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ContactController extends AbstractController {


    @Autowired
    private HttpServletRequest context;

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact(Locale locale, Model model) {

        Breadcrumbs breadcrumbs = new Breadcrumbs(context);
        breadcrumbs.add("contact");
        model.addAttribute(ATTRIBUTE_BREADCRUMBS, breadcrumbs);
        model.addAttribute(ATTRIBUTE_PAGE_TITLE, "Contact page");
        return "contact";
    }
}
