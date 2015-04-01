<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="main.template">
	<tiles:putAttribute name="title" value="Vendor Configuration"></tiles:putAttribute>
    <tiles:putAttribute name="body" value="/tiles/vendorConfigurationBody.jsp"></tiles:putAttribute>    
</tiles:insertDefinition>