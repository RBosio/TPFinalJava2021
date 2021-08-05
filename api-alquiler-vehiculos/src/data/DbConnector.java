package data;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DbConnector {

	private static DbConnector instancia;
	
	private String driver;
	private String host;
	private String port;
	private String user;
	private String password;
	private String db;
	private int conectados=0;
	private Connection conn=null;
	
	private DbConnector() throws IOException {
		try {
			String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String appConfigPath = rootPath + "config.properties";
			Properties dbProps = new Properties();
			dbProps.load(new FileInputStream(appConfigPath));
			
			driver=dbProps.getProperty("driver");
			host=dbProps.getProperty("host");
			port=dbProps.getProperty("port");
			user=dbProps.getProperty("user");
			password=dbProps.getProperty("pass");
			db=dbProps.getProperty("db");
			
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static DbConnector getInstancia() throws IOException {
		if (instancia == null) {
			instancia = new DbConnector();
		}
		return instancia;
	}
	
	public Connection getConn() {
		try {
			if(conn==null || conn.isClosed()) {
				conn=DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+db, user, password);
				conectados=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conectados++;
		return conn;
	}
	
	public void releaseConn() {
		conectados--;
		try {
			if (conectados<=0) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
