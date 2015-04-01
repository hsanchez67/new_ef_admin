<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title><tiles:insertAttribute name="title" /></title>
    <link rel="icon" href="${pageContext.request.contextPath}/css/images/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/layout.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jscal2.css" />    
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/application.js"></script>        
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>    
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jscal2.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lang/en.js"></script>       
</head>

<body>
<!--[if lt IE 7]> <div id="wrapper" class="lt-ie9 lt-ie8 lt-ie7"><![endif]-->
<!--[if IE 7]>    <div id="wrapper" class="lt-ie9 lt-ie8"><![endif]-->
<!--[if IE 8]>    <div id="wrapper" class="lt-ie9"><![endif]-->
<!--[if gt IE 8]><!--><div id="wrapper" ><!--<![endif]-->   

	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="footer" />

</div>
</body>
<script type="text/javascript">
	var isIE =navigator.userAgent.toLowerCase().search(/(msie\s|trident.*rv:)([\w.]+)/)!=-1;
	if (!isIE) {
        var underscore = document.createElement("SCRIPT"),
        backbone = document.createElement("SCRIPT");
        underscore.setAttribute('src', "${pageContext.request.contextPath}/js/libs/underscore-min.js");
        backbone.setAttribute('src', "${pageContext.request.contextPath}/js/libs/backbone-min.js");
        document.body.appendChild(underscore);
        document.body.appendChild(backbone);
}
</script>
</html>
