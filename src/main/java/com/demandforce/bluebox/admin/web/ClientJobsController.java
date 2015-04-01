package com.demandforce.bluebox.admin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.demandforce.bluebox.admin.dto.ClientJobDetail;
import com.demandforce.bluebox.admin.service.JobService;
import com.demandforce.bluebox.admin.utils.IntegerUtils;

@Controller
public class ClientJobsController {
	private final Logger LOGGER = Logger.getLogger(ClientJobsController.class);
	public static final String REQUEST_SEARCH_CLIENT_JOB_DETAIL = "/ajax/goClientJobDetail";	
	private static final int PAGE_SIZE = 25;
	
	@Autowired
	JobService jobService;

	@RequestMapping(value = "/clientJobs", method = RequestMethod.GET)
    public ModelAndView goClientJobs() throws Exception
    {		
		LOGGER.info("Start goClientJobs.");
        ModelAndView mav = new ModelAndView("clientJobs");
        String pageNum = "1";
              
	    List<ClientJob> clientJobs = jobService.selectAllClientJobs();
	    int start = (IntegerUtils.parseInt(pageNum, 1) - 1) * PAGE_SIZE;
	    if (start >= clientJobs.size() || start < 0) {
            start = 0;
        }
	    int end = start + PAGE_SIZE; // +1 because the subList(start,end) exclusive the end value
        if (end > clientJobs.size()) {
            end = clientJobs.size();
        }
        int count = clientJobs.size();	    
	    if (count < PAGE_SIZE) {
            mav.addObject("currentCount", count);
        } else {
            mav.addObject("currentCount", PAGE_SIZE);
        }
        clientJobs = clientJobs.subList(start, end);
	    mav.addObject("clientJobs", clientJobs);	        
	    LOGGER.info("goClientJobs count:"+ clientJobs.size());	    
        mav.addObject("currentPage", pageNum);
        mav.addObject("totalCount", count);
                  
        return mav;
    }
	
	@RequestMapping(value = "/clientJobs/ajax/goClientPage", method = RequestMethod.POST)	    
    public @ResponseBody Map<String, Object> goClientPage(@RequestParam String pageNum) {	        	        	     
	 	LOGGER.info("ClientJobsController.goClientPage");
	 	
	 	 Map<String, Object> map = new HashMap<String, Object>();
	 	
	 	List<ClientJob> clientJobs = jobService.selectAllClientJobs();
	    int start = (IntegerUtils.parseInt(pageNum, 1) - 1) * PAGE_SIZE;
	    if (start >= clientJobs.size() || start < 0) {
            start = 0;
        }
	    int end = start + PAGE_SIZE; // +1 because the subList(start,end) exclusive the end value
        if (end > clientJobs.size()) {
            end = clientJobs.size();
        }
        int count = clientJobs.size();	    
	    if (count < PAGE_SIZE) {
            map.put("currentCount", count);
        } else {
            map.put("currentCount", PAGE_SIZE);
        }
        clientJobs = clientJobs.subList(start, end);
	    map.put("clientJobs", clientJobs);	        
	    LOGGER.info("goClientJobs count:"+ clientJobs.size());	    
        map.put("currentPage", pageNum);
        map.put("totalCount", count);
                  
        return map;
    }
	
	 @RequestMapping(value = "/clientJobs/ajax/goClientJobDetail", method = RequestMethod.POST)	    
	    public @ResponseBody Map<String, Object> goClientJobDetail(@RequestParam String jobId, @RequestParam String pageNum) {	        	        	     
		 	LOGGER.info("ClientJobsController.goSearchVendorConfiguration");		 	
		 	Map<String, Object> map = new HashMap<String, Object>();
		 			 	
			List<ClientJobDetail> cjd = new ArrayList<ClientJobDetail>();
			cjd = jobService.getDetailsByJobId(Integer.parseInt(jobId));
			
			int start = (IntegerUtils.parseInt(pageNum, 1) - 1) * PAGE_SIZE;
			if (start >= cjd.size() || start < 0) {
			        start = 0;
			}
			int end = start + PAGE_SIZE; // +1 because the subList(start,end) exclusive the end value
			if (end > cjd.size()) {
			    end = cjd.size();
			}
			int count = cjd.size();	    
			if (count < PAGE_SIZE) {
			    map.put("currentCount", count);
			} else {
			    map.put("currentCount", PAGE_SIZE);
			 }
			 cjd = cjd.subList(start, end);
			map.put("detailList", cjd);	  
			map.put("jobId", jobId);
			LOGGER.info("goClientJobDetail count:"+ cjd.size());	    
			map.put("currentPage", pageNum);
			map.put("totalCount", count);
			 	
			return map;		 	
	    }	
	 
	 @RequestMapping(value = "/clientJobs/ajax/getConsumerDetail", method = RequestMethod.POST)	    
	    public @ResponseBody ClientJobDetail getConsumerDetail(@RequestParam String jobId, @RequestParam String pageNum,
	            @RequestParam String consumerId) {	        	        	     
		 	LOGGER.info("ConfigurationController.getConsumerDetail");		 	
		 			 	
			ClientJobDetail cd = jobService.getConsumerDetails(Integer.parseInt(jobId), Integer.parseInt(consumerId));
			return cd;
	    }
}
