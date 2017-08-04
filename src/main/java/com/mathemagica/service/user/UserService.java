package com.mathemagica.service.user;

import com.mathemagica.model.user.User;

import java.util.List;

/**
 * Created by Azael on 2017/07/13.
 */
public interface UserService {
    User getByEmail(String email);
    void save(User user);
    void delete(User user);
    void update(User user);
    List<User> getAll();
    User getByNameAndSurname(String name, String surname);
    boolean isUserExist(User user);
    boolean isUserExist(String email);
}
