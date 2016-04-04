package service.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ServiceTools;


import bd.UserTools;

public class RemoveComments extends HttpServlet {
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		String rep=null;
		try{
			UserTools.removeComments();
			rep=ServiceTools.serviceAccepted().toString();
			
		}catch( UnknownHostException  e){
			rep=ServiceTools.serviceRefused("erreur Mango", -2).toString();
		}
		
		reponse.setContentType("text/plain");
		PrintWriter out = reponse.getWriter();
		out.print(rep);
	}

}
