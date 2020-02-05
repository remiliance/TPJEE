package com.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.beans.Client;
import com.dao.ClientDAO;
import com.dao.DAOFactory;


/**
 * Servlet Filter implementation class ChargementMap
 */
@WebFilter("/ChargementMap")
public class ChargementMap implements Filter {
	
	public static final String CONF_DAO_FACTORY      = "daofactory";
    private ClientDAO clientDao;
    /**
     * Default constructor. 
     */
    public ChargementMap() {
        // TODO Auto-generated constructor stub
    }
    public void init( FilterConfig config ) throws ServletException {
        /* Récupération d'une instance de nos DAO Client et Commande */
        this.clientDao = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
        System.out.println("Chargement du filtre1");
    }
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		 System.out.println("Chargement du filtre2");
		HttpSession session = ((HttpServletRequest) request).getSession();
		
        Map<Long, Client> clients = (HashMap<Long, Client>) session.getAttribute( "clients" );
        
        /* Si aucune map n'existe, alors initialisation d'une nouvelle map */
        if ( clients == null ) {
            clients = new HashMap<Long, Client>();
        }
        /* Puis ajout du client courant dans la map */
        List<Client> listeClients = clientDao.lister();
     //   Map<Long, Client> mapClients = new HashMap<Long, Client>();
        for ( Client client : listeClients ) {
           clients.put( client.getId(), client );
        }
        /* Et enfin (ré)enregistrement de la map en session */
        session.setAttribute( "clients", clients );

		
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}



}
