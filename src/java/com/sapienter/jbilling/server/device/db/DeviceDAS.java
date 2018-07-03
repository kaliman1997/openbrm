
  /*  jBilling - The Enterprise Open Source Billing System
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
package com.sapienter.jbilling.server.device.db;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDAS;
import com.sapienter.jbilling.server.device.db.DeviceDTO;

public class DeviceDAS extends AbstractDAS<DeviceDTO> {



	public List<DeviceDTO> findByUserSubscriptions(Integer userId) {
		// I need to access an association, so I can't use the parent helper class
		Criteria criteria = getSession().createCriteria(DeviceDTO.class)
				.createAlias("orderStatus", "s")
					.add(Restrictions.eq("s.id", Constants.ORDER_STATUS_ACTIVE))
				.add(Restrictions.eq("deleted", 0))
				.createAlias("baseUserByUserId", "u")
					.add(Restrictions.eq("u.id", userId))
				.createAlias("orderPeriod", "p")
					.add(Restrictions.ne("p.id", Constants.ORDER_PERIOD_ONCE));
		
		return criteria.list();
	}

	/*public List<DeviceDTO> findAllByEntity( Integer entityId)
	{
		Criteria criteria = getSession().createCriteria(DeviceDTO.class)
				.createAlias("deviceStatus", "s")
					.add(Restrictions.eq("s.id", Constants.DEVICE_STATUS_NEW))
				.add(Restrictions.eq("deleted", 0))
				 .createAlias("entityId", "e")
				.add(Restrictions.eq("e.id", entityId));
		
		return criteria.list();
		
		
	}*/
public List<DeviceDTO> findAllByEntity( Integer entityId)
	{
		Criteria criteria = getSession().createCriteria(DeviceDTO.class)
				.createAlias("deviceStatus", "s")
					.add(Restrictions.eq("s.id", Constants.DEVICE_STATUS_NEW))
				.add(Restrictions.eq("deleted", 0))
				 .createAlias("entity", "e")
				.add(Restrictions.eq("e.id", entityId));
		
		return criteria.list();
		
		
	}

    public List<DeviceDTO> findByEntity(Integer entityId, Integer limit) {
       
        Criteria criteria = getSession().createCriteria(DeviceDTO.class)
                .createAlias("deviceStatus", "s")
                    .add(Restrictions.or(Expression.eq("s.id", Constants.DEVICE_STATUS_NEW), 
			Expression.eq("s.id", Constants.DEVICE_STATUS_RELEASED) ));

                criteria.add(Restrictions.eq("deleted", 0));
                criteria.createAlias("entity", "e")
                    .add(Restrictions.eq("e.id", entityId));
		criteria.setMaxResults(limit);
               
        
        return criteria.list();
    }

    public DeviceDTO findByICC(String icc) {

        Criteria criteria = getSession().createCriteria(DeviceDTO.class);
                criteria.add(Restrictions.eq("deleted", 0));
                criteria.add(Restrictions.eq("icc", icc));

        return (DeviceDTO) criteria.uniqueResult();
    }
 public DeviceDTO getDeviceByIMSI(String  imsi)
	{
	
	 Criteria criteria = getSession().createCriteria(DeviceDTO.class);
                criteria.add(Restrictions.eq("deleted", 0));
                criteria.add(Restrictions.eq("imsi", imsi));

        return (DeviceDTO) criteria.uniqueResult();
	
	}







}
