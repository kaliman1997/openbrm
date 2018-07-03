/*
 * JBILLING CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise jBilling Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise jBilling Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise jBilling Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */
package com.sapienter.jbilling.server.device.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.server.util.db.AbstractGenericStatusDAS;




public class DeviceStatusDAS extends AbstractGenericStatusDAS<DeviceStatusDTO> {

	
	
	private static final String SQL =
            "select * from jbilling_table j join international_description i  on  j.id=i.table_id where j.name='device_status'";

  public List<DeviceStatusDTO> findAllStatus(){
	  Query query = getSession()
				 .createSQLQuery(SQL);
	  
	  return query.list();
  }
	
	
	 public List<DeviceDTO> findByStatus(Integer statusId) {
	        Criteria criteria = getSession().createCriteria(DeviceDTO.class)
	                .createAlias("deviceStatus", "d")
	                    .add(Restrictions.eq("d.id", statusId))
	                    .add(Restrictions.eq("deleted", 0));
			    
		return criteria.list();
	    }
}