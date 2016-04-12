package test;

import java.net.UnknownHostException;
import java.util.Date;

import org.bson.types.ObjectId;

import service.UserServices;

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
		System.out.println(UserServices.rechercheCommentaire("7e258cadc13c406496ae93b89dec575d", "grand jour"));
		

	}

}
