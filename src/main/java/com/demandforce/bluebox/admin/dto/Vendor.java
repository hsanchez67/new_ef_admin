package com.demandforce.bluebox.admin.dto;

public class Vendor {
	private int vendorId;
	private String name;
	private String contact;
	
	public Vendor() {
			
		}
	
	public Vendor(int vendorId, String name, String contact) {
		this.vendorId = vendorId;
		this.name = name;
		this.contact = contact;		
	}
	
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	@Override
	public String toString() {
		return "Vendor [vendorId=" + vendorId + ", name=" + name + ", contact="
				+ contact + "]";
	}

	
}
