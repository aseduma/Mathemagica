package com.mathemagica.web.controller;

import com.mathemagica.model.user.User;
import com.mathemagica.service.user.UserService;
import com.mathemagica.web.view.user.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by Azael on 2017/07/20.
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/", "index"}, method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/login/index");
        return modelAndView;
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public ModelAndView logout(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }


    @RequestMapping(value="/forgot_password", method = RequestMethod.GET)
    public ModelAndView forgotPassword(UserForm userForm){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/forgot_password/index");
        return modelAndView;
    }


    @RequestMapping(value = "/forgot_password", method = RequestMethod.POST)
    public ModelAndView changePassword(@Valid UserForm userForm, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByEmail(userForm.getEmail());
        if (user != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
            userService.updateUser(user);
            modelAndView.addObject("successMessage", "User password changed successfully");
            modelAndView.setViewName("index");
        }else{
            bindingResult
                    .rejectValue("email", "error.user",
                            "User with the email provided does not exist");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/forgot_password/index");
        }
        return modelAndView;
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView register(UserForm userForm){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/register/index");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createUser(@Valid UserForm userForm, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(userForm.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/register/index");
        } else {
            userService.saveUser(userForm);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.setViewName("index");

        }
        return modelAndView;
    }


}
