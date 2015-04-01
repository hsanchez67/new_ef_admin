<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false"%>
<script type="text/javascript">
var fail_msg = "Unexpected error. Please try again.";

function clearMessage(obj)
{
	hideErr($(obj));
	return;
}

function saveConfigurationProperties(lo_ad){
	var append_archive_dir_err_msg = "Please input the Append Archive Directory.";
	var append_results_ttl_err_msg = "Please input the Append Results TTL.";
    var temp_diretory_err_msg = "Please input the Temp Directory.";
    var valid_append_types_err_msg = "Please input the Valid Append Types.";
    var max_records_per_file_err_msg = "Please input the Max Records per File.";
    var result_polling_frequency_err_msg = "Please input the Result Polling Frequency.";
    var submit_frequency_err_msg = "Please input the Submit Frequency";

    
    if($.trim($('[name=appendArchiveDir]').val() ) == '' || $.trim( $('[name=appendResultsTtlDays]').val() ) == '' || $.trim( $('[name=tempDir]').val() ) == '' || $.trim( $('[name=validAppendTypes]').val() ) == '' || $.trim( $('[name=maxRecordsPerFile]').val() ) == '' || $.trim( $('[name=resultPollingFrequency]').val() ) == '' || $.trim( $('[name=submitFrequency]').val() ) == '') {
    	if($.trim( $('[name=appendArchiveDir]').val() ) == '') showErr($('[name=appendArchiveDir]'),append_archive_dir_err_msg);
    	if($.trim( $('[name=appendResultsTtlDays]').val() ) == '') showErr($('[name=appendResultsTtlDays]'),append_results_ttl_err_msg);
        if($.trim( $('[name=tempDir]').val() ) == '') showErr($('[name=tempDir]'),temp_diretory_err_msg);
        if($.trim( $('[name=validAppendTypes]').val() ) == '') showErr($('[name=validAppendTypes]'),valid_append_types_err_msg);
        if($.trim( $('[name=maxRecordsPerFile]').val() ) == '') showErr($('[name=maxRecordsPerFile]'),max_records_per_file_err_msg);
        if($.trim( $('[name=resultPollingFrequency]').val() ) == '') showErr($('[name=resultPollingFrequency]'),result_polling_frequency_err_msg);
        if($.trim( $('[name=submitFrequency]').val() ) == '') showErr($('[name=submitFrequency]'),submit_frequency_err_msg);
        return;
    }        
       
    $("*").css("cursor", "progress");
    $("#part1 input[type=submit]").addClass("button_disabled").attr("disabled","disabled");    
    $.ajax({
        type: "POST",
        url: '${pageContext.request.contextPath}/configuration/ajax/saveConfigurationProperties',
        data: ({ appendArchiveDir : $('[name=appendArchiveDir]').val() , appendResultsTtlDays : $('[name=appendResultsTtlDays]').val() , tempDir : $('[name=tempDir]').val() , validAppendTypes : $('[name=validAppendTypes]').val(),  maxRecordsPerFile : $('[name=maxRecordsPerFile]').val() ,  resultPollingFrequency : $('[name=resultPollingFrequency]').val(),   submitFrequency : $('[name=submitFrequency]').val()}),
        dataType: "json",
        success: function (data)
        {
            $("*").css("cursor", "");
            $("#part1 input[type=submit]").removeClass("button_disabled").removeAttr("disabled");                                   

            $('#result_1').show();
              
            if (data.result == "Error") {
	            $('#action_result_1').html(fail_msg).removeClass("success").addClass("failure");                            
	            $('#action_result_1').html("Error saving configuration properties!");	              
            } 
            
            $('#result_1').delay(10000).fadeOut();
        },        
        error: function(XMLHttpRequest, textStatus, errorThrown){
        	var msg = errorThrown;   
        	alert(msg);
        	$('#result_1').show();
        	$('#action_result_1').html(fail_msg).removeClass("success").addClass("failure");                            
            $('#action_result_1').html("Error saving configuration properties!");                        
            $("*").css("cursor", "");
            $("#part1 input[type=submit]").removeClass("button_disabled").removeAttr("disabled");            
        }
       
    });
	
}
</script>
<div class="tabs_container position_rel">
	<div class="tabBox">
		<div id="part1">
<!--  		
<c:forEach var="entry" items="${properties}">
  Key: <c:out value="${entry.key}"/>
  Value: <c:out value="${entry.value}"/>
</c:forEach>
-->		
<c:forEach var="entry" items="${properties}"> 		 			
	<c:if test="${entry.key == 'append_archive_dir' and !empty entry.value}">
		<c:set var="appendArchiveDir" value="${entry.value}" />			
	</c:if>	
	<c:if test="${entry.key == 'append_results_ttl_days' and !empty entry.value}">
		<c:set var="appendResultsTtlDays" value="${entry.value}" />		
	</c:if>	
	<c:if test="${entry.key == 'max_recs_per_file' and !empty entry.value}">
		<c:set var="maxRecordsPerFile" value="${entry.value}" />		
	</c:if>	
	<c:if test="${entry.key == 'results_frequency_mins' and !empty entry.value}">
		<c:set var="resultPollingFrequency" value="${entry.value}" />		
	</c:if>	
	<c:if test="${entry.key == 'submit_frequency_mins' and !empty entry.value}">
		<c:set var="submitFrequency" value="${entry.value}" />		
	</c:if>	
	<c:if test="${entry.key == 'tmp_dir' and !empty entry.value}">
		<c:set var="tempDir" value="${entry.value}" />		
	</c:if>	
	<c:if test="${entry.key == 'valid_append_types' and !empty entry.value}">
		<c:set var="validAppendTypes" value="${entry.value}" />		
	</c:if>		 		
</c:forEach> 			
			<!-- Configuration Properties -->
			<div class="form float_l">				
				<div class="title_1s legend" style="width:500px">Global Configuration</div>
				<div class="box width_85 float_l">
					<fieldset>						
						<div id="inputDiv">
							<div class="row_wrap">
								<label>Append Archive Directory <em class="required">*</em></label> <input
									type="text" class="input_text _large" name="appendArchiveDir" value="<c:out value="${appendArchiveDir}" />"
									id="businessId" onchange="clearMessage(this);">
							</div>
							<div class="row_wrap">
								<label>Append Results TTL <em class="required">*</em></label> <input
									type="text" class="input_text _large" name="appendResultsTtlDays" value="${appendResultsTtlDays}"
									id="appendResultsTtlDays" onchange="clearMessage(this);">
							</div>
							<div class="row_wrap">
								<label>Temp Directory <em class="required">*</em></label> <input
									type="text" class="input_text _large" name="tempDir" value="${tempDir}"
									id="tempDir" onchange="clearMessage(this);">
							</div>
							<div class="row_wrap">
								<label>Valid Append Types <em class="required">*</em></label> <input
									type="text" class="input_text _large" name="validAppendTypes" value="${validAppendTypes}"
									id="validAppendTypes" onchange="clearMessage(this);">
							</div>
						</div>												
					</fieldset>
				</div>	
			</div>
			<div class="form float_l">	
				<div class="title_1s legend" style="width:500px">Vendor Configuration</div>
				<div class="box width_85 float_l">
					<fieldset>						
						<div id="inputDiv">
							<div class="row_wrap">
								<label>Max Records per File <em class="required">*</em></label> <input
									type="text" class="input_text" name="maxRecordsPerFile" value="${maxRecordsPerFile}"
									id="maxRecordsPerFile" onchange="clearMessage(this);">
							</div>
							<div class="row_wrap">
								<label>Result Polling Frequency <em class="required">*</em></label> <input
									type="text" class="input_text" name="resultPollingFrequency" value="${resultPollingFrequency}"
									id="resultPollingFrequency" onchange="clearMessage(this);">
							</div>
							<div class="row_wrap">
								<label>Submit Frequency <em class="required">*</em></label> <input
									type="text" class="input_text" name="submitFrequency" value="${submitFrequency}"
									id="submitFrequency" onchange="clearMessage(this);">
							</div>
						</div>												
					</fieldset>
				</div>								
			</div>
			<div class="row_submit_wrap" style="width:200px;">				
				<input class="button " type="submit" id="btn_submit_input"
					name="btn_submit_input" value="Submit"
					onclick="saveConfigurationProperties(event);" />
			</div>
			<div class="form hide m_b2 clearBoth" id="result_1">
				<div class="icons_sys success" id="action_result_1">Configuration Properties saved succesfully!</div>					
			</div>
			
		</div>
		<!-- end of Cummunication Status -->
	</div>
	<!-- end of listBox -->
</div>

<div class="clearBoth">
<%@ include file="vendorList.jsp" %>
</div>