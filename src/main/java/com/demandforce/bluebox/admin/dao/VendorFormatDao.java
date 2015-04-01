package com.demandforce.bluebox.admin.dao;

import java.util.List;

import com.demandforce.bluebox.admin.dto.VendorFormat;

public interface VendorFormatDao {	
	public List<VendorFormat> selectAllById(int vendorId);	
	public VendorFormat selectByIdPrecisionAndDirection(int vendorId, String precision, String direction);
	public int update(VendorFormat vendorFormat);
	public int insert(VendorFormat vendorFormat);
}
