package in.saralam.sbs.server.ConvergentRateCard.db;

import java.util.List;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.log4j.Logger;
import java.io.*;
import java.sql.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.*;
import com.sapienter.jbilling.common.Util;

import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.util.db.AbstractDAS;

public class CRateDAS extends AbstractDAS<CRateDTO>{

private static final Logger LOG = Logger.getLogger(CRateDAS.class);

	
  	final String prefixHql =
                "select r " +
                "from international_rate r " +
                "WHERE LEFT(:called, LENGTH(r.prefix)) = r.prefix " + 
		"and r.deleted = 0";

	final String destHql =
                "select r " +
                "from international_rate r " +
                "WHERE r.destination = :destination" +
                "and r.deleted = 0";

	

	final String sql =
                "select * " +
                "from rate r " +
                "WHERE LEFT(:called, LENGTH(r.prefix)) = r.prefix " +
                " and r.rate_plan = :itemId " +
				"and r.valid_from <= :date AND ( r.valid_to >= :date or r.valid_to is null)" + 
				"and r.deleted = 0";/*+ 
				"order by r.valid_to desc limit 1";*/


				
				
	
	@SuppressWarnings("unchecked")
	public List<CRateDTO> findByPrefix(String prefix) {
		Criteria criteria = getSession().createCriteria(CRateDTO.class);
				criteria.add(Restrictions.eq("deleted", 0));
				criteria.add(Restrictions.eq("prefix", prefix));
				
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
        public List<CRateDTO> getRateByPrefix(String prefix) {

		 Query query = getSession()
                	.createQuery(prefixHql)
                	.setString("called", prefix);

		return query.list();
        }

	@SuppressWarnings("unchecked")
        public List<CRateDTO> getRateByImpCat(String impCat) {

                 Query query = getSession()
                        .createQuery(destHql)
                        .setString("destination", impCat);

                return query.list();
        }


	@SuppressWarnings("unchecked")
    public List getRateByPrefixAndDate(String prefix, Date date, Integer itemId) {
		List ratingRes = null;
		Query query=null;
		Date pdate = Util.truncateDate(date);
		try{
	LOG.debug("In CRateDAS values are:"+prefix+","+pdate+","+itemId);
                  query  = getSession()
					    .createSQLQuery(sql)
                        .setParameter("called", prefix)
                        .setParameter("itemId", itemId)
						.setParameter("date", pdate);
                 ratingRes= query.list();
		}catch(Exception e ){
		 //println(e.printStackTrace());
		LOG.debug("Exception is:"+e);
		}
		LOG.debug("out of query:"+query.toString());
		return ratingRes;
        }

	 public List<CRateDTO> findByEntity(Integer entityId, Integer limit) {
	       
	        Criteria criteria = getSession().createCriteria(CRateDTO.class);
	                

	                criteria.add(Restrictions.eq("deleted", 0));
	                criteria.createAlias("entity", "e")
	                    .add(Restrictions.eq("e.id", entityId));
			criteria.setMaxResults(limit);
	               
	        
	        return criteria.list();
	    }
	 
	 
}
