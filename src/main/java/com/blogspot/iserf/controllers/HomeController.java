package com.blogspot.iserf.controllers;

import com.blogspot.iserf.dao.UserDAO;
import com.blogspot.iserf.model.Message;
import com.blogspot.iserf.model.UserModel;
import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends AbstractController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main(Model model, @ModelAttribute(ATTRIBUTE_MESSAGE) Message message) {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("app-beans.xml");
        UserDAO userDAO = ctx.getBean("userDAO", UserDAO.class);
        List<UserModel> userList = userDAO.getAll();

        model.addAttribute(ATTRIBUTE_PAGE_TITLE, "Home page");
        model.addAttribute(ATTRIBUTE_MESSAGE, message);
        return new ModelAndView("home_refactoring", "users", userList);
    }

}
