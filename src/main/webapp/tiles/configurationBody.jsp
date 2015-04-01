<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="body.template" >     
     <tiles:putAttribute name="mainContent" value="/tiles/configurationContent.jsp"></tiles:putAttribute>
     <tiles:putAttribute name="bottom" value="/tiles/vendorList.jsp"></tiles:putAttribute>
</tiles:insertDefinition>