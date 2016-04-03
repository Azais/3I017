package service.test;

import org.bson.types.ObjectId;

import bd.DBStatic;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class TestObjId {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 BasicDBObject query = new BasicDBObject();
		 query.put("_id", new ObjectId("56fea976e4b047655abf02b3"));
		 String sortie;
		 try{
			 Mongo m=new Mongo(DBStatic.mango_host, DBStatic.mango_port);
			 DB db=m.getDB("gr2_foufa_keraro");
			 DBCollection collection=db.getCollection("comments");
			 DBObject dbObj = collection.findOne(query);
			 sortie=dbObj.toString();
		 }catch(Exception e){
			 sortie="caca";
			 e.printStackTrace();
		 }
		 System.out.println(sortie);
	}
}
