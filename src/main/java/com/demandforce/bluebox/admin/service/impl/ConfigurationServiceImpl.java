package com.demandforce.bluebox.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.demandforce.bluebox.admin.dao.PropertiesDao;
import com.demandforce.bluebox.admin.dao.VendorConnectionDao;
import com.demandforce.bluebox.admin.dao.VendorDao;
import com.demandforce.bluebox.admin.dao.VendorFormatDao;
import com.demandforce.bluebox.admin.dto.Vendor;
import com.demandforce.bluebox.admin.dto.VendorConnection;
import com.demandforce.bluebox.admin.dto.VendorFormat;
import com.demandforce.bluebox.admin.service.ConfigurationService;


@Service(value = "configurationService")  
public class ConfigurationServiceImpl implements ConfigurationService {

	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    @Resource
    VendorDao vendorDao;
    
    @Resource
    VendorFormatDao vendorFormatDao;
    
    @Resource
    VendorConnectionDao vendorConnectionDao;
    
    @Resource
    PropertiesDao propertiesDao;

	@Override
	public List<Vendor> getVendorList() {		
		LOGGER.info("ConfigurationService.getVerndorList");
		return vendorDao.selectAll();
	}
	
	@Override
	public Vendor getVendor(int vendorId) {
		LOGGER.info("ConfigurationService.getVendor");
		return vendorDao.selectById(vendorId);
	}
	
	@Override
	public int updateVendor(Vendor vendor) {
		LOGGER.info("ConfigurationService.updateVendor");
		return vendorDao.updateVendor(vendor);
	}
	
	@Override
	public Map<String, String> getProperties(String type, int vendorId) {
		LOGGER.info("ConfigurationService.getProperties:"+type);
		return propertiesDao.getProperties(type, vendorId);
	}				
	
	@Override
	public int saveProperties(Map<String, String> properties, String type) {
		LOGGER.info("ConfigurationService.saveProperties");
		return propertiesDao.saveProperties(properties, type);
	}
	
	@Override
	public List<VendorFormat> selectAllVFById(int vendorId) {
		LOGGER.info("ConfigurationService.selectAllVFById");
		return vendorFormatDao.selectAllById(vendorId);
	}	
	
	@Override
	public VendorFormat selectVFByIdPrecisionAndDirection(int vendorId,
			String precision, String direction) {
		LOGGER.info("ConfigurationService.selectVFByIdPrecisionAndDirection");
		return vendorFormatDao.selectByIdPrecisionAndDirection(vendorId, precision, direction);
	}

	@Override
	public int updateVF(VendorFormat vendorFormat) {
		LOGGER.info("ConfigurationService.updateVF");
		return vendorFormatDao.update(vendorFormat);
	}

	@Override
	public int insertVF(VendorFormat vendorFormat) {
		LOGGER.info("ConfigurationService.insertVF");
		return vendorFormatDao.insert(vendorFormat);
	}
	
	@Override
	public List<VendorConnection> selectAllVCById(int vendorId) {
		LOGGER.info("ConfigurationService.selectAllVCById");
		return vendorConnectionDao.selectAllById(vendorId);
	}	
	
	@Override
	public VendorConnection selectVCByIdPrecisionAndDirection(int vendorId,
			String precision, String direction) {
		LOGGER.info("ConfigurationService.selectVCByIdPrecisionAndDirection");
		return vendorConnectionDao.selectByIdPrecisionAndDirection(vendorId, precision, direction);
	}

	@Override
	public int updateVC(VendorConnection vendorConnection) {
		LOGGER.info("ConfigurationService.updateVF");
		return vendorConnectionDao.update(vendorConnection);
	}

	@Override
	public int insertVC(VendorConnection vendorConnection) {
		LOGGER.info("ConfigurationService.insertVC");
		return vendorConnectionDao.insert(vendorConnection);
	}


    
}
