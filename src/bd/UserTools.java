package bd;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.UUID;

import org.bson.BSONObject;

import com.mongodb.*;
import com.mysql.jdbc.Statement;

public class UserTools {
	
	/**
	 * removeFriend permet à un utilisateur iDA de ne plus suivre idB
	 * @param idA l'id de l'utilisateur suivant souhaitant enlever un ami
	 * @param idB l'id de l'utilisateur suivi
	 * @throws SQLException
	 */
	public static void removeFriend(int idA, int idB) throws SQLException{
		Connection conn=DataBase.getMySqlConnection();
		String query="DELETE FROM friends WHERE de = "+idA+"AND vers = "+idB+";";
		Statement st=(Statement) conn.createStatement();
		st.execute(query);
	}
	
	/**
	 *  idKey permet d'obtenir l'id de l'utilisateur associé à une clef de session
	 * @param key la clef de session
	 * @return
	 * @throws SQLException
	 * @throws KeyNotFoundException
	 */
	public static int idKey(String key) throws SQLException, KeyNotFoundException{
		Connection conn=DataBase.getMySqlConnection();
		String query="SELECT id FROM sessions WHERE unhex(+'"+key+"')=cle AND expired=0";
		Statement st=(Statement) conn.createStatement();
		ResultSet rs=st.executeQuery(query);
		if(rs.next()){
			int ret=rs.getInt("id");
			rs.close();
			st.close();
			conn.close();
			return ret;
		}
		else{
			rs.close();
			st.close();
			conn.close();
			throw new KeyNotFoundException();
		}
	}
	public static String loginId(int id) throws SQLException, userNotFoundException{
		Connection conn=DataBase.getMySqlConnection();
		String query="SELECT login FROM users WHERE id="+id+";";
		Statement st=(Statement) conn.createStatement();
		ResultSet rs=st.executeQuery(query);
		if(rs.next()){
			String ret=rs.getString("login");
			rs.close();
			st.close();
			conn.close();
			return ret;
		}
		else{
			rs.close();
			st.close();
			conn.close();
			throw new userNotFoundException();
		}
	}
	
	
	public static boolean keyVerified(String key) throws SQLException{
		Connection conn=DataBase.getMySqlConnection();
		String query="SELECT * FROM sessions WHERE unhex(+'"+key+"')=cle AND expired=0";
		Statement st=(Statement) conn.createStatement();
		ResultSet rs=st.executeQuery(query);
		boolean res=rs.next();
		rs.close();
		st.close();
		conn.close();
		return res;
		
	}
	//"{ \"auteur\" : { \"id\": 1 , \"login\" : \"Dieu\"}, \"text\" : \"Fiat lux\"}
	public static void addComment(int author_id, String login,String text) throws UnknownHostException, MongoException {
			Mongo m=new Mongo(DBStatic.mango_host, DBStatic.mango_port);
			DB db=m.getDB("gr2_foufa_keraro");

			DBCollection collection=db.getCollection("comments");
			BasicDBObject object=new BasicDBObject();
			BasicDBObject auteur=new BasicDBObject();
			
			auteur.put("id", author_id);
			auteur.put("login", login);
			
			object.put("auteur", auteur);
			object.put("text", text);
			collection.insert(object);
			m.close();
		
		
	}
	public static void removeComments() throws UnknownHostException, MongoException{
		Mongo m=new Mongo(DBStatic.mango_host, DBStatic.mango_port);
		DB db= m.getDB("gr2_foufa_keraro");
		DBCollection collection=db.getCollection("comments");
		collection.remove(new BasicDBObject());
		m.close();
		
	}
	public static String printAllComments(){
		Mongo m;
		try {
			m = new Mongo(DBStatic.mango_host, DBStatic.mango_port);
		} catch (UnknownHostException | MongoException e) {
			return "Erreur UserTools.printComments connexion";
		}
		DB db=m.getDB("gr2_foufa_keraro");
		
		BasicDBObject obj=new BasicDBObject();
		DBCollection collection= db.getCollection("comments");
		DBCursor crs=collection.find();
		String res="[";
		if(crs.hasNext()){
			res=res+crs.next();
		}
		else{
			return "[]";
		}
		while(crs.hasNext()){
			res=res+","+crs.next();
		}
		res=res+"]";
		return res;
	}
	
	public static String printComments(int author_id)  {
		Mongo m;
		try {
			m = new Mongo(DBStatic.mango_host, DBStatic.mango_port);
		} catch (UnknownHostException | MongoException e) {
			return "Erreur UserTools.printComments connexion";
		}
		DB db=m.getDB("gr2_foufa_keraro");
		
		BasicDBObject obj=new BasicDBObject();
		//obj.put("author_id", new BasicDBObject("$eq",author_id));
		obj.put("author_id", author_id);
		DBCollection collection= db.getCollection("comments");
		DBCursor crs=collection.find(obj);
		String res="";
		while(crs.hasNext()){
			res=res+"\n"+crs.next();
		}
		return res;
	}
	
	
	public static void creerTables()throws SQLException{
		Connection conn=DataBase.getMySqlConnection();
		String query1="CREATE TABLE users (id INTEGER PRIMARY KEY auto_increment, login VARCHAR(32) UNIQUE, password VARCHAR(32), prenom VARCHAR(255), nom VARCHAR(255))";
		String query2="CREATE TABLE sessions (cle BINARY(16), id INTEGER, time TIMESTAMP, root BOOLEAN, expired BOOLEAN)";
		String query3="CREATE TABLE friends(de INTEGER, time TIMESTAMP, vers INTEGER)";
		Statement st1= (Statement) conn.createStatement();
		st1.execute(query1);
		st1.execute(query2);
		st1.execute(query3);
		st1.close();
		conn.close();
	}
	
	public static boolean userExists (String login) throws SQLException{
			Connection conn=DataBase.getMySqlConnection();
			System.out.println("connecté");
			String query="SELECT id FROM users WHERE login='"+login+"';";
			Statement st= (Statement) conn.createStatement();
			ResultSet res=st.executeQuery(query);
			boolean retour;
			if(res.next()) // est ce que que il y a un suivant si non l'user n'existe pas
				retour=true;
			else 
				retour=false;
			res.close();
			st.close();
			conn.close(); // Ne pas oublier de fermer la BD
			return retour;

	}
	
	/**
	 * Rend l'id à partir d'un login
	 * @param login
	 * @return
	 * @throws userNotFoundException
	 */
	public static int idLogin(String login) throws userNotFoundException {
		try{
			Connection conn=DataBase.getMySqlConnection();
			String query="SELECT id FROM users WHERE login='"+login+"';";
			Statement st= (Statement) conn.createStatement();
			ResultSet res=st.executeQuery(query);
			
			res.next();
			int retour = res.getInt("id");

			
			
			res.close();
			st.close();
			conn.close();
			
			return retour;
		}catch(SQLException e){
			throw new userNotFoundException();
		}
	}
	
	public static boolean checkPassword (String login, String password){
		try{
			Connection conn=DataBase.getMySqlConnection();
			String query="SELECT password FROM users WHERE login='"+login+"';";
			Statement st=(Statement)conn.createStatement();
			ResultSet res=st.executeQuery(query);
			boolean retour;
			if(!res.next())
				return retour=false;
			else
				retour=res.getString("password").equals(password);
			res.close();
			st.close();
			conn.close();
			return retour;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public static String insertSession(String login, boolean root) throws SQLException, userNotFoundException{
		
		
		// Generation d'une cle aleatoire
		UUID clef = UUID.randomUUID();
		
			int id=idLogin(login);
			Connection conn=DataBase.getMySqlConnection();
			String query="INSERT INTO sessions (cle, id, root, expired) VALUES ( UNHEX('"+clef.toString().replaceAll("-", "")+"'), "+id+", "+root+", false);";
			Statement st=(Statement)conn.createStatement();
				st.execute(query);
			st.close();
			conn.close();
		
		return clef.toString().replaceAll("-","");
		// Verifictation que pas dans la BD
		// Ajout à la BD
	}
	public static String sessionKey(String login)throws SQLException, KeyNotFoundException, userNotFoundException{
		Connection conn=DataBase.getMySqlConnection();
		String query="SELECT HEX(cle) AS cle  FROM sessions WHERE id="+idLogin(login)+";";
		Statement st=(Statement)conn.createStatement();
		ResultSet res=st.executeQuery(query);
		if(!res.next()){
			res.close();
			st.close();
			conn.close();
			throw new KeyNotFoundException();
		}
		String retour=res.getString("cle");
		res.close();
		st.close();
		conn.close();
		return retour;
	}		
	
	public static void register(String login, String nom, String prenom, String password) throws SQLException{
		Connection conn=DataBase.getMySqlConnection();
		String update ="INSERT INTO users (login, nom, prenom, password) VALUES ('"+login+"', '"+nom+"', '"+prenom+"', '"+password+"')";
		Statement st =(Statement)conn.createStatement();
		st.execute(update);
		st.close();
		conn.close();
		

	}
	
	
	
	
	/** Expire une clef session, A REVOIR
	 * 
	 * @param key
	 * @throws SQLException
	 */
	public static void logout(String key) throws SQLException{
		Connection conn = DataBase.getMySqlConnection();
		String query = "Update sessions SET expired=1 WHERE cle=unhex('"+key+"');";
		Statement st= (Statement) conn.createStatement();
		st.execute(query);
		st.close();
		conn.close();
		
	}
	/**
	 * Attention : pas de vérification de l'existence des id
	 * @param idA l'identifiant du l'utilisateur follower
	 * @param idB l'identifiant de l'utilisateur suivi
	 * @return true si idB est suivi par idA
	 * @throws SQLException 
	 */
	
	public static boolean isFriend(int idA, int idB) throws SQLException{
		Connection conn = DataBase.getMySqlConnection();
		String query = "Select vers FROM friends WHERE de="+idA+";";
		Statement st= (Statement) conn.createStatement();
		ResultSet res=st.executeQuery(query);
		System.out.println("Query recherche id execute");
		boolean retour=false;
		while(res.next()){
			if(res.getInt("vers")==idB){
				retour=true;
				break;
			}
		}
		res.close();
		st.close();
		conn.close();
		return retour;
		
	}
	
	/**
	 * Attention : pas de verification de l'existence des id
	 * @param idA l'identifiant de l'utilisateur follower 
	 * @param idB l'identifiant de l'utilisateur suivi
	 * @throws SQLException
	 */
	public static void addFriend(int idA, int idB) throws SQLException{
		
		Connection conn = DataBase.getMySqlConnection();
		String update = "INSERT INTO friends (de, vers) VALUES ("+idA+", "+idB+")";
		Statement st =(Statement)conn.createStatement();
		st.execute(update);
		st.close();
		conn.close();
		
	}
	
	
}
