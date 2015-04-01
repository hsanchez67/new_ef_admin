<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
    $.ajaxSetup({
        global: true,
        complete:function(XMLHttpRequest,textStatus){
            try {
                var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
            }catch(e){
                sessionstatus="";
            }
            if(sessionstatus=="timeout"){
                window.location.replace(window.location.href);
            }
        }
    });
    $(document).bind("ajaxError", function (event,xhr,options,err) {
        var errorMsg=err.message;
        if(typeof errorMsg=='undefined'||errorMsg==null){
            errorMsg='';
        }
        if(typeof errorMsg=='string'){
            errorMsg=errorMsg.toLowerCase();
        }
        var responseText = xhr.responseText;
        if (responseText.indexOf('Your session has timed out.') != -1||err=='timeout') {
            alert("Your session has timed out.");
            window.location.href = window.location.href;
        }
    });
</script>