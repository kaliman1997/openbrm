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

public class ServiceFeatureInfoDAS extends AbstractDAS<ServiceFeatureInfoDTO> {
	

    private static final Logger LOG = Logger.getLogger(ServiceFeatureInfoDAS.class);

	
    public List<ServiceFeatureInfoDTO> findServiceFeatureInfobyServiceFeature(Integer serviceFeatureId){

           
          Criteria criteria = getSession().createCriteria(ServiceFeatureInfoDTO.class)
			      .createAlias("serviceFeature", "sf")
                               .add(Restrictions.eq("sf.id", serviceFeatureId));

             return criteria.list();
   }
}
