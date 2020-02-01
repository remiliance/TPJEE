package com.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.beans.Client;
import com.beans.Commande;
import com.forms.CreationCommandeForm;

/**
 * Servlet implementation class CreationCommande
 */
@WebServlet("/CreationCommande")
public class CreationCommande extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String ATT_COMMANDE = "commande";
    public static final String ATT_FORM     = "form";
	 public static final String VUE_SUCCES = "/WEB-INF/afficherCommande.jsp";
	public static final String VUE_FORM   = "/WEB-INF/creerCommande.jsp"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreationCommande() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CreationCommandeForm CommandeForm= new CreationCommandeForm();
		
		Commande commande= new Commande();
		
		commande=CommandeForm.creerCommande(request);
		
		
	    /* Ajout du bean et de l'objet métier à l'objet requête */
        request.setAttribute( ATT_COMMANDE, commande );
        request.setAttribute( ATT_FORM, CommandeForm );
		
        HttpSession session = request.getSession();
        
        Map<String, Commande> commandes = (HashMap<String, Commande>) session.getAttribute( "commandes" );
        
        /* Si aucune map n'existe, alors initialisation d'une nouvelle map */
        if ( commandes == null ) {
            commandes = new HashMap<String, Commande>();
        }
        /* Puis ajout du client courant dans la map */
        commandes.put( commande.getClient().getNom(), commande );
        /* Et enfin (ré)enregistrement de la map en session */
        
        session.setAttribute( "commandes", commandes );       
        
        
		if ( CommandeForm.getErreurs().isEmpty() ) {
            /* Si aucune erreur, alors affichage de la fiche récapitulative */
            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
		
		
	}

}
