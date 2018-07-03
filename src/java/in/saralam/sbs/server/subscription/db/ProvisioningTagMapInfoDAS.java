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
import com.sapienter.jbilling.server.item.db.ItemDTO;

public class ProvisioningTagMapInfoDAS extends AbstractDAS<ProvisioningTagMapInfoDTO> {
	

	private static final Logger LOG = Logger.getLogger(ProvisioningTagMapInfoDAS.class);

	  @SuppressWarnings("unchecked")
	
           public List<ProvisioningTagMapInfoDTO> findByProvisioningTagMapDTO(ProvisioningTagMapDTO provisioningTagMap) {
              
            Criteria criteria = getSession().createCriteria(ProvisioningTagMapInfoDTO.class)
			      .createAlias("provisioningTagMap", "pm")
                               .add(Restrictions.eq("pm.id", provisioningTagMap.getId()));
                    
                           return criteria.list();
	}

	 public List<ProvisioningTagMapInfoDTO> findByProvisioningTagMapId(Integer  mapId) {

            Criteria criteria = getSession().createCriteria(ProvisioningTagMapInfoDTO.class)
                              .createAlias("provisioningTagMap", "pm")
                               .add(Restrictions.eq("pm.id", mapId));

                           return criteria.list();
        }

	
    
}


    
