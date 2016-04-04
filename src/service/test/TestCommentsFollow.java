package service.test;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.bson.types.ObjectId;

import com.mongodb.MongoException;

import bd.UserTools;

public class TestCommentsFollow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(UserTools.printCommentsFollow(1));
		} catch (UnknownHostException | MongoException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
