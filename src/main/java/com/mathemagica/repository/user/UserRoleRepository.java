package com.mathemagica.repository.user;

import com.mathemagica.model.user.User;
import com.mathemagica.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Azael on 2017/07/13.
 */
@Repository("userRoleRepository")
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findAllByUser(User user);
}
