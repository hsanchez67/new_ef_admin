package com.demandforce.bluebox.admin.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.demandforce.bluebox.admin.dao.PropertiesDao;
import com.demandforce.bluebox.admin.utils.DBUtils;

@Repository
public class PropertiesDaoImpl implements PropertiesDao {
	private final static Logger LOGGER = Logger.getLogger(VendorJobDaoImpl.class);	
	
	private final static String VIEW_GLOBAL_PROPERTIES = "Select `key`, `value` from properties_global";
	private final static String VIEW_VENDOR_PROPERTIES = "Select `key`, `value` from properties_vendor where vendorid = ?";

	@Override
	public Map<String, String> getProperties(String type, int vendorId) {
		LOGGER.info("** SQL PropertiesDao.getGlobalProperties");		
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
			
			conn = DBUtils.getConnection("jdbc/bluebox");
			PreparedStatement ps = null;
			if (type.equals("global")) {
				ps = conn.prepareStatement(VIEW_GLOBAL_PROPERTIES);
			} else {
				ps = conn.prepareStatement(VIEW_VENDOR_PROPERTIES);
				ps.setInt(1, vendorId);
			}
			Map<String, String> propertiesMap = new HashMap<String, String>();			
			ResultSet rs = ps.executeQuery();
			LOGGER.info("Properties List:");
			while (rs.next()) {
				propertiesMap.put(rs.getString("key"), rs.getString("value"));
				LOGGER.info("Key: ".concat(rs.getString("key")));
				LOGGER.info("Value: ".concat(rs.getString("value")));
			}			
			rs.close();
			ps.close();
			return propertiesMap;			
 
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
	public int saveProperties(Map<String, String> properties, String type) {
		LOGGER.info("** SQL PropertiesDao.saveProperties Type:"+type);	
		
		Connection conn = null;
		
		try {
			
			InitialContext context = new InitialContext();			
			BasicDataSource bds = (BasicDataSource)context.lookup("jdbc/bluebox");
			LOGGER.info("DB URL: " + bds.getUrl());			
			
			conn = DBUtils.getConnection("jdbc/bluebox");						

			for (Map.Entry<String, String> entry : properties.entrySet())
			{
				PropertiesDaoImpl.updateConfigProperties(entry.getKey(), entry.getValue(), conn, type);
			    System.out.println(entry.getKey() + "/" + entry.getValue());
			}
			return 1;
 
		} catch (Exception e) {
			LOGGER.error("Error saveGlobalProperties ", e);
			return 0;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// do nothing
				}
			}
		}		
	}
	
	private static int updateConfigProperties(String key, String value, Connection conn, String type) throws Exception {		
		int result = 0;
		PreparedStatement ps = null;
		String sql = "";

		if (type.equals("global")) {
			sql = "update properties_global set " + "value = '" + value + "' where `key` = '" + key + "';";
		} else {
			sql = "update properties_vendor set " + "value = '" + value + "' where `key` = '" + key + "';";
		}

		LOGGER.info("** SQL PropertiesDao.updateConfigProperties");
		LOGGER.info(sql);

		try {
			ps = conn.prepareStatement(sql);
			result = ps.executeUpdate(sql);

		} catch (SQLException e) {			
				LOGGER.info("Error updating Config Settings (" + key + "): " + e.getMessage());
				throw new SQLException(e);			
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// do nothing
				}
			}
		}
		return result;
	}
}
