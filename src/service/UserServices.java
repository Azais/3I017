package service;


//bla bla
/*UserServices répond au TD2
 * Ses fonctions vont appeler des fonctions dans UserTools
 * Ce sont les premières fonctions à écrire  */

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import bd.DBStatic;
import bd.KeyNotFoundException;
import bd.RechercheMapReduce;
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
			System.out.println("point 1");
			if(!UserTools.keyVerified(key))
				return ServiceTools.serviceRefused("Non connecté!!!", 3);
			System.out.println("point 2");
			int idA=UserTools.idKey(key);
			System.out.println("point 3, idA:"+idA);
			
			if(!UserTools.isFriend(idA, idB))
				return ServiceTools.serviceAccepted();
			System.out.println("point 4");
			UserTools.removeFriend(idA, idB);
			System.out.println("point 5");
			return ServiceTools.serviceAccepted();
		}catch(SQLException e){
			return ServiceTools.serviceRefused("erreur SQL", -1);
		} catch (KeyNotFoundException e) {
			// ne doit pas arriver
			return ServiceTools.serviceRefused("erreur", -3);
		}
	}
	public static JSONObject dislike(String key, String idComment){
		try{
			if(UserTools.commentExists(idComment)){
				int idUser;
				try {
					idUser=UserTools.idKey(key);
				}catch (KeyNotFoundException e) {
					return ServiceTools.serviceRefused("Clé de connection invalide", 3);
				}
				UserTools.dislike(idUser, idComment);
				return ServiceTools.serviceAccepted();
			}else{
				return ServiceTools.serviceRefused("Commentaire n'existe pas", 4);
			}
		}catch(UnknownHostException | MongoException e){
			return ServiceTools.serviceRefused("Erreur Mango", -2);
		}catch(SQLException e){
			return ServiceTools.serviceRefused("Erreur SQL", -1);
		}
	}
	
	public static JSONObject like(String key, String idComment){
		try{
			if(UserTools.commentExists(idComment)){
				int idUser;
				try {
					idUser=UserTools.idKey(key);
				}catch (KeyNotFoundException e) {
					return ServiceTools.serviceRefused("Clé de connection invalide", 3);
				}
				UserTools.like(idUser, idComment);
				return ServiceTools.serviceAccepted();
			}else{
				return ServiceTools.serviceRefused("Commentaire n'existe pas", 4);
			}
		}catch(UnknownHostException | MongoException e){
			return ServiceTools.serviceRefused("Erreur Mango", -2);
		}catch(SQLException e){
			return ServiceTools.serviceRefused("Erreur SQL", -1);
		}
	}
	
	/** Crée un utilisateur de pseudo login. 
	 * 	
	 * @param login le pseudo de l'utilisateur (string)
	 * @param password le mot de passe (string)
	 * @param nom le nom de famille (string)
	 * @param prenom le prénom de l'utilisateur (string)
	 * @return un JSONObject indiquant que le service a réussi ou l'erreur.
	 */
	
	public static JSONObject createUser(String login, String password, String nom, String prenom, String photo){
		
		if((login==null)||(password==null)||(nom==null)||(prenom==null)||(photo==null)){
			return ServiceTools.serviceRefused("Paramètres manquants", -3);
		}
		
		// Verifier que le login utilisateur n'est pas déjà dans la bd
		
		try {
			if(UserTools.userExists(login)){
				return ServiceTools.serviceRefused("Deja dans la base", 1);
			}else{
			// Ajouter l'utilisateur à la BD
				try {
					UserTools.register(login, nom, prenom, password, photo);
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
			return ServiceTools.serviceRefused("Paramètres manquants", -3);
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
						
						res.put("picture", UserTools.photoId(id));
						res.put("follow", true);
						
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
							res.put("picture", UserTools.photoId(id));
							res.put("follow", true);
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
			return ServiceTools.serviceRefused("parametre manquant", -3);
		try {
			if(UserTools.keyVerified(key)){
				bd.UserTools.logout(key);
				return ServiceTools.serviceAccepted();
			}else{
				return ServiceTools.serviceRefused("clé n'existe pas", 3);
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
			return ServiceTools.serviceRefused("parametre manquant", -3);
		
		//teste si on est connecté
		try{
			if(!UserTools.keyVerified(key))
				return ServiceTools.serviceRefused("Non connecté!!!", 4);
		
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
				return ServiceTools.serviceRefused("Non connecté!!!", 4);
			int idA=UserTools.idKey(key);
			String logA=UserTools.loginId(idA);
			String logB=UserTools.loginId(idB);
		
			if(logA==null||logB==null)
				return ServiceTools.serviceRefused("Paramètres manquants", -3);
			
			if(logA.equals(logB))
				return ServiceTools.serviceRefused("Amis similaires", 5);
		
			if(UserTools.userExists(logA)){
				if(UserTools.userExists(logB)){
					if(UserTools.isFriend(idA, idB)){
						return ServiceTools.serviceAccepted();
					}else{
						bd.UserTools.addFriend(idA, idB);
						return ServiceTools.serviceAccepted();
					}
				}else{
					System.out.println("Ami imaginaire");
					return ServiceTools.serviceRefused("Ami imaginaire",6);
					
				}
			}else{
				System.out.println("Vous n'existez pas");
				return ServiceTools.serviceRefused("Vous n'existez pas", 7);
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
	public static String printComments(String key){
		try{
			int idFollower;
			try{
				idFollower=UserTools.idKey(key);
				System.out.println("idFollower: "+idFollower);
			}catch(KeyNotFoundException e){
				return ServiceTools.serviceRefused("pas connecté", 3).toString();
			}
			if(UserTools.keyVerified(key)){
				return UserTools.printCommentsFollow(idFollower);
			}
			else{
				return ServiceTools.serviceRefused("pas connecté", 3).toString();
			}
		}catch(SQLException e){
			return ServiceTools.serviceRefused("erreur SQL", -1).toString();
		}catch(MongoException | UnknownHostException e){
			return ServiceTools.serviceRefused("erreur SQL", -2).toString();
		}
	}
	
	/** Affiche tous les commentaires de la base MongoDB
	 * 
	 * @return un String correspondant aux commentaires
	 */
	public static String printAllComments(){
		return UserTools.printAllComments();
			}
	public static JSONObject friends(String key, int id){
		try {
			if(UserTools.keyVerified(key)){
				ArrayList<Integer> set=UserTools.friends(id);
				JSONObject res=new JSONObject();
				for(int i: set){
				
				}
			}
		} catch (SQLException e) {
			return ServiceTools.serviceRefused("erreur sql", -1);
		}
		return null;
	}
	
	public static JSONObject setPassword(String key, String oldPass, String newPass){
		JSONObject res=null;
		try {
			int id=UserTools.idKey(key);
			String login=UserTools.loginId(id);
			if(UserTools.checkPassword(login, oldPass)){
				UserTools.changePassword(id, newPass);
				res=ServiceTools.serviceAccepted();
			}else{
				res=ServiceTools.serviceRefused("mot de passe incorrecte", 4);
			}
		} catch (SQLException e) {
			res=ServiceTools.serviceRefused("erreur SQL", -1);
		} catch (KeyNotFoundException e) {
			
			res=ServiceTools.serviceRefused("clé non trouvée", -3);
		} catch (userNotFoundException e) {
			//ne doit pas arriver
		}
		return res;
	}
	public static JSONObject setPicture(String key, String picture){
		JSONObject res=null;
		try{
			int id=UserTools.idKey(key);
			UserTools.changePicture(id, picture);
			res=ServiceTools.serviceAccepted();
		}catch (SQLException e) {
			res=ServiceTools.serviceRefused("erreur SQL", -1);
		} catch (KeyNotFoundException e) {
			res=ServiceTools.serviceRefused("clé de connection invalide",4);
		}
		return res;
	}
	public static String rechercheCommentaire(String key, String query){
		
		ArrayList<String> comments=null;
		HashMap<Integer, String> mapPhotos=new HashMap<Integer, String>();
		int idFollower=0;
		try {
			if(UserTools.keyVerified(key)){
				try {
					idFollower=UserTools.idKey(key);
				} catch (KeyNotFoundException e) {
					return ServiceTools.serviceRefused("cle n'existe pas", -5).toString();
				}
			}else{
				return ServiceTools.serviceRefused("vous n'etes pas connecte", -3).toString();
			}
			comments=RechercheMapReduce.recherche(query);
		
			Mongo m;
			try {
				m = new Mongo(DBStatic.mango_host, DBStatic.mango_port);
			} catch (UnknownHostException | MongoException e) {
			// TODO Auto-generated catch block
				return ServiceTools.serviceRefused("erreur mongo", -2).toString();
			}
			DB db=m.getDB("gr2_foufa_keraro");
			DBCollection collection=db.getCollection("comments");
			if (comments.size()==0){
				return "[]";
			}
			StringBuffer res=new StringBuffer(256);
			res.append("[");
			BasicDBObject requete=new BasicDBObject();
			String idC=comments.get(0);
			requete.put("_id", new ObjectId(idC));
			DBCursor crs=collection.find(requete);
			if(crs.hasNext()){
				DBObject comment=crs.next();
				comment.put("id", idC);
				comment.removeField("_id");
				DBObject auteur=(DBObject) comment.get("auteur");
				int idFollowed=(Integer) auteur.get("id");
				boolean contact=UserTools.follows(idFollower, idFollowed);
				auteur.put("contact", contact);
				String photo=null;
				if(mapPhotos.containsKey(idFollowed)){
					photo=mapPhotos.get(idFollowed);
				}else{
					photo=UserTools.photoId(idFollowed);
					mapPhotos.put(idFollowed, photo);
					}
				auteur.put("picture", photo);
				comment.removeField("auteur");
				
				comment.put("auteur", auteur);
				comment.put("likes",UserTools.commentLiked(idFollower, idC));
				res.append(comment);
			}
			for(int i=1; i<comments.size();i++){
				requete=new BasicDBObject();
				String idComment=comments.get(i);
				requete.put("_id", new ObjectId(idComment));
				crs=collection.find(requete);
				if(crs.hasNext()){
					DBObject comment=crs.next();
					comment.put("id", idC);
					comment.removeField("_id");
					DBObject auteur=(DBObject) comment.get("auteur");
					int idFollowed=(Integer) auteur.get("id");
					boolean contact=UserTools.follows(idFollower, idFollowed);
					auteur.put("contact", contact);
					String photo=null;
					if(mapPhotos.containsKey(idFollowed)){
						photo=mapPhotos.get(idFollowed);
					}else{
						photo=UserTools.photoId(idFollowed);
						mapPhotos.put(idFollowed, photo);
						}
					auteur.put("picture", photo);
					comment.removeField("auteur");
					
					comment.put("auteur", auteur);
					comment.put("likes",UserTools.commentLiked(idFollower, idC));
					res.append(","+comment);
				}
			}
			res.append(']');
			return res.toString();
		}catch(SQLException e){
			return ServiceTools.serviceRefused("sql erreur", -1).toString();
		}
		
	}
}
