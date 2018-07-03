package in.saralam.sbs.server.crm.event;

import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;

public class CRMEvent implements Event{

    private final Integer entityId;
    private final UserDTO user;
    private String name;

    public CRMEvent(Integer entityId, UserDTO user) {
        this.entityId = entityId;
        this.user = user;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public String getName() {
        return "crm event";
    }

    public UserDTO getUser() {
	return user;
    }
    
    public String getEventName() {
    	return name;
        }
    
    public void setEventName(String name) {
    	this.name=name;
        }
    

    public String toString() {
        return "CRMEvent: entityId = " + entityId + " user = " + user; 
    }

	
}
