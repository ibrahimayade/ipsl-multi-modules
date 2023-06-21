<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1"%>

<%@ page isErrorPage="true" %>

<%
  response.setStatus(200);
%>

<html>
	<head>
		<title>MVC - Personnes</title>
	</head>
	<body background="<c:url value="/ressources/standard.jpg"/>">
		<h2>MVC - personnes</h2>
		L'exception suivante s'est produite :
		<%= exception.getMessage()%>
		<br><br>
		<a href="<c:url value="/do/list"/>">Retour &agrave; la liste</a>
	</body>
</html>
