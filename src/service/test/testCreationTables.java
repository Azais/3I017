package service.test;

import java.sql.SQLException;

import bd.UserTools;

public class testCreationTables {

	public static void main(String[] args) {
			try{
				UserTools.creerTables();
				
			}catch(SQLException e){
				e.printStackTrace();
			}
	}

}
