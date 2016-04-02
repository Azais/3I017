package bd;

import java.sql.SQLException;

public class userNotFoundException extends Exception {
	public static void main(String[] args) {
		try{
			UserTools.creerTables();
			
		}catch(SQLException e){
			e.printStackTrace();
		}
}
}
