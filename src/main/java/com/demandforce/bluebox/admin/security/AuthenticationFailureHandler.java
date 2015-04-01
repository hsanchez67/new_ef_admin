package com.demandforce.bluebox.admin.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liuzhen
 */
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private String usernamekey=UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;    

    @SuppressWarnings("deprecation")
	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = "";
        if (StringUtils.isNotBlank(usernamekey)) {
            username = request.getParameter(usernamekey);
            System.out.println("AuthenticationFailureHandler.username:"+usernamekey);
        } else {
            Object principal = exception.getAuthentication().getPrincipal();
            if (principal instanceof String) {
                username = (String) principal;
            }
            if (principal instanceof UserDetails) {

                username = ((UserDetails) principal).getUsername();
            }
        }
        request.getSession().setAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY, username);
        super.onAuthenticationFailure(request, response, exception);
    }

    public String getUsernamekey() {
        return usernamekey;
    }

    public void setUsernamekey(String usernamekey) {
        this.usernamekey = usernamekey;
    }
}
