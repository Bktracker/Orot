package orot.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import orot.AppConstants;

/**
 * Application Lifecycle Listener implementation class OrotServletContextListener
 *
 */
public class OrotServletContextListener implements ServletContextListener {

	/**
	 * Default constructor. 
	 */
	public OrotServletContextListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0)  { 
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0)  { 
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;


		System.out.println("starting to build DB scheme");
		try{
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context.lookup(AppConstants.DB_DATASOURCE);
			conn = ds.getConnection();//try to connect to DB scheme (if does not exist creates it)

			System.out.println("Creating default tables if they dont exist");

			DatabaseMetaData dbmd = conn.getMetaData();

			rs = dbmd.getTables(null, null, "USERS", null);//check if USERS table exist
			if (!rs.next()) {//table USERS does not exist create new table
				System.out.println("Creating users table");
				stmt =conn.createStatement();
				stmt.executeUpdate(AppConstants.CREATE_USER_TABLE);
				conn.commit();
				stmt.close();
			} 
			rs.close();
			
			rs = dbmd.getTables(null, null, "PROJECT", null);//check if PROJECT table exist
			if (!rs.next()) {//table PROJECT does not exist create new table
				System.out.println("Creating project table");
				stmt =conn.createStatement();
				stmt.executeUpdate(AppConstants.CREATE_PROJECT_TABLE);
				conn.commit();
				stmt.close();
			}
			rs.close();
			
			
			
			
			
			
			System.out.println("creating admin user,email=admin@haifa.ac.il,password=password");
			pstmt=conn.prepareStatement(AppConstants.ADD_NEW_USER);
			pstmt.setString(1,"admin@haifa.ac.il");
			pstmt.setString(2,"password");
			pstmt.setString(3,"Administartor");
			pstmt.setString(4,"descrition");
			pstmt.setString(5," ");
			pstmt.setString(6,"ACTIVE");
			pstmt.setDate(7,new java.sql.Date((new Date()).getTime()));
			pstmt.setString(8," ");
			pstmt.setString(9,"ProfilePictures/admin.jpg");
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
			
			
			System.out.println("creating 10 default projects");
			for (int i=1;i<=10;i++) {
			pstmt=conn.prepareStatement(AppConstants.ADD_NEW_PROJECT);
			pstmt.setString(1, String.format("project" + i));
			pstmt.setString(2,String.format("this is project descreption " + i +" it should explain what this project is about ,it's goals,what and how to acomplish them"));
			pstmt.setString(3,"images/projectDefuault.jpg");
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
			}
		} catch (SQLException | NamingException e) {
			System.out.println("Error while creating DB:" + e.getMessage());
		} finally {
			try {
				rs.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				stmt.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				pstmt.close();
			} catch (Exception e) { /* ignored */
			}
			try {
				conn.close();
			} catch (Exception e) { /* ignored */
			}
		}
	}
}

