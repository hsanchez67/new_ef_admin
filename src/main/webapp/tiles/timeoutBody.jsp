<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>

<!-- main content -->
<div id="main">
    <div class="main_wrap">
        <div class="status_wrap">
             <div class="status_icon timeout_icon">time out</div>
             <div class="status_text">
                    <h3>Your session has timed out.</h3>
                    <p>Your session has timed out and you have been successfully logged off. </p>
                    <p>Please click <a href="${pageContext.request.contextPath}/login">here</a> to try to login again.</p>
             </div>
        </div>
    </div>
</div>
