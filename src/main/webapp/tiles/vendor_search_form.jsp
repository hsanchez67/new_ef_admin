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
});


function clearForm(){
	$("#jid").val('');
}
</script>
<p class="m_b1">Please enter your search criteria in the fields below..</p>
<input type="text" class="input_text width_17 ghost_text" id="jid" name="jid" title="Job ID" placeHolder="Job ID"/>
<input type="hidden" id="jobType" name="jobType" value="vendor" />

<input type="button" class="button btn-blue btn-mini" value="Search"  onclick="validateSearch();"/>
<input type="button" class="button texButn" value="Clear"  onclick="clearForm();"/>