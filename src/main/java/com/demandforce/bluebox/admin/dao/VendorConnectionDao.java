package com.demandforce.bluebox.admin.dao;

import java.util.List;

import com.demandforce.bluebox.admin.dto.VendorConnection; 

public interface VendorConnectionDao {
	public List<VendorConnection> selectAllById(int vendorId);	
	public VendorConnection selectByIdPrecisionAndDirection(int vendorId, String precision, String direction);
	public int update(VendorConnection vendorConnection);
	public int insert(VendorConnection vendorConnection);
}
