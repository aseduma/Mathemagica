package com.mathemagica.service.user;

import com.mathemagica.model.user.User;
import com.mathemagica.web.view.user.UserForm;

/**
 * Created by Azael on 2017/07/13.
 */
public interface UserService {
    User getByEmail(String email);
    void save(UserForm userForm);
    void delete(User user);
    void update(User user);
}
