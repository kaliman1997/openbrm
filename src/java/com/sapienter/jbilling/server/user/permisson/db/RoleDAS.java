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
package com.sapienter.jbilling.server.user.permisson.db;

import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.hibernate.Query;
import com.sapienter.jbilling.server.util.db.AbstractDAS;
import org.apache.log4j.Logger;
public class RoleDAS extends AbstractDAS<RoleDTO> {
	  private final static Logger LOG = Logger.getLogger(RoleDAS.class);


final String sql =
                "select r.id " +
                "from role r " +
                "WHERE  r.role_type_id = :roleTypeId "+
	             " and r.entity_id = :entityId";

	public RoleDTO findByRoleTypeIdAndCompanyId(Integer roleTypeId, Integer companyId) {
		
	    Criteria criteria =getSession().createCriteria(RoleDTO.class)
                            .add(Restrictions.eq("roleTypeId", roleTypeId));
        if (null != companyId) {
            criteria.add(Restrictions.eq("company.id", companyId));
        }
        return findFirst(criteria);
	}
	public Integer findByTypeId(Integer roleTypeId,Integer entityId ) {
		Integer  id= null;
		Query query=null;
		try {
                  query  = getSession()
					      .createSQLQuery(sql)
                          .setParameter("roleTypeId", roleTypeId)
                           .setParameter("entityId", entityId);						   
                  id= (Integer) query.uniqueResult();  
		} catch(Exception e) {
			/*Exception Raises,when uniqueResult() returns multiple records.
			Getting the First record to fix the Issue*/
			query.setMaxResults(1);
			id = (Integer) query.uniqueResult();
		}
		finally{
			return id;
		}
		
	}
		
}
