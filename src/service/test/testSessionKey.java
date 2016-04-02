package service.test;

import java.sql.SQLException;

import bd.KeyNotFoundException;
import bd.UserTools;
import bd.userNotFoundException;

public class testSessionKey {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println(UserTools.sessionKey("keraroo"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (userNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
