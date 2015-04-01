package com.demandforce.bluebox.admin.dao;

import java.util.ArrayList;
import java.util.List;
 
import org.springframework.stereotype.Repository;
 
import com.demandforce.bluebox.admin.model.Role;
import com.demandforce.bluebox.admin.model.User;
 
@Repository
public class UserDao {
 
    public User loadUserByUsername(final String username) {
        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setUsername("admin@demandforce.com");
        user.setPassword("ondemand");
        Role r = new Role();
        r.setName("ROLE_ADMIN");
        List<Role> roles = new ArrayList<Role>();
        roles.add(r);
        user.setAuthorities(roles);
        return user;
    }
}
