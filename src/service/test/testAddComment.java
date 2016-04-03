package service.test;


import service.UserServices;

import com.mongodb.MongoException;


public class testAddComment {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UserServices.addComment("7e258cadc13c406496ae93b89dec575d", "je ne suis pas là");
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
