package service.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserServices;

public class TestPrintComment extends HttpServlet {
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		String res=UserServices.printComment(1);
		reponse.setContentType("text/plain");
		PrintWriter out = reponse.getWriter();
		out.print(res);
	}
}
