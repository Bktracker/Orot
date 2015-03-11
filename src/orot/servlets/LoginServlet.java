package orot.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import com.google.gson.Gson;

import orot.AppConstants;
/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		ResultSet rs =null;
		PreparedStatement pstmt=null;
		Connection conn = null;
		PrintWriter out = null;
		
		
		try{
			Gson gson = new Gson();
			String jsonResult=null;

			response.setContentType("text/html");
			out = response.getWriter();	

			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context.lookup(AppConstants.DB_DATASOURCE);
			conn = ds.getConnection();

			String username = request.getParameter("username");
			String password = request.getParameter("password");

			pstmt=conn.prepareStatement(AppConstants.CHECK_USERNAME_PASSWORD);
			pstmt.setString(1,username);
			pstmt.setString(2,password);
			rs = pstmt.executeQuery();

			if (!rs.next()){ //data not found at DB
				out.print("notFound");
				System.out.print(username);
				System.out.println(" failed to login");
			} else {//return user info
				System.out.print(username);
				System.out.println(" logged in succesfuly");
				rs.close();
				pstmt.close();
				HttpSession session=request.getSession();
				session.setAttribute("username",username);
				session.setMaxInactiveInterval(60*60);
				
				
			}
		} catch (IOException | SQLException | NamingException e) {
			getServletContext().log("Error ,general SQLException ", e);
		} finally {
			try { rs.close(); } catch (Exception e) { /* ignored */ }
			try { pstmt.close(); } catch (Exception e) { /* ignored */ }
			try { conn.close(); } catch (Exception e) { /* ignored */ }
			try { out.close(); } catch (Exception e) { /* ignored */ }

		}
		}
	}
