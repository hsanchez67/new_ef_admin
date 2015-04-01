package com.demandforce.bluebox.admin.service;

import java.util.List;



import java.util.Map;

import com.demandforce.bluebox.admin.dto.Vendor;
import com.demandforce.bluebox.admin.dto.VendorConnection;
import com.demandforce.bluebox.admin.dto.VendorFormat;

public interface ConfigurationService {
	public List<Vendor> getVendorList();
	public Vendor getVendor(int vendorId);
	public int updateVendor(Vendor vendor);
	public Map<String, String> getProperties(String type, int vendorId);		
	public int saveProperties(Map<String, String> properties, String type);
	/* Vendor Format */
	public List<VendorFormat> selectAllVFById(int vendorId); 	
	public VendorFormat selectVFByIdPrecisionAndDirection(int vendorId, String precision, String direction);	
	public int updateVF(VendorFormat vendorFormat);
	public int insertVF(VendorFormat vendorFormat);
	/* Vendor Connection */
	public List<VendorConnection> selectAllVCById(int vendorId); 	
	public VendorConnection selectVCByIdPrecisionAndDirection(int vendorId, String precision, String direction);	
	public int updateVC(VendorConnection vendorConnection);
	public int insertVC(VendorConnection vendorConnection);	
}
