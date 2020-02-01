<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="menu">
    <p><a href="<c:url value="/creationClient"/>">Créer un nouveau client</a></p>
    <p><a href="<c:url value="/creationCommande"/>">Créer une nouvelle commande</a></p>
    <p><a href="<c:url value="/listeClients"/>">Liste des clients</a></p>
    <p><a href="<c:url value="/listeCommandes"/>">Liste des commandes</a></p>
</div>