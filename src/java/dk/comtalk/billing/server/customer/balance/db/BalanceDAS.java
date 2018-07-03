
package dk.comtalk.billing.server.customer.balance.db;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.common.CommonConstants;
import com.sapienter.jbilling.server.util.db.AbstractDAS;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDAS;


              
    
public class BalanceDAS extends AbstractDAS<BalanceDTO> {
    private static final Logger LOG = Logger.getLogger(BalanceDAS.class);
   
       

	public List<BalanceDTO> findByUserId(Integer userId) {
		// I need to access an association, so I can't use the parent helper class
		 Criteria criteria = getSession().createCriteria(BalanceDTO.class)
                                .createAlias("baseUserByUserId", "u")
                                .add(Restrictions.eq("u.id", userId))
                                .add(Restrictions.eq("deleted", 0));
		
		return criteria.list();
	}

	public List<BalanceDTO> findValidByUser(Integer userId) {
                // I need to access an association, so I can't use the parent helper class
                Criteria criteria = getSession().createCriteria(BalanceDTO.class)
				.createAlias("baseUserByUserId", "u")
                                .add(Restrictions.eq("u.id", userId))
		                .add(Restrictions.eq("deleted", 0));

	        Date today = Util.truncateDate(new Date());
        	criteria.add(Restrictions.or(Expression.le("activeSince", today),
                Expression.isNull("activeSince")));
        	criteria.add(Restrictions.or(Expression.gt("activeUntil", today),
                Expression.isNull("activeUntil")));

        	return criteria.list();

        }

	public List<BalanceDTO> findValidByUserAndCurrency(Integer userId, Integer currencyId) {
                // I need to access an association, so I can't use the parent helper class
                Criteria criteria = getSession().createCriteria(BalanceDTO.class)
                                .createAlias("baseUserByUserId", "u")
                                .add(Restrictions.eq("u.id", userId))
				.createAlias("currency", "c")
				.add(Restrictions.eq("c.id", currencyId))
                                .add(Restrictions.eq("deleted", 0));

                Date today = Util.truncateDate(new Date());
                criteria.add(Restrictions.or(Expression.le("activeSince", today),
                Expression.isNull("activeSince")));
                criteria.add(Restrictions.or(Expression.gt("activeUntil", today),
                Expression.isNull("activeUntil")));

                return criteria.list();

        }


			public List<BalanceDTO> findValidByUserAndCurrency(Integer userId, Integer currencyId, Date recStartDate, Date recEndDate) {
				LOG.debug("In findValidByUserAndCurrency with startDate "+recStartDate+" and " +recEndDate);
                // I need to access an association, so I can't use the parent helper class
				if(recEndDate==null){
					LOG.debug("recEndDate is null");
					recEndDate = new Date();
				}
                Date startDate= Util.truncateDate(recStartDate);
                Date endDate = Util.truncateDate(recEndDate);  
                
                Criteria criteria = getSession().createCriteria(BalanceDTO.class)
                                .createAlias("baseUserByUserId", "u")
                                .add(Restrictions.eq("u.id", userId))
                                .createAlias("currency", "c")
                                .add(Restrictions.eq("c.id", currencyId))
                                .add(Restrictions.eq("deleted", 0))
                				.add(Restrictions.and((Restrictions.or(Restrictions.le("activeSince", startDate), Restrictions.isNull("activeSince"))),(Restrictions.or(Restrictions.gt("activeUntil", endDate), Restrictions.isNull("activeUntil")))));
                				
                LOG.debug("criteria is"+criteria.toString());

                return criteria.list();

        }


	public List<BalanceDTO> getUserBalancesByUserOrderAndOrderLine(Integer userId, Integer orderId, Integer orderLineId, Date since, Date till) {

		Criteria criteria = getSession().createCriteria(BalanceDTO.class)
                                .createAlias("baseUserByUserId", "u")
                                .add(Restrictions.eq("u.id", userId))
                                .createAlias("orderDTO", "o")
                                .add(Restrictions.eq("o.id", orderId))
                                .createAlias("orderLineDTO", "ol")
                                .add(Restrictions.eq("ol.id", orderLineId))
                                .add(Restrictions.eq("deleted", 0));

                criteria.add(Restrictions.or(Expression.le("activeSince", since),
                Expression.isNull("activeSince")));
                criteria.add(Restrictions.or(Expression.gt("activeUntil", till),
                Expression.isNull("activeUntil")));

                return criteria.list();



	}


	public List<BalanceDTO> getUserBalancesByUserAndOrderLine(Integer userId, Integer orderLineId, Date since, Date till) {

                Criteria criteria = getSession().createCriteria(BalanceDTO.class)
                                .createAlias("baseUserByUserId", "u")
                                .add(Restrictions.eq("u.id", userId))
                                .createAlias("orderLineDTO", "ol")
                                .add(Restrictions.eq("ol.id", orderLineId))
                                .add(Restrictions.eq("deleted", 0));

                criteria.add(Restrictions.or(Expression.le("activeSince", since),
                Expression.isNull("activeSince")));
                criteria.add(Restrictions.or(Expression.gt("activeUntil", till),
                Expression.isNull("activeUntil")));

                return criteria.list();



        }

			public List<BalanceDTO> getUserBalancesByUserIdAndOrderLine(Integer userId, Integer orderLineId) {

                Criteria criteria = getSession().createCriteria(BalanceDTO.class)
                                .createAlias("baseUserByUserId", "u")
                                .add(Restrictions.eq("u.id", userId))
                                .createAlias("orderLineDTO", "ol")
                                .add(Restrictions.eq("ol.id", orderLineId))
                                .add(Restrictions.eq("deleted", 0));
                return criteria.list();

        }

			public BalanceDTO getUserBalancesByCurrencyAndOrder(Integer currencyId, Integer orderId) {

                Criteria criteria = getSession().createCriteria(BalanceDTO.class)
                                .createAlias("currency", "c")
                                .add(Restrictions.eq("c.id", currencyId))
                                .createAlias("orderDTO", "o")
                                .add(Restrictions.eq("o.id", orderId))
                                .add(Restrictions.eq("deleted", 0));
                return findFirst(criteria);

        }
    

    

}

