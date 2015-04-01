package com.demandforce.bluebox.admin.dao;

import java.util.Map;

public interface PropertiesDao {
	public Map<String, String> getProperties(String type, int vendorId);		
	public int saveProperties(Map<String, String> properties, String type);
}
