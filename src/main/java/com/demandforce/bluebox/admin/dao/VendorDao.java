package com.demandforce.bluebox.admin.dao;

import java.util.List;

import com.demandforce.bluebox.admin.dto.Vendor;


public interface VendorDao {
	public List<Vendor> selectAll();
	public Vendor selectById(int vendorId);
	public int updateVendor(Vendor vendor);
}
