package bd;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;

import com.mongodb.*;
import com.mysql.jdbc.Statement;
public class RechercheMapReduce {
	static String mapDF="function map(){" +
			"var text=this.text;" +
			"var words=text.match(/\\w+/g);" +
			"var df=[];" +
			"for (w in words){" +
				"if(df[words[w]]==null){" +
					"df[words[w]]=1;" +
				"}for(w in df){" +
					"emit(w,{df:1});" +
				"}" +
			"}" +
		"}";
	static String reduceDF="function reduce(key, values){	" +
			"var total=0;	" +
			"for(i in values){		" +
				"total+=values[i].df;	" +
				"};	" +
				"return({word: key, df:total});" +
				"}";
	
	static String mapTF="function mapTF(){	" +
			"						var text=this.text;	" +
									"var words=text.match(/\\w+/g);	" +
									"var tf=[];	" +
									"for(i in words){" +
										"if(tf[words[i]]==null){	" +
											"tf[words[i]]=1;" +
											"}else{" +
												"tf[words[i]]++;" +
											"}" +
									"}" +
									"for(w in tf){" +
									"	emit(this._id, {words: w, tf: tf[w]});" +
									"}" +
								"}";
	
	static String reduceTF="function reduceTF(key, value){	" +
			"return({doc: key, tfs: value});" +
			"}";
	private static void indexDf(Connection conn, DBCollection collection) throws SQLException{
		MapReduceCommand cmdDF=new MapReduceCommand(collection, mapDF, reduceDF, null, MapReduceCommand.OutputType.INLINE, null);
		MapReduceOutput outDF=collection.mapReduce(cmdDF);
		
		for(DBObject obj: outDF.results()){
			System.out.println(obj.toString());
			DBObject value=(DBObject)(obj.get("value"));
			String word=(String) value.get("word");
			int df=((Double)value.get("df")).intValue();
			String queryDf="INSERT INTO df_table(mot, df) VALUES ('"+word+"',"+df+");";
			Statement stDf=(Statement)conn.createStatement();
			stDf.execute(queryDf);
			stDf.close();			
		}
		
	}
	private static void indexTf(Connection conn, DBCollection collection) throws SQLException{
		MapReduceCommand cmdTF=new MapReduceCommand(collection, mapTF, reduceTF, null, MapReduceCommand.OutputType.INLINE, null);
		MapReduceOutput outTF=collection.mapReduce(cmdTF);
		for(DBObject obj: outTF.results()){
			System.out.println(obj.toString());
			DBObject value=(DBObject)(obj.get("value"));
			String idComment=((ObjectId)(obj.get("_id"))).toString();
			
			BasicDBList tfs=null;
			if(value.containsField("tfs"))
				tfs=(BasicDBList) value.get("tfs");
			else{
				tfs=new BasicDBList();
				tfs.add(value);
			}

			for(Object obj2: tfs){
				DBObject inst=(DBObject) obj2;
				String words=(String)inst.get("words");
				int tf=(int)((Double)inst.get("tf")).intValue();
				Statement stTf=(Statement)conn.createStatement();
				String queryTf="INSERT INTO tf_table(document, word, tf) VALUES (UNHEX('"+ idComment +"'),'"+words+"',"+tf+")";
				stTf.execute(queryTf);
				stTf.close();
			}	
		}
	}
	
	public static void reinitTables(Connection conn) throws SQLException{
		String queryDelete= "DELETE FROM df_table WHERE 1;";
		Statement stDeleteDf=(Statement) conn.createStatement();
		stDeleteDf.execute(queryDelete);
		stDeleteDf.close();
		queryDelete= "DELETE FROM tf_table WHERE 1;";
		Statement stDeleteTf=(Statement) conn.createStatement();
		stDeleteTf.execute(queryDelete);
		stDeleteTf.close();
		queryDelete= "DELETE FROM length_message WHERE 1;";
		Statement stDeleteSize=(Statement) conn.createStatement();
		stDeleteSize.execute(queryDelete);
		stDeleteSize.close();
	}
	private static void initLength(DBCollection collection, Connection conn) throws SQLException{
		DBCursor crs=collection.find();
		while(crs.hasNext()){
			DBObject obj=crs.next();
			String comment=obj.get("text").toString();
			int length=comment.replaceAll("[^a-zA-Z]", "").length();
			String idComment=((ObjectId)(obj.get("_id"))).toString();

			String query="INSERT INTO length_message(message,length) VALUES (UNHEX('"+ idComment +"'),"+length+")";
			Statement st=(Statement) conn.createStatement();
			st.execute(query);
			st.close();
		}
	}
	public static void indexation() throws UnknownHostException, MongoException, SQLException{
		
		Mongo m=new Mongo(DBStatic.mango_host, DBStatic.mango_port);
		DB db=m.getDB("gr2_foufa_keraro");
		DBCollection collection=db.getCollection("comments");
		
		Connection conn = DataBase.getMySqlConnection();
		
		reinitTables(conn);
		
		initLength(collection, conn);
		
		System.out.println("\n\t\t---df---\n");
		indexDf(conn, collection);
		
		System.out.println("\n\t\t---tf---\n");
		indexTf( conn,  collection);
		conn.close();
	}

	public static ArrayList<String> recherche(String query) throws SQLException{
		
		ArrayList<String> res=new ArrayList<String>();
		
		//initialisation de la liste des mots de la requete
		Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher = pattern.matcher(query);
		ArrayList<String> words=new ArrayList<String>();
		while(matcher.find())
			words.add(matcher.group());
		StringBuffer listWords=new StringBuffer(256);
		listWords.append('(');
		listWords.append("'"+words.get(0)+"'");
		for(int i=1; i<words.size(); i++){
			listWords.append(",'"+words.get(i)+"'");
		}
		listWords.append(')');
		System.out.println(listWords);
		
		//requete
		String requete="SELECT HEX(document) as idComment, sum(tf_idf) as rsv " +
				"FROM (SELECT document, log(tf*log(8/df)) as tf_idf " +
				"		FROM tf_table, df_table " +
						"WHERE df_table.mot in "+listWords+" and df_table.mot=tf_table.word) as tab_idf group by document order by rsv";
		
		System.out.println(requete);
		Connection conn = DataBase.getMySqlConnection();
		Statement st=(Statement)conn.createStatement();
		ResultSet rs=st.executeQuery(requete);
		while(rs.next()){
			res.add(rs.getString("idComment"));
		}
		rs.close();
		st.close();
		conn.close();
		
		return res;
	}
	public static void main(String args[]){
		try{
			ArrayList<String> res= recherche("grand jour");
			for(String s : res){
				System.out.println(s);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}