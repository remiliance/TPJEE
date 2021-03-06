package com.servlets;
import com.dao.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.beans.Client;
import com.beans.Commande;
import com.forms.CreationClientForm;

public class CreationClient extends HttpServlet {
    public static final String ATT_CLIENT = "client";
    public static final String ATT_FORM   = "form";
	private static final long serialVersionUID = 1L;
	 public static final String VUE_SUCCES = "/WEB-INF/afficherClient.jsp";
	public static final String VUE_FORM   = "/WEB-INF/creerClient.jsp"; 
	  public static final String CONF_DAO_FACTORY = "daofactory";
	
	 private ClientDAO clientDao;
	


	
	 public void init() throws ServletException {
	        /* R�cup�ration d'une instance de notre DAO Utilisateur */
	        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
	    }

   
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
       
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  /* Pr�paration de l'objet formulaire */
       
				
		CreationClientForm form = new CreationClientForm(this.clientDao);       

        /* Traitement de la requ�te et r�cup�ration du bean en r�sultant */
        Client client = form.creerClient( request );

        /* Ajout du bean et de l'objet m�tier � l'objet requ�te */
        request.setAttribute( ATT_CLIENT, client );
        request.setAttribute( ATT_FORM, form );
        
        HttpSession session = request.getSession();
        Map<Long, Client> clients = (HashMap<Long, Client>) session.getAttribute( "clients" );
        
        /* Si aucune map n'existe, alors initialisation d'une nouvelle map */
        if ( clients == null ) {
            clients = new HashMap<Long, Client>();
        }
        /* Puis ajout du client courant dans la map */
        clients.put( client.getId(), client );
        /* Et enfin (r�)enregistrement de la map en session */
        session.setAttribute( "clients", clients );

        

        if ( form.getErreurs().isEmpty() ) {
            /* Si aucune erreur, alors affichage de la fiche r�capitulative */
            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
            /* Sinon, r�-affichage du formulaire de cr�ation avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
    }   
}