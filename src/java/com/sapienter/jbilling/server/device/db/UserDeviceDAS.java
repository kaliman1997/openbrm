package com.sapienter.jbilling.server.device.db;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

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

public class UserDeviceDAS extends AbstractDAS<UserDeviceDTO>{


	private final Logger LOG = Logger.getLogger(UserDeviceDAS.class);
	
	 public static final String FIND_USER_DEVICE_HQL =
        	"SELECT userDevice " +
        	"  FROM UserDeviceDTO userDevice " +
        	" WHERE userDevice.baseUser.id = :userId " + 
		" AND userDevice.deleted = 0";


	 public List<UserDeviceDTO> findByUser(Integer userId) {
	        // I need to access an association, so I can't use the parent helper class

		/*
		Query query = getSession().createQuery(FIND_USER_DEVICE_HQL);
        	query.setParameter("userId", userId);
        	return (UserDeviceDTO) query.uniqueResult();
		*/
	        Criteria criteria = getSession().createCriteria(UserDeviceDTO.class)
	                .createAlias("baseUser", "u")
	                    .add(Restrictions.eq("u.id", userId))
	                    .add(Restrictions.eq("deleted", 0));
			    
		return criteria.list();
	    }
	
	 public UserDeviceDTO findByUserAndDevice(Integer userId, DeviceDTO deviceId) {
	        Criteria criteria = getSession().createCriteria(DeviceDTO.class)
	                .createAlias("deviceStatus", "s")
	                .add(Restrictions.eq("s.id", Constants.DEVICE_STATUS_QUARANTINED))
	                .add(Restrictions.eq("deleted", 0))
	                .createAlias("baseUserByUserId", "u")
	                .add(Restrictions.eq("u.id", userId))
	                .add(Restrictions.eq("device_id", deviceId))
	                .setMaxResults(1);

	        return findFirst(criteria);
	    }

	public UserDeviceDTO getUserDeviceByDeviceId(Integer deviceId) {

        	Criteria criteria = getSession().createCriteria(UserDeviceDTO.class)
                            .createAlias("device", "d")
                              .add(Restrictions.eq("deleted", 0))
                             .add(Restrictions.eq("d.id", deviceId))
                              .setMaxResults(1);
                              return findFirst(criteria);
  	}

	public UserDeviceDTO getUserDeviceByUserAndOrderAndOrderLine(Integer userId,Integer orderId,Integer orderLineId ) {	

      		Criteria criteria = getSession().createCriteria(UserDeviceDTO.class)
    		  			  .createAlias("baseUser", "user")
    		  			  .add(Restrictions.eq("user.id", userId))
                          .createAlias("orderId", "order")
                          .add(Restrictions.eq("order.id", orderId))
                          .createAlias("orderLineId", "orderLine")
                          .add(Restrictions.eq("orderLine.id", orderLineId))
                            .add(Restrictions.eq("deleted", 0))
                            .setMaxResults(1);
                            return (UserDeviceDTO)findFirst(criteria);
	}
	
	
	public List<UserDeviceDTO> getUserDeviceByUserAndOrder(Integer userId,Integer orderId) {	

      		Criteria criteria = getSession().createCriteria(UserDeviceDTO.class)
    		  			  .createAlias("baseUser", "user")
    		  			  .add(Restrictions.eq("user.id", userId))
                          .createAlias("orderId", "order")
                          .add(Restrictions.eq("order.id", orderId))
                          .add(Restrictions.eq("deleted", 0))
                          .setMaxResults(1);
                          return criteria.list();
	}

	public UserDeviceDTO findUserDeviceByOrderAndLine(Integer orderId,Integer orderLineId ) {
	      Criteria criteria = getSession().createCriteria(UserDeviceDTO.class)
                          .createAlias("orderId", "order")
                          .add(Restrictions.eq("order.id", orderId))
                          .createAlias("orderLineId", "orderLine")
                          .add(Restrictions.eq("orderLine.id", orderLineId))
                          .add(Restrictions.eq("deleted", 0))
                          .setMaxResults(1);

                          return (UserDeviceDTO)findFirst(criteria);
	}

	public List<UserDeviceDTO> findUserDeviceByService(Integer orderId,Integer orderLineId ) {
	      
	      LOG.debug("searching user devices by Order Id " + orderId + " order line " + orderLineId);
              Criteria criteria = getSession().createCriteria(UserDeviceDTO.class)
                          .createAlias("orderId", "order")
                          .add(Restrictions.eq("order.id", orderId))
                          .createAlias("orderLineId", "orderLine")
                          .add(Restrictions.eq("orderLine.id", orderLineId))
                          .add(Restrictions.eq("deleted", 0));

			

                          return criteria.list();
        }

	public UserDeviceDTO findByTelephoneNumber(String telephoneNumber){
	
		Criteria criteria = getSession().createCriteria(UserDeviceDTO.class)
                          .add(Restrictions.eq("telephoneNumber", telephoneNumber))
                          .setMaxResults(1);

                          return (UserDeviceDTO)findFirst(criteria);

	}

	public UserDeviceDTO findByExtId1(String extId1){

                Criteria criteria = getSession().createCriteria(UserDeviceDTO.class)
                          .add(Restrictions.eq("extId1", extId1))
			  .add(Restrictions.eq("deleted", 0))
                          .setMaxResults(1);

                          return (UserDeviceDTO)findFirst(criteria);

        }




}
