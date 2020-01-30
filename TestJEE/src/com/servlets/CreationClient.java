package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.Client;
import com.forms.CreationClientForm;

public class CreationClient extends HttpServlet {
    public static final String ATT_CLIENT = "client";
    public static final String ATT_FORM   = "form";
	private static final long serialVersionUID = 1L;
	 public static final String VUE_SUCCES = "/WEB-INF/afficherClient.jsp";
	public static final String VUE_FORM   = "/WEB-INF/creerClient.jsp"; 
   /**
    * @see HttpServlet#HttpServlet()
    */
   public CreationClient() {
       super();
       // TODO Auto-generated constructor stub
   }
	
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
       
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  /* Pr�paration de l'objet formulaire */
        CreationClientForm form = new CreationClientForm();

        /* Traitement de la requ�te et r�cup�ration du bean en r�sultant */
        Client client = form.creerClient( request );

        /* Ajout du bean et de l'objet m�tier � l'objet requ�te */
        request.setAttribute( ATT_CLIENT, client );
        request.setAttribute( ATT_FORM, form );

        if ( form.getErreurs().isEmpty() ) {
            /* Si aucune erreur, alors affichage de la fiche r�capitulative */
            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
            /* Sinon, r�-affichage du formulaire de cr�ation avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
    }   
}