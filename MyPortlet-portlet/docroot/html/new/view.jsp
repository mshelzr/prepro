<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

This is the <b>New Portlet</b> portlet in View mode.
<br/>
<c:out value="${toModel}"></c:out>

