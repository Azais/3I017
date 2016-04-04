package service.test;

import java.sql.SQLException;

import bd.UserTools;
import service.UserServices;

public class TestLike {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println( UserServices.like("7ac6ab3ed9a8470aa61e66e09716a15b", "56fea976e4b047655abf02b2"));
		try{
			System.out.println(UserTools.commentLiked(3, "56fea976e4b047655abf02b2"));
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
