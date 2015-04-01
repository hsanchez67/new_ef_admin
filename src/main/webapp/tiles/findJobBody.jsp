<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/include/include.jsp"%>
<%@ page isELIgnored="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/searchbusiness.jsp"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>
<script type="text/javascript">
$(document).ready(function() {	    
    init();
    
    if($("#tableContent > tr").length != 0){
    	$("#to_view, #pagination, hr.s2").show();
    }else{
    	$("#to_view, #pagination, hr.s2").hide();
    	$("#no_data").show();
    }    
	showPagination();
	
    $("table.data_tal td a").live('click',function(){
        $("table.data_tal tr th").eq(2).nextAll().css('display',"none");
        $("table.data_tal tr th").eq(2).addClass("last");
        $("table.data_tal tr").each(function(){
            $(this).children().eq(2).nextAll().css('display',"none");
            $(this).children().eq(2).addClass("last");
        });
        $(".addHeader").remove();
        $(".tableWrapper").css({"overflow":"scroll", "width":"44%", "min-height":"540px"});
        $(".moreInfo").slideDown("slow");
        $(".moreInfo dd").each(function(){
        	    $(this).html("");
         });        

        ajaxGetDetail($(this).parents("tr").find("td").eq(0).text() // jid        		
        );

        return false
    });
    $(".moreInfo .close").live('click',function(){
    	hideMoreInfo();
    });
    
    $(".moreInfo2 .close").live('click',function(){
    	showMoreInfo();
    });
            
    
    $("table.data_tal2 td a").live('click',function(){                       
    	hideMoreInfo2();
    	 $(".moreInfo2").slideDown("slow");
         $(".moreInfo2 dd").each(function(){
         	    $(this).html("");
          });
        ajaxGetDetail2($(this).parents("tr").find("td").eq(0).text() // consumerid        		
        );

        return false
    });
    
    $("#dataform .tableWrapper").scroll(function() {
    	if($(this).scrollTop()>20){
	      $(".addHeader").remove();
	  	  var dataHeader = $("#dataform table thead").html();
	  	  $("#dataform .tableWrapper").prepend("<table class=\"data_tal zebra addHeader\">"+ dataHeader + "</table>");
	  	  $(".addHeader").width($(".tableWrapper").width()-16);
    	}else{
    		$(".addHeader").remove();
    	}
  	});
    
    $("#dataformDetail .tableWrapperDetail").scroll(function() {    	
    	if($(this).scrollTop()>20){
	      $(".addHeader").remove();
	  	  var dataHeader = $("#dataformDetail table thead").html();
	  	  $("#dataformDetail .tableWrapperDetail").prepend("<table class=\"data_tal2 zebra addHeader\">"+ dataHeader + "</table>");
	  	  $(".addHeader").width($(".tableWrapperDetail").width()-16);
    	}else{
    		$(".addHeader").remove();
    	}
  	});
});    
function init(){
	var jobId = '${jsc.jobID}';
	var businessIdj = '${jsc.businessID}';
	$("#jid").val(jobId);
	$("#bid").val(businessIdj);
	$("#jobTypeList").val('${jsc.jobType}');
	$("#filterForm input[id='filter_jid']").val(jobId);    	
	$("#filterForm input[id='filter_bid']").val(businessIdj);    	
	$("#filterForm input[id='filter_type']").val('${jsc.jobType}');
	if(jobId != "") {
		$("#jid").removeClass("textLighter").css("background","#fff");
	}
	if(businessIdj != "") {
		$("#bid").removeClass("textLighter").css("background","#fff");
	}	
		
}

    function exportResult(){    	    
        document.getElementById("filterForm").action="${ctx}/search/export";
        document.getElementById("filterForm").submit();
    }  

    function saveCondition(){
        var original=$("#conditionForm").serializeArray();
        $(original).each(function(){
            $("#filterForm input[name='"+ this.name+"']").val($.trim(this.value));
        })
    }
</script>
<div id="main">
<div class="main_wrap">
	<div class="top_nav m_t0">
		<h2 class="main_title">Search</h2>
	</div>
	<div class="tabs_container position_rel">
		<div class="form" id="findBusinessLanding">
            <form action="/business/search/export" id="conditionForm">
                <div class="legend_s forInput">
                    <%@ include file="job_search_form.jsp" %>
                </div>
            </form>
			<div class="p_body mar_tb1 position_rel" id="dataform">
				<p id="no_data" class="m_b6 hide">No Jobs found. Please try your search again.</p>
           		
           		<p id="to_view" class="m_b6">To view more information, please click the Job ID.</p>
           		<div class="tableWrapper">
           				<input type="button" class="button button_default btn-mini rightSideBtn" value="Export to CSV" onclick="exportResult()"/>
           				<c:choose>
           				<c:when test="${jsc.jobType == 'vendor'}">
		           		<table class="data_tal zebra">
 		              		<thead>
		              			<tr>
		              				<th width="65">Job ID</th>		              				
		              				<th width="70">Vendor</th>
		              				<th width="45">Precision</th>
		              				<th width="80">Created</th>
		              				<th width="120">Filename</th>
		              				<th width="40">Submitted</th>
		              				<th width="40">Received</th>
		              				<th width="63">Last Modified</th>	
		              			</tr>
		          			</thead>
		          			<tbody id="tableContent">		          		
		          			<c:forEach var="job" items="${jobs}" varStatus="status">
		          				<tr>
		          					<td>${job.vendorJobId}</td>
		          					<td>${job.vendorId}</td>
		          					<td><c:choose><c:when test="${job.precision == 'C'}">Combined</c:when><c:otherwise>Individual</c:otherwise></c:choose></td>
		          					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${job.createdTs}" /></td>
		          					<td>${job.filename}</td>
		          					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${job.submitTs}" /></td>
		          					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${job.receiveTs}" /></td>
		          					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${job.lastModifiedTs}" /></td>
		          				</tr>
		          			</c:forEach>
		          			</tbody>
		           		</table>
		           		</c:when>
		           		<c:otherwise>
						<table class="data_tal zebra">
 		              		<thead>
		              			<tr>
		              				<th width="35">Job ID</th>
									<th width="55">Client</th>		              						              					
		              				<th width="45">Business ID</th>
		              				<th width="63">Created</th>		              				
		              				<th width="63">Submitted</th>		              				
		              				<th width="63">Last Modified</th>
		              				<th width="20">Requested</th>
		              				<th width="20">Matched</th>
		              				<th width="63">Completed</th>
		              						              				
		              			</tr>
		          			</thead>
		          			<tbody id="tableContent">				          			          	
		          			<c:forEach var="job" items="${jobs}" varStatus="status">
		          				<tr>
		          					<td><a href="javascript:void(0)">${job.clientJobId}</a></td>
		          					<td>${job.clientName}</td>
		          					<td>${job.businessId}</td>		          					
		          					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${job.createdTs}" /></td>
		          					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${job.submitTs}" /></td>
		          					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${job.lastModifiedTs}" /></td>
		          					<td>${job.numRequested}</td>
		          					<td>${job.numMatched}</td>
		          					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${job.appendCompletedTs}" /></td>
		          				</tr>
		          			</c:forEach>
		          			</tbody>
		           		</table>		           			
		           		</c:otherwise>
		           		</c:choose>
		           		</div>
		           		<input type="hidden" name="pageSize" id="pageSize" value="25"/>
						<input type="hidden" name="currentCount" id="currentCount" value="${currentCount}"/>
						<input type="hidden" name="totalCount" id="totalCount" value="${totalCount}"/>
						<input type="hidden" name="currentPage" id="currentPage" value="1"/>

						<div id="pagination" class="pagination"></div>

						<input type="hidden" name="nextPage" value="1" id="nextPage">
						<hr class="s1 s2" />
						
						<!-- more info -->
							<div id="moreInfo" class="moreInfo hide">
								<span class="close floatR" style=""></span>
								<div class="title_1s legend">Client Job <span id="jobid_detail"></span> Detail</div>								
								<div class="p_body mar_tb1 position_rel" id="dataformDetail">
								<p class="detail_no_data hide">No Detail found. Please try your search again.</p>
								
				           		<p id="detail_to_view" class="m_b6">To view the client detail, please click the Consumer ID.</p>
				           		<div class="tableWrapperDetail">				           								           		
						           		<table class="data_tal2 zebra">
				 		              		<thead>
						              			<tr>
						              				<th width="35">Consumer ID</th>		              				
						              				<th width="45">FName </th>
						              				<th width="45">LName </th>		              										              						              			
						              				<th width="63">Email</th>
						              				<th width="45">Type</th>
						              				<th width="63">Received</th>		              				
						              			</tr>
						          			</thead>
											<tbody id="tableContentDetail">
											</tbody>
										</table>
									</div>
									<input type="hidden" name="pageSize_detail" id="pageSize_detail" value="25"/>
									<input type="hidden" name="currentCount_detail" id="currentCount_detail" value="0"/>
									<input type="hidden" name="totalCount_detail" id="totalCount_detail" value="0"/>
									<input type="hidden" name="currentPage_detail" id="currentPage_detail" value="1"/>
									<input type="hidden" name="currentJobId" id="currentJobId" value=""/>
												
			
									<input type="hidden" name="nextPage_detail" value="1" id="nextPage_detail">
																															
							</div>
							<div id="pagination_detail" class="pagination"></div>
							<hr class="s1 s2" />																	
        			  </div>
        			  <!-- more info end-->
        			  <!-- more info -->
					  <div class="moreInfo2 hide">
						  	<span class="close floatR" style=""></span>
							<dl><dt class="w1">Consumer ID</dt><dd class="w2" id="detail_consumerId"></dd><dt class="w2">Job ID</dt><dd id="detail_jobid"></dd></dl>
							<dl><dt class="w1">First name</dt><dd id="detail_fname" class="w2"></dd><dt class="w2">Last Name</dt><dd id="detail_lname"></dd></dl>														
							<dl><dt class="w1">Address</dt><dd class="w2" id="detail_address"></dd><dt class="w2">City</dt><dd id="detail_city"></dd></dl>
							<dl><dt class="w1">State</dt><dd class="w2" id="detail_state"></dd><dt class="w2">Zip</dt><dd id="detail_zip"></dd></dl>									
							<hr class="s1 mar_tb2">							
							<dl><dt class="w1">Append Type</dt><dd class="w2" id="detail_appendType"></dd><dt class="w2">Precision Req.</dt><dd id="detail_precisionReq"></dd></dl>
							<dl><dt class="w1">Vendor Job ID</dt><dd class="w2" id="detail_vendorJobId"></dd><dt class="w2">Precision Res.</dt><dd id="detail_precisionRes"></dd></dl>
							<dl><dt class="w1">Appended Email</dt><dd class="w2" id="detail_appendedEmail"></dd></dl>																						
							<hr class="s1 mar_tb2">							
							<dl><dt class="w1">Created Ts.</dt><dd class="w2" id="detail_createdTs"></dd><dt class="w2">Submit Ts.</dt><dd id="detail_submitTs"></dd></dl>
							<dl><dt class="w1">Receive Ts.</dt><dd class="w2" id="detail_receiveTs"></dd><dt class="w1">Download Ts.</dt><dd id="detail_downloadTs"></dd></dl>
							<dl><dt class="w1">LasatModifiedTs</dt><dd class="w2" id="detail_lastModifiedTs"></dd></dl>
						</div>
						<!-- more info end-->
						
						
          </div>
		</div>

    </div> <!-- tabs_container end -->
</div> <!-- main wrap end -->
</div>
<div style="display: none">
    <form id="filterForm">
        <input type="hidden" id="filter_jid" name="jid">
        <input type="hidden" id="filter_type" name="jobType">
        <input type="hidden" id="filter_bid" name="bid">   
    </form>
</div>