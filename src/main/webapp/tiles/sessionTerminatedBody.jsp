<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>

<!-- main content -->
<div id="main">
    <div class="main_wrap">
        <div class="status_wrap">
             <div class="status_icon timeout_icon">Session Terminated</div>
             <div class="status_text">
                    <h3>Your current session has been terminated because this account is being logged in on another computer.</h3>
                    
                    <p>Please click <a href="${pageContext.request.contextPath}/login">here</a> to try to login again.</p>
                    <p>Or <a href="mailto:SBMS-DFAdminToolsCN@intuit.com" class="u_line">Contact us</a> if this should not happen. </p>
             </div>
        </div>
    </div>
</div>
