package com.demandforce.bluebox.admin.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;  
import org.springframework.security.crypto.password.PasswordEncoder;  
import org.springframework.stereotype.Repository;

import com.demandforce.bluebox.admin.dao.AdminUserDao;
import com.demandforce.bluebox.admin.model.Role;
import com.demandforce.bluebox.admin.model.User;

@Repository("adminUserDao")
public class AdminUserDaoImpl implements AdminUserDao {
	
	 static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  
	
	 @Override
	 public User getAdminUserByEmail(final String email) {
		 	User user = new User();
	        user.setFirstName("firstName");
	        user.setLastName("lastName");
	        user.setUsername(email);
	        String encodedPassword = passwordEncoder.encode("ondemand");
	        user.setPassword(encodedPassword);
	        Role r = new Role();
	        r.setName("ROLE_ADMIN");
	        List<Role> roles = new ArrayList<Role>();
	        roles.add(r);
	        user.setAuthorities(roles);
	        return user;
	 }
	
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
