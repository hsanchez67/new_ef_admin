package com.demandforce.bluebox.admin.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.demandforce.bluebox.admin.dao.ClientJobDao;
import com.demandforce.bluebox.admin.dto.ClientJob;
import com.demandforce.bluebox.admin.dto.ClientJobDetail;
import com.demandforce.bluebox.admin.utils.DBUtils;

@Repository
public class ClientJobDaoImpl implements ClientJobDao {
	
private final Logger LOGGER = Logger.getLogger(ClientJobDaoImpl.class);	

	@Override
	public List<ClientJob> selectAll() {
		LOGGER.info("** SQL ClientJobDao.selectAll");
		String sql = "Select `client_jobid`, client_job.`clientid` as clientid, client.`name` as clientname, `businessid`,"
				+ " `created_ts`, `submit_ts`, `last_modified_ts`, `num_requested`, `num_matched`, `append_complete_ts`  "
				+ " from client_job "
				+ " join `client` on `client`.clientid = client_job.clientid ";
		
		Connection conn = null;		
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
			
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			List<ClientJob> clientJobList = new ArrayList<ClientJob>();
			ClientJob clientJob = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				clientJob = new ClientJob(
					rs.getInt("client_jobid"),
					rs.getInt("clientid"),
					rs.getString("clientname"),
					rs.getInt("businessid"),					
					rs.getTimestamp("created_ts"),					
					rs.getTimestamp("submit_ts"),					
					rs.getTimestamp("last_modified_ts"),
					rs.getInt("num_requested"),
					rs.getInt("num_matched"),
					rs.getTimestamp("append_complete_ts")
				);
				clientJobList.add(clientJob);
			}			
			rs.close();
			ps.close();
			return clientJobList;			
 
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		} catch (NamingException e) {
			LOGGER.error("Error Reading jndi.xml", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// do nothing
				}
			}
		}
		return null;
	}	
	
	
	@Override
	public List<ClientJob> selectById(int id) {
		LOGGER.info("** SQL ClientJobDao.findById");		
		String sql = "Select `client_jobid`, client_job.`clientid` as clientid, client.`name` as clientname, `businessid`,"
				+ " `created_ts`, `submit_ts`, `last_modified_ts`, `num_requested`, `num_matched`, `append_complete_ts`  "
				+ " from client_job "
				+ " join `client` on `client`.clientid = client_job.clientid "
				+ " where client_jobid = ? ";
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
									
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			List<ClientJob> clientJobList = new ArrayList<ClientJob>();
			ClientJob clientJob = null;			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				clientJob = new ClientJob(
						rs.getInt("client_jobid"),
						rs.getInt("clientid"),
						rs.getString("clientname"),
						rs.getInt("businessid"),					
						rs.getTimestamp("created_ts"),					
						rs.getTimestamp("submit_ts"),					
						rs.getTimestamp("last_modified_ts"),
						rs.getInt("num_requested"),
						rs.getInt("num_matched"),
						rs.getTimestamp("append_complete_ts")
				);				
				clientJobList.add(clientJob);
			}			
			rs.close();
			ps.close();		
			
			return clientJobList;			
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		} catch (NamingException e) {
			LOGGER.error("Error Reading jndi.xml", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// do nothing
				}
			}
		}
		return null;
	}	
	
	@Override
	public ClientJobDetail getConsumerDetails(int jobId, int consumerId) {
		LOGGER.info("** SQL ClientJobDao.getConsumerDetails");
		String sql = "Select * from client_job_detail where client_jobid = ? and consumerid = ?";
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
									
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, jobId);
			ps.setInt(2, consumerId);			
			ClientJobDetail clientJobDetail = null;			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				clientJobDetail = new ClientJobDetail(
						rs.getInt("client_job_detailid"),
						rs.getInt("client_jobid"),
						rs.getInt("consumerid"),	
						rs.getString("append_types"),
						rs.getString("fname"),
						rs.getString("lname"),
						rs.getString("address1"),
						rs.getString("city"),
						rs.getString("state"),
						rs.getString("zip"),
						rs.getString("precision_req"),
						rs.getInt("vendor_jobid"),
						rs.getString("precision_res"),
						rs.getString("appended_email"),
						rs.getTimestamp("created_ts"),					
						rs.getTimestamp("submit_ts"),	
						rs.getTimestamp("receive_ts"),
						rs.getTimestamp("download_ts"),
						rs.getTimestamp("last_modified_ts")					
				);				
			}			
			rs.close();
			ps.close();		
			
			return clientJobDetail;			
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		} catch (NamingException e) {
			LOGGER.error("Error Reading jndi.xml", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// do nothing
				}
			}
		}
		return null;		
	}
	
	@Override
	public List<ClientJobDetail> getDetailsByJobId(int id) {
		LOGGER.info("** SQL ClientJobDao.getDetailsByJobId");
		String sql = "Select * from client_job_detail where client_jobid = ?";
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
									
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			List<ClientJobDetail> clientJobDetailList = new ArrayList<ClientJobDetail>();
			ClientJobDetail clientJobDetail = null;			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				clientJobDetail = new ClientJobDetail(
						rs.getInt("client_job_detailid"),
						rs.getInt("client_jobid"),
						rs.getInt("consumerid"),	
						rs.getString("append_types"),
						rs.getString("fname"),
						rs.getString("lname"),
						rs.getString("address1"),
						rs.getString("city"),
						rs.getString("state"),
						rs.getString("zip"),
						rs.getString("precision_req"),
						rs.getInt("vendor_jobid"),
						rs.getString("precision_res"),
						rs.getString("appended_email"),
						rs.getTimestamp("created_ts"),					
						rs.getTimestamp("submit_ts"),	
						rs.getTimestamp("receive_ts"),
						rs.getTimestamp("download_ts"),
						rs.getTimestamp("last_modified_ts")					
				);
				clientJobDetailList.add(clientJobDetail);
			}			
			rs.close();
			ps.close();		
			
			return clientJobDetailList;			
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		} catch (NamingException e) {
			LOGGER.error("Error Reading jndi.xml", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// do nothing
				}
			}
		}
		return null;
	}	
	
	@Override
	public List<ClientJob> selectByBusinessId(int bid) {
		LOGGER.info("** SQL ClientJobDao.selectByBusinessId");		
		String sql = "Select `client_jobid`, client_job.`clientid` as clientid, client.`name` as clientname, `businessid`,"
				+ " `created_ts`, `submit_ts`, `last_modified_ts`, `num_requested`, `num_matched`, `append_complete_ts` " 
				+ " from client_job "
				+ " join `client` on `client`.clientid = client_job.clientid "
				+ " where businessid = ? ";
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
									
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bid);
			List<ClientJob> clientJobList = new ArrayList<ClientJob>();
			ClientJob clientJob = null;			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				clientJob = new ClientJob(
						rs.getInt("client_jobid"),	
						rs.getInt("clientid"),
						rs.getString("clientname"),
						rs.getInt("businessid"),					
						rs.getTimestamp("created_ts"),					
						rs.getTimestamp("submit_ts"),					
						rs.getTimestamp("last_modified_ts"),
						rs.getInt("num_requested"),
						rs.getInt("num_matched"),
						rs.getTimestamp("append_complete_ts")
				);	
				clientJobList.add(clientJob);
			}			
			rs.close();
			ps.close();		
			
			return clientJobList;			
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		} catch (NamingException e) {
			LOGGER.error("Error Reading jndi.xml", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// do nothing
				}
			}
		}
		return null;
	}	
	
	
	@Override
	public ClientJob selectByIdWithBusinessId(int id, int bid) {
		LOGGER.info("** SQL ClientJobDao.findByIdWithBusinessId");		
		String sql = "Select `client_jobid`, client_job.`clientid` as clientid, client.`name` as clientname, `businessid`,"
				+ " `created_ts`, `submit_ts`, `last_modified_ts`, `num_requested`, `num_matched`, `append_complete_ts` "
				+ " from client_job "
				+ " join `client` on `client`.clientid = client_job.clientid "
				+ " where client_jobid = ? and businessid = ?  ";
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
									
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, bid);
			ClientJob clientJob = null;			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				clientJob = new ClientJob(
						rs.getInt("client_jobid"),		
						rs.getInt("clientid"),
						rs.getString("clientname"),
						rs.getInt("businessid"),					
						rs.getTimestamp("created_ts"),					
						rs.getTimestamp("submit_ts"),					
						rs.getTimestamp("last_modified_ts"),
						rs.getInt("num_requested"),
						rs.getInt("num_matched"),
						rs.getTimestamp("append_complete_ts")
				);				
			}			
			rs.close();
			ps.close();		
			
			return clientJob;			
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		} catch (NamingException e) {
			LOGGER.error("Error Reading jndi.xml", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// do nothing
				}
			}
		}
		return null;
	}


	@Override
	public Map<String, Object> getServiceElementsByDate(Date date) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		LOGGER.info("** SQL ClientJobDao.getServiceElementsByDate");		
		String sql = "Select count(client_jobid) as bexported, sum(num_requested) rrequested, sum(num_matched) as rmatched "				
				+ " from client_job "				
				+ " where Date(submit_ts) = ? and append_complete_ts != '0000-00-00 00:00:00' ;  ";				
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
									
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, date);
									
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				map.put("date", date);
				map.put("numOfBusiness", rs.getInt("bexported"));
				map.put("numRequested", rs.getInt("rrequested"));
				map.put("numMatched", rs.getInt("rmatched"));				
			}			
			rs.close();
			ps.close();		
			
			return map;			
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		} catch (NamingException e) {
			LOGGER.error("Error Reading jndi.xml", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// do nothing
				}
			}
		}
		return null;
	}


	@Override
	public List<ClientJob> selectAllByDate(Date date) {
		LOGGER.info("** SQL ClientJobDao.selectAllByDate");
		String sql = "Select `client_jobid`, client_job.`clientid` as clientid, client.`name` as clientname, `businessid`,"
				+ " `created_ts`, `submit_ts`, `last_modified_ts`, `num_requested`, `num_matched`, `append_complete_ts`  "
				+ " from client_job "
				+ " join `client` on `client`.clientid = client_job.clientid "
				+ " where Date(created_ts) = ? ";
		
		Connection conn = null;		
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
			
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, date);
			List<ClientJob> clientJobList = new ArrayList<ClientJob>();
			ClientJob clientJob = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				clientJob = new ClientJob(
					rs.getInt("client_jobid"),
					rs.getInt("clientid"),
					rs.getString("clientname"),
					rs.getInt("businessid"),					
					rs.getTimestamp("created_ts"),					
					rs.getTimestamp("submit_ts"),					
					rs.getTimestamp("last_modified_ts"),
					rs.getInt("num_requested"),
					rs.getInt("num_matched"),
					rs.getTimestamp("append_complete_ts")
				);
				clientJobList.add(clientJob);
			}			
			rs.close();
			ps.close();
			return clientJobList;			
 
		} catch (SQLException e) {
			throw new RuntimeException(e); 
		} catch (NamingException e) {
			LOGGER.error("Error Reading jndi.xml", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// do nothing
				}
			}
		}
		return null;
	}	


}
