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
package in.saralam.sbs.server.subscription.db;

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
import in.saralam.sbs.server.subscription.task.SubscriptionActiveEventTask;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDAS;
import  com.sapienter.jbilling.server.item.db.ItemDTO;

public class ServiceDAS extends AbstractDAS<ServiceDTO> {
	

	private static final Logger LOG = Logger.getLogger(ServiceDAS.class);

	
    public ServiceDTO findByOrderLine(Integer orderLineId) {
    	LOG.debug("In findByOrderLine method");
        Criteria criteria = getSession().createCriteria(ServiceDTO.class)
                .createAlias("orderLineDTO", "order")
                .add(Restrictions.eq("order.id", orderLineId))
                .setMaxResults(1);
        LOG.debug("In findByOrderLine method query is executed");

        return findFirst(criteria);
    }
	
		public List<ServiceDTO> findByActiveServices(Integer statusId) {
		// I need to access an association, so I can't use the parent helper class
		LOG.debug("in serviceDAS findByActiveServices method");
		Criteria criteria = getSession().createCriteria(ServiceDTO.class)
				.add(Restrictions.eq("deleted", 0))
				.createAlias("serviceStatus", "s")
					.add(Restrictions.eq("s.id", statusId));
		LOG.debug("in serviceDAS findByActiveServices method ending.. "+criteria.list());
		return criteria.list();
	}
	
	public ServiceDTO findByOrder(Integer orderId) {
    	LOG.debug("In findByOrder method");
        Criteria criteria = getSession().createCriteria(ServiceDTO.class)
                .createAlias("orderDTO", "orderdto")
                .add(Restrictions.eq("orderdto.id", orderId))
                .setMaxResults(1);
        LOG.debug("In findByOrder method query is executed");

        return findFirst(criteria);
    }
	
	
	public List<ServiceDTO> findByUserSubscriptions(Integer userId) {
		// I need to access an association, so I can't use the parent helper class
		Criteria criteria = getSession().createCriteria(ServiceDTO.class)
				.createAlias("orderStatus", "s")
					.add(Restrictions.eq("s.id", Constants.ORDER_STATUS_ACTIVE))
				.add(Restrictions.eq("deleted", 0))
				.createAlias("baseUserByUserId", "u")
					.add(Restrictions.eq("u.id", userId))
				.createAlias("orderPeriod", "p")
					.add(Restrictions.ne("p.id", Constants.ORDER_PERIOD_ONCE));
		
		return criteria.list();
	}

	 public List<ServiceDTO> findSubscriptionsByUser(Integer userId) {
                // I need to access an association, so I can't use the parent helper class
		final String hql = "select s.*  from service s, purchase_order o, order_line ol " +
		 " where s.user_id = :user" + 
		" and s.order_id = o.id " +
		" and s.order_line_id = ol.id " +
		" and ol.order_id = o.id " + 
		" and o.deleted = 0 "+
		" and ol.deleted = 0 ";

		Query query = getSession().createQuery(hql);
        	query.setParameter("user", userId);

        	return query.list();

        }
	
	public List<ServiceDTO> findByUser_Status(Integer userId,Integer statusId) {
		// I need to access an association, so I can't use the parent helper class
		Criteria criteria = getSession().createCriteria(ServiceDTO.class)
				.add(Restrictions.eq("deleted", 0))
				.createAlias("baseUserByUserId", "u")
					.add(Restrictions.eq("u.id", userId))
				.createAlias("orderStatus", "s")
					.add(Restrictions.eq("s.id", statusId));
		
		return criteria.list();
	}

    // used for the web services call to get the latest X orders
    public List<Integer> findIdsByUserLatestFirst(Integer userId, int maxResults) {
        Criteria criteria = getSession().createCriteria(ServiceDTO.class)
                .add(Restrictions.eq("deleted", 0))
                .createAlias("baseUserByUserId", "u")
                    .add(Restrictions.eq("u.id", userId))
                .setProjection(Projections.id())
                .addOrder(Order.desc("id"))
                .setMaxResults(maxResults)
                .setComment("findIdsByUserLatestFirst " + userId + " " + maxResults);
        return criteria.list();
    }


	

    // used for the web services call to get the latest X orders that contain an item of a type id
    @SuppressWarnings("unchecked")
    public List<Integer> findIdsByUserAndItemTypeLatestFirst(Integer userId, Integer itemTypeId, int maxResults) {
        // I'm a HQL guy, not Criteria
        String hql = 
            "select distinct(orderObj.id)" +
            " from ServiceDTO orderObj" +
            " inner join orderObj.lines line" +
            " inner join line.item.itemTypes itemType" +
            " where itemType.id = :typeId" +
            "   and orderObj.baseUserByUserId.id = :userId" +
            "   and orderObj.deleted = 0" +
            " order by orderObj.id desc";
        List<Integer> data = getSession()
                                .createQuery(hql)
                                .setParameter("userId", userId)
                                .setParameter("typeId", itemTypeId)
                                .setMaxResults(maxResults)
                                .list();
        return data;
    }

	/**
	 * @author othman
	 * @return list of active orders
	 */
	public List<ServiceDTO> findToActivateServices() {
		Date today = Util.truncateDate(new Date());
		Criteria criteria = getSession().createCriteria(ServiceDTO.class);

		criteria.add(Restrictions.eq("deleted", 0));
		criteria.add(Restrictions.or(Expression.le("activeSince", today),
				Expression.isNull("activeSince")));
		criteria.add(Restrictions.or(Expression.gt("activeUntil", today),
				Expression.isNull("activeUntil")));

		return criteria.list();
	}

	/**
	 * @author othman
	 * @return list of inactive orders
	 */
	public List<ServiceDTO> findToDeActiveServices() {
		Date today = Util.truncateDate(new Date());
		Criteria criteria = getSession().createCriteria(ServiceDTO.class);

		criteria.add(Restrictions.eq("deleted", 0));
		criteria.add(Restrictions.or(Expression.gt("activeSince", today),
				Expression.le("activeUntil", today)));

		return criteria.list();
	}
	
	public BigDecimal findIsUserSubscribedTo(Integer userId, Integer itemId) {
		String hql = 
				"select sum(l.quantity) " +
				"from ServiceDTO o " +
				"inner join o.lines l " +
				"where l.item.id = :itemId and " +
				"o.baseUserByUserId.id = :userId and " +
				"o.orderPeriod.id != :periodVal and " +
				"o.orderStatus.id = :status and " +
				"o.deleted = 0 and " +
				"l.deleted = 0";

        BigDecimal result = (BigDecimal) getSession()
                .createQuery(hql)
                .setInteger("userId", userId)
                .setInteger("itemId", itemId)
                .setInteger("periodVal", Constants.ORDER_PERIOD_ONCE)
                .setInteger("status", Constants.ORDER_STATUS_ACTIVE)
                .uniqueResult();
        
        return (result == null ? BigDecimal.ZERO : result);
	}
	
	public Integer[] findUserItemsByCategory(Integer userId, 
			Integer categoryId) {
		
		Integer[] result = null;
		
        final String hql =
                "select distinct(i.id) " +
                "from ServiceDTO o " +
                "inner join o.lines l " +
                "inner join l.item i " +
                "inner join i.itemTypes t " +
                "where t.id = :catId and " +
                "o.baseUserByUserId.id = :userId and " +
                "o.orderPeriod.id != :periodVal and " +
                "o.deleted = 0 and " +
                "l.deleted = 0";
        List qRes = getSession()
                .createQuery(hql)
                .setInteger("userId", userId)
                .setInteger("catId", categoryId)
                .setInteger("periodVal", Constants.ORDER_PERIOD_ONCE)
                .list();
        if (qRes != null && qRes.size() > 0) {
            result = (Integer[])qRes.toArray(new Integer[0]);
        }
        return result;
	}

    public List<ServiceDTO> findOneTimersByDate(Integer userId, Date activeSince) {
        final String hql = 
        "select o " +
        "  from ServiceDTO o " +
        " where o.baseUserByUserId.id = :userId " +
        "   and o.orderPeriod.id = " + Constants.ORDER_PERIOD_ONCE +
        "   and activeSince = :activeSince " +
        "   and deleted = 0";

        List<ServiceDTO> result = (List<ServiceDTO>) getSession()
                .createQuery(hql)
                .setInteger("userId", userId)
                .setDate("activeSince", activeSince).list();

        return result;
    }

    public ServiceDTO findForUpdate(Integer id) {
        ServiceDTO retValue = super.findForUpdate(id);
        // lock all the lines
        /*-s-for (ServiceLineDTO line : retValue.getLines()) {
            new ServiceLineDAS().findForUpdate(line.getId());
        }-s-*/
        return retValue;
    }

    public ItemDTO getPlanById ( Integer serviceId) {

      String hql = 
                "select l.item " +
                "from ServiceDTO s " +
                "inner join s.orderLineDTO l " +
                "where s.id = :serviceId and " +
		"s.orderLineDTO.id = l.id and " + 
		"s.orderLineDTO.orderLineType.id = :oltype and " + 
                "s.deleted = 0 and " +
                "l.deleted = 0";

	ItemDTO result = (ItemDTO) getSession().createQuery(hql)
		.setInteger("serviceId", serviceId)
		.setInteger("oltype", 1)
		.uniqueResult();
                
        return result;
    } 
	
	public List<ServiceDTO> findActiveSubscriptionsByUser(Integer userId){
		List<ServiceDTO> result = null;
		return result;
	}
	
		public List<ServiceDTO> findToDeactivateServices() {
		// I need to access an association, so I can't use the parent helper class
		Criteria criteria = getSession().createCriteria(ServiceDTO.class)
				.createAlias("orderDTO", "o")
				.createAlias("o.orderStatus", "s")
					.add(Restrictions.eq("s.id", Constants.ORDER_STATUS_FINISHED))
				.add(Restrictions.eq("deleted", 0))
				.createAlias("serviceStatus", "ss")
					.add(Restrictions.eq("ss.id", Constants.SERVICE_STATUS_ACTIVE));
		
		return criteria.list();
	}
}
