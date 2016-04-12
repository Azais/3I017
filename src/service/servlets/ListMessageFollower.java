package service.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserServices;

public class ListMessageFollower extends HttpServlet {
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		Map<String, String[]> pars=requete.getParameterMap();
		if(pars.containsKey("key")){
			String key=requete.getParameter("key");
			String res;
			if(pars.containsKey("query")){
				String query=requete.getParameter("query");
				res=UserServices.rechercheCommentaire(key, query);
			}else{
				res=UserServices.printComments(key);
			}
			reponse.setContentType("application/json");
			PrintWriter out = reponse.getWriter();
			out.print(res);
		}else{
			String res=UserServices.printAllComments();
			reponse.setContentType("application/json");
			PrintWriter out = reponse.getWriter();
			out.print(res);
		}
	}
	

}
