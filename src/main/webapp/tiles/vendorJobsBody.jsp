<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/searchvendor.jsp"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#to_view, #pagination, hr.s2").hide();
    $("#dataform > div.tableWrapper").hide();
    
    if($("#tableContent > tr").length != 0){
    	$("#to_view, #pagination, hr.s2").show();
    	$("#dataform > div.tableWrapper").show();
    }else{
    	$("#to_view, #pagination, hr.s2").hide();
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
});

    function exportResult(){
    	$("#filterForm input[id='filter_jid']").val($("#conditionForm input[id='jid']").val());    	    	
    	$("#filterForm input[id='filter_type']").val($("#conditionForm input[id='jobType']").val());
        document.getElementById("filterForm").action="${ctx}/search/export";
        document.getElementById("filterForm").submit();
    }

    function ifShowMore(condition){
        if(condition=="true"){
            $(".showHideMore_fb").parents().addClass("arrowRotate");
            $(".showHideMore_fb").css("display","inline");
        }else{
            $(".showHideMore_fb").parents().removeClass("arrowRotate");
            $(".showHideMore_fb").hide();
        }
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
		<h2 class="main_title">Vendor Jobs</h2>
	</div>
	<div class="tabs_container position_rel">
		<div class="form" id="findBusinessLanding">
            <form action="/business/search/export" id="conditionForm">
                <div class="legend_s forInput">
                    <%@ include file="vendor_search_form.jsp" %>
                </div>
            </form>
			<div class="p_body mar_tb1 position_rel" id="dataform">
				<p class="no_data hide">No Jobs found. Please try your search again.</p>

           		<p id="to_view" class="m_b6">To view more information, please click the Job ID.</p>
           		<div class="tableWrapper">
           				<input type="button" class="button button_default btn-mini rightSideBtn" value="Export to CSV" onclick="exportResult()"/>
		           		<table class="data_tal zebra">
 		              		<thead>
		              			<tr>
		              				<th width="45">Job ID</th>		              				
		              				<th width="44">Vendor</th>
		              				<th width="45">Precision</th>
		              				<th width="80">Created</th>
		              				<th width="110">Filename</th>
		              				<th width="80">Submitted</th>
		              				<th width="80">Received</th>
		              				<th width="80">Last Modified</th>
		              				<th width="30">Requested</th>
		              				<th width="30">Matched</th>		              				
		              			</tr>
		          			</thead>
		          			<tbody id="tableContent">		          		
		          			<c:forEach var="job" items="${vendorJobs}" varStatus="status">
		          				<tr>
		          					<td><a href="javascript:void(0)">${job.vendorJobId}</a></td>		          					
		          					<td>${job.vendorId}</td>
		          					<td><c:choose><c:when test="${job.precision == 'C'}">Combined</c:when><c:otherwise>Individual</c:otherwise></c:choose></td>
		          					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${job.createdTs}" /></td>
		          					<td>${job.filename}</td>
		          					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${job.submitTs}" /></td>
		          					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${job.receiveTs}" /></td>
		          					<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${job.lastModifiedTs}" /></td>
		          					<td>${job.numRequested}</td>
		          					<td>${job.numMatched}</td>
		          				</tr>
		          			</c:forEach>
		          			</tbody>
		           		</table>
		           		</div>
		           		<input type="hidden" name="pageSize" id="pageSize" value="25"/>
						<input type="hidden" name="currentCount" id="currentCount" value="${currentCount}"/>
						<input type="hidden" name="totalCount" id="totalCount" value="${totalCount}"/>
						<input type="hidden" name="currentPage" id="currentPage" value="${currentPage}"/>

						<div id="pagination" class="pagination"></div>

						<input type="hidden" name="nextPage" value="1" id="nextPage">
						<hr class="s1 s2" />

						<!-- more info -->
							<div class="moreInfo hide">
								<span class="close floatR" style=""></span>
								<dl><dt class="w1">Job Id</dt><dd class="w2" id="detail_jobId"></dd><dt class="w2">Vendor Id</dt><dd id="detail_vendorId"></dl>
								<dl><dt class="w1">Append Type</dt><dd id="detail_appendType" class="w2"></dd><dt class="w2">Precision</dt><dd id="detail_precision"></dd></dl>
								<dl><dt class="w1">Created Ts.</dt><dd id="detail_createdTs" class="w2"></dd></dl>
								<dl><dt class="w1">Filename</dt><dd class="w2" id="detail_filename"></dd></dl>								
								

								<hr class="s1 mar_tb2">
								
								<dl><dt class="w1">Submit Ts.</dt><dd id="detail_submitTs" class="w2"></dd><dt class="w2">Receive Ts.</dt><dd id="detail_ReceiveTs"></dd></dl>								
								<dl><dt class="w1">Last Modified Date</dt><dd id="detail_lastModifiedTs" class="w2"></dd></dl>															

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
    </form>
</div>