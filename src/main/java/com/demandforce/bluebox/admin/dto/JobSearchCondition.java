package com.demandforce.bluebox.admin.dto;

public class JobSearchCondition {
	
	 	String jobID;
	 	String businessID;
	 	String jobType;
	    int pageSize, startNum;
	    
		public String getJobID() {
			return jobID;
		}
		public void setJobID(String jobID) {
			this.jobID = jobID;
		}
		public int getPageSize() {
			return pageSize;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		public int getStartNum() {
			return startNum;
		}
		public void setStartNum(int startNum) {
			this.startNum = startNum;
		}
		public String getJobType() {
			return jobType;
		}
		public void setJobType(String jobType) {
			this.jobType = jobType;
		}
		public String getBusinessID() {
			return businessID;
		}
		public void setBusinessID(String businessID) {
			this.businessID = businessID;
		}

	    
}
