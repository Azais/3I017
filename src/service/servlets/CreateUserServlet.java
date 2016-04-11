package service.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ServiceTools;
import service.UserServices;

public class CreateUserServlet extends HttpServlet {
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		Map<String, String[]> pars=requete.getParameterMap(); // Recupere les paramètres de l'URL
		
		String rep=ServiceTools.serviceRefused("Erreur paramètres", -1).toString();
		if(pars.containsKey("login")&&pars.containsKey("password")&&pars.containsKey("nom")&&pars.containsKey("prenom")&&pars.containsKey("photo"))
		{
			String valueLogin=requete.getParameter("login");
			String valuePassword=requete.getParameter("password");
			String valueNom=requete.getParameter("nom");
			String valuePrenom=requete.getParameter("prenom");
			String valuePhoto=requete.getParameter("photo");
			rep=UserServices.createUser(valueLogin, valuePassword, valueNom, valuePrenom, valuePhoto).toString();	
		}
		reponse.setContentType("text/plain");
		PrintWriter out = reponse.getWriter();
		out.print(rep);
	}
}
