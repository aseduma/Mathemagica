package com.mathemagica.model.user;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Azael on 2017/07/13.
 */

@Entity
@Table(name = "role", catalog = "mathemagica")
public class Role {

    @Id
    @Column(name="role", nullable = false, unique = true)
    private String role;

    @Column(name="description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<UserRole> userRoles = new HashSet<>();

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
