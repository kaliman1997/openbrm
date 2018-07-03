package in.saralam.sbs.server.subscription.event;

import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;

public class ChangePlanEvent implements Event{

    private final Integer entityId;
    private final UserDTO user;

    public ChangePlanEvent(Integer entityId, UserDTO user) {
        this.entityId = entityId;
        this.user = user;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public String getName() {
        return "change plan event";
    }

    public UserDTO getUser() {
	return user;
    }
    

    public String toString() {
        return "ChangePlanEvent: entityId = " + entityId + " user = " + user; 
    }

	
}
