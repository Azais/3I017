package bd;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;

import org.bson.types.ObjectId;

import com.mongodb.*;
import com.mysql.jdbc.Statement;
public class RechercheMapReduce {
	public static void indexation() throws UnknownHostException, MongoException, SQLException{
		String mapDF="function map(){" +
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
		String reduceDF="function reduce(key, values){	" +
				"var total=0;	" +
				"for(i in values){		" +
					"total+=values[i].df;	" +
					"};	" +
					"return({word: key, df:total});" +
					"}";
		
		String mapTF="function mapTF(){	" +
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
		String reduceTF="function reduceTF(key, value){	" +
				"return({doc: key, tfs: value});" +
				"}";
		
		Mongo m=new Mongo(DBStatic.mango_host, DBStatic.mango_port);
		DB db=m.getDB("gr2_foufa_keraro");
		DBCollection collection=db.getCollection("comments");
		MapReduceCommand cmdDF=new MapReduceCommand(collection, mapDF, reduceDF, null, MapReduceCommand.OutputType.INLINE, null);
		MapReduceOutput outDF=collection.mapReduce(cmdDF);
		
		Connection conn = DataBase.getMySqlConnection();
		
		String queryDelete= "DELETE FROM tf_table WHERE 1;";
		Statement stDelete=(Statement) conn.createStatement();
		stDelete.execute(queryDelete);
		stDelete.close();
		
				
		System.out.println("\n\t\t---df---\n");

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
		System.out.println("\n\t\t---tf---\n");
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
		conn.close();
	}


	public static void main(String args[]){
		try{
			RechercheMapReduce.indexation();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}