package com.demandforce.bluebox.admin.service;

import java.util.List;

/**
 * The Interface Service. This interface describes the abstract behavior of a Service.
 */
public interface Service<T> {
			
	public abstract List<T> findAll();
	
	public abstract T findById(final String id);	
	
	public abstract Boolean deleteById(final String id, long expectedAffectedRowCount);	
	
	public abstract String insert(final T object);
}
