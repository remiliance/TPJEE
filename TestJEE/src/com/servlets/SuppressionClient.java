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
import com.dao.*;

/**
 * Servlet implementation class SuppressionClient
 */
@WebServlet("/SuppressionClient")
public class SuppressionClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE_SUCCES = "/WEB-INF/listerClients.jsp";
	 public static final String VUE              = "/listeClients";
	 public static final String CONF_DAO_FACTORY = "daofactory";
    /**
     * @see HttpServlet#HttpServlet()
     */
	 
	 private ClientDAO clientDao;
	 
    public SuppressionClient() {
        super();
        // TODO Auto-generated constructor stub
    }
    
   

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
    	this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
			
		HttpSession session = request.getSession();
        
        Map<Long, Client> clients = (HashMap<Long, Client>) session.getAttribute( "clients" );
        String valeur = request.getParameter( "idClient" );
        
        long i;
        i= Long.parseLong(valeur);
        
        clients.remove(i);
        clientDao.delete(i);
        
        session.setAttribute("clients", clients);
        
        response.sendRedirect( request.getContextPath() + VUE );
      
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
