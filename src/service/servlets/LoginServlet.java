package service.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import service.Operation;
import service.ServiceTools;
import service.UserServices;

public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		Map<String, String[]> pars=requete.getParameterMap();
		
		String rep=ServiceTools.serviceRefused("Erreur paramètres", -1).toString();
		if(pars.containsKey("login")&&pars.containsKey("password"))
		{
			String valueLogin=requete.getParameter("login");
			String valuePassword=requete.getParameter("password");
		
			rep=UserServices.login(valueLogin, valuePassword).toString();
			
			
		}
		reponse.setContentType("text/plain");
		PrintWriter out = reponse.getWriter();
		out.print(rep);
	}
	
}
