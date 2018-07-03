package in.saralam.sbs.server.deferredAction.db;

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
import com.sapienter.jbilling.server.util.db.AbstractDAS;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.joda.time.DateMidnight;

import java.util.Collections;

import com.sapienter.jbilling.common.Util;
import com.sapienter.jbilling.server.util.Constants;
import in.saralam.sbs.server.common.SBSConstants;
import java.sql.*;
import in.saralam.sbs.server.deferredAction.task.IDeferredAction;
public class DeferredActionDAS extends AbstractDAS<DeferredActionDTO> implements java.io.Serializable{
 private static final Logger LOG = Logger.getLogger(DeferredActionDAS.class);
public List<DeferredActionDTO> findDeferredActionsToProcess(int entityId, Date billingDate) {
  
  Criteria criteria = getSession().createCriteria(DeferredActionDTO.class)
                   .add(Restrictions.eq("deleted", 0))
                   .add(Restrictions.lt("whenDate", billingDate))
                   .createAlias("entity", "c")
                   .add(Restrictions.eq("c.id", entityId))
                   .createAlias("deferredActionStatus", "ds")
                   .add(Restrictions.eq("ds.id", SBSConstants.DEFERRED_ACTION_STATUS_PENDING));
 LOG.debug(" in DeferredActionDAS criteria"+criteria);
 return criteria.list();
    }
public Connection getConnectionida(){
    Connection conn = getSession().connection();
    return conn;
   }
    public void reset() {
        getSession().flush();
        getSession().clear();
    }

     public DeferredActionDTO findDeferredActionByUserId( Integer  userId){
		 Criteria criteria = getSession().createCriteria(DeferredActionDTO.class)
			                 .createAlias("baseUser", "user")
			                 .add(Restrictions.eq("user.id", userId));
			  return (DeferredActionDTO) criteria.uniqueResult();
	 }
}
