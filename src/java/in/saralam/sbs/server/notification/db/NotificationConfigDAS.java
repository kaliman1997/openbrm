package in.saralam.sbs.server.notification.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.server.notification.db.NotificationMessageLineDTO;
import com.sapienter.jbilling.server.util.db.AbstractDAS;

public class NotificationConfigDAS extends AbstractDAS<NotificationConfigDTO>{

	 public List<NotificationConfigDTO> findAll(Integer entityId) {
	        Criteria criteria = getSession().createCriteria(NotificationConfigDTO.class)
	        		.createAlias("messageId", "m")
	        		.createAlias("m.notificationMessageSection", "s")
	        		.createAlias("s.notificationMessage", "n")
	        		.createAlias("n.entity", "e")
                    .add(Restrictions.eq("e.id", entityId));              
	        return  criteria.list();
	    }
	 
	 public List<NotificationMessageLineDTO> findNotificationMessages(Integer entityId) {
	        Criteria criteria = getSession().createCriteria(NotificationMessageLineDTO.class)
	        		.createAlias("notificationMessageSection", "s")
                    .add(Restrictions.eq("s.section", 1))
                    .createAlias("s.notificationMessage", "n")
	        		.createAlias("n.entity", "e")
                    .add(Restrictions.eq("e.id", entityId));        
	        return  criteria.list();
	    }
		
	 public List<NotificationConfigDTO> findConfigByEventName(String eventName){
		 Criteria  criteria = getSession().createCriteria(NotificationConfigDTO.class)
	        		.createAlias("subject", "e")
	        		.add(Restrictions.eq("e.subject", eventName))
	        		.add(Restrictions.eq("deleted", 0));
		 return criteria.list();
	 }	
}
