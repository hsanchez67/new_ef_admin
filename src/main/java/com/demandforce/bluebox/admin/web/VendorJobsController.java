package com.demandforce.bluebox.admin.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demandforce.bluebox.admin.dao.VendorJobDao;
import com.demandforce.bluebox.admin.dto.ClientJob;
import com.demandforce.bluebox.admin.dto.ClientJobDetail;
import com.demandforce.bluebox.admin.dto.VendorJob;
import com.demandforce.bluebox.admin.service.JobService;
import com.demandforce.bluebox.admin.utils.IntegerUtils;


@Controller
public class VendorJobsController {
	
	private final Logger LOGGER = Logger.getLogger(VendorJobsController.class);
	private static final int PAGE_SIZE = 25;
		
	@Autowired
	JobService jobService;	

	@RequestMapping(value = "/vendorJobs", method = RequestMethod.GET)
    public ModelAndView goVendorJobs() throws Exception
    {		
		LOGGER.info("Start goVendorJobs.");
        ModelAndView mav = new ModelAndView("vendorJobs");  
        String pageNum = "1";
        
	    List<VendorJob> vendorJobs = jobService.selectAllVendorJobs();
	    int start = (IntegerUtils.parseInt(pageNum, 1) - 1) * PAGE_SIZE;
	    if (start >= vendorJobs.size() || start < 0) {
            start = 0;
        }
	    int end = start + PAGE_SIZE; // +1 because the subList(start,end) exclusive the end value
        if (end > vendorJobs.size()) {
            end = vendorJobs.size();
        }
        int count = vendorJobs.size();	    
	    if (count < PAGE_SIZE) {
            mav.addObject("currentCount", count);
        } else {
            mav.addObject("currentCount", PAGE_SIZE);
        }
	    vendorJobs = vendorJobs.subList(start, end);
	    mav.addObject("vendorJobs", vendorJobs);	        
	    LOGGER.info("goVendorJobs count:"+ vendorJobs.size());	    
        mav.addObject("currentPage", pageNum);
        mav.addObject("totalCount", count);
                  
        return mav;
    }
	
	@RequestMapping(value = "/vendorJobs/ajax/getVendorJobDetail", method = RequestMethod.POST)	    
    public @ResponseBody VendorJob getVendorJobDetail(@RequestParam String jobId) {	        	        	     
	 	LOGGER.info("ConfigurationController.getConsumerDetail");		 	
	 			 	
		VendorJob vj = jobService.getVendorJobDetail(Integer.parseInt(jobId));
		return vj;
    }	 
}