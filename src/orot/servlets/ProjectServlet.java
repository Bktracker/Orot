package orot.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import orot.model.Project;
/**
 * Servlet implementation class ProjectServlet
 */
public class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs =null;
		Statement stmt=null;
		Connection conn = null;
		PrintWriter out = null;
		try {
			System.out.println("project servlet");
			Collection<Project> projectResult= new ArrayList <Project>();
			
			response.setContentType("text/html");

			out = response.getWriter();	

			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context.lookup(AppConstants.DB_DATASOURCE);
			conn = ds.getConnection();
			HttpSession session=request.getSession(false);

			if (session ==null) { //Prevent access to SRV if user does not have an active session
				return ;
			}

			Gson gson = new Gson();
			String jsonResult=null;
			
			stmt =conn.createStatement();
			rs = stmt.executeQuery(AppConstants.SELECT_ALL_PROJECTS);
			
			while(rs.next()){
				projectResult.add(new Project(rs.getString(1),rs.getString(2),rs.getString(3)));
			}
			rs.close();
			
			jsonResult=gson.toJson(projectResult,AppConstants.PROJECT_COLLECTION);
			out.print(jsonResult);
			
			conn.commit();

		} catch (IOException | SQLException | NamingException e) {
			getServletContext().log("Error ,general SQLException ", e);
		} finally {
			try { rs.close(); } catch (Exception e) { /* ignored */ }
			try { stmt.close(); } catch (Exception e) { /* ignored */ }
			try { conn.close(); } catch (Exception e) { /* ignored */ }
			try { out.close(); } catch (Exception e) { /* ignored */ }

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
