package com.mathemagica.service.user;

import com.mathemagica.model.user.Role;
import com.mathemagica.model.user.User;
import com.mathemagica.model.user.UserRole;
import com.mathemagica.repository.user.RoleRepository;
import com.mathemagica.repository.user.UserRepository;
import com.mathemagica.repository.user.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Azael on 2017/07/13.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getByNameAndSurname(String name, String surname) {
        return userRepository.findByNameAndSurname(name,surname);
    }

    @Override
    public boolean isUserExist(User user) {
        return userRepository.findByEmail(user.getEmail()) != null;
    }

    @Override
    public void save(User user) {
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
        userRepository.save(user);
    }

    @Override
    public boolean isUserExist(String email) {
        return userRepository.findByEmail(email) != null;
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
