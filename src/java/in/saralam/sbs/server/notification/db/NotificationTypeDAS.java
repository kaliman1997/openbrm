package in.saralam.sbs.server.notification.db;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;

import com.sapienter.jbilling.server.device.db.DeviceStatusDTO;
import com.sapienter.jbilling.server.util.db.AbstractGenericStatusDAS;

              
    
public class NotificationTypeDAS extends AbstractGenericStatusDAS<NotificationTypeDTO> {
    private static final Logger LOG = Logger.getLogger(NotificationTypeDAS.class);
   
    
    public List<NotificationTypeDTO> findAll() {
        Criteria criteria = getSession().createCriteria(NotificationTypeDTO.class);        
        return  criteria.list();
    }   
    
   

}
