package com.demandforce.bluebox.admin.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;




import com.demandforce.bluebox.admin.config.AdminConfig;
import com.demandforce.bluebox.admin.dao.ClientJobDao;
import com.demandforce.bluebox.admin.dao.VendorJobDao;
import com.demandforce.bluebox.admin.dto.ClientJob;
import com.demandforce.bluebox.admin.dto.ClientJobDetail;
import com.demandforce.bluebox.admin.dto.JobSearchCondition;
import com.demandforce.bluebox.admin.dto.VendorJob;
import com.demandforce.bluebox.admin.service.JobService;
import com.demandforce.bluebox.admin.utils.EnvUtils;
import com.demandforce.bluebox.admin.utils.IntegerUtils;



@Controller
@RequestMapping(value = "/search")
public class JobSearchController {
	
	@Autowired
	VendorJobDao vendorJobDao;	
	
	@Autowired
	ClientJobDao clientJobDao;
	
	@Autowired
	JobService jobService;
	
	private final Logger LOGGER = Logger.getLogger(JobSearchController.class);
	
	 private static final int PAGE_SIZE = 25;
	 public static final String VIEW_INDEX = "findJob";	 
	 public static final String REQUEST_SEARCH_JOB = "/ajax/doSearch";
	 public static final String REQUEST_INDEX = "";
	 
	 @RequestMapping(value = REQUEST_INDEX, method = {RequestMethod.POST, RequestMethod.GET})
	 @ResponseBody
	 public ModelAndView goSearch(@RequestParam(required = false) String jid, @RequestParam(required = false) String bid,  @RequestParam(required = false) String jobType,
	                              @RequestParam(required = false) String pageNum, @RequestParam(required = false) boolean isSearch) {
		 LOGGER.info("goSearch.");
		 
		 ModelAndView mav = null;		 
			 mav = new ModelAndView(VIEW_INDEX);		 
		 	int count;
	        JobSearchCondition jsc = new JobSearchCondition();

	        if (StringUtils.isNotEmpty(jid) || StringUtils.isNotEmpty(bid)) {
	            jsc.setJobID(jid);	
	            jsc.setBusinessID(bid);
	            jsc.setJobType(jobType);
	            jsc.setStartNum((IntegerUtils.parseInt(pageNum, 1) - 1) * PAGE_SIZE);
	            jsc.setPageSize(PAGE_SIZE);
	        }
	        	        
	        
	        if (jobType.equals("vendor")) {
	        	List<VendorJob> vendorJobs = new ArrayList<VendorJob>();
	        	VendorJob job = null;
	        	job = vendorJobDao.selectById(Integer.parseInt(jid));
	        	if (job != null) {
	        		vendorJobs.add(job);
	        	}
	        	mav.addObject("jobs", vendorJobs);
	            count = vendorJobs.size();
	        } else {
	        	List<ClientJob> clientJobs = new ArrayList<ClientJob>();	        	
	        	if (!bid.equals("") && jid.equals("")) {
	        		clientJobs = clientJobDao.selectByBusinessId(Integer.parseInt(bid));
	        	} else if (bid.equals("") && !jid.equals("")) {
	        		clientJobs = clientJobDao.selectById(Integer.parseInt(jid));
	        	}	        	
	        	mav.addObject("jobs", clientJobs);
	            count = clientJobs.size();
	        }	
	        	        
	        
	        if (count < PAGE_SIZE) {
	            mav.addObject("currentCount", count);
	        } else {
	            mav.addObject("currentCount", PAGE_SIZE);
	        }
	        mav.addObject("currentPage", pageNum);
	        mav.addObject("totalCount", count);
	        mav.addObject("jsc", jsc);		        	        
	        mav.addObject("isSearch", isSearch);
	        return mav;
	    }
	 
	 
	 	@RequestMapping(value = REQUEST_SEARCH_JOB, method = {RequestMethod.POST, RequestMethod.GET})
	    @ResponseBody
	    public Map<String, Object> search(String jid, String jobType, String pageNum) {
	 		LOGGER.info("search.");
	 		
	        Map<String, Object> map = new HashMap<String, Object>();	        
	        int count;	        
	        JobSearchCondition jsc = new JobSearchCondition();
	        jsc.setJobID(jid);
	        jsc.setJobType(jobType);
	        jsc.setStartNum((IntegerUtils.parseInt(pageNum, 1) - 1) * PAGE_SIZE);
	        jsc.setPageSize(PAGE_SIZE);
	        if (jobType.equals("vendor")) {
	        	VendorJob job = null;
	        	job = vendorJobDao.selectById(Integer.parseInt(jid));
	        	map.put("job", job);
	            count = 1;
	        } else {
	        	List<ClientJob> jobs = new ArrayList<ClientJob>();	        	
	        	jobs = clientJobDao.selectById(Integer.parseInt(jid));
	        	map.put("job", jobs);
	            count = 1;
	        }	
	        	        
	        
	        if (count < PAGE_SIZE) {
	            map.put("currentCount", count);
	        } else {
	            map.put("currentCount", PAGE_SIZE);
	        }
	        map.put("currentPage", pageNum);
	        map.put("totalCount", count);
	        map.put("jsc", jsc);	        
	       
	        String serverName = "";
	        if (EnvUtils.isLocal()) {
	        	serverName = AdminConfig.getInstance().get("env.bp.dev.local");
	 		}else if (EnvUtils.isDev()) {
	            serverName = AdminConfig.getInstance().get("env.bp.dev.url");
	        } else if (EnvUtils.isStaging()) {
	            serverName = AdminConfig.getInstance().get("env.bp.staging.url");
	        } else if (EnvUtils.isE2eStg()) {
	            serverName = AdminConfig.getInstance().get("env.bp.e2tstg.url");
	        }
	        map.put("serverName", serverName);
	        return map;
	    }
	 	
	 	 private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	 	 
	     private void createClientRow(List<ClientJob> list, String[] header, ICsvMapWriter csvWriter) throws IOException {
	         for (ClientJob client : list) {
	             Map<String, Object> dataMap = new HashMap<String, Object>();
	             dataMap.put(header[0], client.getClientJobId());
	             dataMap.put(header[1], client.getClientName());
	             dataMap.put(header[2], client.getBusinessId());	             
	             dataMap.put(header[3], client.getCreatedTs() == null ? "" : df.format(client.getCreatedTs()));
	             dataMap.put(header[4], client.getSubmitTs() == null ? "" : df.format(client.getSubmitTs()));
	             dataMap.put(header[5], client.getLastModifiedTs() == null ? "" : df.format(client.getLastModifiedTs()));
	             dataMap.put(header[6], client.getNumRequested());
	             dataMap.put(header[7], client.getNumMatched());
	             dataMap.put(header[8], client.getAppendCompletedTs() == null ? "" : df.format(client.getAppendCompletedTs()));	             
	             csvWriter.write(dataMap, header);
	         }
	     }
	     
	     private void createVendorRow(List<VendorJob> list, String[] header, ICsvMapWriter csvWriter) throws IOException {
	         for (VendorJob vendor : list) {
	             Map<String, Object> dataMap = new HashMap<String, Object>();
	             dataMap.put(header[0], vendor.getVendorJobId());
	             dataMap.put(header[1], vendor.getVendorId());
	             dataMap.put(header[2], vendor.getPrecision() == "C" ? "Combined" : "Individual");	             
	             dataMap.put(header[3], vendor.getCreatedTs() == null ? "" : df.format(vendor.getCreatedTs()));
	             dataMap.put(header[4], vendor.getFilename());
	             dataMap.put(header[5], vendor.getSubmitTs() == null ? "" : df.format(vendor.getSubmitTs()));
	             dataMap.put(header[6], vendor.getReceiveTs() == null ? "" : df.format(vendor.getReceiveTs()));
	             dataMap.put(header[7], vendor.getLastModifiedTs() == null ? "" : df.format(vendor.getLastModifiedTs()));
	             dataMap.put(header[8], vendor.getNumRequested());
	             dataMap.put(header[9], vendor.getNumMatched());	             	            
	             csvWriter.write(dataMap, header);
	         }
	     }
	     
	     private void createClientDetailRow(List<ClientJobDetail> list, String[] header, ICsvMapWriter csvWriter) throws IOException {
	         for (ClientJobDetail client : list) {
	             Map<String, Object> dataMap = new HashMap<String, Object>();
	             dataMap.put(header[0], client.getClientJobId());
	             dataMap.put(header[1], client.getConsumerId());
	             dataMap.put(header[2], client.getAppendTypes());
	             dataMap.put(header[3], client.getFname());
	             dataMap.put(header[4], client.getLname());
	             dataMap.put(header[5], client.getAddress1());
	             dataMap.put(header[6], client.getCity());
	             dataMap.put(header[7], client.getState());
	             dataMap.put(header[8], client.getZip());
	             dataMap.put(header[9], client.getPrecisionReq() == "C" ? "Combined" : "Individual");
	             dataMap.put(header[10], client.getVendorJobId());
	             dataMap.put(header[11], client.getPrecisionRes() == "C" ? "Combined" : "Individual");
	             dataMap.put(header[12], client.getAppendedEmail());
	             dataMap.put(header[13], client.getCreatedTs() == null ? "" : df.format(client.getCreatedTs()));
	             dataMap.put(header[14], client.getSubmitTs() == null ? "" : df.format(client.getSubmitTs()));
	             dataMap.put(header[15], client.getReceiveTs() == null ? "" : df.format(client.getReceiveTs()));
	             dataMap.put(header[16], client.getDownloadTs() == null ? "" : df.format(client.getDownloadTs()));
	             dataMap.put(header[17], client.getLastModifiedTs() == null ? "" : df.format(client.getLastModifiedTs()));	             	            
	             csvWriter.write(dataMap, header);
	         }
	     }
	 	
	 	
	 	@RequestMapping("export")
	    public String export(String jid, String bid, String jobType, HttpServletResponse response) {
	        int count;
	        LOGGER.info("Export job Id="+jid+" - business Id="+bid+" - Job Type="+jobType);	        	        
	        List<VendorJob> vendorJobs = new ArrayList<VendorJob>();
	        List<ClientJob> clientJobs = new ArrayList<ClientJob>();	        		       
	        	        
	        
	        if (jobType.equals("vendor")) {	        	
	        	VendorJob job = null;
	        	if (jid != null && !jid.equals("")) {
	        		job = vendorJobDao.selectById(Integer.parseInt(jid));
	        		if (job != null) {
	        			vendorJobs.add(job);
	        		}
	        	} else {
	        		vendorJobs = vendorJobDao.selectAll();
	        	}	        	
	            count = vendorJobs.size();
	        } else {	        	
	        	if (bid.equals("") && jid.equals("")) {
	        		clientJobs = clientJobDao.selectAll();
	        	} else if (!bid.equals("") && jid.equals("")) {
	        		clientJobs = clientJobDao.selectByBusinessId(Integer.parseInt(bid));
	        	} else if (bid.equals("") && !jid.equals("")) {
	        		clientJobs = clientJobDao.selectById(Integer.parseInt(jid));
	        	}	        		      	        	
	            count = clientJobs.size();
	        }
	        LOGGER.info("Export Count="+count);

	        String csvFileName = "jobsResult.csv";
	        response.setContentType("text/csv");
	        // creates mock data
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
	        response.setHeader(headerKey, headerValue);

	        try {
	            // uses the Super CSV API to generate CSV data from the model data
	            ICsvMapWriter csvWriter = new CsvMapWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);	            
	            if (jobType.equals("client")) {
	            	String[] header = {"Job ID", "Client", "Business ID", "Created", "Submitted", "Last Modified", "Requested", "Matched", "Completed"};
	            	csvWriter.writeHeader(header);
	            	createClientRow(clientJobs, header, csvWriter);
	            } else {
	            	String[] header = {"Job ID", "Vendor", "Precision", "Created", "Filename", "Submitted", "Received", "Last Modified", "Requested", "Matched"};
	            	csvWriter.writeHeader(header);
	            	createVendorRow(vendorJobs, header, csvWriter);
	            }	            
	            
	            
	            csvWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	 	
	 	@RequestMapping("exportDetails")
	    public String exportDetails(String jid, String bid, String jobType, HttpServletResponse response) {
	        int count;
	        LOGGER.info("Export job Details job id="+jid+" - business Id="+bid+" - Job Type="+jobType);	        	        	        
	        List<ClientJobDetail> clientJobDetail = new ArrayList<ClientJobDetail>();	        		       
	        	        	        	        	        		        	        			        		        
	        if (!jid.equals("")) {
	        	clientJobDetail = clientJobDao.getDetailsByJobId(Integer.parseInt(jid));
	        }	        		      	        	
	        count = clientJobDetail.size();
	        
	        LOGGER.info("Export Count="+count);

	        String csvFileName = "clientJobDetailsResult.csv";
	        response.setContentType("text/csv");
	        // creates mock data
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
	        response.setHeader(headerKey, headerValue);

	        try {
	            // uses the Super CSV API to generate CSV data from the model data
	            ICsvMapWriter csvWriter = new CsvMapWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);	            
	            
	            String[] header = {"Job ID", "Consumer ID", "Append Type", "Firt Name", "Last Name", "Address 1", "City", "State", "Zip", "Precision Requested", "Vendor ID", "Precision Result", "Appended Email", "Created", "Submitted", "Received", "Downloaded",  "Last Modified"};
	            csvWriter.writeHeader(header);
	            createClientDetailRow(clientJobDetail, header, csvWriter);
	            	            	            	            
	            csvWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }


}
