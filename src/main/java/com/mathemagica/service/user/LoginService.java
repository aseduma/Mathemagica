package com.mathemagica.service.user;

import com.mathemagica.model.user.User;
import com.mathemagica.model.user.UserRole;
import com.mathemagica.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Azael on 2017/07/13.
 */
@Service("loginService")
public class LoginService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return createUser(user,grantedAuthorities(user.getUserRoles()));
    }

    private org.springframework.security.core.userdetails.User createUser(User user, List<GrantedAuthority> grantedAuthorities){
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.isActive(), true, true, true, grantedAuthorities);
    }

    private List<GrantedAuthority> grantedAuthorities(Set<UserRole> userRoles) {
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        // Build user's authorities
        grantedAuthoritySet.addAll(userRoles.stream().map(userRole ->
                new SimpleGrantedAuthority(userRole.getRole().getRole())).collect(Collectors.toList()));
        return new ArrayList<>(grantedAuthoritySet);
    }
}
