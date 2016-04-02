package service.test;

import com.mongodb.BasicDBObject;

public class testMangoFind {

	public static void main(String[] args) {
		BasicDBObject obj=new BasicDBObject();
		obj.put("author_id", new BasicDBObject("$eq",1));
		System.out.println(new BasicDBObject("author_id", new BasicDBObject("$eq", 1)));
		System.out.println(obj);

	}

}
