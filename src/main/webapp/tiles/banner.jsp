<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!-- header -->
<div id="header_primary">
	<div class="header_content header_content_bg">
		<div class="header_top clearfix position_rel">
			<h1 id="df_logo" class="border_r">
				<a href="#">Demandforce</a>
			</h1>
			<h2 class="admin_name">Email Append Administration Portal</h2>
			<div class="nav_utility  clearfix">
			<span class="normal" id="welcome_span">Welcome </span>
				<div style="min-width:155px;display:inline;text-align:right;">
					<a href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a>
				</div>
			</div>
		</div>
		
		<ul class="nav_primary">
			<li class="nav_level_1 selected">
				<a href="${pageContext.request.contextPath}/welcome">Home</a>
			</li>
			<li class="nav_level_1 ">
				<a href="${pageContext.request.contextPath}/clientJobs">Client Jobs</a>
			</li>	
			<li class="nav_level_1 ">
				<a href="${pageContext.request.contextPath}/vendorJobs">Vendor Jobs</a>
			</li>
			<li class="nav_level_1 ">
				<a href="${pageContext.request.contextPath}/configuration">Configuration</a>
		<!-- 		<ul class="nav_level_2">
					<li>
						<a href="${pageContext.request.contextPath}/configuration">Global Configuration</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/vendorConfiguration">Vendor Configuration</a>
					</li>
				</ul> -->
			</li>
		</ul>		
	</div>	
</div>	
<!-- header end -->
