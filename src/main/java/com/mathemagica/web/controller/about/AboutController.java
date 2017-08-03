package com.mathemagica.web.controller.about;

import com.mathemagica.model.user.User;
import com.mathemagica.service.about.ContactUsService;
import com.mathemagica.service.nav.NavService;
import com.mathemagica.service.user.UserService;
import com.mathemagica.web.view.about.ContactUsForm;
import com.mathemagica.web.view.user.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by Azael on 2017/08/03.
 */
@Controller
public class AboutController {
    private UserService userService;
    private NavService navService;
    private ContactUsService contactUsService;

    @Autowired
    public AboutController(UserService userService, NavService navService,ContactUsService contactUsService) {
        this.userService = userService;
        this.navService = navService;
        this.contactUsService = contactUsService;
    }

    @RequestMapping(value="/about", method = RequestMethod.GET)
    public ModelAndView about(UserForm userForm,ContactUsForm contactUsForm){
        ModelAndView modelAndView = getModelAndView();
        return modelAndView;
    }

    @RequestMapping(value="/contact_us", method = RequestMethod.POST)
    public ModelAndView contactUs(UserForm userForm,@Valid ContactUsForm contactUsForm, BindingResult bindingResult){
        ModelAndView modelAndView = getModelAndView();
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }else{
            contactUsService.save(contactUsForm);
        }
        return modelAndView;
    }

    private ModelAndView getModelAndView(){
        User user = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("about/index");
        modelAndView.addObject("navs",navService.createNav(user,"About"));
        return modelAndView;
    }
}
