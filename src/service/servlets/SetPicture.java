package service.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import service.ServiceTools;
import service.UserServices;

public class SetPicture extends HttpServlet {

	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		Map<String, String[]> pars=requete.getParameterMap();
		JSONObject rep=ServiceTools.serviceRefused("Erreur paramètres", -1);
		if(pars.containsKey("key")&&pars.containsKey("newPicture")){
			String key=requete.getParameter("key");
			String picture=requete.getParameter("newPicture");
			rep=UserServices.setPicture(key, picture);
		}
		reponse.setContentType("text/plain");
		PrintWriter out = reponse.getWriter();
		out.print(rep);	
	}

}
