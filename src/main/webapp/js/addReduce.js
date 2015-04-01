function return_false(){return false;};

function disabledDrag(){
	$('body').bind('click',function(event){ 
		//alert($(".multipleSelectRight li.selected[isundermanagement=0]").length);
		//if($(event.target).attr("isundermanagement")==0||$(event.target).parent().attr("isundermanagement")==0){
		//if($(".multipleSelectRight li.selected[isundermanagement=0]").length==1){
		if($(event.target).parents(".multipleSelectRight").length>0){//if is a child element of the right option
			if($(event.target).attr("isundermanagement")==0||$(event.target).parent().attr("isundermanagement")==0){
				$("#remove_1").addClass("disabled").attr("disabled");
    			$("#remove_1").bind("click",return_false());
			}else{
				$("#remove_1").removeClass("disabled").removeAttr("disabled");
    			$("#remove_1").unbind("click",return_false());  		    			
			}
		}else if($(".multipleSelectRight li.selected[isundermanagement=0]").length>=1){
  			$("#remove_1").addClass("disabled").attr("disabled");
			$("#remove_1").bind("click",return_false());    		
		}else{
			
			$("#remove_1").removeClass("disabled").removeAttr("disabled");
			$("#remove_1").unbind("click",return_false()); 			
		}
	});
	
}


$(document).ready(function(){
	//high light when click the option
	$(".mul_select_box li").live('click',function(){
		$(".mul_select_box li").removeClass("selected");					   
		$(this).addClass("selected");
	});
	
	//add to current user roles when click Right triangle
    $('#addto_1').live('click',function(){ 
        var $options = $('.multipleSelectLeft .selected');     
        var $remove = $options.detach();   
        $remove.appendTo('.multipleSelectRight'); 
		
		$remove.append("<span class='close_s'></span>"); 
		$remove.prepend("<input type='checkbox' />");
		//zebra();
	    dragAndDrop();	
    });
    
    //move from the current user to roles table when click close button
    $(".close_s").live('click',function(){
		var $removeROptions_1 = $(this).parent("li");  
		if($removeROptions_1.attr("isundermanagement")!=0){//if this option is disabled for remove
			$removeROptions_1.appendTo('.multipleSelectLeft');     
			$('.multipleSelectLeft .close_s').remove();
			$('.multipleSelectLeft input:checkbox').remove();
		    dragAndDrop();	
		}
	});
    
    // move from the current user to roles table when click left triangle
    $('#remove_1').live('click',function(){  
	var $removeOptions = $('.multipleSelectRight .selected');     
        $removeOptions.appendTo('.multipleSelectLeft');     
		$('.multipleSelectLeft .close_s').remove();
		$('.multipleSelectLeft input:checkbox').remove();
		if($('.multipleSelectRight li').length == 0){
			//$('.selectAll_1').show();
	     	//$('.selectAll_1 input').attr('checked',true);
		}	
		//zebra();
	    dragAndDrop();	
    });

    zebra();   
    dragAndDrop();	
    selectHeight();
    disabledDrag();
});


//add zebra for role list.
function zebra(){
    $(".mul_select_box li").removeClass("even").css("visibility","visible");
    $(".multipleSelectLeft li:visible:odd,.multipleSelectRight li:visible:odd").addClass("even");
}

//sort the multiselect value.
function sortAlpha(){
	$(".mul_select_box").each(function(){
		$select_box = $(this);
		var rows = $select_box.find("li").get();
		rows.sort(function(a,b){
			var keyA = $(a).children(".mul_select_value").text().toUpperCase();
			var keyB = $(b).children(".mul_select_value").text().toUpperCase();
			if(keyA<keyB) return -1;
			if(keyA>keyB) return 1;
			return 0;
		});
		$.each(rows, function(index, row){
			$select_box.append(row);
		});
						
	});	
}

function selectHeight(){
	var left_height = $(".multipleSelectLeft").height();
	var right_height = $(".multipleSelectRight").height();
	if(left_height>=right_height) $(".mul_select_box").height(left_height);
	else $(".mul_select_box").height(right_height);
}

//for drag user roles
function dragAndDrop(){
	function dragLeft(){
    	var x,y;
	    $(".multipleSelectLeft li").draggable({
			start:function(event,ui){
				$(".mul_select_box li").removeClass("selected");
				ui.helper.addClass("selected dragging").removeClass("even");
				$(this).css("visibility","hidden");
				var $offset=$(this).offset();
				x=$offset.left;
				y=$offset.top;
			},
			helper: "clone",
			scroll: false,
			cursor: "move",
			//cursorAt: {"left":30, "top":10},
			revert: "invalid",
			stop: function(event,ui){
				if(ui.helper.offset().left!=x || ui.helper.offset().top!=y) $(this).remove();
				else $(this).css("visibility","visible");
				dragLeft();
				dragRight();
				//sortAlpha();
				zebra();
				$("#part3_userrole").hide();
			}
	    });
	}
    
	function dropRight(){
	    $(".multipleSelectRight").droppable({
	    	drop:function(event,ui){
	    		var me=this;
	    		if(ui.helper.parent().not(me))
	    		$(this).append(ui.helper.clone().css({"position":"static"})
	    										.removeClass("dragging")
	    										.find("input:checkbox").remove().end()
							    				.prepend("<input type='checkbox' />")
												.find("span.close_s").remove().end()
							    				.append("<span class='close_s'></span>"));
	    		ui.helper.remove();
				zebra();
				sortAlpha();
	     	}
	    });
	}
	
	function dragRight(){
    	var x,y;
	    $(".multipleSelectRight li[isundermanagement=1]").draggable({
			start:function(event,ui){
				$(".mul_select_box li").removeClass("selected");
				ui.helper.addClass("selected dragging").removeClass("even");
				$(this).css("visibility","hidden");
				var $offset=$(this).offset();
				x=$offset.left;
				y=$offset.top;
			},
			helper: "clone",
			scroll: false,
			cursor: "move",
			revert: "invalid",
			stop: function(event,ui){
				if(ui.helper.offset().left!=x || ui.helper.offset().top!=y) $(this).remove();
				else $(this).css("visibility","visible");
				dragLeft();
				dragRight();
				zebra();
			}
	    });
	}
	
	function dropLeft(){
	    $(".multipleSelectLeft").droppable({
	    	drop:function(event,ui){
	    		var me=this;
	    		if(ui.helper.parent().not(me))
	    		$(this).append(ui.helper.clone().css({"position":"static"})
												.removeClass("dragging")
												.find("span.close_s").remove().end()
												.find("input:checkbox").remove().end());
	    		ui.helper.remove();
				sortAlpha();
				zebra();
	    	}
	    });
	}
	
	dragLeft();
	dragRight();
	dropRight();
	dropLeft();
	sortAlpha();
	zebra();
	
}
