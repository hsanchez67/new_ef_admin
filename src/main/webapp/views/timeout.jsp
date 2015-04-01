<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="main.template" >
    <tiles:putAttribute name="header" value="/tiles/headerOuter.jsp"></tiles:putAttribute>
    <tiles:putAttribute name="body" value="/tiles/timeoutBody.jsp"></tiles:putAttribute>
    <tiles:putAttribute name="title" value="Time Out"></tiles:putAttribute>    
</tiles:insertDefinition>