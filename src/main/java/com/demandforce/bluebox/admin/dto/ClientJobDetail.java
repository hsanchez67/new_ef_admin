package com.demandforce.bluebox.admin.dto;

import java.sql.Timestamp;

public class ClientJobDetail {
	private int clientJobDetailId;
	private int clientJobId;
	private int consumerId;
	private String appendTypes;
	private String fname;
	private String lname;
	private String address1;
	private String city;
	private String state;
	private String zip;
	private String precisionReq;
	private int vendorJobId;
	private String precisionRes;
	private String appendedEmail;
	private Timestamp createdTs;	
	private Timestamp submitTs;	
	private Timestamp receiveTs;
	private Timestamp downloadTs;
	private Timestamp lastModifiedTs;
	public ClientJobDetail(int int1, int int2, int int3, String string,
			String string2, String string3, String string4, String string5,
			String string6, String string7, String string8, int int4, String string9, String string10,
			Timestamp timestamp, Timestamp timestamp2, Timestamp timestamp3,
			Timestamp timestamp4, Timestamp timestamp5) {
			this.clientJobDetailId = int1;
			this.clientJobId = int2;
			this.consumerId = int3;
			this.appendTypes = string;
			this.fname = string2;
			this.lname = string3;
			this.address1 = string4;
			this.city = string5;
			this.state = string6;
			this.zip = string7;
			this.precisionReq = string8;
			this.vendorJobId = int4;
			this.precisionRes = string9;
			this.appendedEmail = string10;			
			this.createdTs = timestamp;
			this.submitTs = timestamp2;
			this.receiveTs = timestamp3;
			this.downloadTs = timestamp4;
			this.lastModifiedTs = timestamp5;
	}
	
	public int getClientJobDetailId() {
		return clientJobDetailId;
	}
	public void setClientJobDetailId(int clientJobDetailId) {
		this.clientJobDetailId = clientJobDetailId;
	}
	public int getClientJobId() {
		return clientJobId;
	}
	public void setClientJobId(int clientJobId) {
		this.clientJobId = clientJobId;
	}
	public int getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(int consumerId) {
		this.consumerId = consumerId;
	}
	public String getAppendTypes() {
		return appendTypes;
	}
	public void setAppendTypes(String appendTypes) {
		this.appendTypes = appendTypes;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPrecisionReq() {
		return precisionReq;
	}
	public void setPrecisionReq(String precisionReq) {
		this.precisionReq = precisionReq;
	}
	public String getAppendedEmail() {
		return appendedEmail;
	}
	public void setAppendedEmail(String appendedEmail) {
		this.appendedEmail = appendedEmail;
	}
	public Timestamp getCreatedTs() {
		return createdTs;
	}
	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
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
	public Timestamp getDownloadTs() {
		return downloadTs;
	}
	public void setDownloadTs(Timestamp downloadTs) {
		this.downloadTs = downloadTs;
	}
	public Timestamp getLastModifiedTs() {
		return lastModifiedTs;
	}
	public void setLastModifiedTs(Timestamp lastModifiedTs) {
		this.lastModifiedTs = lastModifiedTs;
	}
	public int getVendorJobId() {
		return vendorJobId;
	}

	public void setVendorJobId(int vendorJobId) {
		this.vendorJobId = vendorJobId;
	}

	public String getPrecisionRes() {
		return precisionRes;
	}

	public void setPrecisionRes(String precisionRes) {
		this.precisionRes = precisionRes;
	}

	@Override
	public String toString() {
		return "ClientJobDetail [clientJobDetailId=" + clientJobDetailId
				+ ", clientJobId=" + clientJobId + ", consumerId=" + consumerId
				+ ", appendTypes=" + appendTypes + ", fname=" + fname
				+ ", lname=" + lname + ", address1=" + address1 + ", city="
				+ city + ", state=" + state + ", zip=" + zip
				+ ", precisionReq=" + precisionReq + ", vendorJobId="
				+ vendorJobId + ", precisionRes=" + precisionRes
				+ ", appendedEmail=" + appendedEmail + ", createdTs="
				+ createdTs + ", submitTs=" + submitTs + ", receiveTs="
				+ receiveTs + ", downloadTs=" + downloadTs
				+ ", lastModifiedTs=" + lastModifiedTs + "]";
	}
	
		
}
