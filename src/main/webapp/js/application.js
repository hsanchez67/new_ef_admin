//left navigation high light
function get_selected_left_tabs(){
	var url = window.location.href;
	var page_name = url.substring(url.indexOf("/",8));
	var param =page_name.lastIndexOf("?");
	if(param!=-1) {
		page_name = page_name.substring(0,param);
	}
	var left_tabs = $('.tabs_nav li');
	left_tabs.removeClass('selected');
	left_tabs.each(function(i){
		if($(this).children('a').attr('href')==page_name)
			$(this).addClass('selected');
	});

}

function redirectToTimeout(){
	var url = window.location.href;
	var page_name = url.substring(url.indexOf("/",8));
	if(page_name.indexOf('/configuration/')!=-1) {
		// redirect to Timeout page
	}
}

//primary navigation high light
function get_selected_primary_tabs(){
	var url = window.location.href;
	var page_name = url.substring(url.indexOf("/",8));//get url except host name. such as url:http://172.16.2.138:8080/review/remove, page_name=/review/remove.	
	var primary_tabs = $('.nav_primary li.nav_level_1');
	var primary_tabs_text = [];
	$('.nav_primary li.nav_level_1 > a:first-child').each(function(i){
		primary_tabs_text[i]=$(this).text();
	});
	var lastFolder = page_name.slice(page_name.lastIndexOf("/"));			
	if(lastFolder=="/welcome" || lastFolder =="/search"){
		for(var i=0; i<primary_tabs_text.length; i++){
			if(primary_tabs_text[i]=="Home")
				primary_tabs.removeClass('selected').eq(i).addClass('selected');
		}
	}else if(page_name.indexOf('/vendorJobs/')!=-1 || lastFolder=="/vendorJobs"){
		for(var i=0; i<primary_tabs_text.length; i++){
			if(primary_tabs_text[i]=="Vendor Jobs")
				primary_tabs.removeClass('selected').eq(i).addClass('selected');
		}
	}else if(page_name.indexOf('/clientJobs/')!=-1 || lastFolder=="/clientJobs"){
		for(var i=0; i<primary_tabs_text.length; i++){
			if(primary_tabs_text[i]=="Client Jobs")
				primary_tabs.removeClass('selected').eq(i).addClass('selected');
		}
	} else if(page_name.indexOf('/configuration/')!=-1 || lastFolder=="/configuration" || page_name.indexOf('/vendorConfiguration/')!=-1 || lastFolder=="/vendorConfiguration"){
		for(var i=0; i<primary_tabs_text.length; i++){
			if(primary_tabs_text[i]=="Configuration")
				primary_tabs.removeClass('selected').eq(i).addClass('selected');
		}
	} else {
		for(var i=0; i<primary_tabs_text.length; i++){
			if(primary_tabs_text[i]=="Home")
				primary_tabs.removeClass('selected').eq(i).addClass('selected');
		}		
	}
}

function landing_items(){
    $(".items").each(function(i){
        var item = "<li></li>";
        var iNum = $(this).children("li:not(.disable)").removeClass("last").length;
        var remainder = iNum%4;
        if(remainder!=0){
            switch(remainder){
                case 1:
                    $(this).append(item+item+item);
                    break
                case 2:
                    $(this).append(item+item);
                    break
                default: // reminder ==3
                    $(this).append(item);           
            }
        }
        var n = iNum/4;
        for(i=0; i<=n; i++){
            $(this).children("li:not(.disable)").eq(i*4-1).addClass("last");
        }
    });
}
//rating number like 100,90,80... turn to the stars html.
function get_rate_stars(rate){
	var num = (rate/20);
	var stars_class=num.toString().replace(".","p");
	var html = '<ul class="star_rating_min" title="' + num +'">' +
    				'<li class="score' + stars_class + '"><var class="astar rating">' + num +'</var></li>' +
    			'</ul>';
	return html; 
}

//add a space after "," for long string and didn't return new line.
function addSpace(str){
	if(str==null) return "";
	else str = str.replace(/,\b/g ,", ");
    return str;
}

//if there is a word is too long and overflow the box, add <wbr></wbr> for word break,
function wordBreak(str,maxLength){//str is the string which need to word break, maxLength is the max length must be word break.
    var words = str.split(/\s+/); //split the string with a space.
    var seperate="<wbr></wbr>";
    for(var i=0; i<words.length; i++){
        var sLength = words[i].length;
        if ( sLength > maxLength ){// find the word which longer than the maxlength.
            n = Math.floor(sLength/maxLength);
            var characters=words[i].split("");
            for(var j=1; j<=n; j++ ){
            	characters.splice(j*maxLength+j-1, 0, seperate);//add <wbr></wbr>&#8203; after every max length .
            }
            words[i]=characters.join("");
        }
    }
    str = words.join(" ");
    return str;
}

//isEmpty
function isEmpty(me){
	if($.trim(me.val()) == '') return true;
	else return false;	
}

function isNumber(me){
	var reg_num = /^[1-9]\d*$/;
	if (typeof me === 'object'){
		if(reg_num.test($.trim(me.val()))) return true;
	}else{
		if(reg_num.test($.trim(me))) return true;
	}
	return false;
}

function isDouble(me){ 
    var doublePat=/^[-+]?(\d+)\.(\d+)$/; 
    var matchArray=me.match(doublePat); 
    if(matchArray!=null){
     return true; 
    } else {
     return false; 
    }
 }

function is_phone(str){
	str = format_phone(str);//remove space - ( ) charactor in the phone number;
		
    var reg_phone = /^\d{10}$|^\d{11}$/;
	return reg_phone.test(str);
}

function format_phone(str, hasFormat){
	if (hasFormat == 'undefined'){
		hasFormat = false;
	}
	str = str.replace(/ |-|\(|\)/g,"");//remove space - ( ) charactor in the phone number;
	if (hasFormat){
		str = str.replace(" ","");
		if (str.length != 10){
		}else{
			str = '(' + str.substring(0, 3) + ') ' + str.substring(3, 6) + "-" + str.substring(6);
		}
	}
		
	return str;
}


/* 
 * 
	@Description	
		Matches a latitude in the range of -90 to 90 degrees, with between 1 and 6 trailing decimal places.
		A valid latitude should slot within [-90.0,90.0], the length of whose decimal part should less than 7.
	@Matches	
		-90.0 -77.284382 89.999999 1.0001
	@Non-Matches
		-90.1 90.12345 91 -20.1234567 -90 90
*/
function isDecimalLatitude(me)
{
	var latiPat = /^-?([1-8]?[0-9]\.{1}\d{1,6}$|90\.{1}0{1,6}$)/;
	if (latiPat.test(me)){
    	return true; 
    } else {
    	return false; 
    }
}

/* 
 * 
	@Description	
		Longitude validation (similiar to Latitude Validation) and inspired by the same code.
		A valid longitude should slot within [-180.0,180.0], the length of whose decimal part should less than 7.
	@Matches	
		180.0, -180.0, 98.092391
	@Non-Matches
		181, 180, -98.0923913
*/
function isDecimalLongitude(me)
{
	var longiPat = /^-?([1][0-7][0-9]|[1-9]?[0-9][\.]\d{1,6}$|180\.0{1,6}$)/;
	// var longiPat = "^-?([1][0-7][0-9]\.\d{1,6}$|[1-9]?[0-9]\.\d{1,6}$|180\.\0{1,6}$)";
	
    if(longiPat.test(me)){
    	return true; 
    } else {
    	return false; 
    }
}

function isValidEMail(EmailStr){
	 var myReg  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	 if(myReg.test(EmailStr)) 
	     return true; 
	 return false; 
}

//show invalid input error msg
function showErr(me,err_msg, _append){
	if (_append == undefined){
		_append = false;
	}
	var err_msg = '<div class="valid_err">' + err_msg +'</div>';
	var label_width = me.siblings("label").width();
	if(label_width==null){
		label_width = me.parents(".row_wrap").siblings(".row_wrap").eq(0).children("label").width();
	}
	//if(me.siblings('.valid_err').size() == 0){
		//me.parent('.row_wrap').append(err_msg);
	if (!_append){
		me.siblings('.valid_err').remove();
	}
	me.parent().append(err_msg);

	//}
	me.addClass('input_valid_err').siblings('.valid_err').show().css("padding-left",label_width+15);
	if(me.hasClass('nopadding')) me.siblings('.valid_err').css("padding-left",0);
	if(me.parents().hasClass('nopadding')) me.siblings('.valid_err').css("padding-left",0);
	if(me.hasClass('ifile')) me.siblings('.upfile').addClass('input_valid_err');
	return false;
}

//hide error msg if input valid
function hideErr(me){
	me.removeClass('input_valid_err').siblings('.valid_err').remove();
	if(me.hasClass('ifile')) me.siblings('.upfile').removeClass('input_valid_err');
	//return true;
}

//reset form
function resetAnother(){
    location.reload();
}

//browser button for every browser like the same.
function browserButton(){
	$('.fake_browse').click(function(){
		$(this).siblings('.ifile').click();
	});
}

function realBrowserButton(){
	$('.ifile').change(function(){
		var file_name = this.value.substr(this.value.lastIndexOf('\\')+1);
		$(this).attr('title',file_name).siblings('.upfile').val(file_name).attr('title',file_name);
	});	
}

function isAllowedFileType(fileObj){    
    var fileType = fileObj.value.substring(fileObj.value.lastIndexOf(".")+1, fileObj.value.length);
    if(fileType=="xls" || fileType=="xlsx" || fileType=="csv")
        return true;
    else
        return false;
}

function resetFileInput(obj){
    var ie = (navigator.appVersion.indexOf("MSIE")!=-1);
    if(ie){
        var obj2= obj.cloneNode(false);
        obj2.onchange= obj.onchange;
        obj.parentNode.replaceChild(obj2,obj);
    }else{
        obj.value="";
    }
}

function checkFileInfo(obj){
    hideErr($(obj));
    var upload_err_msg = "Please upload the correct file format.";
    if(obj.value!=""){
        if(!isAllowedFileType(obj)){            
            showErr($(obj),upload_err_msg);
            resetFileInput(obj);
            realBrowserButton();
            return false;
        }else{
        	var file_name = obj.value.substr(obj.value.lastIndexOf('\\')+1);
    		$(obj).attr('title',file_name).siblings('.upfile').val(file_name).attr('title',file_name);
        }
    }else{
        showErr($(obj),upload_err_msg);
    }
}

function activity_detail(){
	$('.activity_detail').each(function(i){
		var separator=".";
		var s_word=["."," from "," to "," for "];
		//var s_test = "Maggie Ma. updated 11 customer details to Delete for Demanforce."
		var s = $(this).html();
		var s_word_index = new Array() ;
		for (var x in s_word)
		{
			s_word_index[x] = s.indexOf(s_word[x]);
		}
//		name = "<span class='high_keyword'>" + name + "</span>"; // wrap the name with bold span.
		var slice_detail,slice_1,slice_2,slice_3,slice_4;
		slice_detail = s.slice(s_word_index[0]);
        var name = s.slice(0,s_word_index[0]);//get the name
        if(name.length== s.length-1||s_word_index[0]> s.indexOf("</b>")){
            var nameEnd=name.indexOf("</b>")+4;
            name=name.substr(0,nameEnd);
            slice_detail= s.slice(nameEnd);
            s_word_index[0]=nameEnd;
        }
		if(s_word_index[1]==-1 && s_word_index[2] !=-1 && s_word_index[3] !=-1){ //to ... for
			slice_1 = s.slice(s_word_index[0],s_word_index[2]+3);
			slice_2 = s.slice(s_word_index[2]+3,s_word_index[3]);
			slice_3 = s.slice(s_word_index[3]);
            if (slice_2.indexOf("<b>") != 0)
                slice_2 = "<span class='high_keyword'>" + slice_2 + "</span>"; // wrap the status with bold span.
			slice_detail = slice_1 + slice_2 + slice_3;
		
		}else if(s_word_index[1]!=-1 && s_word_index[2]!=-1  && s_word_index[3] !=-1){ //from ... to ... for
			slice_1 = s.slice(s_word_index[0],s_word_index[1]+5); 
			if(s_word_index[2]>s_word_index[1]){ //there is a "to" before form, the is not the real "to"
				slice_2 = s.slice(s_word_index[1]+5,s_word_index[2]);	
				slice_3 = s.slice(s_word_index[2]+3,s_word_index[3]);
			}else{
				var realTo = s.lastIndexOf(" to ");//business status history has two  "to", but the second "to" is the real "to";
				slice_2 = s.slice(s_word_index[1]+5,realTo);	
				slice_3 = s.slice(realTo+3,s_word_index[3]);
			}
			slice_4 = s.slice(s_word_index[3]);
            if($.trim(slice_2).indexOf("<b>")!=0)
                slice_2 = "<span class='high_keyword'>" + slice_2 + "</span>"; // wrap the status form with bold span.
            if($.trim(slice_3).indexOf("<b>")!=0)
                slice_3 = "<span class='high_keyword'>" + slice_3 + "</span>"; // wrap the name with bold span.
			slice_detail = slice_1 + slice_2 +  s_word[2] + slice_3 + slice_4;
		}
        if(slice_detail.indexOf(".")==0){
            slice_detail=slice_detail.substr(1);
        }
		$(this).html(name + slice_detail);
	});
}

function cellphone_detail(){
	$('.cell_detail').each(function(i){
		var s = $(this).text();		
		var cell_detail="";
	    var ss=s.split(')');
	    for (var m=0;m<ss.length-1;m++ ) { 
	        cell_detail = cell_detail + '<li>' + ss[m] +')</li>';
		}
        $(this).replaceWith(cell_detail);
	});
}

/* Function grabs param called "name" from the url */
function gup( name ){
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( window.location.href );
  if( results == null )
	return "";
  else {
  	var result = results[1];
  	result = result.replace(/%20/g, " ");
	return result;
  }
}

/* get the current form tabs highlight when have form tabs. */
function tabsForm(){
	var num = gup("tab");
	var forms = $("div.tabBox > div");
	var tabs= $("div.listBox a");
	if(num!="" && num<=tabs.length && num>0){
		//forms.hide();
		forms.eq(num-1).show().siblings().hide();
		//tabs.removeClass("current");
		tabs.eq(num-1).addClass("current").siblings().removeClass("current");
	}else{
		forms.eq(0).show();
		tabs.eq(0).addClass("current").siblings().removeClass("current");
	} 
}


function getScrollTop()	{
	var scrollTop=0;
	if(document.documentElement&&document.documentElement.scrollTop) {
		scrollTop=document.documentElement.scrollTop;
	}else if(document.body) {
		scrollTop=document.body.scrollTop;
	}
	return scrollTop;
}	

function getInnerText(obj){
	if (obj == undefined){
		return "";
	}
	return obj.innerText == undefined? obj.textContent:obj.innerText;
}

function more_detail(){
	//more details show for history
	$("span.show_detail").click(function(){
		$(".pup_box").hide();
		var detail_box=$(this).next(".pup_box");
		detail_box.show().css("top",getScrollTop());
		
		if(detail_box.find(".data_tal_wider").length >= 1){
			detail_box.addClass("pop_box_wider");
		}
	    return false;
    });
	//more details hide for history
	$(".close").click(function(){
		$(this).parent(".pup_box").hide();
	});
}

function loading(){
	var loadPos = $(".tabs_container");
	var h = loadPos.outerHeight();
	$("#loading").remove();
	loadPos.append("<div id='loading'><img src='css/images/loading.gif' /></div>");
	
	if($(".tabs_container div").hasClass("listBox")){
		$("#loading").css('margin-top',"70px");
		h = h-72;
	}
	loadPos.children("#loading").height(h-2).width(loadPos.outerWidth()-4);
	$("#loading img").css('margin-top',(h-52)/2);
}

function showOrHideLoading(){
	$(".tabs_container").ajaxStart(function() {
        loading();
    }).ajaxComplete(function() {
        $("#loading").remove();
    });
}

function preview(event,url) {
	if ( event && event.preventDefault )   
		event.preventDefault();   
    else   
       window.event.returnValue = false; 
	var src = event.target || window.event.srcElement; 
	if(url == undefined)
		url=src.href;
	window.open(url, "Preview", "width=800, height=800, scrollbars=yes");
} 

function decodeHtml(str)
{
	var htmlChar="&amp;";


	if(str.indexOf(htmlChar)!=-1)
	{
		str=str.replace(/&amp;/g, '&');
	}

	return str;
}

/* View More Function, such as: You're viewing 50 out of 200 records. View more.*/


function view_more_records(){
	var totalRecords = $(".view_more_records_tal tbody tr");
	var totalSize = totalRecords.length;
	var pageSize = 50;
	if(totalSize > pageSize){
		var show_more= '<p class="moreItems">You\'re viewing <span class="currentItems">' + pageSize + '</span> out of <span class="totalItems">' + totalSize + '</span> records. <a class="get_more" href="#">View more</a>.</p>'
		$(".view_more_records_tal").after(show_more);
	}
	totalRecords.slice(pageSize).addClass("hide");

	$(".get_more").live("click",function(event){
		var currentRecords=totalRecords.not($(".hide"));
		var currentSize=currentRecords.length;
		var totalPage=Math.ceil(totalSize/pageSize);
		if(currentSize + pageSize > totalSize){
			totalRecords.slice(currentSize).removeClass("hide");	
			$(".moreItems").remove();
		}else{
			totalRecords.slice(currentSize,currentSize + pageSize).removeClass("hide");
		}
		$(".moreItems .currentItems").text(currentSize + pageSize);
		event.preventDefault();
    });
	
}

Array.prototype.distinct=function(){
    var a=[],b=[];
    for(var prop in this){
        var d = this[prop];
        if (d===a[prop]) continue;
        if (b[d]!=1){
            a.push(d);
            b[d]=1;
       }
    }
    return a;
}

function removeDuplicate(a){
    var toObject = function(a) { 
        var o = {}; 
        for (var i=0; i<a.length; i++) {  
            o[a[i]] = true; 
        } 
        return o; 
    }; 
    
    var keys = function(o) { 
        var a=[], i; 
        for (i in o) { 
            if (o.hasOwnProperty(i)) { // lang.hasOwnProperty(o, i) 
                a.push($.trim(i)); 
            } 
        } 
        return a; 
    }; 
    
    var uniq = function(a) { 
        return keys(toObject(a)); 
    }; 
    
    return uniq(a);
}

encodeHTML = function (source) {
    return String(source)
        .replace(/&/g,'&amp;')
        .replace(/</g,'&lt;')
        .replace(/>/g,'&gt;')
        .replace(/\\/g,'&#92;')
        .replace(/"/g,'&quot;')
        .replace(/'/g,'&#39;');
};

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
}  

function isXML(str){
    var isXML = true;
    try{
    	isXML = $.parseXML(str);
    }catch(e){
    	isXML = false;
    }
    return isXML;
}

function checkbox_all(all, individual){
	var mark=true;

	function isAllOrNot(){
		individual.each(function(){
			mark=true&& this.checked;
			if(mark==false)
				return mark;
		});
		if(mark==true)
			all.attr("checked","checked");
		else
			all.removeAttr("checked");
	}
			
	isAllOrNot();
	
	all.click(function(){//checked "All", checked the all industry, unchecked "All", unchecked the all industry
		if(this.checked==true)
			individual.attr("checked","checked")
		else
			individual.removeAttr("checked");
	});
	
	individual.click(function(){//all industry are checked , also checked "All", otherwise, unchecked "All".
		isAllOrNot();				
	});

}

function reloadBottom(){
	var url = $("#contextPath").val() + $("#mapping").val() + "/bottom";
	$.ajaxSetup ({ cache: false }); 
	$("#recent_activity").load(url,function(){
		activity_detail();
		more_detail();
		$(".zebra").each(function(i){
			$(this).children("tbody").children("tr:not(.reveal):odd").addClass("even");
		});
		$(".pup_box, .login_s2").click(function(event){event.stopPropagation();});
	});
	
}

function showHiddenDiv(obj,event)//for splash page history log preview splash.
{
	event.preventDefault();
	var hideDiv = $(obj).parents('.hrefDiv').next('.hiddenDiv');
	if(hideDiv.is(":visible")){
		hideDiv.hide();
	}else{
		hideDiv.show();
		$(obj).parents('.pup_box').css({width:'740px', marginLeft:'-370px'});
	}
	
}

function radio_option(radios,options,event){
	var i = radios.index(event);
	options.addClass("hide").eq(i).removeClass("hide");
}

function splash_iframe(){
	$(".more_detail_popbox").find("span.pup_detail_l:contains('Splash Preview')").each(function(i){
		var $this=$(this);
		var splash_wrap = $this.parent("li").next("li");
		if($.trim(splash_wrap.text())=="")
			splash_wrap = splash_wrap.next("li");
			var splash_content = splash_wrap.html();
			splash_num++;
			var splash_iframe = '<iframe name="splash_iframe_' + splash_num +'" width="740" height="200" frameborder="0" ></iframe>'
			splash_wrap.html(splash_iframe);
			var frame = window.frames["splash_iframe_" + splash_num];
			frame.document.open();
			frame.document.write(splash_content);
			frame.document.close();

	})
	$("body").removeAttr("style");
}

function closeWindow(){
	window.open('', '_parent', '');
	window.close();
}


$(document).ready(function(){
	get_selected_left_tabs();//left navitation high light
	get_selected_primary_tabs();//primary navitation high light
	tabsForm();//form tabs high light
	
    /**
     * When we hover over the main nav, show us the next level of nav. 
     * When we mouse away from the nav, hide all second levels.
    */ 
    $('.nav_level_1').hover(function(){
            $('.nav_level_2').hide();
            var select_sub = $(this).children('.nav_level_2');
            if($(".nav_primary > li:nth-child(4) ul > li > ul.nav_level_2_items").length>4){
            	$(".nav_primary > li:nth-child(4) ul").css("left","-335px");
            }
            select_sub.slideDown('fast',function(){
                select_sub.find(".nav_level_2_items").removeClass("last").height(select_sub.height()-5).last().addClass("last"); 
            });
        },function(){}
    );
    $('.nav_primary').mouseleave(function(){
        $('.nav_level_2').hide();
    });


	$('.return_false').submit(function(){ return false}); //for form has ajax submit, don't need the form itself submit
	$('a.return_false').click(function(){ return false});
	browserButton();//browser button
	realBrowserButton();
	
	//for ghost text	
	$(".ghost_text").addClass("textLighter").focus(function(){
		$(this).removeClass("textLighter").css("background","#fff");
		if(this.value==$(this).attr("title")) this.value="";
	}).blur(function(){
		if(isEmpty($(this))){	
			$(this).addClass("textLighter").val($(this).attr("title")).css("background","");
		}
		$(this).css("background","");
	});
	
	// for the activity log details bold name and status.
	activity_detail();
	
	//disable hyper links
	$('a.disable').click(function(){return false;});
	
	//add zebra for table
	$(".zebra").each(function(i){
		$(this).children("tbody").children("tr:not(.reveal):odd").addClass("even");
	});
	//$('.zebra tbody tr:odd').addClass('even');
	
	//add title for long input
	$('.title_show').blur(function(){
		var inputvalue = this.value;
		$(this).attr('title',inputvalue);
	});
	
	//more details show for history
	more_detail();
	
	//$(".pup_box, .login_s2").click(function(){return false;});
	$(".pup_box, .login_s2").click(function(event){event.stopPropagation();});
	//$(".pup_box a , .login_s2 > p").click(function(event){event.stopPropagation();});
	$('body').bind('click',function(){ 
		$(".pup_box, .login_s2 > div").hide(); //hide more details when click other place of the page.
		$(".login_s2 p span").removeClass("border_corb").addClass("border_cort");
		$(".login_s2 input:text").attr("value","");
		$(".login_s2 .valid_err").remove();
		$(".login_s2 input").removeClass("input_valid_err");
	});
	
	$(".login_s2 > p:first").toggle(function(){
			var my = $(".login_s2 > p:first");
			my.next("div").slideToggle("fast",function(){
			my.children("span").removeClass("border_cort").addClass("border_corb");
			$(".login_s2 input:text").attr("value","").focus();
		});
	 },function(){
			var my = $(".login_s2 > p:first");
			$(".login_s2 input:text").attr("value","");
			$(".login_s2 .valid_err").remove();
			$(".login_s2 input").removeClass("input_valid_err");
			my.next("div").slideToggle("fast",function(){
				my.children("span").removeClass("border_corb").addClass("border_cort");
		});
	});
	
});

function goVendorConfiguration(vendorId, url){	    
    $.form(url,
            { vendorId: vendorId }, 'POST').submit();    
}

function goBack(url) {		
	window.location.href = url; 
}