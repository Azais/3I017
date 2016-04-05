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

public class RemoveFriendServlet extends HttpServlet {
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		Map<String, String[]> pars=requete.getParameterMap();
		String rep=ServiceTools.serviceRefused("Erreur paramètres", -1).toString();
		if(pars.containsKey("key")&&pars.containsKey("id_friend")){
			String key=requete.getParameter("key");
			try{
				int id_friend=Integer.parseInt(requete.getParameter("id_friend"));
				rep=UserServices.removeFriend(key, id_friend).toString();
			}catch(NumberFormatException e){
				rep=ServiceTools.serviceRefused("id non entier", 53).toString();
			}
		}
		reponse.setContentType("text/plain");
		PrintWriter out = reponse.getWriter();
		out.print(rep);
	}
}
