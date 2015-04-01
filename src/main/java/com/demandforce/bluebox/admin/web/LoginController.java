package com.demandforce.bluebox.admin.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demandforce.bluebox.admin.dto.ClientJob;
import com.demandforce.bluebox.admin.service.JobService;
import com.demandforce.bluebox.admin.service.UserService;


/**
 * Login controller
 * 
 * @author hsanchez
 * 
 */
@Controller
public class LoginController
{
	@Autowired  
    private UserService userService;
	
	@Autowired
	private JobService jobService;

    private final Logger LOGGER = Logger.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView goLogin()
    {    	
        ModelAndView mav = new ModelAndView("login");
        LOGGER.info("Start goLogin.");                
        
        return mav;
    }
    
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView goWelcome() throws Exception
    {
        ModelAndView mav = new ModelAndView("welcome");
      
        LOGGER.info("Start goWelcome.");
        Date date = new Date();
        mav.addObject("start", new SimpleDateFormat("MM/dd/yyyy").format(date));
        Map<String, Object> map = new HashMap<String, Object>();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        map = jobService.getServiceElementsForDate(sqlDate);
        mav.addObject("details", map);
        return mav;
    }
    
    @RequestMapping(value = "/welcome/ajax/getServiceData", method = RequestMethod.POST)	    
    public @ResponseBody Map<String, Object> getServiceData(@RequestParam String date) throws ParseException {	        	        	     
	 	LOGGER.info("LoginController.getServiceData");		 	
	 	Map<String, Object> map = new HashMap<String, Object>();
	 	LOGGER.info("LoginController.getServiceData.date"+date);
	 	Date dateString = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(date);
	 	java.sql.Date sqlDate = new java.sql.Date(dateString.getTime());
	 	Map<String, Object> map2 = new HashMap<String, Object>();
        map2 = jobService.getServiceElementsForDate(sqlDate);
        map.put("details", map2);
        LOGGER.info("LoginController.getServiceData after details list");
		List<ClientJob> clientJobList = new ArrayList<ClientJob>();
		clientJobList = jobService.selectAllClientJobsByDate(sqlDate); 	
		map.put("list", clientJobList);
		LOGGER.info("LoginController.getServiceData after list list");
		return map;		 	
    }	


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView goLogout()
    {

        ModelAndView mav = new ModelAndView("logout");
        return mav;

    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public ModelAndView accessDenied()
    {

        ModelAndView mav = new ModelAndView("accessDenied");
        return mav;

    }

    @RequestMapping(value = "/timeout", method = RequestMethod.GET)
    public ModelAndView timeout()
    {
        ModelAndView mav = new ModelAndView("timeout");
        return mav;
    }

    @RequestMapping(value = "/sessionTerminated", method = RequestMethod.GET)
    public ModelAndView sessionTerminated()
    {
        ModelAndView mav = new ModelAndView("sessionTerminated");
        return mav;
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView error()
    {
        ModelAndView mav = new ModelAndView("error");
        return mav;
    }

    @RequestMapping(value = "/error404", method = RequestMethod.GET)
    public ModelAndView error404()
    {
        ModelAndView mav = new ModelAndView("error404");
        return mav;
    }
    
          
   }
