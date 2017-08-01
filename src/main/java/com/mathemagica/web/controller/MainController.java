package com.mathemagica.web.controller;

import com.mathemagica.model.user.User;
import com.mathemagica.service.user.LoginService;
import com.mathemagica.service.user.UserService;
import com.mathemagica.web.view.user.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Azael on 2017/07/20.
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;

    @RequestMapping(value={"/", "index", "register"}, method = RequestMethod.GET)
    public ModelAndView index(UserForm userForm){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              UserForm userForm){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("showLogin",error);
        modelAndView.setViewName("index");
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
        modelAndView.addObject("showForgotPassword", "true");
        modelAndView.setViewName("index");
        return modelAndView;
    }


    @RequestMapping(value = "/forgot_password", method = RequestMethod.POST)
    public ModelAndView changePassword(@Valid UserForm userForm, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("showForgotPassword", "true");
        modelAndView.setViewName("index");
        if (!isValidForm(bindingResult.getAllErrors(),true)) {
            return modelAndView;
        }else{
            User user = userService.findUserByEmail(userForm.getEmail());
            if (user == null) {
                bindingResult
                        .rejectValue("email", "error.userForm",
                                "User with the email provided does not exist");
            }
            if(!user.getName().equals(userForm.getName())){
                bindingResult
                        .rejectValue("name", "error.userForm",
                                "User with the name provided does not exist");
                return modelAndView;
            }
            if(!user.getSurname().equals(userForm.getSurname())){
                bindingResult
                        .rejectValue("surname", "error.userForm",
                                "User with the surname provided does not exist");
                return modelAndView;
            }
            user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
            userService.updateUser(user);
            modelAndView.addObject("showForgotPassword", null);
            modelAndView.addObject("successMessage", "User password changed successfully");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createUser(@Valid UserForm userForm, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByEmail(userForm.getEmail());
        if (user != null) {
            bindingResult
                    .rejectValue("email", "error.userForm",
                            "There is already a user registered with the email provided");
        }
        if (isValidForm(bindingResult.getAllErrors(),true)) {
            userService.saveUser(userForm);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.setViewName("index");
        }else {
            modelAndView.addObject("showRegister",true);
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    private boolean isValidForm(List<ObjectError> objectErrors, boolean isFullValidation) {
        for (ObjectError objectError : objectErrors){
            if(((FieldError)objectError).getField().contains("email")
                    || ((FieldError)objectError).getField().contains("password")
                    ){
                return false;
            }

        }
        return isFullValidForm(objectErrors, isFullValidation);
    }

    private boolean isFullValidForm(List<ObjectError> objectErrors, boolean isFullValidation ) {
        for (ObjectError objectError : objectErrors){
            if(isFullValidation){
                if(((FieldError)objectError).getField().contains("name")
                        || ((FieldError)objectError).getField().contains("surname")
                        ){
                    return false;
                }
            }
        }
        return true;
    }
}
