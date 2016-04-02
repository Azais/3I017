package service;

import java.io.*;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

public class OperationServlet extends HttpServlet {
	public void doGet(HttpServletRequest requete, HttpServletResponse reponse) throws ServletException, IOException {
		Map<String, String[]> pars=requete.getParameterMap();
		int resultat=0;
		String rep;
		if(pars.containsKey("a")&&pars.containsKey("b")&&pars.containsKey("op"))
		{
			String valueA=requete.getParameter("a");
			String valueB=requete.getParameter("b");
			String operation=requete.getParameter("op");
			int a = Integer.parseInt(valueA);
			int b = Integer.parseInt(valueB);
			rep="resultat=";
			if(operation.equals("add")||operation.equals("mul")||operation.equals("div")){
				Operation op=new Operation();
				rep=rep+op.calcul(a, b, operation);
			}else{
				rep="pas d'opération";
			}
		}else{
			rep="pas de réponse";
		}
		
		reponse.setContentType("text/html");
		PrintWriter out = reponse.getWriter();
		out.print(rep);
		out.print("</body>");
	}

}
