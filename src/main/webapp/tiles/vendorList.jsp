<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false"%>
<!-- vendor list-->
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}" />
            <div class="panel" style="width:450px">
                <div class="p_body">
                    <table class="data_tal zebra">
                        <thead>
                            <tr>                            
                                <th class="first" width="65">Vendor ID</th>
                                <th width="85">Name</th>
                                <th class="85">Contact</th>
                                <th class="last">Configure</th>
                           </tr>
                        </thead>
                        <tbody>
                        <c:if test="${vendorList != null}">
	                        <c:forEach var="vendor" items="${vendorList}" varStatus="status">
		          				<tr>
		          					<td>${vendor.vendorId}</td>
		          					<td>${vendor.name}</td>
		          					<td>${vendor.contact}</td>
		          					<td><a class="cursor" style="cursor:pointer" onclick="goVendorConfiguration(${vendor.vendorId}, '${pageContext.request.contextPath}/vendorConfiguration')">Configure</a></td>		          					
		          				</tr>
		          			</c:forEach>
	                    </c:if>
                        </tbody>
                    </table>
                    <script>
                   </script>
                </div>
            </div>                          

<!--  vendor list end -->