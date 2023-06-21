<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs-datetime.tld" prefix="dt" %>

<html>
	<head>
		<title>MVC - Personnes</title>
	</head>
	<body background="<c:url value="/ressources/standard.jpg"/>">
			<c:if test="${erreurs!=null}">
				<h3>Les erreurs suivantes se sont produites :</h3>
				<ul>
					<c:forEach items="${erreurs}" var="erreur">
						<li><c:out value="${erreur}"/></li>
					</c:forEach>
				</ul>
			<hr>
		</c:if>
		<h2>Liste des personnes</h2>
		<table border="1">
			<tr>
				<th>Id</th>
				<th>Version</th>
				<th>Pr&eacute;nom</th>
				<th>Nom</th>
				<th>Date de naissance</th>
				<th>Mari&eacute;</th>
				<th>Nombre d'enfants</th>
				<th></th>
			</tr>
			<c:forEach var="personne" items="${personnes}">
				<tr>
					<td><c:out value="${personne.id}"/></td>
					<td><c:out value="${personne.version}"/></td>
					<td><c:out value="${personne.prenom}"/></td>
					<td><c:out value="${personne.nom}"/></td>
					<td><dt:format pattern="dd/MM/yyyy">${personne.dateNaissance.time}</dt:format></td>
          <td><c:out value="${personne.marie}"/></td>
					<td><c:out value="${personne.nbEnfants}"/></td>
					<td><a href="<c:url value="/do/edit?id=${personne.id}"/>">Modifier</a></td>
					<td><a href="<c:url value="/do/delete?id=${personne.id}"/>">Supprimer</a></td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<a href="<c:url value="/do/edit?id=-1"/>">Ajout</a>
	</body>
</html>
