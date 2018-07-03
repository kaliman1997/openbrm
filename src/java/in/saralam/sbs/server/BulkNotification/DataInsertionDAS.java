package in.saralam.sbs.server.BulkNotification;

import org.apache.log4j.Logger;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.InternationalDescriptionDTO;
import com.sapienter.jbilling.server.util.db.AbstractDAS;



public class DataInsertionDAS extends AbstractDAS<InternationalDescriptionDTO>{
	
		private static final Logger LOG = Logger.getLogger(DataInsertionDAS.class);
		
	 
      
	 public Integer InsertData(String phoneNumber) {
			
			int foreignId;
			Integer appId = 0;
			
		final String QUERY = "select MAX(foreign_id) from international_description where foreign_id like '10%' and table_id = 47; ";
			 
           Query q = getSession().createSQLQuery(QUERY);
           List currentSeq = q.list();
                  
           if(currentSeq == null){	

				Query query = getSession().createSQLQuery("insert into international_description (table_id," +
                "foreign_id, psudo_column, language_id, content) values ( " +
                "47," + "100,"+ ",'description',1,'" + phoneNumber + "')");		   
		   			LOG.debug("No foreign_id in international_description");          
           }else{
          
        	   appId = (Integer)currentSeq.get(0);
              
			Query query = getSession().createSQLQuery("insert into international_description (table_id," +
                "foreign_id, psudo_column, language_id, content) values ( " +
                "47," + (appId + 1)+ ",'description',1,'" + phoneNumber + "')");
		
				LOG.debug("we are in  DataInsertionDAS1" + query);
			
			query.executeUpdate();
		
		}
	 
	   return (appId + 1);
						}




 public Integer InsertEmailData(String phoneNumber) {
			
			int foreignId;
			Integer appId = 0;
			
	//final String QUERY = "select MAX(foreign_id) from international_description where 'foreign_id' like '20%' and table_id =47;";
	final String QUERY = "select MAX(foreign_id) from international_description where cast(foreign_id as varchar) like '20%' and table_id=47;";
			 LOG.debug("we are in  DataInsertionDAS2 insertEmaildata" + QUERY);
           Query q = getSession().createSQLQuery(QUERY);
           List currentSeq = q.list();
                  
           if(currentSeq == null){	

			Query query = getSession().createSQLQuery("insert into international_description (table_id," +
                "foreign_id, psudo_column, language_id, content) values ( " +
                "47," + "200," + ",'description',1,'" + phoneNumber + "')");
				   
		   			LOG.debug("No foreign_id in international_description");          
           }else{
          
        	   appId = (Integer)currentSeq.get(0);
			   LOG.debug("appId is"+appId);
              
			Query query = getSession().createSQLQuery("insert into international_description (table_id," +
                "foreign_id, psudo_column, language_id, content) values ( " +
                "47," + (appId + 1)+ ",'description',1,'" + phoneNumber + "')");
		
				LOG.debug("we are in  DataInsertionDAS1" + query);
			
			query.executeUpdate();
		
		}
	 
	   return (appId + 1);
						}

}