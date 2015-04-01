package com.demandforce.bluebox.admin.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.Ostermiller.util.StringHelper;
import com.demandforce.bluebox.admin.dao.VendorConnectionDao;
import com.demandforce.bluebox.admin.dto.VendorConnection;
import com.demandforce.bluebox.admin.utils.DBUtils;

@Repository
public class VendorConnectionDaoImpl implements VendorConnectionDao {
	private final Logger LOGGER = Logger.getLogger(VendorConnectionDaoImpl.class);	

	@Override
	public List<VendorConnection> selectAllById(int vendorId) {
		LOGGER.info("** SQL VendorConnectionDao.selectAllById.ID:"+vendorId);
		String sql = "Select * from vendor_connection where vendorid = ?";
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
			
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, vendorId);
			List<VendorConnection> vendorConnectionList = new ArrayList<VendorConnection>();
			VendorConnection vendorConnection = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				vendorConnection = new VendorConnection(
					rs.getInt("vendor_connectionid"),
					rs.getInt("vendorid"),					
					rs.getString("append_type"),					
					rs.getString("direction"),
					rs.getString("precision"),
					rs.getString("protocol"),
					rs.getString("uri"),
					rs.getString("directory"),
					rs.getString("user"),
					rs.getString("pass")					
				);
				vendorConnectionList.add(vendorConnection);
			}			
			rs.close();
			ps.close();
			return vendorConnectionList;			
 
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
	public VendorConnection selectByIdPrecisionAndDirection(int vendorId, String precision, String direction) {
		LOGGER.info("** SQL VendorConnectionDao.selectByIdPrecisionAndDirection.ID:"+vendorId);
		String sql = "Select * from vendor_connection where `vendorid` = ? and `precision` = ? and `direction` = ?";
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
			
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, vendorId);	
			ps.setString(2, precision);
			ps.setString(3, direction);
			VendorConnection vendorConnection = null;
			LOGGER.info("** SQL "+ps.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				vendorConnection = new VendorConnection(
					rs.getInt("vendor_connectionid"),
					rs.getInt("vendorid"),					
					rs.getString("append_type"),					
					rs.getString("direction"),
					rs.getString("precision"),
					rs.getString("protocol"),
					rs.getString("uri"),
					rs.getString("directory"),
					rs.getString("user"),
					rs.getString("pass")			
				);				
			}			
			rs.close();
			ps.close();
			return vendorConnection;			
 
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
	public int update(VendorConnection vendorConnection) {
		LOGGER.info("** SQL VendorConnectionDao.update");
		String sql = "update vendor_connection set `append_type` = '"+vendorConnection.getAppendType()+"',  `protocol` = '"+vendorConnection.getProtocol()+"'"
				+ ", `uri` = '"+StringHelper.escapeSQL(vendorConnection.getUri())+"', "
				+ " `directory` = '"+StringHelper.escapeSQL(vendorConnection.getDirectory())+"',"
				+ " `user` = '"+StringHelper.escapeSQL(vendorConnection.getUser())+"',"
				+ " `pass` = '"+StringHelper.escapeSQL(vendorConnection.getPass())+"' "				
				+ " where vendor_connectionid = "+vendorConnection.getVendorConnectionId()+";";
		
		LOGGER.info("** SQL VendorConnectionDao.update sql: "+ sql.toString());
		
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
	
	@Override
	public int insert(VendorConnection vendorConnection) {
		LOGGER.info("** SQL VendorDao.update");
		String sql = "insert into vendor_connection (`vendorid`, `precision`, `direction`, `append_type`, `protocol`, `uri`, `directory`, `user`, `pass`) values "
				+ " (?,?,?,?,?,?,?,?,?);";
		
		Connection conn = null;
		int result = 0;		
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
			
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, vendorConnection.getVendorId());
			ps.setString(2, vendorConnection.getPrecision());
			ps.setString(3, vendorConnection.getDirection());
			ps.setString(4, vendorConnection.getAppendType());
			ps.setString(5, vendorConnection.getProtocol());
			ps.setString(6, vendorConnection.getUri());
			ps.setString(7, vendorConnection.getDirectory());
			ps.setString(8, vendorConnection.getUser());
			ps.setString(9, vendorConnection.getPass());
			LOGGER.info("** SQL "+ps.toString());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}
					
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
