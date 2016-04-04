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

public class AddCommentServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		Map<String, String[]> pars=requete.getParameterMap(); // Recupere les paramètres de l'URL
		String rep=ServiceTools.serviceRefused("Erreur paramètres Servlet Add Comment", -110).toString();
		if(pars.containsKey("key") && pars.containsKey("text")){
			String key=requete.getParameter("key");
			String text=requete.getParameter("text");
			rep=UserServices.addComment(key, text).toString();
		}
		
		reponse.setContentType("text/plain");
		PrintWriter out = reponse.getWriter();
		out.println(rep);
	}
}
