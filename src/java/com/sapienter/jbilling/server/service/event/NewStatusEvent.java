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

package com.sapienter.jbilling.server.service.event;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.service.ServiceBL;
import com.sapienter.jbilling.server.system.event.Event;

public class NewStatusEvent implements Event {

    private static final Logger LOG = Logger.getLogger(NewStatusEvent.class); 
    private Integer entityId;
    private Integer userId;
    private Integer serviceId;
    private Integer serviceType;
    private Integer oldStatusId;
    private Integer newStatusId;
    
    public NewStatusEvent(Integer serviceId, Integer oldStatusId, Integer newStatusId) {
        try {
            ServiceBL service = new ServiceBL(serviceId);
            
            this.entityId = service.getEntity().getUser().getEntity().getId();
            this.userId = service.getEntity().getUser().getUserId();
            //this.serviceType = service.getEntity().getServicePeriod().getId();
            this.oldStatusId = oldStatusId;
            this.newStatusId = newStatusId;
        } catch (Exception e) {
            LOG.error("Handling service in event", e);
        } 
        this.serviceId = serviceId;
    }
    
    public Integer getEntityId() {
        return entityId;
    }

    public String getName() {
        return "New status";
    }

    public String toString() {
        return getName();
    }
    public Integer getServiceId() {
        return serviceId;
    }
    public Integer getUserId() {
        return userId;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public Integer getNewStatusId() {
        return newStatusId;
    }

    public Integer getOldStatusId() {
        return oldStatusId;
    }

    
}
