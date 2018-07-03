/*
    jBilling - The Enterprise Open Source Billing System
    Copyright (C) 2003-2009 Enterprise jBilling Software Ltd. and Emiliano Conde

    This file is part of jbilling.

    jbilling is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    jbilling is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with jbilling.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.sapienter.jbilling.server.user.event;

import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.UserBL;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.ContactBL;
import com.sapienter.jbilling.server.system.event.Event;
import com.sapienter.jbilling.server.user.contact.db.ContactDTO;
import javax.naming.*;
import javax.ejb.*;

public class NewCustomerEvent implements Event {

    private final Integer entityId;
    private final Integer userId;
    private final ContactDTO contact;

    public NewCustomerEvent(Integer entityId, Integer userId, ContactDTO contact) {
        this.entityId = entityId;
        this.userId = userId;
	this.contact = contact;
    }

    public Integer getUserId()
    {
    	return userId;
    }  
    
    public Integer getEntityId() {
        return entityId;
    }

    public String getName() {
        return "user created event";
    }

    public UserDTO getUser() {
	return new UserBL(userId).getEntity();
    }
    
    public UserWS getUserWS() {
	return new UserBL(userId).getUserWS();
    }  

    public ContactDTO getContact() {
	return contact;

    }

    public String toString() {
        return "NewCustomerEvent: entityId = " + entityId + " user = " + userId + "Conatct = " + contact; 
    }

}
