package com.mathemagica.service.user;

import com.mathemagica.model.user.User;
import com.mathemagica.web.view.user.UserForm;

/**
 * Created by Azael on 2017/07/13.
 */
public interface UserService {
    User findUserByEmail(String email);
    void saveUser(UserForm userForm);
    void deleteUser(User user);
    void updateUser(User user);
}
