package avanzadagroup.net.dataacess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import avanzadagroup.net.altanAPI.OAuth;

import com.sapienter.jbilling.common.FormatLogger;
import com.sapienter.jbilling.server.util.Context;

public class BanwireStoreBarcode {
	private static final FormatLogger LOG = new FormatLogger(
			Logger.getLogger(OAuth.class));

	private static final String POSTGRES_PORT = "5432";
	private static final String POSTGRES_URL = "jdbc:postgresql://localhost:"
			+ POSTGRES_PORT + "/";
	private static final String POSTGRES_DRIVER_NAME = "org.postgresql.Driver";
	private static final String DB_NAME = "openbrm_prod";
	private static final String USERNAME = "openbrm_prod";
	private static final String PASSWORD = "openbrm_prod";

	public synchronized static void write(Integer invoiceId , String resultCode, Integer barcodeId, 
			String barcode, String barcodeImage){
			String query = "";	
	       Connection conn = null;

	        try {
	            Class.forName(POSTGRES_DRIVER_NAME).newInstance();
	            conn = DriverManager.getConnection(POSTGRES_URL + DB_NAME, USERNAME, PASSWORD);
	            LOG.debug("CBOSS:: Connected to the database...");
	            Statement st = conn.createStatement();
	            query = "INSERT INTO banwire_store_barcode VALUES(default, "
	            		+ invoiceId + ", " + "'"+barcode+"', '"+barcodeImage+"', "
	            								+ "'" + resultCode+ "', " + barcodeId+ ", 1)";	
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
	
	public static String getStoreBarCode(Integer invoiceId) {
		
		Connection connection = null;

		try {
            Class.forName(POSTGRES_DRIVER_NAME).newInstance();
            connection = DriverManager.getConnection(POSTGRES_URL + DB_NAME, USERNAME, PASSWORD);

			Statement stmt = connection.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT result_code, barcode, barcode_img from banwire_store_barcode where invoice_id = " + invoiceId);
			
			if(rs.next()){
				return rs.getString("result_code") + "|" + rs.getString("barcode") + "|" +rs.getString("barcode_img");
			} else {
				return "not_found";
			}
			
		} catch (Exception e) {
			LOG.debug("CBOSS:: Exception %s", e);
			return "error";
		} finally {
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					LOG.debug(e);
				}
			}
		}

	}	
}
