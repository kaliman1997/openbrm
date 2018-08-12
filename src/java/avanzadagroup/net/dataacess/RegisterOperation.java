package avanzadagroup.net.dataacess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

import avanzadagroup.net.altanAPI.OAuth;

import com.sapienter.jbilling.common.FormatLogger;

public class RegisterOperation {
	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(OAuth.class));

	private static final String POSTGRES_PORT = "5432";
	private static final String POSTGRES_URL = "jdbc:postgresql://localhost:"
			+ POSTGRES_PORT + "/";
	private static final String POSTGRES_DRIVER_NAME = "org.postgresql.Driver";
	private static final String DB_NAME = "openbrm_prod";
	private static final String USERNAME = "openbrm_prod";
	private static final String PASSWORD = "openbrm_prod";

	public synchronized static void write(String operationType, String operationResultCode, String response, 
			String msisdn){
			String query = "";	
	       Connection conn = null;
	        String url = POSTGRES_URL;
	        String dbName = DB_NAME;
	        String driver = POSTGRES_DRIVER_NAME;
	        String userName = USERNAME;
	        String password = PASSWORD;

	        try {
	            Class.forName(driver).newInstance();
	            conn = DriverManager.getConnection(url + dbName, userName, password);
	            LOG.debug("CBOSS:: Connected to the database...");
	            Statement st = conn.createStatement();
	            query = "INSERT INTO altan_requests VALUES(default, "
	            		+ "current_timestamp, '"+operationType+"', '"+operationResultCode+"', "
	            				+ "'"+response.replace("'", "''")+"', '"+msisdn+"', 0)";
	            st.executeUpdate(query);
	            
	            LOG.debug("CBOSS:: Disconnected from database...");
	        } catch (Exception e) {
	        	
	        	
	            LOG.debug("CBOSS:: An error ocurred accessing the DB..." + e);
	            LOG.debug("CBOSS:: query " + query);	            
	            for(StackTraceElement ste:e.getStackTrace()){
	            	LOG.debug("CBOSS::" + ste.toString());
	            }
	        } finally {
	        	if(conn!=null){
	        		try {
						conn.close();
					} catch (SQLException e) {
						LOG.debug("CBOSS::"+ e);
					}
	        	}
	        }
		
	}
}
