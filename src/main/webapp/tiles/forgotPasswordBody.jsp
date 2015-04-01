<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<script type="text/javascript">

function is_empty(s) {   
	return ((s == null) || (s.length == 0))
}

function check_username(){
	
	var re_email = /^(.+)@(.+)$/;
	
    var err_msg = "<div class='valid_err font_11'>Please enter a valid email address.</div>";
    var s= $('[name=j_username]').val();
    if( is_empty(s) || !re_email.test(s) ){//if is empty or not a valid email
        if($('[name=j_username]').siblings('.valid_err').size() == 0)
        	$('[name=j_username]').parent('.row_wrap').append(err_msg);              
        $('[name=j_username]').addClass('input_valid_err').siblings('.valid_err').show();
        return false;
    }else{
    	$('[name=j_username]').removeClass('input_valid_err').siblings('.valid_err').hide();
    	return true;
    }
}

function ajaxSubmit(){
	
	var pass_username = check_username(document.getElementById("j_username"));
    if(!pass_username){
    	return false;
    }
    $("*").css("cursor", "progress");
    $("#part2 input[type=submit]").addClass("button_disabled").attr("disabled","disabled");    
    
    $.ajax({
        type: "POST",
        url: '${pageContext.request.contextPath}/resetPassword',
        data: ({email : $('[name=j_username]').val()}),
	                
        success: function(data) {
            $("*").css("cursor", "");
            $("#part1 input[type=submit]").removeClass("button_disabled").removeAttr("disabled");        
            //alert(data.result);
            if (data.result == "not_exists"){
            	showErr($('#j_username'),"User doesn't exist!");
            } else{
                $('#part1').hide();
                $('#part2').show();
                
                if(data.result == 'SUCCESS')
                {
                	$('#action_result').html("Password Reset Successful.");
                    $('#action_result_detail').html("The new password has been successfully sent to your email address. Please check your inbox.");
                }
                else if(data.result == "unsent"){
                    $('#action_result').html("Password Reset Failed.");
                    $('#action_result_detail').html("Your password has been successfully reset but failed to be sent to your email address. Please try again or contact system admin for the investigation.");
                }
                else{
                	$('#action_result').html("Password Reset Failed.");
                	$('#action_result_detail').html("Please try again or contact system admin for the investigation.");
                }
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            var msg = $.parseJSON(XMLHttpRequest.responseText).error;	                    
            showErr($('#j_username'),msg);
            $("*").css("cursor", "");
            $("#part1 input[type=submit]").removeClass("button_disabled").removeAttr("disabled");            
            
        }
     });
}


</script>
<!-- main content -->
<div id="main">
    <div class="main_wrap">
		<div class="status_wrap" id="part1">
			<form class="form gradient_lightgrey login_box forget_password return_false" name="loginform" action="#" method="post" >
				<p class="legend gradient_lightgrey">Forgot Password</p>
				<!-- <hr class="s1 s2 mar_tb1"> -->
				<fieldset>
					<div class="row_wrap m_lr1 normal_line">A new auto-generated password will be sent to your email address. Please check your inbox later.</div>
					<div class="row_wrap">
						<label for="j_username">Username</label>
						<input class="input_text" type="text" name="j_username" id="j_username" title="Enter your email address" placeholder="Enter your email address" onBlur="check_username()"/>
					</div>
			    	<div class="row_submit_wrap">
		            	<input type="submit" value="Submit" class="button" onClick="ajaxSubmit()"/>
		            	<input type="button" value="Cancel" class="button texButn" onclick="window.location.href='${pageContext.request.contextPath}/login'" />
		        	</div>
				
				</fieldset>
       		</form>
    	</div>
    	
   	 	<div class="status_wrap form hide" id="part2">
				 <div class="status_icon success_icon">time out</div>
				 <div class="status_text">
					<h3 id="action_result">Password Reset Successful.</h3>
					<p id="action_result_detail">The new password has been successfully sent to your email address. Please check your inbox.</p>
					<p><a href="/login">Return to Login</a><p>
				</div>
		</div>
	</div>
</div>
