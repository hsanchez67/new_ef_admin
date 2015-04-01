<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="main">
    <div class="main_wrap">
        <tiles:insertAttribute name="topNav" />
        <div class="main_content position_rel">
            <div class="section clearfix">
                <tiles:insertAttribute name="mainContent" />
            </div>      
 
        </div>
        
        
    </div>
</div>