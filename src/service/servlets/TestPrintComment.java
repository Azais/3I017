package service.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoException;

import bd.UserTools;

import service.ServiceTools;
import service.UserServices;;

public class TestPrintComment extends HttpServlet {
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		
		String res;
		try {
			res = UserTools.printCommentsFollow(0);
		} catch (MongoException e) {
			res= ServiceTools.serviceRefused("Erreur Mongo", -2).toString();
			
		} catch (SQLException e) {
			res= ServiceTools.serviceRefused("Erreur SQK", -1).toString();
			e.printStackTrace();
		}
		reponse.setContentType("text/plain");
		PrintWriter out = reponse.getWriter();
		out.print(res);
	}
}
