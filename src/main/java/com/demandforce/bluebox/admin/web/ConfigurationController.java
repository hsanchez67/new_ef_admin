package com.demandforce.bluebox.admin.web;

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

import com.demandforce.bluebox.admin.dto.Vendor;
import com.demandforce.bluebox.admin.dto.VendorConnection;
import com.demandforce.bluebox.admin.dto.VendorFormat;
import com.demandforce.bluebox.admin.service.ConfigurationService;



@Controller
@RequestMapping(value = "")
public class ConfigurationController {
	public static final String VIEW_INDEX_CONFIGURATION = "configuration";
	public static final String VIEW_VENDOR_CONFIGURATION = "vendorConfiguration";
	
	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	ConfigurationService configurationService;
	
	@RequestMapping(value = "/configuration")    
    public ModelAndView goConfiguration()
    {
		ModelAndView mav = new ModelAndView(VIEW_INDEX_CONFIGURATION); 
		LOGGER.info("ConfigurationController.goConfiguration.");
		mav.addObject("mainTitle", "Configuration");
		
		mav.addObject("properties", configurationService.getProperties("global", 0));
		
		List<Vendor> vendors = configurationService.getVendorList();
		mav.addObject("vendorList", vendors);
        
        return mav;
    }
	
	@RequestMapping(value = "/vendorConfiguration", method = RequestMethod.POST) 	
    public ModelAndView goVendorConfiguration(@RequestParam String vendorId)
    {
		ModelAndView mav = new ModelAndView(VIEW_VENDOR_CONFIGURATION); 
		LOGGER.info("ConfigurationController.goVendorConfiguration.");
		mav.addObject("mainTitle", "Vendor Configuration");
				
		if (vendorId != null) {
			Vendor vendor = configurationService.getVendor(Integer.parseInt(vendorId));
			mav.addObject("vendor", vendor);
			mav.addObject("properties", configurationService.getProperties("vendor", 1));
			List<VendorFormat> vfs = configurationService.selectAllVFById(Integer.parseInt(vendorId));
			mav.addObject("vendorFormats", vfs);
		}
        
        return mav;
    }
		
	 @RequestMapping(value = "/configuration/ajax/saveConfigurationProperties", method = RequestMethod.POST)	    
	    public @ResponseBody Map<String, Object> saveConfigurationProperties(@RequestParam String appendArchiveDir, @RequestParam String appendResultsTtlDays,
	            @RequestParam String tempDir, @RequestParam String validAppendTypes, @RequestParam String maxRecordsPerFile,
	            @RequestParam String resultPollingFrequency, @RequestParam String submitFrequency) {	        	        	     
		 	LOGGER.info("ConfigurationController.saveConfigurationProperties");
		 	int result = 0;
		 	Map<String, String> propertiesMap = new HashMap<String, String>();	        
	        propertiesMap.put("append_archive_dir", appendArchiveDir);
	        propertiesMap.put("append_results_ttl_days", appendResultsTtlDays);
	        propertiesMap.put("tmp_dir", tempDir);
	        propertiesMap.put("valid_append_types", validAppendTypes);
	        propertiesMap.put("max_recs_per_file", maxRecordsPerFile);
	        propertiesMap.put("results_frequency_mins", resultPollingFrequency);
	        propertiesMap.put("submit_frequency_mins", submitFrequency);
	        result = configurationService.saveProperties(propertiesMap, "global");
	         //   cxVO.setErrMsg("Business Id " + businessId + " not found! ");
	        LOGGER.info("ConfigurationController.saveConfigurationProperties result:"+result);
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        
	        if (result == 0) {
	        	resultMap.put("result", "Error");	        	
	        } else {
	        	resultMap.put("result", "Success");	        	
	        }	         		 		      
	        return resultMap;
	    }
	 
	 @RequestMapping(value = "/configuration/ajax/saveVendorConfigurationProperties", method = RequestMethod.POST)	    
	    public @ResponseBody Map<String, Object> saveVendorConfigurationProperties(@RequestParam String vendorName, @RequestParam String vendorContact,
	            @RequestParam String maxRecordsPerFile, @RequestParam String pollingInterval, 
	            @RequestParam String vendorId) {	        	        	     
		 	LOGGER.info("ConfigurationController.saveVendorConfigurationProperties");
		 	int result = 0;
		 	int result2 = 0;
		 	
		 	if (vendorId != null || vendorId != "") {
			 	Vendor vendor = new Vendor(Integer.parseInt(vendorId), vendorName, vendorContact);
			 	result2 = configurationService.updateVendor(vendor);
			 	
			 	Map<String, String> propertiesMap = new HashMap<String, String>();	        
		        propertiesMap.put("max_records_per_file", maxRecordsPerFile);
		        propertiesMap.put("polling_interval", pollingInterval);
		        propertiesMap.put("vendorid", vendorId);	        
		        result = configurationService.saveProperties(propertiesMap, "vendor");
		 	}
		        
		    LOGGER.info("ConfigurationController.saveVendorConfigurationProperties result:"+result);
		    Map<String, Object> resultMap = new HashMap<String, Object>();		 	
	        
		    result = result * result2;
	        if (result == 0) {
	        	resultMap.put("result", "Error");	        	
	        } else {
	        	resultMap.put("result", "Success");	        	
	        }	         		 		      
	        return resultMap;
	    }
	 
	 @RequestMapping(value = "/configuration/ajax/saveVendorConfigurationDataFileFormat", method = RequestMethod.POST)	    
	    public @ResponseBody Map<String, Object> saveVendorConfigurationDataFileFormat(@RequestParam String precision, @RequestParam String fileFormat,
	            @RequestParam String appendType, @RequestParam String fieldSeparator, @RequestParam String recordSeparator, @RequestParam String enclosedBy,  @RequestParam String escape,   
	            @RequestParam String columnDefinitions, @RequestParam String direction, @RequestParam String vendorFormatId, @RequestParam String vendorId) {	        	        	     
		 	LOGGER.info("ConfigurationController.saveVendorConfigurationDataFileFormat");
		 	int result = 0;		 	
		 	int vfId = 0;
		 	if (vendorFormatId == null || vendorFormatId.equals("")) {
		 		vendorFormatId = ""; 
		 	} else {
		 		vfId = Integer.parseInt("vendorFormatId");
		 	}
		 	if (vendorId != null || vendorId != "") {
			 	VendorFormat vf = new VendorFormat(vfId, Integer.parseInt(vendorId), appendType, direction, precision, 
			 			fileFormat, fieldSeparator, recordSeparator, enclosedBy, escape, columnDefinitions);
			 	
			 	if (!vendorFormatId.equals("")) {
			 		result = configurationService.updateVF(vf);
			 	} else {
			 		result = configurationService.insertVF(vf);
			 	}
			 				 	
		 	}
		        
		    LOGGER.info("ConfigurationController.saveVendorConfigurationDataFileFormat result:"+result);
		    Map<String, Object> resultMap = new HashMap<String, Object>();		 	
	        		   
	        if (result == 0) {
	        	resultMap.put("result", "Error");	        	
	        } else {	        	
	        	resultMap.put("result", "Success");	        	
	        }	
	        if (vfId == 0) {
	        	resultMap.put("vendorFormatId", result);
	        } else {
	        	resultMap.put("vendorFormatId", vfId);
	        }
	        return resultMap;
	    }
	 
	 @RequestMapping(value = "/configuration/ajax/saveVendorFTPServerProperties", method = RequestMethod.POST)	    
	    public @ResponseBody Map<String, Object> saveVendorFTPServerProperties(@RequestParam String direction, @RequestParam String appendType,
	            @RequestParam String protocol, @RequestParam String uri, @RequestParam String directory, @RequestParam String username,  @RequestParam String password,   
	            @RequestParam String precision, @RequestParam String vendorConnectionId, @RequestParam String vendorId) {	        	        	     
		 	LOGGER.info("ConfigurationController.saveVendorFTPServerProperties");
		 	int result = 0;		 	
		 	int vcId = 0;
		 	if (vendorConnectionId == null || vendorConnectionId.equals("")) {
		 		vendorConnectionId = "";		 		
		 	} else {
		 		vcId = Integer.parseInt(vendorConnectionId);
		 	}
		 	if (vendorId != null || vendorId != "") {		 		
			 	VendorConnection vc = new VendorConnection(vcId, Integer.parseInt(vendorId), appendType, direction, precision, 
			 			protocol, uri, directory, username, password);
				
			 	if (!vendorConnectionId.equals("")) {
			 		result = configurationService.updateVC(vc);
			 	} else {
			 		result = configurationService.insertVC(vc);
			 	}
			 				 	
		 	}
		        
		    LOGGER.info("ConfigurationController.saveVendorFTPServerProperties result:"+result);
		    Map<String, Object> resultMap = new HashMap<String, Object>();		 	
	        		   
	        if (result == 0) {
	        	resultMap.put("result", "Error");	        	
	        } else {	        	
	        	resultMap.put("result", "Success");	        	
	        }	    
	        if (vcId == 0) {
	        	resultMap.put("vendorConnectionId", result);
	        } else {
	        	resultMap.put("vendorConnectionId", vcId);
	        }
	        return resultMap;
	    }
	 
	 @RequestMapping(value = "/configuration/ajax/goSearchVendorConfiguration", method = RequestMethod.POST)	    
	    public @ResponseBody VendorFormat goSearchVendorConfiguration(@RequestParam String vendorId, @RequestParam String precision,
	            @RequestParam String direction) {	        	        	     
		 	LOGGER.info("ConfigurationController.goSearchVendorConfiguration");		 	
		 	
		 	if (vendorId != null || vendorId != "") {
			 	VendorFormat vf = new VendorFormat();
			 	vf = configurationService.selectVFByIdPrecisionAndDirection(Integer.parseInt(vendorId), precision, direction);
			 	if (vf != null) {
			 		return vf;
			 	}
		 	}		        		    	         		 		     
	        return null;
	    }
	 
	 @RequestMapping(value = "/configuration/ajax/goSearchFTPVendorConfiguration", method = RequestMethod.POST)	    
	    public @ResponseBody VendorConnection goSearchFTPVendorConfiguration(@RequestParam String vendorId, @RequestParam String precision,
	            @RequestParam String direction) {	        	        	     
		 	LOGGER.info("ConfigurationController.goSearchFTPVendorConfiguration");		 	
		 	
		 	if (vendorId != null || vendorId != "") {
			 	VendorConnection vc = new VendorConnection();
			 	vc = configurationService.selectVCByIdPrecisionAndDirection(Integer.parseInt(vendorId), precision, direction);
			 	if (vc != null) {
			 		return vc;
			 	}			 	
		 	}		        		    	         		 		     
	        return null;
	    }
	 
	 
}
