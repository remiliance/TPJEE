package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.beans.Client;

public class ClientDAO extends DAOUtilitaire<Client>{

	private static final String SQL_INSERT        = "INSERT INTO Client (nom, prenom, adresse, telephone, email, image) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_PAR_ID = "DELETE FROM Client WHERE id = ?";
	private static final String SQL_SELECT        = "SELECT id, nom, prenom, adresse, telephone, email, image FROM Client ORDER BY id";

	 public ClientDAO(DAOFactory daoFactory1) {
		super(daoFactory1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Client obj) throws DAOException {
		
		Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        
        
//       String  url = "jdbc:mysql://localhost:3306/tp_sdzee?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
//       String user = "root";
//       String passwd = "admin";
  	 
        
//  	    if(connect == null){
//  	      try {
//  	    	connect= this.daoFactory.getConnection();
//  	    	  System.out.println("CONNEXION ! ");
//  	      } catch (SQLException e) {
//  	      System.out.println("ERREUR DE CONNEXION ! ");
//  	      }}
//  	      else
//  	    	  System.out.println("Déjà connecté");
  	    
        try {
        	if(connect == null)
        	{
        		connect = daoFactory.getConnection();
        		System.out.println("CONNEXION ! ");
        	}
        	else
        		System.out.println("Déjà connecté");
            preparedStatement = initialisationRequetePreparee( connect, SQL_INSERT, true,
                    obj.getNom(), obj.getPrenom(),
                    obj.getAdresse(), obj.getTelephone(),
                    obj.getEmail(), obj.getImage() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création du client, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                obj.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de la création du client en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connect );
        }
        return true;
    }

	
		
   



	@Override
	public boolean update(Client obj) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Client find(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) throws DAOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		
				Connection connect = null;
		        PreparedStatement preparedStatement = null;
		        ResultSet valeursAutoGenerees = null;
		        try {
		            connect = daoFactory.getConnection();
		            preparedStatement = initialisationRequetePreparee( connect, SQL_DELETE_PAR_ID, true,
		                    id);
		            int statut = preparedStatement.executeUpdate();
		            if ( statut == 0 ) {
		                throw new DAOException( "Échec de la suppression du client" );
		            }
		        } catch ( SQLException e ) {
		            throw new DAOException( e );
		        } finally {
		            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connect );
		        }
		        return true;

	}

	@Override
	public List<Client> lister() throws DAOException {
		List<Client> listing = new ArrayList<Client>();
		Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        try {
        	 connect = daoFactory.getConnection();
             preparedStatement = connect.prepareStatement( SQL_SELECT );
             valeursAutoGenerees = preparedStatement.executeQuery();
             while ( valeursAutoGenerees.next() ) {
                 listing.add(map(valeursAutoGenerees) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connect );
        }		
		return listing;
	}
	   private static Client map( ResultSet resultSet ) throws SQLException {
	        Client client = new Client();
	        client.setId( resultSet.getLong( "id" ) );
	        client.setNom( resultSet.getString( "nom" ) );
	        client.setPrenom( resultSet.getString( "prenom" ) );
	        client.setAdresse( resultSet.getString( "adresse" ) );
	        client.setTelephone( resultSet.getString( "telephone" ) );
	        client.setEmail( resultSet.getString( "email" ) );
	        client.setImage( resultSet.getString( "image" ) );
	        return client;
	    }

	
}
