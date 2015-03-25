package orot;


import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.reflect.TypeToken;

import orot.model.Project;
import orot.model.Users;




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
			+ "U_EMAIL VARCHAR(80) CHECK (U_EMAIL <> '') PRIMARY KEY,"
			+ "U_PASSWORD VARCHAR(20) NOT NULL CHECK (U_PASSWORD <> ''),"
			+ "U_NICKNAME VARCHAR(20) NOT NULL CHECK (U_NICKNAME <> ''),"
			+ "U_DESCRIPTION VARCHAR(500),"
			+ "U_CONFIG VARCHAR(254),"
			+ "U_STATUS VARCHAR(32),"//ACTIVE,BANNED,SUSPENDED
			+ "U_JOIN_DATE DATE,"
			+ "U_PHONE VARCHAR(25),"
			+ "U_PICTURE VARCHAR(255),"
			+ "UNIQUE (U_NICKNAME))";
	
	
	
	public final String CREATE_PROJECT_TABLE="CREATE TABLE PROJECT("
			+ "P_NAME VARCHAR(20) CHECK (P_NAME <> '') PRIMARY KEY, "
			+ "P_DESCRIPTION VARCHAR(500),"
			+ "P_ICON VARCHAR(80) )";
	
	public final String CREATE_ADMIN_TABLE="CREATE TABLE ADMINISTRATORS("
			+ "A_EMAIL VARCHAR(80) PRIMARY KEY, "
			+ "FOREIGN KEY	(A_EMAIL) REFERENCES USERS(U_EMAIL))";
	
	public final String CREATE_TECHSUPPORT_TABLE="CREATE TABLE TECHSUPPORT("
			+ "T_EMAIL VARCHAR(80) PRIMARY KEY, "
			+ "FOREIGN KEY	(T_EMAIL) REFERENCES USERS(U_EMAIL))";
	
	public final String CREATE_LEADER_TABLE="CREATE TABLE LEADER( "
			+ "L_EMAIL VARCHAR(80) ,"
			+ "L_NAME VARCHAR(20), "
			+ "PRIMARY KEY	(L_USERNAME ,L_NAME), "
			+ "FOREIGN KEY	(L_EMAIL) REFERENCES USERS(U_EMAIL) ,"
			+ "FOREIGN KEY	(L_NAME) REFERENCES PROJECT(P_NAME))";
	
	public final String CREATE_TRANSCRIPT_SCHEME_TABLE="CREATE TABLE TRANSCRIPT_SCHEME( "
			+ "TS_ID INT PRIMARY KEY, "
			+ "TS_PGP VARCHAR(20), "
			+ "TS_EMAIL VARCHAR(80), "
			+ "TS_NUM_OF_COLUMNS  INT,"
			+ "TS_NUM_OF_ROWS INT,"
			+ "TS_ABNORMAL_SECTION VARCHAR(128)";
	/*---------------------------------------------------------------------------------------------------------*/
	/*                                   sql select table queries                                              */
	/*---------------------------------------------------------------------------------------------------------*/	

	public final String CHECK_USERNAME_PASSWORD= "SELECT * "
			+ "FROM USERS "
			+ "WHERE U_EMAIL=? "
			+ "AND U_PASSWORD=?";


	public final String SELECT_USER_BY_EMAIL ="SELECT * "
			+ "FROM USERS "
			+ "WHERE U_EMAIL=? ";
	
	public final String SELECT_ALL_PROJECTS= "SELECT * "
			+ "FROM PROJECT ";
	/*---------------------------------------------------------------------------------------------------------*/
	/*                                   sql insert and delete queries                                         */
	/*---------------------------------------------------------------------------------------------------------*/
	public final String ADD_NEW_USER="INSERT INTO USERS VALUES(?,?,?,?,?,?,?,?,?)";
	public final String ADD_NEW_PROJECT="INSERT INTO PROJECT VALUES(?,?,?)";
	
	
	/*---------------------------------------------------------------------------------------------------------*/
	/*                                   object lists                                                          */
	/*---------------------------------------------------------------------------------------------------------*/

	
	public final Type PROJECT_COLLECTION = new TypeToken<Collection<Project>>() {}.getType();
	public final Type USER_COLLECTION = new TypeToken<Collection<Users>>() {}.getType();
}



//cd C:\Users\priva_000\Desktop\web_project\db-derby-10.11.1.1-bin\bin
//connect 'jdbc:derby:C:\Users\priva_000\Desktop\web_project\eclipse\OrotDB';
//drop table username.users;