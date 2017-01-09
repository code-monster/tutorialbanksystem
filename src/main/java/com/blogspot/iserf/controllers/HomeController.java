package com.blogspot.iserf.controllers;

import com.blogspot.iserf.model.DB.HomeDb;
import com.blogspot.iserf.model.Message;
import com.blogspot.iserf.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;

@Controller
public class HomeController extends AbstractController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main(Model model, @ModelAttribute(ATTRIBUTE_MESSAGE) Message message) {

        ArrayList<User> userList = HomeDb.getUserList();

        model.addAttribute(ATTRIBUTE_PAGE_TITLE, "Home page");
        model.addAttribute(ATTRIBUTE_MESSAGE, message);
        return new ModelAndView("home", "users", userList);
    }

}
