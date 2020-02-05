package com.forms;

import java.io.IOException;

import com.dao.*;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.beans.Client;


import eu.medsea.mimeutil.MimeUtil;

public class CreationClientForm {

		private static final String CHAMP_NOM       = "nomClient";
	    private static final String CHAMP_PRENOM    = "prenomClient";
	    private static final String CHAMP_ADRESSE   = "adresseClient";
	    private static final String CHAMP_TELEPHONE = "telephoneClient";
	    private static final String CHAMP_EMAIL     = "emailClient";
	    private static final String CHAMP_FICHIER     = "imageClient";
	    
	    private String              resultat;
	    private DAOFactory daoFactory;
	    private ClientDAO clientDao /*= new ClientDAO(daoFactory)*/;
	    
	    private Map<String, String> erreurs         = new HashMap<String, String>();

	    //Constructeur
	    public CreationClientForm( ClientDAO clientDao ) {
	        this.clientDao = clientDao;
	    }
	    	    
	    public Map<String, String> getErreurs() {
	        return erreurs;
	    }

	    public String getResultat() {
	        return resultat;
	    }
	    
	    public Client creerClient( HttpServletRequest request ) {
	        String nom = getValeurChamp( request, CHAMP_NOM );
	        String prenom = getValeurChamp( request, CHAMP_PRENOM );
	        String adresse = getValeurChamp( request, CHAMP_ADRESSE );
	        String telephone = getValeurChamp( request, CHAMP_TELEPHONE );
	        String email = getValeurChamp( request, CHAMP_EMAIL );
	        String fichier = getValeurChamp( request, CHAMP_FICHIER );       
	        
	        Client client = new Client();

	        try {
	            validationNom( nom );
	        } catch ( Exception e ) {
	            setErreur( CHAMP_NOM, e.getMessage() );
	        }
	        client.setNom( nom );

	        try {
	            validationPrenom( prenom );
	        } catch ( Exception e ) {
	            setErreur( CHAMP_PRENOM, e.getMessage() );
	        }
	        client.setPrenom( prenom );

	        try {
	            validationAdresse( adresse );
	        } catch ( Exception e ) {
	            setErreur( CHAMP_ADRESSE, e.getMessage() );
	        }
	        client.setAdresse( adresse );

	        try {
	            validationTelephone( telephone );
	        } catch ( Exception e ) {
	            setErreur( CHAMP_TELEPHONE, e.getMessage() );
	        }
	        client.setTelephone( telephone );

	        try {
	            validationEmail( email );
	        } catch ( Exception e ) {
	            setErreur( CHAMP_EMAIL, e.getMessage() );
	        }
	        client.setEmail( email );
	        
//	        String image= null;
//	        try {	           
//	           image=validationFichier( request, fichier );
//	        } catch ( Exception e ) {
//	            setErreur( CHAMP_FICHIER, e.getMessage() );
//	        }
//	        client.setCheminPhoto(image);
//	        System.out.println(image);
	        try {
	        if ( erreurs.isEmpty() ) {
	            resultat = "Succès de la création du client. Insertion en database";
	            boolean crea=false;
	            crea=clientDao.create(client);            
	        } else {
	            resultat = "Échec de la création du client.";
	        }
	        }
	        catch ( DAOException e ) {
	            setErreur( "imprévu", "Erreur imprévue lors de la création." );
	            resultat = "Échec de la création du client : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
	            e.printStackTrace();
	        }
	    
	        return client;
	    }
	    
	    
	    private String validationFichier( HttpServletRequest request, String nom ) throws Exception {
	    	 /*
	         * Récupération du contenu du champ image du formulaire. Il faut ici
	         * utiliser la méthode getPart().
	         */
	        String nomFichier = null;
	        InputStream contenuFichier = null;
	        
	        try {
	        	javax.servlet.http.Part part = request.getPart( CHAMP_FICHIER );
	            nomFichier = getNomFichier( part );

	            /*
	             * Si la méthode getNomFichier() a renvoyé quelque chose, il s'agit
	             * donc d'un champ de type fichier (input type="file").
	             */
	            if ( nomFichier != null && !nomFichier.isEmpty() ) {
	                /*
	                 * Antibug pour Internet Explorer, qui transmet pour une raison
	                 * mystique le chemin du fichier local à la machine du client...
	                 * 
	                 * Ex : C:/dossier/sous-dossier/fichier.ext
	                 * 
	                 * On doit donc faire en sorte de ne sélectionner que le nom et
	                 * l'extension du fichier, et de se débarrasser du superflu.
	                 */
	                nomFichier = nomFichier.substring( nomFichier.lastIndexOf( '/' ) + 1 )
	                        .substring( nomFichier.lastIndexOf( '\\' ) + 1 );

	                /* Récupération du contenu du fichier */
	                
	                contenuFichier = part.getInputStream();

	                /* Extraction du type MIME du fichier depuis l'InputStream */
	                MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
	                Collection<?> mimeTypes = MimeUtil.getMimeTypes( contenuFichier );

	                /*
	                 * Si le fichier est bien une image, alors son en-tête MIME
	                 * commence par la chaîne "image"
	                 */
	                if ( mimeTypes.toString().startsWith( "image" ) ) {
	                    /* Ecriture du fichier sur le disque */
	                    //ecrireFichier( contenuFichier, nomFichier, chemin );
	                } else {
	                    throw new Exception( "Le fichier envoyé doit être une image." );
	                }
	            }
	        } catch ( IllegalStateException e ) {
	            /*
	             * Exception retournée si la taille des données dépasse les limites
	             * définies dans la section <multipart-config> de la déclaration de
	             * notre servlet d'upload dans le fichier web.xml
	             */
	            e.printStackTrace();
	            throw new Exception( "Le fichier envoyé ne doit pas dépasser 1Mo." );
	        } catch ( IOException e ) {
	            /*
	             * Exception retournée si une erreur au niveau des répertoires de
	             * stockage survient (répertoire inexistant, droits d'accès
	             * insuffisants, etc.)
	             */
	            e.printStackTrace();
	            throw new Exception( "Erreur de configuration du serveur." );
	        } catch ( ServletException e ) {
	            /*
	             * Exception retournée si la requête n'est pas de type
	             * multipart/form-data.
	             */
	            e.printStackTrace();
	            throw new Exception(
	                    "Ce type de requête n'est pas supporté, merci d'utiliser le formulaire prévu pour envoyer votre fichier." );
	        }

	        return nomFichier;
	    }

	    
	    private void validationNom( String nom ) throws Exception {
	        if ( nom != null && nom.length() < 2 ) {
	            throw new Exception( "Le nom d'utilisateur doit contenir au moins 2 caractères." );
	        }
	    }
	    
	    private void validationPrenom( String prenom ) throws Exception {
	        if ( prenom != null && prenom.length() < 2 ) {
	            throw new Exception( "Le prénom d'utilisateur doit contenir au moins 2 caractères." );
	        }
	    }

	    private void validationAdresse( String adresse ) throws Exception {
	        if ( adresse != null ) {
	            if ( adresse.length() < 5 ) {
	                throw new Exception( "L'adresse de livraison doit contenir au moins 10 caractères." );
	            }
	        } else {
	            throw new Exception( "Merci d'entrer une adresse de livraison." );
	        }
	    }

	    private void validationTelephone( String telephone ) throws Exception {
	        if ( telephone != null ) {
	            if ( !telephone.matches( "^\\d+$" ) ) {
	                throw new Exception( "Le numéro de téléphone doit uniquement contenir des chiffres." );
	            } else if ( telephone.length() < 4 ) {
	                throw new Exception( "Le numéro de téléphone doit contenir au moins 4 chiffres." );
	            }
	        } else {
	            throw new Exception( "Merci d'entrer un numéro de téléphone." );
	        }
	    }

	    private void validationEmail( String email ) throws Exception {
	        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
	            throw new Exception( "Merci de saisir une adresse mail valide." );
	        }
	    }
	    
	    

	    /*
	     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	     */
	    private void setErreur( String champ, String message ) {
	        erreurs.put( champ, message );
	    }

	    /*
	     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	     * sinon.
	     */
	    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
	        String valeur = request.getParameter( nomChamp );
	        if ( valeur == null || valeur.trim().length() == 0 ) {
	            return null;
	        } else {
	            return valeur;
	        }
	    }
	    
	    private static String getNomFichier( javax.servlet.http.Part part ) {
	        /* Boucle sur chacun des paramètres de l'en-tête "content-disposition". */
	        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
	        	/* Recherche de l'éventuelle présence du paramètre "filename". */
	            if ( contentDisposition.trim().startsWith("filename") ) {
	                /* Si "filename" est présent, alors renvoi de sa valeur, c'est-à-dire du nom de fichier. */
	                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 );
	            }
	        }
	        /* Et pour terminer, si rien n'a été trouvé... */
	        return null;
	    }
	    
	    
	}
