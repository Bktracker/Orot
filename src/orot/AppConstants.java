package orot;


import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.reflect.TypeToken;

import orot.model.Project;




/**
 * a interface for system wide constants and configuration
 * all sql queries are save here
*/
public interface AppConstants {

	/*---------------------------------------------------------------------------------------------------------*/
	/*                                      general constants                                                  */
	/*---------------------------------------------------------------------------------------------------------*/	
	public final String DB_NAME = "OrotDB";
	public final String DB_DATASOURCE = "java:comp/env/jdbc/OrotDatasource";

	/*---------------------------------------------------------------------------------------------------------*/
	/*                                   sql create table queries                                              */
	/*---------------------------------------------------------------------------------------------------------*/	

	
	public final String CREATE_USER_TABLE="CREATE TABLE USERS("
			+ "U_USERNAME VARCHAR(20) CHECK (U_USERNAME <> '') PRIMARY KEY,"
			+ "U_PASSWORD VARCHAR(20) NOT NULL CHECK (U_PASSWORD <> ''),"
			+ "U_NICKNAME VARCHAR(20) NOT NULL CHECK (U_NICKNAME <> ''),"
			+ "U_DESCRIPTION VARCHAR(500),"
			+ "U_EMAIL VARCHAR(80),"
			+ "U_CONFIG VARCHAR(254),"
			+ "U_STATUS VARCHAR(32),"//ACTIVE,BANNED,SUSPENDED
			+ "U_JOIN_DATE DATE,"//ACTIVE,BANNED,SUSPENDED
			+ "U_PHONE VARCHAR(25),"//ACTIVE,BANNED,SUSPENDED
			+ "U_PICTURE VARCHAR(255),"//ACTIVE,BANNED,SUSPENDED
			+ "UNIQUE (U_NICKNAME))";
	
	
	
	public final String CREATE_PROJECT_TABLE="CREATE TABLE PROJECT("
			+ "P_NAME VARCHAR(20) CHECK (P_NAME <> '') PRIMARY KEY, "
			+ "P_DESCRIPTION VARCHAR(500),"
			+ "P_ICON VARCHAR(80) )";
			
	/*---------------------------------------------------------------------------------------------------------*/
	/*                                   sql select table queries                                              */
	/*---------------------------------------------------------------------------------------------------------*/	

	public final String CHECK_USERNAME_PASSWORD= "SELECT * "
			+ "FROM USERS "
			+ "WHERE U_USERNAME=? "
			+ "AND U_PASSWORD=?";
	
	
	public final String SELECT_ALL_PROJECTS= "SELECT * "
			+ "FROM PROJECT ";
	/*---------------------------------------------------------------------------------------------------------*/
	/*                                   sql insert and delete queries                                         */
	/*---------------------------------------------------------------------------------------------------------*/
	public final String ADD_NEW_USER="INSERT INTO USERS VALUES(?,?,?,?,?,?,?,?,?,?)";
	public final String ADD_NEW_PROJECT="INSERT INTO PROJECT VALUES(?,?,?)";
	
	
	
	
	public final Type PROJECT_COLLECTION = new TypeToken<Collection<Project>>() {}.getType();
}



//cd C:\Users\priva_000\Desktop\web_project\db-derby-10.11.1.1-bin\bin
//connect 'jdbc:derby:C:\Users\priva_000\Desktop\web_project\eclipse\OrotDB';
//drop table username.users;