package com.demandforce.bluebox.admin.dto;

import java.sql.Timestamp;

public class ClientJob {
	private int clientJobId;
	private int clientId;
	private String clientName;
	private int businessId;
	private Timestamp createdTs;	
	private Timestamp submitTs;	
	private Timestamp lastModifiedTs;
	private int numRequested;
	private int numMatched;
	private Timestamp appendCompletedTs;
	
	public ClientJob() {
		
	}
	
	public ClientJob(int clientJobId, int clientId, String clientName, int businessId, Timestamp createdTs,
				Timestamp submitTs, Timestamp lastModifiedTs, int numRequested, int numMatched, Timestamp appendCompletedTs) {
		this.clientJobId = clientJobId;		
		this.clientId = clientId;
		this.clientName = clientName;
		this.businessId = businessId;		
		this.createdTs = createdTs;		
		this.submitTs = submitTs;		
		this.lastModifiedTs = lastModifiedTs;
		this.numRequested = numRequested;
		this.numMatched = numMatched;
		this.appendCompletedTs = appendCompletedTs;
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
	
	public Timestamp getLastModifiedTs() {
		return lastModifiedTs;
	}
	public void setLastModifiedTs(Timestamp lastModifiedTs) {
		this.lastModifiedTs = lastModifiedTs;
	}

	public int getClientJobId() {
		return clientJobId;
	}

	public void setClientJobId(int clientJobId) {
		this.clientJobId = clientJobId;
	}

	public int getBusinessId() {
		return businessId;
	}

	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}
	
	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Override
	public String toString() {
		return "ClientJob [clientJobId=" + clientJobId + ", clientId="
				+ clientId + ", clientName=" + clientName + ", businessId="
				+ businessId + ", createdTs=" + createdTs + ", submitTs="
				+ submitTs + ", lastModifiedTs=" + lastModifiedTs
				+ ", numRequested=" + numRequested + ", numMatched="
				+ numMatched + ", appendCompletedTs=" + appendCompletedTs + "]";
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

	public Timestamp getAppendCompletedTs() {
		return appendCompletedTs;
	}

	public void setAppendCompletedTs(Timestamp appendCompletedTs) {
		this.appendCompletedTs = appendCompletedTs;
	}
		
		

}
