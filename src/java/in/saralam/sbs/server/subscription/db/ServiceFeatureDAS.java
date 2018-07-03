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

public class ServiceFeatureDAS extends AbstractDAS<ServiceFeatureDTO> {
	

	private static final Logger LOG = Logger.getLogger(ServiceFeatureDAS.class);

	
    public ServiceFeatureDTO findByOrderLine(Integer provisioningId) {
    	LOG.debug("In findByOrderLine method");
        Criteria criteria = getSession().createCriteria(ServiceFeatureDTO.class)
                .createAlias("ProvisioningTagMapDTO", "provisioning")
                .add(Restrictions.eq("provisioning.id", provisioningId))
                .setMaxResults(1);
        LOG.debug("In findByOrderLine method query is executed");

        return findFirst(criteria);
    }	
	
    public List<ServiceFeatureDTO> findServiceFeaturesbyService( Integer serviceid){

          Criteria criteria = getSession().createCriteria(ServiceFeatureDTO.class)
				.add(Restrictions.eq("deleted", 0))
			      .createAlias("serviceId", "i")
                               .add(Restrictions.eq("i.id", serviceid));

             return criteria.list();
   }

    public List<ServiceFeatureDTO> getPrimaryByServiceId(Integer serviceId){

          Criteria criteria = getSession().createCriteria(ServiceFeatureDTO.class)
                              .add(Restrictions.eq("deleted", 0))
                              .createAlias("serviceId", "i")
                               .add(Restrictions.eq("i.id", serviceId))
                              .add(Restrictions.isNull("parent"));


             return criteria.list();
   }
}
