<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs-datetime.tld" prefix="dt" %>

<html>
	<head>
		<title>MVC - Personnes</title>
	</head>
	<body background="../ressources/standard.jpg">
		<h2>Ajout/Modification d'une personne</h2>
		<c:if test="${erreurEdit!=''}">
			<h3>Echec de la mise à jour :</h3>
  		L'erreur suivante s'est produite : ${erreurEdit}
			<hr>
		</c:if>
		<form method="post" action="<c:url value="/do/validate"/>">
			<table border="1">
				<tr>
					<td>Id</td>
					<td>${id}</td>
				</tr>
				<tr>
					<td>Version</td>
					<td>${version}</td>
				</tr>
				<tr>
					<td>Pr&eacute;nom</td>
					<td>
						<input type="text" value="${prenom}" name="prenom" size="20">
					</td>
					<td>${erreurPrenom}</td>
				</tr>
				<tr>
					<td>Nom</td>
					<td>
						<input type="text" value="${nom}" name="nom" size="20">
					</td>
					<td>${erreurNom}</td>
				</tr>
				<tr>
				<td>Date de naissance (JJ/MM/AAAA)</td>
					<td>
						<input type="text" value="${dateNaissance}" name="dateNaissance">
					</td>
					<td>${erreurDateNaissance}</td>
				</tr>
				<tr>
					<td>Mari&eacute;</td>
					<td>
						<c:choose>
							<c:when test="${marie}">
								<input type="radio" name="marie" value="true" checked>Oui
								<input type="radio" name="marie" value="false">Non
							</c:when>
							<c:otherwise>
								<input type="radio" name="marie" value="true">Oui
								<input type="radio" name="marie" value="false" checked>Non
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td>Nombre d'enfants</td>
					<td>
						<input type="text" value="${nbEnfants}" name="nbEnfants">
					</td>
					<td>${erreurNbEnfants}</td>
				</tr>
			</table>
			<br>
			<input type="hidden" value="${id}" name="id">
      <input type="hidden" value="${version}" name="version">
			<input type="submit" value="Valider">
			<a href="<c:url value="/do/list"/>">Annuler</a>
		</form>
	</body>
</html>
