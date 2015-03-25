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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import orot.AppConstants;
/**
 * Servlet implementation class RegisterServlet
 */
@MultipartConfig
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
		OutputStream outFile = null;
		InputStream filecontent = null;
		PrintWriter writer=null;
		try {
			response.setContentType("text/html");
			out = response.getWriter();	

			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context.lookup(AppConstants.DB_DATASOURCE);
			conn = ds.getConnection();

			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String nickname = request.getParameter("nickname");
			String description = request.getParameter("description");

			final Part filePart = request.getPart("file");
			System.out.println("register");
			final String path = "C:/Users/priva_000/workspace/Orot/WebContent/images/ProfilePictures";
			String fileName =  String.format( nickname+".jpg");
			
			writer = response.getWriter();
			System.out.println("ssss");
//syso
			pstmt=conn.prepareStatement(AppConstants.ADD_NEW_USER);
			pstmt.setString(1,email);
			pstmt.setString(2,password);
			pstmt.setString(3,nickname);
			pstmt.setString(4,description);
			pstmt.setString(5," ");
			pstmt.setString(6,"ACTIVE");
			pstmt.setDate(7,new java.sql.Date((new Date()).getTime()));
			pstmt.setString(8," ");
			String picLocation =  String.format("ProfilePictures/" +fileName);
			pstmt.setString(9,picLocation);
			pstmt.executeUpdate();

			conn.commit();

			
			outFile = new FileOutputStream(new File(path + File.separator + fileName));
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	        	outFile.write(bytes, 0, read);
	        }
	        System.out.println("New file " + fileName + " created at " + path);
	        
			

			System.out.print("Creating new user:" );
			System.out.println(email);
			HttpSession session=request.getSession();
			session.setAttribute("email",email);
			session.setMaxInactiveInterval(60*60);

			out.print("OK");

		} catch (IOException | SQLException | NamingException e) {
			getServletContext().log("Error ,general SQLException ", e);
		} finally {
			try { pstmt.close(); } catch (Exception e) { /* ignored */ }
			try { conn.close(); } catch (Exception e) { /* ignored */ }
			try { out.close(); } catch (Exception e) { /* ignored */ }
			try { filecontent.close(); } catch (Exception e) { /* ignored */ }
			try { writer.close(); } catch (Exception e) { /* ignored */ }
			try { outFile.close(); } catch (Exception e) { /* ignored */ }
		

		}


	}

}
