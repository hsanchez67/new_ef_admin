package com.demandforce.bluebox.admin.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.demandforce.bluebox.admin.dto.ClientJob;
import com.demandforce.bluebox.admin.dto.ClientJobDetail;
import com.demandforce.bluebox.admin.dto.VendorJob;

public interface JobService {
	public List<ClientJobDetail> getDetailsByJobId(int jobId);
	public List<ClientJob> selectAllClientJobs();
	public List<VendorJob> selectAllVendorJobs();
	public ClientJobDetail getConsumerDetails(int jobId, int consumerId);
	public VendorJob getVendorJobDetail(int jobId);
	public Map<String, Object> getServiceElementsForDate(Date date);
	public List<ClientJob> selectAllClientJobsByDate(Date date);
	public List<ClientJob> selectClientJobsByBusinessId(int bid);
	public List<ClientJob> selectClientJobsById(int jobId);
}
