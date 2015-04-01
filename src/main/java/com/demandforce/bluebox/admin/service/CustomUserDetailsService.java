package com.demandforce.bluebox.admin.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;     
import org.springframework.security.core.userdetails.UserDetails;  
import org.springframework.security.core.userdetails.UserDetailsService;  
import org.springframework.security.core.userdetails.UsernameNotFoundException;  
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;  
import org.springframework.security.crypto.password.PasswordEncoder;  
import org.springframework.stereotype.Service;  
  


import com.demandforce.bluebox.admin.dao.AdminUserDao;
import com.demandforce.bluebox.admin.model.User;
  
  
@Service  
public class CustomUserDetailsService implements UserDetailsService {  
  
	@Autowired  
    private AdminUserDao adminUserDao;   
  
    static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  
    
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
       
    
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    	LOGGER.info("CustomUserDetailsService:loadUserByUsername:"+s);
        User adminUser = adminUserDao.getAdminUserByEmail(s);
        
        UserDetails user = null;
        //check if this user with this username exist, if not, throw an exception  
        // and stop the login process  
        if (adminUser == null) {  
            throw new UsernameNotFoundException("User details not found with this username: " + s);  
        }                
        
        LOGGER.info("CustomUserDetailsService:loadUserByUsername.userDetails"+adminUser.toString());
  
        return user;  
    }  
  
      
}  
