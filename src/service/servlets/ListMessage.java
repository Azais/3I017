package service.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserServices;


public class ListMessage extends javax.servlet.http.HttpServlet {


	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		String res=UserServices.printAllComments();
		reponse.setContentType("application/json");
		PrintWriter out = reponse.getWriter();
		out.print(res);
	}
}
