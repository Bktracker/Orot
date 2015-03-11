package orot.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckSessionServlet
 */
public class CheckSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CheckSessionServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * Check if user is logged in ,return OK if logged in otherwise notFound
	 * @param request the http servlet request
	 * @param response the http servlet reponse
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @throws ServletException a general servlet exception probable cause life cycle of servlet
	 * @throws IOException  an iO exception has occurred e.g failed to output or read
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();	
		System.out.println("check session");
		HttpSession session = request.getSession(false);
		if(session==null) {//check if user is logged in and session exists
			out.print("notFound");
		} else {
			out.print("OK");
		}
		out.close();
	}

}
