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
	            resultat = "Succ�s de la cr�ation du client. Insertion en database";
	            boolean crea=false;
	            crea=clientDao.create(client);            
	        } else {
	            resultat = "�chec de la cr�ation du client.";
	        }
	        }
	        catch ( DAOException e ) {
	            setErreur( "impr�vu", "Erreur impr�vue lors de la cr�ation." );
	            resultat = "�chec de la cr�ation du client : une erreur impr�vue est survenue, merci de r�essayer dans quelques instants.";
	            e.printStackTrace();
	        }
	    
	        return client;
	    }
	    
	    
	    private String validationFichier( HttpServletRequest request, String nom ) throws Exception {
	    	 /*
	         * R�cup�ration du contenu du champ image du formulaire. Il faut ici
	         * utiliser la m�thode getPart().
	         */
	        String nomFichier = null;
	        InputStream contenuFichier = null;
	        
	        try {
	        	javax.servlet.http.Part part = request.getPart( CHAMP_FICHIER );
	            nomFichier = getNomFichier( part );

	            /*
	             * Si la m�thode getNomFichier() a renvoy� quelque chose, il s'agit
	             * donc d'un champ de type fichier (input type="file").
	             */
	            if ( nomFichier != null && !nomFichier.isEmpty() ) {
	                /*
	                 * Antibug pour Internet Explorer, qui transmet pour une raison
	                 * mystique le chemin du fichier local � la machine du client...
	                 * 
	                 * Ex : C:/dossier/sous-dossier/fichier.ext
	                 * 
	                 * On doit donc faire en sorte de ne s�lectionner que le nom et
	                 * l'extension du fichier, et de se d�barrasser du superflu.
	                 */
	                nomFichier = nomFichier.substring( nomFichier.lastIndexOf( '/' ) + 1 )
	                        .substring( nomFichier.lastIndexOf( '\\' ) + 1 );

	                /* R�cup�ration du contenu du fichier */
	                
	                contenuFichier = part.getInputStream();

	                /* Extraction du type MIME du fichier depuis l'InputStream */
	                MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
	                Collection<?> mimeTypes = MimeUtil.getMimeTypes( contenuFichier );

	                /*
	                 * Si le fichier est bien une image, alors son en-t�te MIME
	                 * commence par la cha�ne "image"
	                 */
	                if ( mimeTypes.toString().startsWith( "image" ) ) {
	                    /* Ecriture du fichier sur le disque */
	                    //ecrireFichier( contenuFichier, nomFichier, chemin );
	                } else {
	                    throw new Exception( "Le fichier envoy� doit �tre une image." );
	                }
	            }
	        } catch ( IllegalStateException e ) {
	            /*
	             * Exception retourn�e si la taille des donn�es d�passe les limites
	             * d�finies dans la section <multipart-config> de la d�claration de
	             * notre servlet d'upload dans le fichier web.xml
	             */
	            e.printStackTrace();
	            throw new Exception( "Le fichier envoy� ne doit pas d�passer 1Mo." );
	        } catch ( IOException e ) {
	            /*
	             * Exception retourn�e si une erreur au niveau des r�pertoires de
	             * stockage survient (r�pertoire inexistant, droits d'acc�s
	             * insuffisants, etc.)
	             */
	            e.printStackTrace();
	            throw new Exception( "Erreur de configuration du serveur." );
	        } catch ( ServletException e ) {
	            /*
	             * Exception retourn�e si la requ�te n'est pas de type
	             * multipart/form-data.
	             */
	            e.printStackTrace();
	            throw new Exception(
	                    "Ce type de requ�te n'est pas support�, merci d'utiliser le formulaire pr�vu pour envoyer votre fichier." );
	        }

	        return nomFichier;
	    }

	    
	    private void validationNom( String nom ) throws Exception {
	        if ( nom != null && nom.length() < 2 ) {
	            throw new Exception( "Le nom d'utilisateur doit contenir au moins 2 caract�res." );
	        }
	    }
	    
	    private void validationPrenom( String prenom ) throws Exception {
	        if ( prenom != null && prenom.length() < 2 ) {
	            throw new Exception( "Le pr�nom d'utilisateur doit contenir au moins 2 caract�res." );
	        }
	    }

	    private void validationAdresse( String adresse ) throws Exception {
	        if ( adresse != null ) {
	            if ( adresse.length() < 5 ) {
	                throw new Exception( "L'adresse de livraison doit contenir au moins 10 caract�res." );
	            }
	        } else {
	            throw new Exception( "Merci d'entrer une adresse de livraison." );
	        }
	    }

	    private void validationTelephone( String telephone ) throws Exception {
	        if ( telephone != null ) {
	            if ( !telephone.matches( "^\\d+$" ) ) {
	                throw new Exception( "Le num�ro de t�l�phone doit uniquement contenir des chiffres." );
	            } else if ( telephone.length() < 4 ) {
	                throw new Exception( "Le num�ro de t�l�phone doit contenir au moins 4 chiffres." );
	            }
	        } else {
	            throw new Exception( "Merci d'entrer un num�ro de t�l�phone." );
	        }
	    }

	    private void validationEmail( String email ) throws Exception {
	        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
	            throw new Exception( "Merci de saisir une adresse mail valide." );
	        }
	    }
	    
	    

	    /*
	     * Ajoute un message correspondant au champ sp�cifi� � la map des erreurs.
	     */
	    private void setErreur( String champ, String message ) {
	        erreurs.put( champ, message );
	    }

	    /*
	     * M�thode utilitaire qui retourne null si un champ est vide, et son contenu
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
	        /* Boucle sur chacun des param�tres de l'en-t�te "content-disposition". */
	        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
	        	/* Recherche de l'�ventuelle pr�sence du param�tre "filename". */
	            if ( contentDisposition.trim().startsWith("filename") ) {
	                /* Si "filename" est pr�sent, alors renvoi de sa valeur, c'est-�-dire du nom de fichier. */
	                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 );
	            }
	        }
	        /* Et pour terminer, si rien n'a �t� trouv�... */
	        return null;
	    }
	    
	    
	}
