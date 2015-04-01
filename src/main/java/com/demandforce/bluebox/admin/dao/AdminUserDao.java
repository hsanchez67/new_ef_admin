package com.demandforce.bluebox.admin.dao;



import com.demandforce.bluebox.admin.model.User;


public interface AdminUserDao {
	public User getAdminUserByEmail(final String email);
}
