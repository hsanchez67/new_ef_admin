<script type="text/javascript" src="${pageContext.request.contextPath}/js/searchbusiness.jsp"></script>
<script type="text/javascript">
$(document).ready(function(){      
    var ua = navigator.userAgent;        
    if(ua.indexOf("MSIE")>0 && ua.indexOf("MSIE 9.0")>0){    	
       $("input.ghost_text").each(function(){      	   
	    	if($(this).val() == ""){
				$(this).removeClass("textLighter");
				$(this).addClass("textLighter").val($(this).attr("title"));			
	    	}
		});
    }
    $("#bid").change(function() {    	
    	if (!isEmpty($("#bid"))) {
    		$('#jobTypeList').val('client');		
    	} else {
    		$('#jobTypeList').val('');		
    	}
    });
});

</script>
<p class="m_b1">Please enter your search criteria in the fields below.</p>
<input type="text" class="input_text width_17 ghost_text" id="jid" name="jid" title="Job ID" placeHolder="Job ID"/>
<input type="text" class="input_text width_17 ghost_text" id="bid" name="bid" title="Business ID" placeHolder="Business ID"/>
<select id="jobTypeList" name="jobType" class="width_15">
    <option value="">- Job Type -</option>
    <option value="vendor">Vendor</option>
	<option value="client">Client</option>
</select>

<input type="button" class="button btn-blue btn-mini" value="Search"  onclick="validateSearch();"/>
<input type="button" class="button texButn" value="Clear"  onclick="clearForm();"/>