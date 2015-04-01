<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="main.template">
	<tiles:putAttribute name="title" value="Find Job"></tiles:putAttribute>
    <tiles:putAttribute name="body" value="/tiles/findJobBody.jsp"></tiles:putAttribute>
</tiles:insertDefinition>
