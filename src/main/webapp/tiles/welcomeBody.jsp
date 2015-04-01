<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">	
var start = "";
$(document).ready(function(){
		 var from_cal = Calendar.setup({
	        onSelect: function () {
	            this.hide();
	            calendarExpand();
	        },
	        onChange : function() {
	        	ajaxGetServiceDataForDate($("#startDate").val());
	        },
	        animation: false,
	        bottomBar: false,
	        max: getCurrentDate(),
	        trigger: "startDate",
	        inputField: "startDate",
	        dateFormat: "%o/%e/%Y"
	    });
	    
	    start = $("#startDate").val();
	    
	    $("#dataform .tableWrapper2").scroll(function() {
	    	if($(this).scrollTop()>20){
		      $(".addHeader").remove();
		  	  var dataHeader = $("#dataform table thead").html();
		  	  $("#dataform .tableWrapper2").prepend("<table class=\"data_tal zebra addHeader\">"+ dataHeader + "</table>");
		  	  $(".addHeader").width($(".tableWrapper2").width()-16);
	    	}else{
	    		$(".addHeader").remove();
	    	}
	  	});
	    
	    if($("#tableContent > tr").length != 0){
	    	$("#serviceData").show();
	    	$("#noServiceData").hide();
	    }else{
	    	$("#serviceData").hide();
	    	$("#noServiceData").show();
	    }  
	    	    
});	    

function getCurrentDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear().toString();
    var month = currentDate.getMonth() + 1;
    var date = currentDate.getDate();
    if (month < 10) month = ("0" + month).toString();
    if (date < 10) date = ("0" + date).toString();
    currentDate = parseInt(year + month + date);
    return currentDate;
}

function getCurrentDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear().toString();
    var month = currentDate.getMonth() + 1;
    var date = currentDate.getDate();
    if (month < 10) month = ("0" + month).toString();
    if (date < 10) date = ("0" + date).toString();
    currentDate = parseInt(year + month + date);
    return currentDate;
}
</script>
<!-- main content -->
<div id="main">
	<div class="main_wrap">
		<div class="top_nav">
			<h2 class="main_title" style="width:100%;margin-bottom:0;">Welcome to the Email Append Administration Portal!</h2>			
		</div>	
		<div class="main_content">
			<div class="area_sec">				
				<div class="forInput">
				<p class="font_16b m_t2 m_b1 color_add">Find by Job or Business</p>				  
					   <%@ include file="job_search_form.jsp"%>			   
				</div>
			</div>		
						
			<div class="p_body mar_tb1 position_rel" id="dataform">
				<p class="m_t2 m-b3">
					<em class="required">*</em>
					<span class="labTit">Service data by Job created date</span>
					<input id="startDate" class="input_text input_calendar" type="text" readonly="readonly" value="${start }" name="startDate" autocomplete="off">
				</p>							
			</div>	       		
			
	<div class="tabs_container position_rel " style="width:86%">
		<div id="noServiceData" class="form" id="findBusinessLanding">            
           <div class="legend_s forInput">
              No jobs on <span id="startDateHeader" class="high_keyword">${start }</span>    
           </div>
         </div>
		<div id="serviceData" class="form" id="findBusinessLanding">            
           <div class="legend_s forInput">
              On <span id="startDateHeader" class="high_keyword">${start }</span>: <span id="businessesHeader" class="high_keyword">${details.numOfBusiness}</span>
					Businesses exported <span id="requestedHeader" class="high_keyword">${details.numRequested}</span> Records 
					and got <span id="matchedHeader" class="high_keyword">${details.numMatched}</span>	Matches    
           </div>            
			<div class="p_body mar_tb1 position_rel" id="dataform">			
           		<p id="to_view" class="m_b6">To view the detail, please click the Business ID.</p>
           		<div class="tableWrapper2">           				
		           		<table class="data_tal zebra">
 		              		<thead>
		              			<tr>		              															              						              				
		              				<th width="45">Business ID</th>		              				
									<th width="45">Exported</th>	
									<th width="63">Created</th>
									<th width="63">Submitted</th>	              					
									<th class="middle" width="63">Status</th>		              					              				
		              				<th width="45">Matched</th>
		              				<th width="63">Completed</th>
		              				<th width="63">Status</th>		              						              						              						              				
		              			</tr>
		          			</thead>
		          			<tbody id="tableContent">				          			          			          				          			
		          			</tbody>
		           		</table>
		           		</div>
		           		
		</div>
	</div>
    </div> <!-- tabs_container end -->
			
			
				
		</div>
	</div>
</div>
<!-- main content end -->


