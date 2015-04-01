<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include/include.jsp"%>
<%@ page isELIgnored="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<p class="breadcrumbs">
    <a href="${ctx}${_topNode.url}"> ${_topNode.name}</a> ${empty _currentNode.parentNode?"":"/"} ${_currentNode.parentNode.name}
</p>
<div class="top_nav">
    <h2 class="main_title">${mainTitle}</h2>
</div>