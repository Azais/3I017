package service.test;

import java.sql.SQLException;

import service.UserServices;
import bd.UserTools;

public class testLogin {

	public static void main(String[] args) {
		System.out.println(UserServices.login("keraroo", "kerarooo"));
	}

}
