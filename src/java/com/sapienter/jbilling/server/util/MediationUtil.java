/*
 * OpenBRM CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise OpenBRM Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise OpenBRM Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise OpenBRM Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */

/*
 * Created on Oct 08, 2013
 */
package com.sapienter.jbilling.server.util;

import org.apache.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.*;
import com.sapienter.jbilling.common.JNDILookup;
import javax.naming.NamingException;
import com.sapienter.jbilling.server.util.MediationErrorWS;

/**
     * Ok, this is cheating, but heck is easy and fast.
     * @param fromDate
     * @param toDate
     * @return
     * @throws SQLException
     * @throws NamingException
     */
	 
public class MediationUtil {
    private static final Logger log = Logger.getLogger(MediationUtil.class);
   
    public static List<MediationErrorWS> getMediationErrorsByDate(String fromDate, String toDate, int limit) throws SQLException, NamingException {
        
		log.debug("Perform getting errors from database ");
        //String datefrom = fromDate;
		//String dateto = toDate;
		log.debug("fromdate and todate are " + fromDate + " and " + toDate + " and " +limit);
		MediationErrorWS mediationErrorWS = null;
		List<MediationErrorWS> mediationErrorList = new ArrayList<MediationErrorWS>();
		Connection connection = null;
        try {
            
			JNDILookup jndi = JNDILookup.getFactory();
            connection = jndi.lookUpDataSource().getConnection();
			    StringBuilder query = new StringBuilder();
                query.append("SELECT * FROM mediation_errors");
                
				if(fromDate.isEmpty() && toDate.isEmpty()){
			  	     query.append(" ORDER BY jbilling_timestamp DESC")
					      .append(" LIMIT ")
					      .append(limit);
				}
				else{
				     query.append(" WHERE jbilling_timestamp BETWEEN '"+ fromDate +"' AND '"+ toDate +"'")
				          .append(" LIMIT ")
					      .append(limit);
				     }
		   
    		log.debug("SQL query: '" + query + "'");
			PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
			ResultSet result = preparedStatement.executeQuery();
			
		    while (result.next()) {
			mediationErrorWS = new MediationErrorWS(result.getString("uniqid"), result.getString("index"), result.getString("startTime"), result.getString("endTime"), result.getInt("volume"), 
			                                         result.getString("metricType"), result.getString("error_message"), result.getTimestamp("jbilling_timestamp"));
			mediationErrorList.add(mediationErrorWS);
				}
            }
			catch (SQLException e) {
            log.error("getting errors from database failed", e);
                }
			
			finally {
                if (connection != null) {
                try {
                    connection.close();
                        } catch (SQLException e) {
                        log.error(e);
                    }
                }
            }
        
		return mediationErrorList; 
        
    }
}
    	
	


