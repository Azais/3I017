package service;



/*UserServices répond au TD2
 * Ses fonctions vont appeler des fonctions dans UserTools
 * Ce sont les premières fonctions à écrire  */

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import bd.KeyNotFoundException;
import bd.UserTools;
import bd.userNotFoundException;

public class UserServices {

	/** Enlever un ami/followed (les amis sont les personnes qu'on suit)
	 * 
	 * @param key la clé de session
	 * @param idB le login de l'ami à enlever
	 * @return un JSONObject indiquant le succès ou l'echec de l'opération
	 */
	
	public static JSONObject removeFriend(String key, int idB){
		try{
			if(key==null)
				return ServiceTools.serviceRefused("Paramètres manquants", -1);
			
			if(!UserTools.keyVerified(key))
				return ServiceTools.serviceRefused("Non connecté!!!", 3);
			int idA=UserTools.idKey(key);
			
			if(!UserTools.isFriend(idA, idB))
				return ServiceTools.serviceRefused("vous n'êtes pas potes", 52);
			UserServices.removeFriend(key, idB);
			return ServiceTools.serviceAccepted();
		}catch(SQLException e){
			return ServiceTools.serviceRefused("erreur SQL", -1);
		} catch (KeyNotFoundException e) {
			// ne doit pas arriver
			return ServiceTools.serviceRefused("erreur", -3);
		}
	}
	
	/** Crée un utilisateur de pseudo login. 
	 * 	
	 * @param login le pseudo de l'utilisateur (string)
	 * @param password le mot de passe (string)
	 * @param nom le nom de famille (string)
	 * @param prenom le prénom de l'utilisateur (string)
	 * @return un JSONObject indiquant que le service a réussi ou l'erreur
	 */
	
	public static JSONObject createUser(String login, String password, String nom, String prenom){
		
		if((login==null)||(password==null)||(nom==null)||(prenom==null)){
			return ServiceTools.serviceRefused("Paramètres manquants", -1);
		}
		
		// Verifier que le login utilisateur n'est pas déjà dans la bd
		
		try {
			if(UserTools.userExists(login)){
				return ServiceTools.serviceRefused("Deja dans la base", 1);
			}else{
			// Ajouter l'utilisateur à la BD
				try {
					UserTools.register(login, nom, prenom, password);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ServiceTools.serviceAccepted();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ServiceTools.serviceRefused("erreur SQL", -1);
		}
	}
/*	
	public static JSONObject addComment(String key, int author_id, String login,String text){
		if (key==null || author_id)
		try{
			if(!UserTools.keyVerified(key))
				return ServiceTools.serviceRefused("Non connecté!!!", 3);
		
		
			UserTools.addComment(author_id, login, text);
			return ServiceTools.serviceAccepted();
		}catch(UnknownHostException | MongoException e){
			return ServiceTools.serviceRefused("Erreur base MongoDB", -2);
		}catch(SQLException e){
			return ServiceTools.serviceRefused("erreur sql", -1);
		}
	}
	
*/	
	/** Permet à un utilisateur de se connecter
	 * 
	 * @param login le login de l'utilisateur
	 * @param password sont mot de passe
	 * @return Un JSONObject indiquant la réussite ou l'echec de l'opération
	 */
	
	public static JSONObject login(String login, String password) {
		if((login==null)||(password==null)){
			return ServiceTools.serviceRefused("Paramètres manquants", -1);
		}
		// Verifier que l'utilisateur (login) existe
		try {
			if(UserTools.userExists(login)){
				
				// Verifier que c'est le bon mdp
				if(UserTools.checkPassword(login, password)){
					
					// Verifier que session pas ouverte
					try{
						
						String clef=null;
						try {
							clef = UserTools.sessionKey(login);
						} catch (userNotFoundException e) {
							// ne doit pas arriver
							e.printStackTrace();
						}
						int id=0;
						try{
							id=UserTools.idLogin(login);
						}catch(userNotFoundException e){
							//ne doit pas arriver
						}
			
						JSONObject res=new JSONObject();
						res.put("key", clef);
						res.put("id", id);
						
						return res;
					}catch(KeyNotFoundException e){
						String clef=null;
						try {
							clef = UserTools.insertSession(login, false);
						} catch (userNotFoundException e2) {
							// ne doit pas arriver
							e2.printStackTrace();
						}
						
						JSONObject res=new JSONObject();
						int id=0;
						try{
							id=UserTools.idLogin(login);
						}catch(userNotFoundException e2){
							//ne doit pas arriver
						}
						try {
							res.put("key", clef);
							res.put("id", id);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
						return res;
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				}else{
					return ServiceTools.serviceRefused("Mot de passe incorrect", 2);
				}
				
			}else {
				return ServiceTools.serviceRefused("Login inconnu", 1);
			}
		} catch (SQLException e) {
			return ServiceTools.serviceRefused("Erreur SQL", -1);
		}
	}
	
	/** On deconnecte la session key
	 * 
	 * @param key la clé de session
	 * @return un JSONObject indiquant la réussite ou l'échec du service
	 */
	
	public static JSONObject logout(String key){
		
		if (key==null)
			return ServiceTools.serviceRefused("parametre manquant", -1);
		try {
			if(UserTools.keyVerified(key)){
				bd.UserTools.logout(key);
				return ServiceTools.serviceAccepted();
			}else{
				return ServiceTools.serviceRefused("clé n'existe pas", -1);
			}
		} catch (SQLException e) {
			return ServiceTools.serviceRefused("Erreur SQL", -1);
		}
	}
	
	
	
	
	/** Permet d'ajouter un commentaire à la base. L'auteur est fourni en consultant la clef de session
	 * 
	 * @param key la clef de session 
	 * @param text le texte du commentaire
	 * @return un JSONObject indiquant la réussite ou l'échec du service
	 */
	
	
	public static JSONObject addComment(String key, String text){
		//vérifie les paramètrees 
		if (key==null || text==null)
			return ServiceTools.serviceRefused("parametre manquant", -1);
		
		//teste si on est connecté
		try{
			if(!UserTools.keyVerified(key))
				return ServiceTools.serviceRefused("Non connecté!!!", 3);
		
			int id = UserTools.idKey(key);
			String login = UserTools.loginId(id);
			UserTools.addComment(id,login,text);
			return ServiceTools.serviceAccepted();

		}catch (KeyNotFoundException| userNotFoundException e) {
			//ne doit pas arriver
			return ServiceTools.serviceRefused("utilisateur banni", 5);
		}catch(SQLException e){
			return ServiceTools.serviceRefused("erreur sql", -1);
		} catch (UnknownHostException e) {
			return ServiceTools.serviceRefused("erreur Mango", -2);
		} catch (MongoException e) {
			return ServiceTools.serviceRefused("erreur Mango", -2);
		}		
		
	}
	
	/**
	 * Ajoute un follower/ami logA à logB
	 * @param logA le login de l'utilisateur qui souhaite suivre un autre
	 * @param logB le login de l'ulisateur suivi
	 * @return
	 */
	public static JSONObject addFriend(String key, int idB){
		
		try{
			if(!UserTools.keyVerified(key))
				return ServiceTools.serviceRefused("Non connecté!!!", 3);
			int idA=UserTools.idKey(key);
			String logA=UserTools.loginId(idA);
			String logB=UserTools.loginId(idB);
		
			if(logA==null||logB==null)
				return ServiceTools.serviceRefused("Paramètres manquants", -1);
			
			if(logA.equals(logB))
				return ServiceTools.serviceRefused("Amis similaires", -1);
		
			if(UserTools.userExists(logA)){
				if(UserTools.userExists(logB)){
					if(UserTools.isFriend(idA, idB)){
						return ServiceTools.serviceRefused("Déjà harcelé", -1);
					}else{
						bd.UserTools.addFriend(idA, idB);
						return ServiceTools.serviceAccepted();
					}
				}else{
					System.out.println("Ami imaginaire");
					return ServiceTools.serviceRefused("Ami imaginaire", -1);
					
				}
			}else{
				System.out.println("Vous n'existez pas");
				return ServiceTools.serviceRefused("Vous n'existez pas", -1);
			}
		
		}catch (SQLException e){
			return ServiceTools.serviceRefused("Erreur SQL", -1);
		} catch (userNotFoundException e) {
			//ne doit pas arriver
			return ServiceTools.serviceRefused("t'as été banné", 3);
		} catch (KeyNotFoundException e) {
			//ne doit pas arriver
			return ServiceTools.serviceRefused("ué, fallait lire la F.U.Q", -2);
		}
	}
 
	/** Affiche un nombre de commentaires fixes
	 * 
	 * @param i le nombre de commentaires à afficher
	 * @return un String les commentaires
	 */
	public static String printComment(int i) {

			return UserTools.printComments(i);
	
		
	}
	
	/** Affiche tous les commentaires de la base MongoDB
	 * 
	 * @return un String correspondant aux commentaires
	 */
	public static String printAllComments(){
		return UserTools.printAllComments();
			}
}