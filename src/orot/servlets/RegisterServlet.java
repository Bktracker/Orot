package orot.servlets;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import orot.AppConstants;
/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PreparedStatement pstmt=null;
		Connection conn = null;
		PrintWriter out = null;

		try {
			response.setContentType("text/html");
			out = response.getWriter();	

			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context.lookup(AppConstants.DB_DATASOURCE);
			conn = ds.getConnection();

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String nickname = request.getParameter("nickname");
			String description = request.getParameter("description");
			String email = request.getParameter("email");


			pstmt=conn.prepareStatement(AppConstants.ADD_NEW_USER);
			pstmt.setString(1,username);
			pstmt.setString(2,password);
			pstmt.setString(3,nickname);
			pstmt.setString(4,description);
			pstmt.setString(5,email);
			pstmt.setString(6," ");
			pstmt.setString(7,"ACTIVE");
			pstmt.setDate(8,new java.sql.Date((new Date()).getTime()));
			pstmt.setString(9," ");
			pstmt.setString(10," ");
			pstmt.executeUpdate();

			conn.commit();


			System.out.print("Creating new user:" );
			System.out.println(username);
			HttpSession session=request.getSession();
			session.setAttribute("username",username);
			session.setMaxInactiveInterval(60*60);

			out.print("OK");

		} catch (IOException | SQLException | NamingException e) {
			getServletContext().log("Error ,general SQLException ", e);
		} finally {
			try { pstmt.close(); } catch (Exception e) { /* ignored */ }
			try { conn.close(); } catch (Exception e) { /* ignored */ }
			try { out.close(); } catch (Exception e) { /* ignored */ }


		}


	}

}
