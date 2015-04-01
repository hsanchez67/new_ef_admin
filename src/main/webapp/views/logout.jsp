<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="main.template" >
    <tiles:putAttribute name="header" value="/tiles/headerOuter.jsp"></tiles:putAttribute>
    <tiles:putAttribute name="body" value="/tiles/logoutBody.jsp"></tiles:putAttribute>
    <tiles:putAttribute name="title" value="Logout"></tiles:putAttribute>    
</tiles:insertDefinition>