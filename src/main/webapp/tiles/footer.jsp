<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Locale"%>

<%
Date now = new Date();
SimpleDateFormat sFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US); 
String curDateTime=sFormat.format(now);
	
int year = Calendar.getInstance().get(Calendar.YEAR);

%>

<!-- footer -->
<div id="footer">
    <p>
        <span><%=curDateTime%></span> | 
        <a href="javascript:pop('http://www.demandforce.com/terms-conditions/',640, 480, 1);">Terms &amp; Conditions</a> | 
        <a href="http://www.demandforce.com/privacy.shtml" target="_blank">Privacy Policy</a> | 
        <span>Phone: 1.800.246.9853</span>
    </p>
    <p>&copy; 2003 - <%=year%> Demandforce, Inc. All Rights Reserved.</p>
    
</div>
<!-- footer end -->