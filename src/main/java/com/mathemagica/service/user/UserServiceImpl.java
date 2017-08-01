package com.mathemagica.service.user;

import com.mathemagica.model.user.Role;
import com.mathemagica.model.user.User;
import com.mathemagica.model.user.UserRole;
import com.mathemagica.repository.user.RoleRepository;
import com.mathemagica.repository.user.UserRepository;
import com.mathemagica.repository.user.UserRoleRepository;
import com.mathemagica.web.view.user.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Azael on 2017/07/13.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setName(userForm.getName());
        user.setSurname(userForm.getSurname());
        user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
        user.setActive(true);
        userRepository.save(user);
        saveUserRole(user, "USER");
    }

    @Override
    public void delete(User user) {
        userRoleRepository.findAllByUser(user).forEach(userRoleRepository::delete);
        userRepository.delete(user);
    }

    @Override
    public void update(User user) {

    }

    private void saveUserRole(User user, String roleName) {
        Role role = roleRepository.findByRole(roleName);
        if (role != null && !hasRole(user, role)) {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            userRoleRepository.save(userRole);
        }
    }

    private boolean hasRole(User user, Role role) {
        for (UserRole userRole : user.getUserRoles()) {
            if (userRole.getRole().getRole().equals(role.getRole())) {
                return true;
            }
        }
        return false;
    }
}
