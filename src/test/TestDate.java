package test;

import java.net.UnknownHostException;
import java.util.Date;

import bd.DBStatic;
import bd.UserTools;

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
				/*or(int i=1;i<=12;i++){
					System.out.println(i+" "+UserTools.printCommentsFollow(i));
				}*/
				System.out.println(UserTools.printCommentsFollow(3));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
