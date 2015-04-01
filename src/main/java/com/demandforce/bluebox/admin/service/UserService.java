package com.demandforce.bluebox.admin.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;  

import com.demandforce.bluebox.admin.dto.AdminUser;  
  
@Service("userService")  
public class UserService {  
	
	 private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
  
    public AdminUser getUserByUsername(String username) { 
    	LOGGER.info("UserService:getUserByUsername:"+username);
        AdminUser user = new AdminUser();  
        //logic here to get your user from the database          
        if (username.equals("admin@demandforce.com") || username.equals("user@demandforce.com")) {              
            user.setEmail(username);
            user.setPassword("password");              
            //if username is admin, role will be admin, else role is user only
            user.setRole(username.equals("admin@demandforce.com")?"admin" :"user");
             
            //return the usermap  
            return user;  
        }  
        //if username is not equal to admin, return null  
        return null;  
    }  
}  
