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

public class ProvisioningTagMapDAS extends AbstractDAS<ProvisioningTagMapDTO> {
	

	private static final Logger LOG = Logger.getLogger(ProvisioningTagMapDAS.class);

	  @SuppressWarnings("unchecked")
	
           public List<ProvisioningTagMapDTO> findByItemId( Integer itemid) 
           {
              
            Criteria criteria = getSession().createCriteria(ProvisioningTagMapDTO.class)
			      .createAlias("itemId", "i")
                               .add(Restrictions.eq("i.id", itemid));
                    
                           return criteria.list();
	   }

	   public List<ProvisioningTagMapDTO> getPrimaryByItemId(Integer itemid)
           {

            Criteria criteria = getSession().createCriteria(ProvisioningTagMapDTO.class)
                              .createAlias("itemId", "i")
                               .add(Restrictions.eq("i.id", itemid))
				.add(Restrictions.isNull("parent"))
                               .add(Restrictions.eq("deleted", 0));

                           return criteria.list();
           }
		   
		   public List<ProvisioningTagMapDTO> getProvisioningItems(ProvisioningTagDTO tag ){
		   
		    Criteria criteria = getSession().createCriteria(ProvisioningTagMapDTO.class)
								.add(Restrictions.eq("provisioningTag", tag))
								.add(Restrictions.eq("deleted", 0));
								
				return criteria.list();		   
		   }

	
    
}


    
