package in.saralam.sbs.server.notification.db;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.common.CommonConstants;
import com.sapienter.jbilling.server.util.db.AbstractDAS;

              
    
public class NotificationEventDAS extends AbstractDAS<NotificationEventDTO> {
    private static final Logger LOG = Logger.getLogger(NotificationEventDAS.class);
   
    
    public List<NotificationEventDTO> findAll() {
        Criteria criteria = getSession().createCriteria(NotificationEventDTO.class);        
        return  criteria.list();
    }   

}
