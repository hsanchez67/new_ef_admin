package com.demandforce.bluebox.admin.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.demandforce.bluebox.admin.dto.ClientJob;
import com.demandforce.bluebox.admin.dto.ClientJobDetail;

public interface ClientJobDao {

	public List<ClientJob> selectAll();
	public List<ClientJob> selectById(int id);
	public List<ClientJob> selectByBusinessId(int bid);	
	public ClientJob selectByIdWithBusinessId(int id, int bid); 
	public List<ClientJobDetail> getDetailsByJobId(int id);
	public ClientJobDetail getConsumerDetails(int jobId, int consumerId);
	public Map<String, Object> getServiceElementsByDate(Date date);
	public List<ClientJob> selectAllByDate(Date date);
}
