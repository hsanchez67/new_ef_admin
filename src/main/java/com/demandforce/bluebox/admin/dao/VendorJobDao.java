package com.demandforce.bluebox.admin.dao;

import java.util.List;

import com.demandforce.bluebox.admin.dto.VendorJob;

public interface VendorJobDao {
	
	public List<VendorJob> selectAll();
	public VendorJob selectById(int id);

}
