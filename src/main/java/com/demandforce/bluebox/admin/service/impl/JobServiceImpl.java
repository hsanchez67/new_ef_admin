package com.demandforce.bluebox.admin.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.demandforce.bluebox.admin.dao.ClientJobDao;
import com.demandforce.bluebox.admin.dao.VendorJobDao;
import com.demandforce.bluebox.admin.dto.ClientJob;
import com.demandforce.bluebox.admin.dto.ClientJobDetail;
import com.demandforce.bluebox.admin.dto.VendorJob;
import com.demandforce.bluebox.admin.service.JobService;

@Service(value = "jobService") 
public class JobServiceImpl implements JobService {
	
	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
	
	@Resource
	ClientJobDao clientJobDao;
		
	@Resource
	VendorJobDao vendorJobDao;
	
	@Override
	public List<ClientJobDetail> getDetailsByJobId(int jobId) {	
		LOGGER.info("JobService.getDetailsByJobId");		
		return clientJobDao.getDetailsByJobId(jobId);
	}
	
	@Override
	public ClientJobDetail getConsumerDetails(int jobId, int consumerId) {
		LOGGER.info("JobService.getConsumerDetails");
		return clientJobDao.getConsumerDetails(jobId, consumerId);
	}
	
	@Override
	public List<ClientJob> selectAllClientJobs() {
		LOGGER.info("JobService.selectAllClientJobs");
		return clientJobDao.selectAll();
	}
	
	@Override
	public List<VendorJob> selectAllVendorJobs() {
		LOGGER.info("JobService.selectAllVendorJobs");
		return vendorJobDao.selectAll();
	}
	
	@Override
	public VendorJob getVendorJobDetail(int jobId) {
		LOGGER.info("JobService.getVendorJobDetail");
		return vendorJobDao.selectById(jobId);
	}

	@Override
	public Map<String, Object> getServiceElementsForDate(Date date) {		 
		return clientJobDao.getServiceElementsByDate(date);
	}
	
	@Override
	public List<ClientJob> selectAllClientJobsByDate(Date date) {
		return clientJobDao.selectAllByDate(date);
	}
	
	@Override
	public List<ClientJob> selectClientJobsByBusinessId(int bid) {
		return clientJobDao.selectByBusinessId(bid);
	}
	
	@Override
	public List<ClientJob> selectClientJobsById(int jobId) {
		return clientJobDao.selectById(jobId);
	}
}
