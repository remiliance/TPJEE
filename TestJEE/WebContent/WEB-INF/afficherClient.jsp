<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Creation Client</title>
<link type="text/css" rel="stylesheet" href="inc/style.css" />
</head>
<body>
	<p>
		<c:import url="/inc/menu.jsp" />

		<c:import url="/inc/inc_client_form.jsp" />

		<c:out value="${ message }" />
	</p>
	<p>Id : ${ client.id }</p>
	<p>Nom : ${ client.nom }</p>
	<p>
		<c:out value="${ client.nom }" />
	</p>
	<p>Prénom : ${ client.prenom }</p>
	<p>Adresse : ${ client.adresse }</p>
	<p>Numéro de téléphone : ${ client.telephone }</p>
	<p>Email : ${ client.email }</p>



</body>
</html>