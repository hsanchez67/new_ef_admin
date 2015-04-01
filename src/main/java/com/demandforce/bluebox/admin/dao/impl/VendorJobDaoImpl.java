package com.demandforce.bluebox.admin.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.demandforce.bluebox.admin.dao.VendorJobDao;
import com.demandforce.bluebox.admin.dto.VendorJob;
import com.demandforce.bluebox.admin.utils.DBUtils;


@Repository
public class VendorJobDaoImpl implements VendorJobDao {
	
	private final Logger LOGGER = Logger.getLogger(VendorJobDaoImpl.class);	

	@Override
	public List<VendorJob> selectAll() {
		LOGGER.info("** SQL VendorJobDao.selectAll");
		String sql = "Select * from vendor_job";
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
			
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			List<VendorJob> vendorJobList = new ArrayList<VendorJob>();
			VendorJob vendorJob = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				vendorJob = new VendorJob(
					rs.getInt("vendor_jobid"),
					rs.getInt("vendorid"),
					rs.getString("append_type"),
					rs.getString("precision"),
					rs.getTimestamp("created_ts"),
					rs.getString("filename"), 
					rs.getTimestamp("submit_ts"),
					rs.getTimestamp("receive_ts"),
					rs.getTimestamp("last_modified_ts"),
					rs.getInt("num_requested"),
					rs.getInt("num_matched")
				);
				vendorJobList.add(vendorJob);
			}			
			rs.close();
			ps.close();
			return vendorJobList;			
 
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
	public VendorJob selectById(int id) {
		LOGGER.info("** SQL VendorJobDao.findById");
		String sql = "Select * from vendor_job where vendor_jobid = ?";
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
									
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			VendorJob vendorJob = null;			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				vendorJob = new VendorJob(
					rs.getInt("vendor_jobid"),
					rs.getInt("vendorid"),
					rs.getString("append_type"),
					rs.getString("precision"),
					rs.getTimestamp("created_ts"),
					rs.getString("filename"), 
					rs.getTimestamp("submit_ts"),
					rs.getTimestamp("receive_ts"),
					rs.getTimestamp("last_modified_ts"),
					rs.getInt("num_requested"),
					rs.getInt("num_matched")
				);				
			}			
			rs.close();
			ps.close();			return vendorJob;			

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
