package com.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class DAOUtilitaire <T> {
	public Connection connect = null;
	public DAOFactory daoFactory;
	   
	  public DAOUtilitaire( DAOFactory daoFactory1 ) {
	        this.daoFactory = daoFactory1;
	    }
	  
	  
	  public abstract boolean create(T obj) throws DAOException;	 
	  public abstract boolean delete(Long id)throws DAOException;
	  public abstract boolean update(T obj)throws DAOException;
	  public abstract T find(int id)throws DAOException;
	  public abstract List<T> lister() throws DAOException;

	  
	  /*
	   * Initialise la requête préparée basée sur la connexion passée en argument,
	   * avec la requête SQL et les objets donnés.
	   */
	  public PreparedStatement initialisationRequetePreparee( Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
	      PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
	      for ( int i = 0; i < objets.length; i++ ) {
	          preparedStatement.setObject( i + 1, objets[i] );
	      }
	      return preparedStatement;
	  }
	  /* Fermeture silencieuse du resultset */
	  public static void fermetureSilencieuse( ResultSet resultSet ) {
	      if ( resultSet != null ) {
	          try {
	              resultSet.close();
	          } catch ( SQLException e ) {
	              System.out.println( "Échec de la fermeture du ResultSet : " + e.getMessage() );
	          }
	      }
	  }

	  /* Fermeture silencieuse du statement */
	  public static void fermetureSilencieuse( Statement statement ) {
	      if ( statement != null ) {
	          try {
	              statement.close();
	          } catch ( SQLException e ) {
	              System.out.println( "Échec de la fermeture du Statement : " + e.getMessage() );
	          }
	      }
	  }

	  /* Fermeture silencieuse de la connexion */
	  public static void fermetureSilencieuse( Connection connexion ) {
	      if ( connexion != null ) {
	          try {
	              connexion.close();
	          } catch ( SQLException e ) {
	              System.out.println( "Échec de la fermeture de la connexion : " + e.getMessage() );
	          }
	      }
	  }

	  /* Fermetures silencieuses du statement et de la connexion */
	  public static void fermeturesSilencieuses( Statement statement, Connection connexion ) {
	      fermetureSilencieuse( statement );
	      fermetureSilencieuse( connexion );
	  }

	  /* Fermetures silencieuses du resultset, du statement et de la connexion */
	  public static void fermeturesSilencieuses( ResultSet resultSet, Statement statement, Connection connexion ) {
	      fermetureSilencieuse( resultSet );
	      fermetureSilencieuse( statement );
	      fermetureSilencieuse( connexion );
	  }
}

