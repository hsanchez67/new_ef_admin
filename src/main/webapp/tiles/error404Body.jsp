<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>

<!-- main content -->
<style>
.status_text{padding-top:0;}
ul li{list-style:inside;padding-left:15px;margin-top:0;}
li{line-height:22px;}
</style>
<div id="main">
    <div class="main_wrap">
        <div class="status_wrap">
             <div class="status_icon icon_404">404 error</div>
             <div class="status_text">
                    <h3>Wait, why am I here?</h3>
                    <p>Welcome to a 404 page. You are here because the page you have requested doesn't exist, at least not at this location.</p>
                    <h3 style="margin-top:15px;margin-bottom:8px;">Perhaps you could ... </h3>
                    <ul>
                    	<li>start at the <a href="${pageContext.request.contextPath}/welcome">home page</a>,</li>
                    	<li>return to the <a href="javascript:void(0)" onclick="window.history.back()">previous page</a>,</li>
                    	<li>or send  <a href="maito:SBMS-DFAdminToolsCN@intuit.com">Admin Tools Team</a> a note.</li>
                    </ul>
             </div>
        </div>
    </div>
</div>
<!-- main content end -->
