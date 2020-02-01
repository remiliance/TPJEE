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

/**
 * Servlet implementation class SuppressionClient
 */
@WebServlet("/SuppressionClient")
public class SuppressionClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE_SUCCES = "/WEB-INF/listerClients.jsp";
	 public static final String VUE              = "/listeClients";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuppressionClient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
			
		HttpSession session = request.getSession();
        
        Map<String, Client> clients = (HashMap<String, Client>) session.getAttribute( "clients" );
        String valeur = request.getParameter( "nomClient" );
        
        clients.remove(valeur);
        
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
