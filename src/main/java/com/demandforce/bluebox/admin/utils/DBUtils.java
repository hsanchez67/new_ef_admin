package com.demandforce.bluebox.admin.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DBUtils {

	static Logger log  = Logger.getLogger(DBUtils.class.getName());
//	private static final String JNDI_TOMCAT_PREFIX = "java:comp/env/";
	private static boolean jndiTomcatMode = false;
	
    public static Connection getConnection(String jdbcName) {
        Connection dbConnection = null;
        try {
            dbConnection = contextLookup(jdbcName).getConnection();
        } catch(Exception e) {
            throw new RuntimeException(e);
        } 
        return dbConnection;
    }    

    /**
     * Does a JNDI name lookup, checking for tomcat name syntax also.
     */
    private static DataSource contextLookup(String jndiLookupName) {

        Context context = null;
        try {
            context = new InitialContext();
            // Don't bother looking up the normal name if we've determined we're in JNDI tomcat mode.
            if (!jndiTomcatMode) {
                try {
                    return (DataSource) context.lookup(jndiLookupName);
                } catch (Exception e) {
                    // Don't panic yet.
                    log.warn("JNDI naming exception for: " + jndiLookupName);
                }
            }

            // Normal lookup failed / is disabled. Try tomcat naming convention.
            //DataSource ds = (DataSource) context.lookup(JNDI_TOMCAT_PREFIX + jndiLookupName);
            DataSource ds = (DataSource) context.lookup(jndiLookupName);

            // This version succeeded; tomcat lookups should be enabled.
            if (!jndiTomcatMode) {
                jndiTomcatMode = true;
            }
            return ds;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (context != null) {
                try {
                    context.close();
                } catch (Exception e) {
                    // Ignore.
                }
            }
        }
    }
    
	/*
	 * @param url      - url to db(ex. <serverName>/<schema>)
	 * @param user 	   - username if db is protected
	 * @param password - password if db is protected
	 * @return Connection
	 */
	public static Connection getMySQLConnection(String url, String user, String password) throws Exception {
		// load MySQL Driver
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String database = "jdbc:mysql://";
		database+= url.trim();
		return DriverManager.getConnection( database ,(user==null?"":user),(password==null?"":password));
	}
	

	// === DBUtils ===
	public static void close(Object... objects) {
		for(Object o : objects) {
			if(o instanceof Connection)
				close((Connection)o);
			else if(o instanceof Statement)
				close((Statement)o);
			else if(o instanceof PreparedStatement)
				close((PreparedStatement)o);
			else if(o instanceof ResultSet)
				close((ResultSet)o);
			else 
				; // do nothing
		}
	}	
	
	public static void close(Connection o) {
		if(o != null)
			try {
				o.close();
				o = null;
			} catch(SQLException e) {}
	}
	public static void close(Statement o) {
		if(o != null)
			try {
				o.close();
				o = null;
			} catch(SQLException e) {}
	}
	public static void close(PreparedStatement o) {
		if(o != null)
			try {
				o.close();
				o = null;
			} catch(SQLException e) {}
	}
	public static void close(ResultSet o) {
		if(o != null)
			try {
				o.close();
				o = null;
			} catch(SQLException e) {}
	}
}
