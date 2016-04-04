package test;

import java.net.UnknownHostException;
import java.util.Date;

import bd.DBStatic;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class TestDate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				Mongo m=new Mongo(DBStatic.mango_host, DBStatic.mango_port);
				DB db=m.getDB("gr2_foufa_keraro");
				//db.createCollection("test", null);
				DBCollection collection=db.getCollection("test");
				BasicDBObject query=new BasicDBObject();
				Date now=new Date();
				query.put("moi", 2);
				query.put("postDate", now);
				System.out.println(query);
				//collection.insert(query);
				//DBCursor crs=collection.find();
				//while(crs.hasNext())
					//System.out.println(crs.next());
				
			} catch (UnknownHostException | MongoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
