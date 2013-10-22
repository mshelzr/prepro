<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

	
<portlet:renderURL var="consultarPers">
	<portlet:param name="action" value="consultarPersona"></portlet:param>
	<portlet:param name="nombre" value=""></portlet:param>			
</portlet:renderURL>
		
<portlet:renderURL var="toPdfURL">
	<portlet:param name="action" value="toPdf"></portlet:param>
</portlet:renderURL>
		
<portlet:defineObjects />
<form:form id="fmid">
	<c:forEach items="${listaPersona}" var="persona">
	
		<portlet:renderURL var="executeInner">
			<portlet:param name="action" value="consultarPersona"></portlet:param>
			<portlet:param name="nombre" value="${persona.nombre}"></portlet:param>			
		</portlet:renderURL>
		
		<a href="${executeInner}">
			${persona.idPersona}, ${persona.nombre}, ${persona.apellido} 
		</a>
		<br />
	</c:forEach>
	<br/>
	<input type="submit" value="Nueva Persona" onclick="document.getElementById('fmid').action='${consultarPers}';submit()"></input>
	<br/>
	<input type="button" value="Submit"
    onClick="location.href = '<portlet:resourceURL><portlet:param name="reportType" value="pdf" /></portlet:resourceURL>'" />
</form:form>
