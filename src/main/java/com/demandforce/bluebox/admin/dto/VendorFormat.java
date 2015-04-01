package com.demandforce.bluebox.admin.dto;

public class VendorFormat {
	private int vendorFormatId;
	private int vendorId;
	private String appendType;
	private String direction;
	private String precision;
	private String fileFormat;
	private String fieldSeparator;
	private String recordSeparator;
	private String enclosedByChar;
	private String escapeChar;
	private String columnList;
	
	public VendorFormat() {
			
		}
	
	public VendorFormat(int vendorFormatId, int vendorId, String appendType, String direction, String precision,
			String fileFormat, String fieldSeparator, String recordSeparator, String enclosedByChar, String escapeChar, String columnList) {
		this.vendorFormatId = vendorFormatId;
		this.vendorId = vendorId;
		this.appendType = appendType;
		this.direction = direction;
		this.precision = precision;
		this.fileFormat = fileFormat;
		this.fieldSeparator = fieldSeparator;
		this.recordSeparator = recordSeparator;
		this.enclosedByChar = enclosedByChar;
		this.escapeChar = escapeChar;
		this.columnList = columnList;
	}

	public int getVendorFormatId() {
		return vendorFormatId;
	}

	public void setVendorFormatId(int vendorFormatId) {
		this.vendorFormatId = vendorFormatId;
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

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getFieldSeparator() {
		return fieldSeparator;
	}

	public void setFieldSeparator(String fieldSeparator) {
		this.fieldSeparator = fieldSeparator;
	}

	public String getRecordSeparator() {
		return recordSeparator;
	}

	public void setRecordSeparator(String recordSeparator) {
		this.recordSeparator = recordSeparator;
	}

	public String getEnclosedByChar() {
		return enclosedByChar;
	}

	public void setEnclosedByChar(String enclosedByChar) {
		this.enclosedByChar = enclosedByChar;
	}

	public String getEscapeChar() {
		return escapeChar;
	}

	public void setEscapeChar(String escapeChar) {
		this.escapeChar = escapeChar;
	}

	public String getColumnList() {
		return columnList;
	}

	public void setColumnList(String columnList) {
		this.columnList = columnList;
	}

	@Override
	public String toString() {
		return "VendorFormat [vendorFormatId=" + vendorFormatId + ", vendorId="
				+ vendorId + ", appendType=" + appendType + ", direction="
				+ direction + ", precision=" + precision + ", fileFormat="
				+ fileFormat + ", fieldSeparator=" + fieldSeparator
				+ ", recordSeparator=" + recordSeparator + ", enclosedByChar="
				+ enclosedByChar + ", escapeChar=" + escapeChar
				+ ", columnList=" + columnList + "]";
	}
	
	
}
