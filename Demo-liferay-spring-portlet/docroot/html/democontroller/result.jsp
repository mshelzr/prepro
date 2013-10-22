<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<portlet:defineObjects />

<portlet:renderURL var="irCrear" >
 <portlet:param name="action" value="cud"></portlet:param>
 <portlet:param name="persona" value="${persona}"></portlet:param> 
 <portlet:param name="operacion" value="_create"></portlet:param> 
</portlet:renderURL>

<portlet:renderURL var="irModificar" >
 <portlet:param name="action" value="cud"></portlet:param>
 <portlet:param name="persona" value="${persona}"></portlet:param> 
 <portlet:param name="operacion" value="_update"></portlet:param> 
</portlet:renderURL>

<portlet:renderURL var="irBorrar" >
 <portlet:param name="action" value="cud"></portlet:param>
 <portlet:param name="persona" value="${persona}"></portlet:param> 
 <portlet:param name="operacion" value="_delete"></portlet:param> 
</portlet:renderURL>

<form:form commandName="persona" name="fm" id="fmid" >
	<form:label path="nombre">Nombre: </form:label>
	<form:input path="nombre"/><br/>
	<form:label path="apellido">Apellido: </form:label>
	<form:input path="apellido" /> <br/>
	<form:label path="telf">Telefono: </form:label>
	<form:input path="telf"/><br/>
	<form:label path="sexo">Sexo: </form:label>
	<form:select path="sexo">
		<form:option value="Masculino">Masculino</form:option>
		<form:option value="Femenino">Femenino</form:option>
	</form:select><br/>
	<c:if test="${empty persona.nombre}">
		<input type="submit" value="Crear" onclick="document.getElementById('fmid').action='${irCrear}';" />
	</c:if>
	<c:if test="${not empty persona.nombre}">
		<input type="submit" value="Modificar" onclick="document.getElementById('fmid').action='${irModificar}';" />
		<input type="submit" value="Eliminar" onclick="document.getElementById('fmid').action='${irBorrar}';"></input>
	</c:if>
</form:form>

</body>
</html>