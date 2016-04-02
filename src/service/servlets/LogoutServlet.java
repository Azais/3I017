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

public class LogoutServlet extends HttpServlet {
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		Map<String, String[]> pars=requete.getParameterMap();
		
		String rep=ServiceTools.serviceRefused("Erreur paramètres", -1).toString();
		if(pars.containsKey("key"))
		{
			String valueKey=requete.getParameter("key");
			rep=UserServices.logout(valueKey).toString();
			
			
		}
		reponse.setContentType("text/plain");
		PrintWriter out = reponse.getWriter();
		out.print(rep);
	}
}
