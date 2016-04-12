package test;

import java.net.UnknownHostException;
import java.util.Date;

import org.bson.types.ObjectId;

import bd.DBStatic;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class TestObjectId {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Mongo m;
		try {
			m = new Mongo(DBStatic.mango_host, DBStatic.mango_port);
			DB db=m.getDB("gr2_foufa_keraro");
			//db.createCollection("test", null);
			DBCollection collection=db.getCollection("comments");
			DBCursor crs=collection.find();
			while(crs.hasNext()){
				System.out.println(crs.next());
			}
		} catch (UnknownHostException | MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		

	}

}
