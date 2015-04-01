<%@ page contentType='application/javascript' %>


function hideMoreInfo(){
	$(".moreInfo").slideUp("fast");
    $(".tableWrapper").css('height',"").css('overflow',"").css('width',"").css('min-height',"");
    $("table.data_tal tr th, table.data_tal tr td").show();
    $("table.data_tal tr td:nth-child(3), table.data_tal tr th:nth-child(3)").removeClass("last");
    $(".addHeader").remove();
}

function hideMoreInfo2(){
	$(".moreInfo").slideUp("fast");            
}

function showMoreInfo(){
	$(".moreInfo2").slideUp("fast");
	$(".moreInfo").slideDown("slow");
}


function validateSearch(){	
	$("input.ghost_text").each(function(){ 
    	if($(this).val() == $(this).attr("title")){
				$(this).val("");
    	}
	});
	var url = window.location.href;
	var pageText = url.indexOf("welcome");	
	hideMoreInfo();
	$("#to_view, #pagination, hr.s2").hide();
	if (validateForm()){
	    if (pageText != -1){	    	
	        goSearch();
	    }else{
          /*  saveCondition();
	    	ajaxSearch('search');
	    	$("#nextPage").attr("value", '1');
	    	showPagination(); */
	    	goSearch();
	    }
	}
}

function validateForm(){    
	var a = validateJobID();	
	var b = validateBusinessID();		
	d = (a || b);
	if (!(a || b)) {
		alert("Please input a valid Job or Business Id!");		
	} 		
	return d;
}

function validateJobID(){		
	if (isEmpty($("#jid")) || !isNumber($.trim($("#jid").val()))){
	//	alert("Please input a valid Job Id!");
		return false;
	}
	return true;
}

function validateJobType(){   
	if (isEmpty($("#jobTypeList"))){
	//	alert("Please select a Job Type!");
		return false;
	}
	return true;
}

function validateBusinessID(){
	if (isEmpty($("#bid")) || !isNumber($.trim($("#bid").val()))){
	//	alert("Please input a valid Business Id!");
		return false;
	}
	return true;
}

function validatePhone(){
	if (!isEmpty($("#phone")) && !is_phone($.trim($("#phone").val()))){
		alert("Please input a valid Main Phone!");
		return false;
	}
	return true;
}
   
function validateEmail(){
	if (!isEmpty($("#email")) && !isValidEMail($.trim($("#email").val()))){
		alert("Please input a valid email");
		return false;
	}
	return true;
}

function goSearch(){	    
    $.form("${pageContext.request.contextPath}/search",
            { jid: $.trim($("#jid").val()),  
            	bid: $.trim($("#bid").val()),
            	jobType: 'client',               	           
                isSearch: true
            }, 'POST').submit();    
}

function ajaxSearch(action){
	if (action == 'undefined') action = '';
    $("*").css("cursor", "progress");
    
    $("#dataform").ajaxStart(function() {
        loading();
    }).ajaxComplete(function() {
        $("#loading").remove();
    });
    var _param= {jid: $.trim($("#jid").val()),
        bname: $("#jobTypeList").val(),        
        pageNum: (action == 'search')? '1' : $("#nextPage").val()
    };    
    $.ajax({
        type: "POST",
        url: '${pageContext.request.contextPath}/clientJobs/ajax/goClientPage',
        dataType: 'json',
        async: false,
        data: {pageNum: $("#nextPage").val()},
        success: function(data) {
            $("*").css("cursor", "");
            
            if (data) {
            	$("#tableContent").html("");
                if (data.clientJobs && data.clientJobs.length < 1){
                    $("#dataform > .tableWrapper").hide();
                    $("#dataform > .no_data").show();
                }else{
                    $("#dataform > .no_data").hide();                                        
                    $.each(data.clientJobs, function() {                                                            	
                        var biz = '<tr>' + '<td> <a href="javascript:void(0)">' + this.clientJobId + '</a>';                        
                        biz += '</td>'
                            + '<td>' + this.clientName + '</td>'
                            + '<td>' + this.businessId + '</td>'
                            + '<td>' + formatDate(this.createdTs) +'</td>'
                            + '<td>' + formatDate(this.submitTs) + '</td>'
                            + '<td>' + formatDate(this.lastModifiedTs) + '</td>'  
                            + '<td>' + this.numRequested + '</td>'  
                            + '<td>' + this.numMatched + '</td>'  
                            + '<td>' + formatDate(this.lastModifiedTs) + '</td>'                            
                            + '</tr>';
                        $("#tableContent").append(biz);
                    });
                    $("#dataform > .tableWrapper").show();
                    
                 /*   $(".data_tal tr td:nth-child(2)").each(function(){
                    	var strS = $(this).text();
                    	$(this).html(wordBreak(strS,4));	
                    });
                    $(".data_tal tr td:nth-child(3)").each(function(){
                    	var strS = $(this).text();
                    	$(this).html("<a href='javascript:void(0)'>" + wordBreak(strS,12) + "</a>");	
                    });
                    $(".data_tal tr td:nth-child(7)").each(function(){
                    	var strS = $(this).text();
                    	$(this).html(wordBreak(strS,10));	
                    });
                    $(".data_tal tr td:nth-child(9)").each(function(){
                    	var strS = $(this).text();
                    	$(this).html(wordBreak(strS,10));	
                    });
                    $(".data_tal tr td:nth-child(12)").each(function(){
                    	var strS = $(this).text();
                    	$(this).html(wordBreak(strS,10));	
                    }); 
                    */
                    $(".data_tal tr:even").addClass("even");
                    
                    // $("#nextPage").attr("value",(parseInt($("#nextPage").val()) + 1));						
					$("#totalCount").val(parseInt(data.totalCount));
					$("#currentPage").val(parseInt(data.currentPage));
					if($("#tableContent > tr").length != 0){
				    	$("#to_view, #pagination, hr.s2").show();
				    }else{
				    	$("#to_view, #pagination, hr.s2").hide();
				    }
                }
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            $("*").css("cursor", "");
             var msg = $.parseJSON(XMLHttpRequest.responseText).error;
             alert("Met following error, please contact system admin for investigation: \n"+ msg);
        }
    });
}

function showPagination(){
     var pageText='';
     if($("#totalCount").val()>0)
     {
          pageText="<div class='page'>";
          var totalCount=parseInt($("#totalCount").val());
          var pageSize=parseInt($("#pageSize").val());
          var currentPage=parseInt($("#currentPage").val());
          var totalPage=Math.ceil(totalCount/pageSize);

         if(currentPage==1)
         {
             pageText+="<span class='page_aquo previous'><var>&laquo;</var> Previous</span>";
         }else
         {
             pageText+="<a class='page_aquo previous' href='javascript:reloadPage(&apos;previous&apos;);'><var>&laquo;</var> Previous</a>";                    
         }
         
          if(totalPage <= 10)
          {
              for(var i=1;i<=totalPage;i++)
              {
                  if(i == currentPage)
                  {
                      pageText+="<span class='selected'>"+i+"</span>";
                  }else{
                      pageText+="<a href='javascript:reloadPage("+i+");'>"+i+"</a>";
                  }
               }
          }else
           {
               if(currentPage == 1){
                   pageText+="<span class='selected'>1</span>";
               }else{
                   pageText+="<a href='javascript:reloadPage(1);'>1</a>";                      
               }
               
               if(currentPage-5 <= 0){
                   for(var i=2;i<10;i++){
                     if(i == currentPage){
                         pageText+="<span class='selected'>"+i+"</span>";
                     }else{ 
                         pageText+="<a href='javascript:reloadPage("+i+");'>"+i+"</a>";
                     }
                   }                          
                   pageText+="<span>...</span>";                         
                 }else if(currentPage+5 > totalPage){
                     pageText+="<span>...</span> "; 

                     for(var i=totalPage-5;i<=totalPage-1;i++){
                         if(i == currentPage){
                             pageText+="<span class='selected'>"+i+"</span>";
                          }else{ 
                             pageText+="<a href='javascript:reloadPage("+i+");'>"+i+"</a>";
                          }
                       }
                   }else{
                       pageText+="<span class='text'>...</span>";
                       pageText+="<a href='javascript:reloadPage("+ (currentPage*1-3) +");'>"+ (currentPage*1-3) +"</a>";
                       pageText+="<a href='javascript:reloadPage("+ (currentPage*1-2) +");'>"+ (currentPage*1-2) +"</a>";
                       pageText+="<a href='javascript:reloadPage("+ (currentPage*1-1) +");'>"+ (currentPage*1-1) +"</a>";
                   
                       pageText+="<span class='selected'>"+currentPage+"</span>";
                  
                       pageText+="<a href='javascript:reloadPage("+ (currentPage*1+1) +");'>"+ (currentPage*1+1) +"</a>";
                       pageText+="<a href='javascript:reloadPage("+ (currentPage*1+2) +");'>"+ (currentPage*1+2) +"</a>";
                       pageText+="<a href='javascript:reloadPage("+ (currentPage*1+3) +");'>"+ (currentPage*1+3) +"</a>";
                       pageText+="<span>...</span>";
                  } 
               
               
                   if(currentPage == totalPage){ 
                       pageText+="<span class='selected'>"+totalPage+"</span>";                       
                   }else{ 
                       pageText+="<a href='javascript:reloadPage("+totalPage+");'>"+totalPage+"</a>";                           
                   }
           }                   
     
          //pagination end 
          if(currentPage*pageSize >= totalCount || totalCount == 0){              
              pageText+="<span class='page_aquo'> Next <var>&raquo;</var> </span>";              
          }else{ 
              pageText+="<a class='page_aquo' href='javascript:reloadPage(&apos;next&apos;);'>Next <var>&raquo;</var></a>";                 
          }
          
          pageText+="<span class='floatR'><b>";
          
          var fromPage;
          var toPage;
          
              if((currentPage-1)*pageSize <= 0)
                  fromPage = 1;
              else
                  fromPage = (currentPage-1)*pageSize + 1;
                  
              if(currentPage*pageSize > totalCount)
                  toPage = totalCount;
              else
                  toPage = currentPage*pageSize;
                  
                  
              pageText+=fromPage + " - " + toPage;
              
              pageText+="</b> of <b>";
              pageText+=totalCount + " records </span></div>";

     }    
     $('#pagination').html(pageText);
}

function showPaginationDetail(){
     var pageText='';
     if($("#totalCount_detail").val()>0)
     {
          pageText="<div class='page'>";
          var totalCount=parseInt($("#totalCount_detail").val());
          var pageSize=parseInt($("#pageSize_detail").val());
          var currentPage=parseInt($("#currentPage_detail").val());
          var totalPage=Math.ceil(totalCount/pageSize);

         if(currentPage==1)
         {
             pageText+="<span class='page_aquo previous'><var>&laquo;</var> Previous</span>";
         }else
         {
             pageText+="<a class='page_aquo previous' href='javascript:reloadPageDetail(&apos;previous&apos;);'><var>&laquo;</var> Previous</a>";                    
         }
         
          if(totalPage <= 10)
          {
              for(var i=1;i<=totalPage;i++)
              {
                  if(i == currentPage)
                  {
                      pageText+="<span class='selected'>"+i+"</span>";
                  }else{
                      pageText+="<a href='javascript:reloadPageDetail("+i+");'>"+i+"</a>";
                  }
               }
          }else
           {
               if(currentPage == 1){
                   pageText+="<span class='selected'>1</span>";
               }else{
                   pageText+="<a href='javascript:reloadPageDetail(1);'>1</a>";                      
               }
               
               if(currentPage-5 <= 0){
                   for(var i=2;i<10;i++){
                     if(i == currentPage){
                         pageText+="<span class='selected'>"+i+"</span>";
                     }else{ 
                         pageText+="<a href='javascript:reloadPageDetail("+i+");'>"+i+"</a>";
                     }
                   }                          
                   pageText+="<span>...</span>";                         
                 }else if(currentPage+5 > totalPage){
                     pageText+="<span>...</span> "; 

                     for(var i=totalPage-5;i<=totalPage-1;i++){
                         if(i == currentPage){
                             pageText+="<span class='selected'>"+i+"</span>";
                          }else{ 
                             pageText+="<a href='javascript:reloadPageDetail("+i+");'>"+i+"</a>";
                          }
                       }
                   }else{
                       pageText+="<span class='text'>...</span>";
                       pageText+="<a href='javascript:reloadPageDetail("+ (currentPage*1-3) +");'>"+ (currentPage*1-3) +"</a>";
                       pageText+="<a href='javascript:reloadPageDetail("+ (currentPage*1-2) +");'>"+ (currentPage*1-2) +"</a>";
                       pageText+="<a href='javascript:reloadPageDetail("+ (currentPage*1-1) +");'>"+ (currentPage*1-1) +"</a>";
                   
                       pageText+="<span class='selected'>"+currentPage+"</span>";
                  
                       pageText+="<a href='javascript:reloadPageDetail("+ (currentPage*1+1) +");'>"+ (currentPage*1+1) +"</a>";
                       pageText+="<a href='javascript:reloadPageDetail("+ (currentPage*1+2) +");'>"+ (currentPage*1+2) +"</a>";
                       pageText+="<a href='javascript:reloadPageDetail("+ (currentPage*1+3) +");'>"+ (currentPage*1+3) +"</a>";
                       pageText+="<span>...</span>";
                  } 
               
               
                   if(currentPage == totalPage){ 
                       pageText+="<span class='selected'>"+totalPage+"</span>";                       
                   }else{ 
                       pageText+="<a href='javascript:reloadPageDetail("+totalPage+");'>"+totalPage+"</a>";                           
                   }
           }                   
     
          //pagination end 
          if(currentPage*pageSize >= totalCount || totalCount == 0){              
              pageText+="<span class='page_aquo'> Next <var>&raquo;</var> </span>";              
          }else{ 
              pageText+="<a class='page_aquo' href='javascript:reloadPageDetail(&apos;next&apos;);'>Next <var>&raquo;</var></a>";                 
          }
          
          pageText+="<span class='floatR'><b>";
          
          var fromPage;
          var toPage;
          
              if((currentPage-1)*pageSize <= 0)
                  fromPage = 1;
              else
                  fromPage = (currentPage-1)*pageSize + 1;
                  
              if(currentPage*pageSize > totalCount)
                  toPage = totalCount;
              else
                  toPage = currentPage*pageSize;
                  
                  
              pageText+=fromPage + " - " + toPage;
              
              pageText+="</b> of <b>";
              pageText+=totalCount + " records </span></div>";

     }    
     $('#pagination_detail').html(pageText);
}

function reloadPageDetail(p)
{          
    var tCurrentPage = $("#currentPage_detail").val();     
    if(p == 'next'){
           tCurrentPage=tCurrentPage*1 + 1;
    }
    if(p == 'previous'){
          tCurrentPage=tCurrentPage*1-1;
    }
    if((p != 'next') && (p != 'previous') && (p != '')){
          tCurrentPage=p;
    }
    $("#currentPage_detail").val(tCurrentPage);
    $("#nextPage_detail").attr("value", tCurrentPage);
    
    ajaxGetDetail($("#currentJobId").val());
    showPaginationDetail();
  //  hideMoreInfo(); 
}

function reloadPage(p)
{          
    var tCurrentPage = $("#currentPage").val();     
    if(p == 'next'){
           tCurrentPage=tCurrentPage*1 + 1;
    }
    if(p == 'previous'){
          tCurrentPage=tCurrentPage*1-1;
    }
    if((p != 'next') && (p != 'previous') && (p != '')){
          tCurrentPage=p;
    }
    $("#currentPage").val(tCurrentPage);
    $("#nextPage").attr("value", tCurrentPage);

    ajaxSearch();
    showPagination();
    hideMoreInfo(); 
}

function nav_login2BP(bid, serverName){
	var url = link_login2BP + "?bid=" + bid + "&sName=" + serverName;
	window.open(url, "Login to BP", "width=800, height=800, scrollbars=yes");
}

function nav_sf(sfid){
	var url = link_sf + "/" + sfid;
	window.open(url, "Login to Salesforce", "width=800, height=800, scrollbars=yes");
}

function parseStatus(status){
	return status == 1 ? '<span class="color_add">' + lifecycleStateMap[status] + '</span>' : '<span class="color_delete">' + lifecycleStateMap[status] + '</span>';
}

function parseCommStatus(status){
	return status.toLowerCase() == 'on' ? '<span class="color_add">On</span>' : '<span class="color_delete">Off</span>';
}

function parsePrecision(precision){
	if (precision == "C") {
		return "Combined";
	} else if (precision == "I") {
		return "Individual";
	} else {
		return "";
	}			
}

function formatIndustry(industry){
    return industry == 0 ? '<span class="color_delete">' + industryMap[industry] + '</span>' : industryMap[industry];
}

function formatNodeGroup(ng){
	return "ng" + ng.replace("d3", "").replace(".demandforce.com", "").replace(".demandforced3.com", "").replace("devng", "");
}

function formatDate(millDate){	
	if (millDate == null) return "";
	if (millDate == '0000-00-00 00:00:00') return "";
	var date = new Date(millDate);
	var month = (date.getMonth()+1) > 9 ? (date.getMonth()+1) : "0" + (date.getMonth()+1);
	var day = (date.getDate()+1) > 9 ? (date.getDate()+1) : "0" + (date.getDate()+1);
	var hours = (date.getHours()) > 9 ? (date.getHours()) : "0" + (date.getHours());
	var minutes = (date.getMinutes()) > 9 ? (date.getMinutes()) : "0" + (date.getMinutes());
	var seconds = (date.getSeconds()) > 9 ? (date.getSeconds()) : "0" + (date.getSeconds());
	var dateString = month + "/" + 
    				day + "/" + 
    				date.getFullYear() + " " + formatAMPM(date);
    				/*hours + ":" + 
    				minutes + ":" + 
    				seconds;*/
	return dateString;
}



function formatAMPM(date) {
  var hours = date.getHours();
  var minutes = date.getMinutes();
  var ampm = hours >= 12 ? 'PM' : 'AM';
  hours = hours % 12;
  hours = hours ? hours : 12; // the hour '0' should be '12'
  minutes = minutes < 10 ? '0'+minutes : minutes;
  var strTime = hours + ':' + minutes + ' ' + ampm;
  return strTime;
}



function formatBoolean(isTest){
    return isTest? "Yes":"No";
}

function removeSpecialChars(str){
	if (str == null) return "";
	return str.replace("'", "\'");
}

function clearForm(){
	$("#jid").val('');
	$("#bid").val('');
	$("#jobTypeList").val('');	
	// $("#tableContent").html("");
	// $('#pagination').html('');
	// $("#dataform > .tableWrapper").hide();
	// $("#dataform #to_view").hide();
}

function ajaxGetDetail2(consumerid){
	$("*").css("cursor", "progress");
    
    $("#dataform").ajaxStart(function() {
        loading();
    }).ajaxComplete(function() {
        $("#loading").remove();
    });
    $.ajax({
        type: "POST",
        url: '${pageContext.request.contextPath}/clientJobs/ajax/getConsumerDetail',
        dataType: 'json',
        data: {jobId: $("#currentJobId").val(), pageNum: $("#nextPage_detail").val(), consumerId: consumerid},
        success: function(data) {
            $("*").css("cursor", "");
            
            if (data) {                                   	
            	$("#detail_consumerId").text(data.consumerId);
            	$("#detail_jobid").text(data.clientJobId);
            	$("#detail_fname").text(data.fname);
            	$("#detail_lname").text(data.lname);
            	$("#detail_address").text(data.address1);
            	$("#detail_city").text(data.city);
            	$("#detail_state").text(data.state);
            	$("#detail_zip").text(data.zip);
            	$("#detail_appendType").text(data.appendTypes);
            	$("#detail_precisionReq").text(parsePrecision(data.precisionReq));
            	$("#detail_vendorJobId").text(data.vendorJobId);
            	$("#detail_precisionRes").text(parsePrecision(data.precisionRes));
            	$("#detail_appendedEmail").text(data.appendedEmail);
            	$("#detail_createdTs").text(formatDate(data.createdTs));
            	$("#detail_submitTs").text(formatDate(data.submitTs));
            	$("#detail_receiveTs").text(formatDate(data.recieveTs));
            	$("#detail_downloadTs").text(formatDate(data.downloadTs));
            	$("#detail_lastModifiedTs").text(formatDate(data.lastModifiedTs));
            }
        },
        error: function(e){
            $("*").css("cursor", "");
            //alert('Error: ' + e);
        }
    }); 
}

function ajaxGetDetail(jid){
	$("*").css("cursor", "progress");
    
    $("#dataform").ajaxStart(function() {
        loading();
    }).ajaxComplete(function() {
        $("#loading").remove();
    });
    $("#filterForm input[id='filter_jid']").val(jid);
    $.ajax({
        type: "POST",
        url: '${pageContext.request.contextPath}/clientJobs/ajax/goClientJobDetail',
        dataType: 'json',
        data: {jobId: jid, pageNum: $("#nextPage_detail").val()},
        success: function(data) {
            $("*").css("cursor", "");
            
            if (data) {
            	$(".moreInfo2").slideUp("fast");
            	$("#tableContentDetail").html("");
            	if (data.length < 1){
                    $("#dataformDetail > .tableWrapper").hide();
                    $("#dataformDetail > .no_data").show();
                }else{
                    $("#dataformDetail > .no_data").hide();
                    $("#jobid_detail").html(data.jobId);            		
            		$.each(data.detailList, function(i, detail) {
	            		var biz = '<tr>' + '<td><a href="javascript:void(0)">' + detail.consumerId + '</a></td>'
    					+ '<td>' + detail.fname + '</td>'
    					+ '<td>' + detail.lname+ '</td>'    	
    					+ '<td>' + detail.appendedEmail + '</td>'				    					
    					+ '<td>' + detail.appendTypes + '</td>'    					
    					+ '<td class="last">' + formatDate(detail.createdTs) + '</td>'
    					+ '</tr>';
                        $("#tableContentDetail").append(biz);
  					});
  					$("#dataformDetail > .tableWrapperDetail").show();  
  					  					
  					$("#totalCount_detail").val(parseInt(data.totalCount));
					$("#currentPage_detail").val(parseInt(data.currentPage));
					$("#currentJobId").val(parseInt(data.jobId));
  					if($("#tableContentDetail > tr").length != 0){
				    	$("#detail_to_view, #pagination_detail, hr.s2").show();
				    }else{
				    	$("#detail_to_view, #pagination_detail, hr.s2").hide();
				    }    
					showPaginationDetail();					
  									  			
            	}
            }
        },
        error: function(e){
            $("*").css("cursor", "");
            //alert('Error: ' + e);
        }
    }); 
}
