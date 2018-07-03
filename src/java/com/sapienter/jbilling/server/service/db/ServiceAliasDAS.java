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
package com.sapienter.jbilling.server.service.db;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.apache.log4j.Logger;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.common.Util;
//import com.sapienter.jbilling.server.service.task.SubscriptionActiveEventTask;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDAS;
import com.sapienter.jbilling.server.item.db.ItemDTO;

public class ServiceAliasDAS extends AbstractDAS<ServiceAliasDTO> {
	

	private static final Logger LOG = Logger.getLogger(ServiceAliasDAS.class);

	
    public ServiceAliasDTO findByAlias(String aliasName) {
    	LOG.debug("In findByAliase method");
        Criteria criteria = getSession().createCriteria(ServiceAliasDTO.class)
                .add(Restrictions.eq("aliasName", aliasName))
				 .add(Restrictions.eq("deleted", 0))
                .setMaxResults(1);
        
        LOG.debug("In findByOrderLine method query is executed");

        return findFirst(criteria);
    }
	public ServiceDTO findServiceByAlias(String aliasName) {
		// I need to access an association, so I can't use the parent helper class
		    ServiceAliasDTO sadto = findByAlias(aliasName);
            					
		return sadto.getServiceId();
		
	}
	
	 public List<ServiceAliasDTO> findServiceAliasbyService( Integer serviceid){

          Criteria criteria = getSession().createCriteria(ServiceAliasDTO.class)
				.add(Restrictions.eq("deleted", 0))
			      .createAlias("serviceId", "i")
                               .add(Restrictions.eq("i.id", serviceid));

             return criteria.list();
   }
	
	
	
	public ServiceAliasDTO findForUpdate(Integer serviceId) {
       LOG.debug("In findByAliase method");
        Criteria criteria = getSession().createCriteria(ServiceAliasDTO.class)
                .createAlias("ServiceAliasDTO", "sa")
                .add(Restrictions.eq("sa.serviceId", serviceId))
				 .add(Restrictions.eq("deleted", 0))
                .setMaxResults(1);
        LOG.debug("In findByOrderLine method query is executed");

        return findFirst(criteria);
    }
	
	public ServiceAliasDTO findByService(Integer serviceId) {
       LOG.debug("In findByService method");
        Criteria criteria = getSession().createCriteria(ServiceAliasDTO.class)
                 .createAlias("serviceId", "i")
                 .add(Restrictions.eq("i.id", serviceId))
                 .add(Restrictions.eq("deleted", 0))
                 .setMaxResults(1);
       return findFirst(criteria);
	   
    }
	} 

