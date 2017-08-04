package com.mathemagica.utils.user;

import com.mathemagica.model.user.User;
import com.mathemagica.web.view.user.UserForm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Azael on 2017/08/04.
 */
public class UserUtils {
    void save(UserForm userForm){
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setName(userForm.getName());
        user.setSurname(userForm.getSurname());
        user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
        user.setActive(true);
    }
    void save(User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    }
}
