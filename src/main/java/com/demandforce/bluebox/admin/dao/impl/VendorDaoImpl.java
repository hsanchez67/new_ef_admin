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

import com.demandforce.bluebox.admin.dao.VendorDao;
import com.demandforce.bluebox.admin.dto.Vendor;
import com.demandforce.bluebox.admin.utils.DBUtils;

@Repository
public class VendorDaoImpl implements VendorDao {
	
	private final Logger LOGGER = Logger.getLogger(VendorJobDaoImpl.class);	

	@Override
	public List<Vendor> selectAll() {
		LOGGER.info("** SQL VendorDao.selectAll");
		String sql = "Select * from vendor";
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
			
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			List<Vendor> vendorList = new ArrayList<Vendor>();
			Vendor vendor = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				vendor = new Vendor(
					rs.getInt("vendorid"),					
					rs.getString("name"),					
					rs.getString("contact") 								
				);
				vendorList.add(vendor);
			}			
			rs.close();
			ps.close();
			return vendorList;			
 
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
	
	public Vendor selectById(int vendorId) {
		LOGGER.info("** SQL VendorDao.selectById");
		String sql = "Select * from vendor where vendorid = ?";
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
			
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, vendorId);
			Vendor vendor = null;			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				vendor = new Vendor(
					rs.getInt("vendorid"),					
					rs.getString("name"),					
					rs.getString("contact") 								
				);				
			}			
			rs.close();
			ps.close();
			return vendor;			
 
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
	
	public int updateVendor(Vendor vendor) {		
		LOGGER.info("** SQL VendorDao.updateVendor ID:"+vendor.getVendorId());
		String sql = "update vendor set " + "name = '" + vendor.getName() + "', `contact` = '" + vendor.getContact() + "' "
				+ "where vendorid = "+vendor.getVendorId()+";";
		
		Connection conn = null;
		int result = 0;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
			
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			result = ps.executeUpdate(sql);
					
			ps.close();
			return result;			
 
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
		return result;
	}

}
