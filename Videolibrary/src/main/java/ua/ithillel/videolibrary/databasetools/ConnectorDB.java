package ua.ithillel.videolibrary.databasetools;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectorDB {
		
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Context ctx = (Context) new InitialContext().lookup("java:comp/env");
			DataSource ds = (DataSource) ctx.lookup("videolibrary_web");
			connection = ds.getConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		return connection;
	}
}