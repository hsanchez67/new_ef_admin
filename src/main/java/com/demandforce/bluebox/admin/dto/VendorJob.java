package com.demandforce.bluebox.admin.dto;

import java.sql.Timestamp;

public class VendorJob {
	private int vendorJobId;
	private int vendorId;
	private String appendType;
	private String precision;
	private Timestamp createdTs;
	private String filename;
	private Timestamp submitTs;
	private Timestamp receiveTs;
	private Timestamp lastModifiedTs;
	private int numRequested;
	private int numMatched;
	
	public VendorJob() {
		
	}
	
	public VendorJob(int vendorJobId, int vendorId, String appendType, String precision, Timestamp createdTs,
				String filename, Timestamp submitTs, Timestamp receiveTs, Timestamp lastModifiedTs, int numRequested, int numMatched) {
		this.vendorJobId = vendorJobId;
		this.vendorId = vendorId;
		this.appendType = appendType;
		this.precision = precision;
		this.createdTs = createdTs;
		this.filename = filename;
		this.submitTs = submitTs;
		this.receiveTs = receiveTs;
		this.lastModifiedTs = lastModifiedTs;
		this.numRequested = numRequested;
		this.numMatched = numMatched;
	}
	
	public int getVendorJobId() {
		return vendorJobId;
	}
	public void setVendorJobId(int vendorJobId) {
		this.vendorJobId = vendorJobId;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public Timestamp getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Timestamp getSubmitTs() {
		return submitTs;
	}
	public void setSubmitTs(Timestamp submitTs) {
		this.submitTs = submitTs;
	}
	public Timestamp getReceiveTs() {
		return receiveTs;
	}
	public void setReceiveTs(Timestamp receiveTs) {
		this.receiveTs = receiveTs;
	}
	public Timestamp getLastModifiedTs() {
		return lastModifiedTs;
	}
	public void setLastModifiedTs(Timestamp lastModifiedTs) {
		this.lastModifiedTs = lastModifiedTs;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getAppendType() {
		return appendType;
	}

	public void setAppendType(String appendType) {
		this.appendType = appendType;
	}

	@Override
	public String toString() {
		return "VendorJob [vendorJobId=" + vendorJobId + ", vendorId="
				+ vendorId + ", appendType=" + appendType + ", precision="
				+ precision + ", createdTs=" + createdTs + ", filename="
				+ filename + ", submitTs=" + submitTs + ", receiveTs="
				+ receiveTs + ", lastModifiedTs=" + lastModifiedTs
				+ ", numRequested=" + numRequested + ", numMatched="
				+ numMatched + "]";
	}

	public int getNumRequested() {
		return numRequested;
	}

	public void setNumRequested(int numRequested) {
		this.numRequested = numRequested;
	}

	public int getNumMatched() {
		return numMatched;
	}

	public void setNumMatched(int numMatched) {
		this.numMatched = numMatched;
	}
	
		

}
