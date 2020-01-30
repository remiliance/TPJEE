<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<label for="nomClient">Nom <span class="requis">*</span></label>
<input type="text" id="nomClient" name="nomClient" value="" size="20"
	maxlength="20" />
<br />

<label for="prenomClient">Prénom </label>
<input type="text" id="prenomClient" name="prenomClient" value=""
	size="20" maxlength="20" />
<br />

<label for="adresseClient">Adresse de livraison <span
	class="requis">*</span></label>
<input type="text" id="adresseClient" name="adresseClient" value=""
	size="20" maxlength="20" />
<br />

<label for="telephoneClient">Numéro de téléphone <span
	class="requis">*</span></label>
<input type="text" id="telephoneClient" name="telephoneClient" value=""
	size="20" maxlength="20" />
<br />

<label for="emailClient">Adresse email</label>
<input type="email" id="emailClient" name="emailClient" value=""
	size="20" maxlength="60" />
<br />



	<label for="dateCommande">Date <span class="requis">*</span></label> <input
		type="text" id="dateCommande" name="dateCommande" value="" size="20"
		maxlength="20" disabled /> <br /> <label for="montantCommande">Montant
		<span class="requis">*</span>
	</label> <input type="text" id="montantCommande" name="montantCommande"
		value="" size="20" maxlength="20" /> <br /> <label
		for="modePaiementCommande">Mode de paiement <span
		class="requis">*</span></label> <input type="text" id="modePaiementCommande"
		name="modePaiementCommande" value="" size="20" maxlength="20" /> <br />

	<label for="statutPaiementCommande">Statut du paiement</label> <input
		type="text" id="statutPaiementCommande" name="statutPaiementCommande"
		value="" size="20" maxlength="20" /> <br /> <label
		for="modeLivraisonCommande">Mode de livraison <span
		class="requis">*</span></label> <input type="text" id="modeLivraisonCommande"
		name="modeLivraisonCommande" value="" size="20" maxlength="20" /> <br />

	<label for="statutLivraisonCommande">Statut de la livraison</label> <input
		type="text" id="statutLivraisonCommande"
		name="statutLivraisonCommande" value="" size="20" maxlength="20" />