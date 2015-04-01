<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Locale"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
Date now = new Date();
SimpleDateFormat sFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US); 
String curDateTime=sFormat.format(now);
	
int year = Calendar.getInstance().get(Calendar.YEAR);

%>

<!DOCTYPE HTML>
<html style="background-color:#59728e">
<head>
    <title>Demandforce Email Finder Admin Portal</title>
    <link rel="icon" href="${pageContext.request.contextPath}/css/images/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/layout.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    
$(document).ready(function(){
	
    var re_email = /^(.+)@(.+)$/;
    
    function is_empty(s) {   
    	return ((s == null) || (s.length == 0));
    }
    
    function check_username(me){
        var err_msg = "<div class='valid_err'>Please enter a valid email address.</div>";
        var s=me.value;
        if( is_empty(s) || !re_email.test(s) ){//if is empty or not a valid email
            if($(me).siblings('.valid_err').size() == 0)
                $(me).parent('.row_wrap').append(err_msg);              
            $(me).addClass('input_valid_err').siblings('.valid_err').show();
            return false;
        }else{
        	 $(me).removeClass('input_valid_err').siblings('.valid_err').hide();
        	return true;
        }
    	
    }
    
    $('#j_username').blur(function(){
    	check_username(this);   
    });
    $('#j_password').blur(function(){
    	check_password(this);   
    });

    function check_password(me){
        var err_msg = "<div class='valid_err'>Please enter your password.</div>";
        var s=me.value;
        if( is_empty(s) ){//if the password is empty
            if($(me).siblings('.valid_err').size() == 0)
                $(me).parent('.row_wrap').append(err_msg);              
            $(me).addClass('input_valid_err').siblings('.valid_err').show();
           return false;
        }else{
            $(me).removeClass('input_valid_err').siblings('.valid_err').hide();
            return true;
        }
    }

    /*$('#j_password').blur(function(){
    	check_password(this);
    });*/

    $('[name=loginform]').submit(function(){
        var pass_username = check_username(document.getElementById("j_username"));
        var pass_password = check_password(document.getElementById("j_password"));
        return (pass_username && pass_password);
    });
});    

    </script>
</head>

<body class="login_page">
<!--[if lt IE 7]> <div id="wrapper" class="lt-ie9 lt-ie8 lt-ie7"><![endif]-->
<!--[if IE 7]>    <div id="wrapper" class="lt-ie9 lt-ie8"><![endif]-->
<!--[if IE 8]>    <div id="wrapper" class="lt-ie9"><![endif]-->
<!--[if gt IE 8]><!--><div id="wrapper"><!--<![endif]-->    

<!-- main content -->
<div class="main_wrap">
    <div class="status_wrap">
        <div class="login_logo">demandforce <hr class="line_s1" />
    </div>
    <form class="form gradient_lightgrey login_box" name="loginform" action="${pageContext.request.contextPath}/j_spring_security_check" method="post" >
        <p class="legend gradient_lightgrey">Email Finder Admin Login</p>
        <fieldset>        	
	        <c:if test="${!empty(SPRING_SECURITY_LAST_EXCEPTION) }">	        
	        <div class="row_wrap">
	            <div class="valid_err">Your login attempt has failed. The username or <br/>password may be incorrect.</div>
	        </div>
	        </c:if>
            <div class="row_wrap">
                <label for="j_username">Username</label>
                <input class="input_text" type="text" name="j_username" id="j_username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" title="Enter your email address" placeholder="Enter your email address" />
            </div>
            <div class="row_wrap">
                <label for="j_password">Password</label>
                <input class="input_text"  type="password" name="j_password" id="j_password"/>
            </div>
            <div class="row_submit_wrap">
           <input type="submit" value="Login" class="button"> <a href="${pageContext.request.contextPath}/forgotPassword" class="u_line hide" style="vertical-align:middle;margin-left:15px;">Forgot password?</a>
            </div>
            <div class="row_submit_wrap">Can&rsquo;t log in?  <a href="mailto:SBMS-DFAdminToolsCN@intuit.com" class="u_line">Contact us</a>.</div>
        </fieldset>
    </form>
    <div class="login_hint">
        <hr class="line_s1" /> 
        <p class="mar_tb1 font_11">This site contains information which may be confidential and/or privileged. Unless you are the intended recipient (or authorized to receive for the intended recipient), you may not login, access, read, use, copy or disclose to anyone the site or any information contained in the message. If you have entered this site in error, please advise the Demandforce, inc (<a href="http://www.demandforce.com" class="u_line" target="_blank">www.demandforce.com</a>) and delete the document and any attachment(s) thereto without retaining any copies. Any site access will be punishable to the full extent of the law.</p>
        <hr class="line_s1" />
        
        <p class="footer m_t1 font_11">
            <span><%=curDateTime%></span> | 
            <a href="http://www.demandforce.com/terms-conditions" class="u_line">Terms &amp; Conditions</a> | 
            <a href="http://www.demandforce.com/privacy-policy/" target="_blank" class="u_line">Privacy Policy</a> | 
            <span>Phone: 1.800.246.9853</span><br />
            &copy; 2003 - <%=year%> Demandforce, Inc. All Rights Reserved.
        </p>
    </div>
</div>
<!-- main content end-->
</div>
</body>
</html>