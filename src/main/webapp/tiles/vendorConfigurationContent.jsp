<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false"%>
<script type="text/javascript">
var fail_msg = "Unexpected error. Please try again.";

var validator = {};

validator.check_entry = function(id) {	
	var label = $("label[for="+id+"]").text();	
    var name_msg = "Please input "+label+".";
    var c = $('#'+id);
    if(isEmpty(c)){
        showErr(c,name_msg);
        return false;
    }else{
        hideErr(c);
        return true;
    }
};

validator.check_column_definitions = function(obj) {	
	var label = "Column Definitions";	
    var name_msg = "Please input "+label+".";
    var c = $(obj);
    if(isEmpty(c)){
        showErr(c,name_msg);
        return false;
    }else{
        hideErr(c);
        return true;
    }
};

validator.check_data_file_format = function(obj) {
    var msg = "Please select a Data File Format.";    
    var dff = $(obj);
    if(isEmpty(dff)){
        showErr(dff, msg);        
        return false;
    }else{        
        hideErr(dff);
        return true;
    }
};

validator.check_append_type = function(obj) {
    var msg = "Please select an Append Type.";    
    var dff = $(obj);
    if(isEmpty(dff)){
        showErr(dff, msg);        
        return false;
    }else{        
        hideErr(dff);
        return true;
    }
};

validator.check_precision = function(obj) {
    var msg = "Please select a Precision.";    
    var dff = $(obj);
    if(isEmpty(dff)){
        showErr(dff, msg);        
        return false;
    }else{        
        hideErr(dff);
        return true;
    }
};

validator.check_ftp_direction = function(obj) {
    var msg = "Please select a Direction.";    
    var dff = $(obj);
    if(isEmpty(dff)){
        showErr(dff, msg);        
        return false;
    } else {
        hideErr(dff);
        return true;
    }
};

validator.check_ftp_protocol = function(obj) {
    var msg = "Please select a Protocol.";    
    var dff = $(obj);
    if(isEmpty(dff)){
        showErr(dff, msg);        
        return false;
    } else {
        hideErr(dff);
        return true;
    }
};

validator.check_input = function(obj, label) {		
    var name_msg = "Please input "+label+".";
    var c = $(obj);
    if(isEmpty(c)){
        showErr(c,name_msg);
        return false;
    }else{
        hideErr(c);
        return true;
    }
};


function clearMessage(obj)
{
	hideErr($(obj));
	return;
}

function goSearchVendorConfiguration(p, id) {
	var direction = $("input:radio[name=data_file_direction]").get(0).checked;
	var d = '';
	if (direction) {
		d = 'I';
	} else {
		d = 'O';
	}
	$.ajax({
        type: "POST",
        url: '${pageContext.request.contextPath}/configuration/ajax/goSearchVendorConfiguration',
        data: ({ vendorId : id , precision : p , direction : d}),
        dataType: "json",
        success: function (data)
        {
        	if (data && data.direction == 'I') {        		
        		$('#inbound_data_precision').val(data.precision);
        		$('#inbound_data_file_format').val(data.fileFormat);
        		$('#inbound_data_file_append_type').val(data.appendType);
        		$('#inbound_data_file_field_separator').val(data.fieldSeparator);
        		$('#inbound_data_file_record_separator').val(data.recordSeparator);
        		$('#inbound_data_file_enclosed_by').val(data.enclosedByChar);
        		$('#inbound_data_file_escape').val(data.escapeChar);
        		$('#inbound_column_definitions').val(data.columnList);      
        		$('#inbound_vendor_formatid').val(data.vendorFormatId);
        		$('#inbound_vendor_id').val(data.vendorId);
        	} else {
        		$('#outbound_data_precision').val(data.precision);
        		$('#outbound_data_file_format').val(data.fileFormat);
        		$('#outbound_data_file_append_type').val(data.appendType);
        		$('#outbound_data_file_field_separator').val(data.fieldSeparator);
        		$('#outbound_data_file_record_separator').val(data.recordSeparator);
        		$('#outbound_data_file_enclosed_by').val(data.enclosedByChar);
        		$('#outbound_data_file_escape').val(data.escapeChar);
        		$('#outbound_column_definitions').val(data.columnList);      
        		$('#outbound_vendor_formatid').val(data.vendorFormatId);
        		$('#outbound_vendor_id').val(data.vendorId);
        	}
        },        
        error: function(XMLHttpRequest, textStatus, errorThrown){
        	            
        }
       
    });
}

function goSearchFtpVendorConfiguration(d, id) {
	var precision = $("input:radio[name=ftp_server_type]").get(0).checked;
	var p = '';
	if (precision) {
		p = 'I';
	} else {
		p = 'C';
	}	
	$.ajax({
        type: "POST",
        url: '${pageContext.request.contextPath}/configuration/ajax/goSearchFTPVendorConfiguration',
        data: ({ vendorId : id , precision : p , direction : d}),
        dataType: "json",
        success: function (data)
        {           	
        	if (data != null && data.precision == 'I') {        		
        		$('#individual_ftp_direction').val(data.direction);
        		$('#individual_ftp_append_type').val(data.appendType);
        		$('#individual_ftp_protocol').val(data.protocol);
        		$('#individual_ftp_uri').val(data.uri);        		
        		$('#individual_ftp_directory').val(data.directory);        		
        		$('#individual_ftp_username').val(data.username);      
        		$('#individual_ftp_password').val(data.password);
        		$('#individual_ftp_vendor_connectionid').val(data.vendorId);
        		$('#individual_ftp_vendor_id').val(data.vendorId);
        	} else if (data != null && data.precision == 'C') {
        		$('#combined_ftp_direction').val(data.direction);
        		$('#combined_ftp_append_type').val(data.appendType);
        		$('#combined_ftp_protocol').val(data.protocol);
        		$('#combined_ftp_uri').val(data.uri);        		
        		$('#combined_ftp_directory').val(data.directory);        		
        		$('#combined_ftp_username').val(data.user);      
        		$('#combined_ftp_password').val(data.pass);
        		$('#combined_ftp_vendor_connectionid').val(data.vendorId);
        		$('#combined_ftp_vendor_id').val(data.vendorId);
        	}
        },        
        error: function(XMLHttpRequest, textStatus, errorThrown){
        	var msg = $.parseJSON(XMLHttpRequest.responseText).error;
            alert("Met following error, please contact system admin for investigation: \n"+ msg);         
        }
       
    });
    	
}

function saveVendorConfigurationFTPServerProperties(id){
	var precision = $("input:radio[name=ftp_server_type]").get(0).checked;
	var p = '';
	if (precision) {
		p = 'I';
		input_prev = "individual_ftp_";
	} else {
		p = 'C';
		input_prev = "combined_ftp_";
	}	
			
	var direction_err_msg = "Please select a Direction.";	
	var append_type_err_msg = "Please select an Append Type.";
	var protocol_err_msg = "Please select a Protocol.";
    var uri_err_msg = "Please input a Uri.";  
    var directory_err_msg = "Please input a Directory.";
	var username_err_msg = "Please input a Username.";
	var password_err_msg = "Please input a Password.";
    
    if($.trim($('[name='+input_prev+'direction]').val() ) == '' || $.trim( $('[name='+input_prev+'append_type]').val() ) == '' || $.trim( $('[name='+input_prev+'protocol]').val() ) == '' || $.trim( $('[name='+input_prev+'uri]').val()) == ''
    		|| $.trim( $('[name='+input_prev+'directory]').val()) == '' || $.trim( $('[name='+input_prev+'username]').val()) == '' || $.trim( $('[name='+input_prev+'password]').val()) == '') {    	
    	if($.trim( $('[name='+input_prev+'direction]').val() ) == '') showErr($('[name='+input_prev+'direction]'),direction_err_msg);
    	if($.trim( $('[name='+input_prev+'append_type]').val() ) == '') showErr($('[name='+input_prev+'append_type]'),append_type_err_msg);
    	if($.trim( $('[name='+input_prev+'protocol]').val() ) == '') showErr($('[name='+input_prev+'protocol]'),protocol_err_msg);
        if($.trim( $('[name='+input_prev+'uri]').val() ) == '') showErr($('[name='+input_prev+'uri]'),uri_err_msg);
        if($.trim( $('[name='+input_prev+'directory]').val() ) == '') showErr($('[name='+input_prev+'directory]'),directory_err_msg);
        if($.trim( $('[name='+input_prev+'username]').val() ) == '') showErr($('[name='+input_prev+'username]'),username_err_msg);
        if($.trim( $('[name='+input_prev+'password]').val() ) == '') showErr($('[name='+input_prev+'password]'),password_err_msg);
        return;
    }        
       	      
    $("*").css("cursor", "progress");
    $("#ftp_servers input[type=submit]").addClass("button_disabled").attr("disabled","disabled");    
     $.ajax({
        type: "POST",
        url: '${pageContext.request.contextPath}/configuration/ajax/saveVendorFTPServerProperties',
        data: ({ direction : $('[name='+input_prev+'direction]').val() , appendType : $('[name='+input_prev+'append_type]').val(), protocol : $('[name='+input_prev+'protocol]').val(), 
        		uri : $('[name='+input_prev+'uri]').val(),        		
        		directory : $('[name='+input_prev+'directory]').val(), username : $('[name='+input_prev+'username]').val(),
        		password : $('[name='+input_prev+'password]').val(),
        		vendorConnectionId: $('[name='+input_prev+'vendor_connectionid]').val(), precision: p, vendorId : id}),
        dataType: "json",
        success: function (data)
        {
            $("*").css("cursor", "");
            $("#ftp_servers input[type=submit]").removeClass("button_disabled").removeAttr("disabled");                                   

            $('#result_3').show();
              
            if (data.result == "Error") {
	            $('#action_result_3').html(fail_msg).removeClass("success").addClass("failure");                            
	            $('#action_result_3').html("Error saving FTP Server configuration properties!");	              
            } else {
            	$('#'+input_prev+'vendor_connectionid').val(data.vendorConnectionId);
        		$('#'+input_prev+'vendor_id').val(id);
            }
            
            $('#result_3').delay(10000).fadeOut();
        },        
        error: function(XMLHttpRequest, textStatus, errorThrown){
        	$('#result_3').show();
        	$('#action_result_3').html(fail_msg).removeClass("success").addClass("failure");                            
            $('#action_result_3').html("Error saving FTP Server configuration properties!");                        
            $("*").css("cursor", "");
            $("#ftp_servers input[type=submit]").removeClass("button_disabled").removeAttr("disabled");            
        }
       
    });

}


function saveVendorConfigurationDataFileFormat(id){
	var direction = $("input:radio[name=data_file_direction]").get(0).checked;	
	var d = '';
	var input_prev = "";
	if (direction) {
		d = 'I';
		input_prev = "inbound_";
	} else {
		d = 'O';
		input_prev = "outbound_";
	}		
	var precision_err_msg = "Please select a Precision.";
	var data_file_format_err_msg = "Please select a Data File Format.";
	var data_file_append_type_err_msg = "Please select an Append Type.";
    var column_definitions_err_msg = "Please input Column Definitions.";        
    
    
    if($.trim($('[name='+input_prev+'data_precision]').val() ) == '' || $.trim( $('[name='+input_prev+'data_file_format]').val() ) == '' || $.trim( $('[name='+input_prev+'data_file_append_type]').val() ) == '' || $.trim( $('[name='+input_prev+'column_definitions]').val()) == '') {    	
    	if($.trim( $('[name='+input_prev+'data_precision]').val() ) == '') showErr($('[name='+input_prev+'data_precision]'),precision_err_msg);
    	if($.trim( $('[name='+input_prev+'data_file_format]').val() ) == '') showErr($('[name='+input_prev+'data_file_format]'),data_file_format_err_msg);
    	if($.trim( $('[name='+input_prev+'data_file_append_type]').val() ) == '') showErr($('[name='+input_prev+'data_file_append_type]'),data_file_append_type_err_msg);
        if($.trim( $('[name='+input_prev+'column_definitions]').val() ) == '') showErr($('[name='+input_prev+'column_definitions]'),column_definitions_err_msg);              
        return;
    }           
       	      
    $("*").css("cursor", "progress");
    $("#data_file input[type=submit]").addClass("button_disabled").attr("disabled","disabled");    
     $.ajax({
        type: "POST",
        url: '${pageContext.request.contextPath}/configuration/ajax/saveVendorConfigurationDataFileFormat',
        data: ({ precision : $('[name='+input_prev+'data_precision]').val() , fileFormat : $('[name='+input_prev+'data_file_format]').val(), appendType : $('[name='+input_prev+'data_file_append_type]').val(), 
        		fieldSeparator : $('[name='+input_prev+'data_file_field_separator]').val(),        		
        		recordSeparator : $('[name='+input_prev+'data_file_record_separator]').val(), enclosedBy : $('[name='+input_prev+'data_file_enclosed_by]').val(),
        		escape : $('[name='+input_prev+'data_file_escape]').val(), columnDefinitions : $('[name='+input_prev+'column_definitions]').val(),
        		vendorFormatId: $('[name='+input_prev+'vendor_formatid]').val(), direction: d, vendorId : id}),
        dataType: "json",
        success: function (data)
        {
            $("*").css("cursor", "");
            $("#data_file input[type=submit]").removeClass("button_disabled").removeAttr("disabled");                                   

            $('#result_2').show();
              
            if (data.result == "Error") {
	            $('#action_result_2').html(fail_msg).removeClass("success").addClass("failure");                            
	            $('#action_result_2').html("Error saving configuration properties!");	              
            }  else {
            	$('#'+input_prev+'vendor_formatid').val(data.vendorConnectionId);
        		$('#'+input_prev+'vendor_id').val(id);
            }
            
            $('#result_2').delay(10000).fadeOut();
        },        
        error: function(XMLHttpRequest, textStatus, errorThrown){
        	$('#result_2').show();
        	$('#action_result_2').html(fail_msg).removeClass("success").addClass("failure");                            
            $('#action_result_2').html("Error saving configuration properties!");                        
            $("*").css("cursor", "");
            $("#data_file input[type=submit]").removeClass("button_disabled").removeAttr("disabled");            
        }
       
    });

}

function saveVendorConfigurationProperties(id){
	var vendor_name_err_msg = "Please input the Vendor Name.";
	var vendor_contact_err_msg = "Please input the Vendor Contact.";
    var max_records_per_file_err_msg = "Please input the Max. Records per File.";
    var polling_interval_err_msg = "Please input the Polling Interval.";    
    
    
    if($.trim($('[name=vendorName]').val() ) == '' || $.trim( $('[name=vendorContact]').val() ) == '' || $.trim( $('[name=maxRecordsPerFile]').val() ) == '' || $.trim( $('[name=pollingInterval]').val() ) == '') {    	
    	if($.trim( $('[name=vendorName]').val() ) == '') showErr($('[name=vendorName]'),vendor_name_err_msg);
    	if($.trim( $('[name=vendorContact]').val() ) == '') showErr($('[name=vendorContact]'),vendor_contact_err_msg);
        if($.trim( $('[name=maxRecordsPerFile]').val() ) == '') showErr($('[name=maxRecordsPerFile]'),max_records_per_file_err_msg);
        if($.trim( $('[name=pollingInterval]').val() ) == '') showErr($('[name=pollingInterval]'),polling_interval_err_msg);       
        return;
    }        
        
       
    $("*").css("cursor", "progress");
    $("#part1 input[type=submit]").addClass("button_disabled").attr("disabled","disabled");    
    $.ajax({
        type: "POST",
        url: '${pageContext.request.contextPath}/configuration/ajax/saveVendorConfigurationProperties',
        data: ({ vendorName : $('[name=vendorName]').val() , vendorContact : $('[name=vendorContact]').val() , maxRecordsPerFile : $('[name=maxRecordsPerFile]').val() , pollingInterval : $('[name=pollingInterval]').val(), vendorId : id}),
        dataType: "json",
        success: function (data)
        {
            $("*").css("cursor", "");
            $("#part1 input[type=submit]").removeClass("button_disabled").removeAttr("disabled");                                   

            $('#result_1').show();
              
            if (data.result == "Error") {
	            $('#action_result_1').html(fail_msg).removeClass("success").addClass("failure");                            
	            $('#action_result_1').html("Error saving data file properties!");	              
            } 
            
            $('#result_1').delay(10000).fadeOut();
        },        
        error: function(XMLHttpRequest, textStatus, errorThrown){
        	$('#result_1').show();
        	$('#action_result_1').html(fail_msg).removeClass("success").addClass("failure");                            
            $('#action_result_1').html("Error saving data file properties!");                        
            $("*").css("cursor", "");
            $("#part1 input[type=submit]").removeClass("button_disabled").removeAttr("disabled");            
        }
       
    });
	
}

$("document").ready(function(){
	$("[name=data_file_direction]").click(function(){
	    var options = $(this).parents("fieldset").children(".radio_option");
	    radio_option($("[name=data_file_direction]"), options, this);
	}).eq(0).attr("checked","checked");
	
	$("[name=ftp_server_type]").click(function(){
	    var options = $(this).parents("fieldset").children(".radio_option");
	    radio_option($("[name=ftp_server_type]"), options, this);
	}).eq(0).attr("checked","checked");
	
});
</script>

<div class="tabs_container position_rel">
	<div class="tabBox">
		<div id="part1">
			<div class="legend_s" >
            	<span class="title_1s" >Vendor Configuration General Properties</span>                               
        	</div>	
<!--  		
<c:forEach var="entry" items="${properties}">
  Key: <c:out value="${entry.key}"/>
  Value: <c:out value="${entry.value}"/>
</c:forEach>
-->		
<c:forEach var="entry" items="${properties}"> 		 			
	<c:if test="${entry.key == 'max_records_per_file' and !empty entry.value}">
		<c:set var="maxRecordsPerFile" value="${entry.value}" />			
	</c:if>	
	<c:if test="${entry.key == 'polling_interval' and !empty entry.value}">
		<c:set var="pollingInterval" value="${entry.value}" />		
	</c:if>				 	
</c:forEach> 			
			<!-- Configuration Properties -->
			<div class="form float_l">	
				<div class="legend">Fill in the form below.</div>							
				<div class="box width_100 float_l">
					<fieldset>						
						<div class="tableInfo width_46 float_l" id="inputDiv1">
							<div class="row_wrap">
								<label>Vendor ID </label> <input
									type="text" class="input_text" name="vendorId" value="${vendor.vendorId}"
									id="vendorName" readonly="readonly">
							</div>
							<div class="row_wrap">
								<label>Vendor Name <em class="required">*</em></label> <input
									type="text" class="input_text" name="vendorName" value="${vendor.name}"
									id="vendorName" onchange="clearMessage(this);">
							</div>
							<div class="row_wrap">
								<label>Vendor Contact <em class="required">*</em></label> <input
									type="text" class="input_text" name="vendorContact" value="${vendor.contact}"
									id="vendorContact" onchange="clearMessage(this);">
							</div>							
						</div>		
						<div class="tableInfo width_46 float_l" id="inputDiv2">
							<div class="row_wrap">
								<label>Max. Records / File <em class="required">*</em></label> <input
									type="text" class="input_text" name="maxRecordsPerFile" value="${maxRecordsPerFile}"
									id="maxRecordsPerFile" onchange="clearMessage(this);">
							</div>
							<div class="row_wrap">
								<label>Polling Interval <em class="required">*</em></label> <input
									type="text" class="input_text _small" name="pollingInterval" value="${pollingInterval}"
									id="pollingInterval" onchange="clearMessage(this);"> Minutes
							</div>												
						</div>												
					</fieldset>
				</div>	
			</div>			
			<div class="row_submit_wrap" style="width:200px;">				
				<input class="button " type="submit" id="btn_submit_input"
					name="btn_submit_input" value="Submit"
					onclick="saveVendorConfigurationProperties(${vendor.vendorId});" />
				<input class="button button_cancel" type="button" id="btn_cancel_input"
					name="btn_cancel_input" value="Cancel"
					onclick="goBack('${pageContext.request.contextPath}/configuration');" />					
			</div>
			<div class="form hide m_b2 clearBoth" id="result_1">
				<div class="icons_sys success" id="action_result_1">Vendor Configuration Properties saved succesfully!</div>					
			</div>
			
		</div>
		<!-- end of Cummunication Status -->
	</div>
	<!-- end of listBox -->
</div>

<div id="dataFile_properties" style="width:49%" class="form tabs_container position_rel float_l">
	<div id="data_file" class="clearfix">		
		<div class="legend_s" >
              <span class="title_1s" >Vendor Configuration Data File Properties</span>                               
        </div>	
		<div class="legend">Fill in the form below.</div>
		<div class="box width_88 float_l">
	    	 <fieldset>
	        	  <div class="row_wrap">
		              	<label>Data File </label>                                                                                        
		                <span class="wordblock">
		                	<input type="radio" name="data_file_direction" value="1" /> Inbound 
		                	<input class="mleft" type="radio" name="data_file_direction" value="0" /> Outbound
		                 </span>                                         
	                </div>
	                
	                <div class="row_wrap industry_row radio_option">
                    	<div class="top_arrow"><span class="border_corb arrow_border" style="left:208px"></span><span class="border_corb arrow_s1" style="left:208px"></span></div>
                    		<div class="row_wrap">
				                <label>Precision <em class="required">*</em></label>
				                <select id="inbound_data_precision" name="inbound_data_precision" onblur="validator.check_precision(this);" onchange="goSearchVendorConfiguration(this.value, ${vendor.vendorId})" style="width:195px;">
				                    <option value="">- Please Select a Precision -</option>
				                    <option value="I">Individual</option>
				                    <option value="C">Combined</option>
				                </select>
				            </div>                        	 
                        	<div class="row_wrap">
				                <label>Data File Format <em class="required">*</em></label>
				                <select id="inbound_data_file_format" name="inbound_data_file_format" onblur="validator.check_data_file_format(this);" style="width:195px;"> 
				                    <option value="">- Please Select a File Format -</option>
				                    <option value="CSV">CSV</option>
				                </select>
				            </div>
				            <div class="row_wrap">
				                <label>Append Type <em class="required">*</em></label>
				                <select id="inbound_data_file_append_type" name="inbound_data_file_append_type" onblur="validator.check_append_type(this);" style="width:200px;"> 
				                    <option value="">- Please Select an Append Type -</option>
				                    <option value="EMAIL">EMAIL</option>
				                </select>
				            </div>
				            <div class="row_wrap">
				                <label>Field Separator </label>				                
				                <input class="input_text title_show"  type="text"  class="input_text _small" name="inbound_data_file_field_separator" 
				                	id="inbound_data_file_field_separator" />
				            </div>
				            <div class="row_wrap">
				                <label>Record Separator </label>
				                <input class="input_text title_show" type="text"  class="input_text _small" name="inbound_data_file_record_separator" 
				                	id="inbound_data_file_record_separator" />
				            </div>
				            <div class="row_wrap">
				                <label>EnclosedBy </label>
				                <input class="input_text title_show" type="text"  class="input_text _small" name="inbound_data_file_enclosed_by" 
				                	id="inbound_data_file_enclosed_by" />
				            </div>
				            <div class="row_wrap">
				                <label>Escape </label>
				                <input class="input_text title_show" type="text"  class="input_text _small" name="inbound_data_file_escape" 
				                	id="inbound_data_file_escape" />
				            </div>
				            <div class="row_wrap">
                             	<label>Column Definitions <em class="required">*</em></label>
                             	<span class="wordblock">
                                	<span class="blockShow">
                             			<span class="label_description wordblock" style="display:inline;">Please separate them using commas.</span>                             			                             			
                             		</span>
                             		<textarea style="width:210px;" name="inbound_column_definitions" id="inbound_column_definitions" rows="8" cols="80" class="m_b1 nopadding" onblur="validator.check_column_definitions(this)"></textarea>
                             	</span>		                             	                                                                                                                       
                            </div>
                            <input type="hidden" id="inbound_vendor_id" name="inbound_vendor_id" value="" />
                            <input type="hidden" id="inbound_vendor_formatid" name="inbound_vendor_formatid" value="" />
                    </div>
                       
                    <div class="row_wrap radio_option hide" id="add_bid_wrap">
                    	<div class="top_arrow"><span class="border_corb arrow_border" style="left:275px"></span><span class="border_corb arrow_s1" style="left:275px"></span></div>
                        	                 		<div class="row_wrap">
				                <label>Precision <em class="required">*</em></label>
				                <select id="outbound_data_precision" name="outbound_data_precision" onblur="validator.check_precision(this);" onchange="goSearchVendorConfiguration(this.value, ${vendor.vendorId})" style="width:195px;">
				                    <option value="">- Please Select a Precision -</option>
				                    <option value="I">Individual</option>
				                    <option value="C">Combined</option>
				                </select>
				            </div>                        	 
                        	<div class="row_wrap">
				                <label>Data File Format <em class="required">*</em></label>
				                <select id="outbound_data_file_format" name="outbound_data_file_format" onblur="validator.check_data_file_format(this);" style="width:195px;">
				                    <option value="">- Please Select a File Format -</option>
				                    <option value="CSV">CSV</option>
				                </select>
				            </div>
				            <div class="row_wrap">
				                <label>Append Type <em class="required">*</em></label>
				                <select id="outbound_data_file_append_type" name="outbound_data_file_append_type" onblur="validator.check_append_type(this);" style="width:200px;"> 
				                    <option value="">- Please Select an Append Type -</option>
				                    <option value="EMAIL">EMAIL</option>
				                </select>
				            </div>
				            <div class="row_wrap">
				                <label>Field Separator </label>				                
				                <input class="input_text title_show"  type="text"  class="input_text _small" name="outbound_data_file_field_separator" 
				                	id="outbound_data_file_field_separator" />
				            </div>
				            <div class="row_wrap">
				                <label>Record Separator </label>
				                <input class="input_text title_show" type="text"  class="input_text _small" name="outbound_data_file_record_separator" 
				                	id="outbound_data_file_record_separator" />
				            </div>
				            <div class="row_wrap">
				                <label>EnclosedBy </label>
				                <input class="input_text title_show" type="text"  class="input_text _small" name="outbound_data_file_enclosed_by" 
				                	id="outbound_data_file_enclosed_by" />
				            </div>
				            <div class="row_wrap">
				                <label>Escape </label>
				                <input class="input_text title_show" type="text"  class="input_text _small" name="outbound_data_file_escape" 
				                	id="outbound_data_file_escape" />
				            </div>
				            <div class="row_wrap">
                             	<label>Column Definitions <em class="required">*</em></label>
                             	<span class="wordblock">
                                	<span class="blockShow">
                             			<span class="label_description wordblock" style="display:inline;">Please separate them using commas.</span>                             			                             			
                             		</span>
                             		<textarea style="width:210px;" name="outbound_column_definitions" id="outbound_column_definitions" rows="8" cols="80" class="m_b1 nopadding" onblur="validator.check_column_definitions(this)"></textarea>
                             	</span>		                             	                                                                                                                       
                            </div>
                            <input type="hidden" id="outbound_vendor_id" name="outbound_vendor_id" value="" />
                            <input type="hidden" id="outbound_vendor_formatid" name="outbound_vendor_formatid" value="" />
                    </div>    	     	 
	          </fieldset>
	          
	          <div class="row_submit_wrap" style="width:200px;">				
				<input class="button " type="submit" id="btn_submit_input"
					name="btn_submit_input" value="Submit"
					onclick="saveVendorConfigurationDataFileFormat(${vendor.vendorId});" />
				<input class="button button_cancel" type="button" id="btn_cancel_input"
					name="btn_cancel_input" value="Cancel"
					onclick="goBack('${pageContext.request.contextPath}/configuration');" />					
			</div>
			<div class="form hide m_b2 clearBoth" id="result_2">
				<div class="icons_sys success" id="action_result_2">Data File Properties saved succesfully!</div>					
			</div>
	          
	      </div>
	 </div>
 </div>  
 
 <!--  FTP Servers begin -->
 
 <div id="ftp_servers_properties" style="width:48%" class="form tabs_container position_rel float_l m_l5">
	<div id="ftp_servers" class="clearfix">		
		<div class="legend_s" >
              <span class="title_1s" >Vendor Configuration FTP Server Properties</span>                               
        </div>	
		<div class="legend">Fill in the form below.</div>
		<div class="box width_88 float_l">
	    	 <fieldset>
	        	  <div class="row_wrap">
		              	<label>FTP Servers </label>                                                                                        
		                <span class="wordblock">
		                	<input type="radio" name="ftp_server_type" value="1" /> Individual 		                	
		                	<input class="mleft" type="radio" name="ftp_server_type" value="0" /> Combined
		                 </span>                                         
	                </div>
	                
	                <div class="row_wrap industry_row radio_option">
                    	<div class="top_arrow"><span class="border_corb arrow_border" style="left:208px"></span><span class="border_corb arrow_s1" style="left:208px"></span></div>
                    		<div class="row_wrap">
				                <label>Direction <em class="required">*</em></label>
				                <select id="individual_ftp_direction" name="individual_ftp_direction" onblur="validator.check_ftp_direction(this);" onchange="goSearchFtpVendorConfiguration(this.value, ${vendor.vendorId});" style="width:195px;"> 
				                    <option value="">- Please Select a Direction -</option>
				                    <option value="I">Inbound</option>
				                    <option value="O">Outbound</option>
				                </select>
				            </div>                    		                        	                         	
				            <div class="row_wrap">
				                <label>Append Type <em class="required">*</em></label>
				                <select id="individual_ftp_append_type" name="individual_ftp_append_type" onblur="validator.check_append_type(this);" style="width:200px;"> 
				                    <option value="">- Please Select an Append Type -</option>
				                    <option value="EMAIL">EMAIL</option>
				                </select>
				            </div>
				            <div class="row_wrap">
				                <label>Protocol <em class="required">*</em></label>
				                <select id="individual_ftp_protocol" name="individual_ftp_protocol" onblur="validator.check_ftp_protocol(this);" style="width:200px;"> 
				                    <option value="">- Please Select a Protocol -</option>
				                    <option value="FTP">FTP</option>
				                </select>
				            </div>
				            <div class="row_wrap">
				                <label>Uri <em class="required">*</em></label>
				                <input class="input_text title_show _mediumlarge"  type="text" name="individual_ftp_uri" 
				                	id="individual_ftp_uri" onblur="validator.check_input(this, 'Uri');" />
				            </div>                    		
							<div class="row_wrap">
				                <label>Directory <em class="required">*</em></label>
				                <input class="input_text title_show _mediumlarge"  type="text"  name="individual_ftp_directory" 
				                	id="individual_ftp_directory"  onblur="validator.check_input(this, 'Directory');" />
				            </div>				                                    	                         	
				            <div class="row_wrap">
				                <label>Username <em class="required">*</em></label>
				                <input class="input_text title_show _mediumlarge"  type="text"  name="individual_ftp_username" 
				                	id="individual_ftp_username"  onblur="validator.check_input(this, 'Username');" />
				            </div>
				            <div class="row_wrap">
				                <label>Password <em class="required">*</em></label>				                
				                <input class="input_text title_show _mediumlarge"  type="text"  name="individual_ftp_password" 
				                	id="individual_ftp_password" onblur="validator.check_input(this, 'Password');" />
				            </div>
				            <input type="hidden" id="individual_ftp_vendor_connectionid" name="individual_ftp_vendor_connectionid" value="" />				            
                            <input type="hidden" id="individual_ftp_vendor_id" name="individual_ftp_vendor_id" value="" />                            
                    </div>                                           
                    <div class="row_wrap radio_option hide" id="add_bid_wrap">
                    	<div class="top_arrow"><span class="border_corb arrow_border" style="left:285px"></span><span class="border_corb arrow_s1" style="left:285px"></span></div>
                    		<div class="row_wrap">
				                <label>Direction <em class="required">*</em></label>
				                <select id="combined_ftp_direction" name="combined_ftp_direction" onblur="validator.check_ftp_direction(this);" onchange="goSearchFtpVendorConfiguration(this.value, ${vendor.vendorId});" style="width:195px;"> 
				                    <option value="">- Please Select a Direction -</option>
				                    <option value="I">Inbound</option>
				                    <option value="O">Outbound</option>
				                </select>
				            </div>                    		                       	                         
				            <div class="row_wrap">
				                <label>Append Type <em class="required">*</em></label>
				                <select id="combined_ftp_append_type" name="combined_ftp_append_type" onblur="validator.check_append_type(this);" style="width:200px;"> 
				                    <option value="">- Please Select an Append Type -</option>
				                    <option value="EMAIL">EMAIL</option>
				                </select>
				            </div>
				            <div class="row_wrap">
				                <label>Protocol <em class="required">*</em></label>
				                <select id="combined_ftp_protocol" name="combined_ftp_protocol" onblur="validator.check_ftp_protocol(this);" style="width:200px;"> 
				                    <option value="">- Please Select a Protocol -</option>
				                    <option value="FTP">FTP</option>
				                </select>
				            </div>
				            <div class="row_wrap">
				                <label>Uri <em class="required">*</em></label>
				                <input class="input_text title_show _mediumlarge"  type="text" name="combined_ftp_uri" 
				                	id="combined_ftp_uri" onblur="validator.check_input(this, 'Uri');" />
				            </div>                        	
				            <div class="row_wrap">
				                <label>Directory <em class="required">*</em></label>
				                <input class="input_text title_show _mediumlarge"  type="text"  name="combined_ftp_directory" 
				                	id="combined_ftp_directory" onblur="validator.check_input(this, 'Directory');" />
				            </div>                        	                         
				            <div class="row_wrap">
				                <label>Username <em class="required">*</em></label>
				                <input class="input_text title_show _mediumlarge"  type="text"  name="combined_ftp_username" 
				                	id="combined_ftp_username" onblur="validator.check_input(this, 'Username');" />
				            </div>
				            <div class="row_wrap">
				                <label>Password <em class="required">*</em></label>				                
				                <input class="input_text title_show _mediumlarge"  type="text"  name="combined_ftp_password" 
				                	id="combined_ftp_password" onblur="validator.check_input(this, 'Password');" />
				            </div>				            
				            <input type="hidden" id="combined_vendor_connectionid" name="combined_vendor_connectionid" value="" />
                            <input type="hidden" id="combined_vendor_id" name="combined_vendor_id" value="" />
                    </div>     	     	 
	          </fieldset>
	          
	          <div class="row_submit_wrap" style="width:200px;">				
				<input class="button " type="submit" id="btn_submit_input"
					name="btn_submit_input" value="Submit"
					onclick="saveVendorConfigurationFTPServerProperties(${vendor.vendorId});" />
				<input class="button button_cancel" type="button" id="btn_cancel_input"
					name="btn_cancel_input" value="Cancel"
					onclick="goBack('${pageContext.request.contextPath}/configuration');" />					
			</div>
			<div class="form hide m_b2 clearBoth" id="result_3">
				<div class="icons_sys success" id="action_result_3">FTP Server Properties saved succesfully!</div>					
			</div>
	          
	      </div>
	 </div>
 </div>  
 
 <!--  FTP Servers end -->   
                