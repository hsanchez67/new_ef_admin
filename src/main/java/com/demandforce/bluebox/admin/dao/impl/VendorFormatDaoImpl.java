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
import com.demandforce.bluebox.admin.dao.VendorFormatDao;
import com.demandforce.bluebox.admin.dto.VendorFormat;
import com.demandforce.bluebox.admin.utils.DBUtils;

@Repository
public class VendorFormatDaoImpl implements VendorFormatDao	{
	private final Logger LOGGER = Logger.getLogger(VendorFormatDaoImpl.class);	

	@Override
	public List<VendorFormat> selectAllById(int vendorId) {
		LOGGER.info("** SQL VendorFormatDao.selectAllById.ID:"+vendorId);
		String sql = "Select * from vendor_format where vendorid = ?";
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
			
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, vendorId);
			List<VendorFormat> vendorFormatList = new ArrayList<VendorFormat>();
			VendorFormat vendorFormat = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				vendorFormat = new VendorFormat(
					rs.getInt("vendor_formatid"),
					rs.getInt("vendorid"),					
					rs.getString("append_type"),					
					rs.getString("direction"),
					rs.getString("precision"),
					rs.getString("file_format"),
					rs.getString("field_separator"),
					rs.getString("record_separator"),
					rs.getString("enclosed_by_char"),
					rs.getString("escape_char"),
					rs.getString("column_list")
				);
				vendorFormatList.add(vendorFormat);
			}			
			rs.close();
			ps.close();
			return vendorFormatList;			
 
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
	public VendorFormat selectByIdPrecisionAndDirection(int vendorId, String precision, String direction) {
		LOGGER.info("** SQL VendorFormatDao.selectByIdPrecisionAndDirection.ID:"+vendorId);
		String sql = "Select * from vendor_format where `vendorid` = ? and `precision` = ? and `direction` = ?";
		
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
			VendorFormat vendorFormat = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				vendorFormat = new VendorFormat(
					rs.getInt("vendor_formatid"),
					rs.getInt("vendorid"),					
					rs.getString("append_type"),					
					rs.getString("direction"),
					rs.getString("precision"),
					rs.getString("file_format"),
					rs.getString("field_separator"),
					rs.getString("record_separator"),
					rs.getString("enclosed_by_char"),
					rs.getString("escape_char"),
					rs.getString("column_list")
				);				
			}			
			rs.close();
			ps.close();
			return vendorFormat;			
 
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
	public int update(VendorFormat vendorFormat) {
		LOGGER.info("** SQL VendorFormatDao.update");
		String sql = "update vendor_format set append_type = '"+vendorFormat.getAppendType()+"',  file_format = '"+vendorFormat.getFileFormat()+"'"
				+ ", field_separator = '"+StringHelper.escapeSQL(vendorFormat.getFieldSeparator())+"', "
				+ " record_separator = '"+StringHelper.escapeSQL(vendorFormat.getRecordSeparator())+"',"
				+ " enclosed_by_char = '"+StringHelper.escapeSQL(vendorFormat.getEnclosedByChar())+"',"
				+ " escape_char = '"+StringHelper.escapeSQL(vendorFormat.getEscapeChar())+"',"
				+ " column_list = '"+StringHelper.escapeSQL(vendorFormat.getColumnList())+"' "
				+ " where vendor_formatid = "+vendorFormat.getVendorFormatId()+";";
		
		LOGGER.info("** SQL VendorFormatDao.update sql: "+ sql.toString());
		
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
	public int insert(VendorFormat vendorFormat) {
		LOGGER.info("** SQL VendorDao.update");
		String sql = "insert into vendor_format (`vendorid`, `precision`, `direction`, `append_type`, `file_format`, `field_separator`,"
				+ "`record_separator`, `enclosed_by_char`, `escape_char`, `column_list`) values "
				+ " (?,?,?,?,?,?,?,?,?,?)";
		
		Connection conn = null;
		int result = 0;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
			
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, vendorFormat.getVendorId());
			ps.setString(2, vendorFormat.getPrecision());
			ps.setString(3, vendorFormat.getDirection());
			ps.setString(4, vendorFormat.getAppendType());
			ps.setString(5, vendorFormat.getFileFormat());
			ps.setString(6, vendorFormat.getFieldSeparator());
			ps.setString(7, vendorFormat.getRecordSeparator());
			ps.setString(8, vendorFormat.getEnclosedByChar());
			ps.setString(9, vendorFormat.getEscapeChar());
			ps.setString(10, vendorFormat.getColumnList());
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
