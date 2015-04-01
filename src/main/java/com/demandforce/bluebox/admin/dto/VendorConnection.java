package com.demandforce.bluebox.admin.dto;

public class VendorConnection {
	private int vendorConnectionId;
	private int vendorId;
	private String appendType;
	private String direction;
	private String precision;
	private String protocol;
	private String uri;
	private String directory;
	private String user;
	private String pass;	
	
	public VendorConnection() {
			
		}
	
	public VendorConnection(int vendorConnectionId, int vendorId, String appendType, String direction, String precision,
			String protocol, String uri, String directory, String user, String pass) {
		this.vendorConnectionId = vendorConnectionId;
		this.vendorId = vendorId;
		this.appendType = appendType;
		this.direction = direction;
		this.precision = precision;
		this.protocol = protocol;
		this.uri = uri;
		this.directory = directory;
		this.user = user;
		this.pass = pass;		
	}

	public int getVendorConnectionId() {
		return vendorConnectionId;
	}

	public void setVendorConnectionId(int vendorConnectionId) {
		this.vendorConnectionId = vendorConnectionId;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getAppendType() {
		return appendType;
	}

	public void setAppendType(String appendType) {
		this.appendType = appendType;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "VendorConnection [vendorConnectionId=" + vendorConnectionId
				+ ", vendorId=" + vendorId + ", appendType=" + appendType
				+ ", direction=" + direction + ", precision=" + precision
				+ ", protocol=" + protocol + ", uri=" + uri + ", directory="
				+ directory + ", user=" + user + ", pass="
				+ pass + "]";
	}
	
}
