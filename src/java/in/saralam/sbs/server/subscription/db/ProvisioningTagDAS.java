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
import in.saralam.sbs.server.subscription.db.ProvisioningTagDTO;

public class ProvisioningTagDAS extends AbstractDAS<ProvisioningTagDTO> {
	

	private static final Logger LOG = Logger.getLogger(ProvisioningTagDAS.class);
	
	public ProvisioningTagDTO findByCode(String code){
	
	  Criteria criteria = getSession().createCriteria(ProvisioningTagDTO.class)
						.add(Restrictions.eq("code", code));
						
      return findFirst(criteria);         						
	}
	
    public List<ProvisioningTagDTO> getProvisioningItems(ProvisioningTagDTO tag ){
	  Criteria criteria = getSession().createCriteria(ProvisioningTagDTO.class)
		                  .add(Restrictions.eq("provisioningTag", tag))
						  .add(Restrictions.eq("deleted", 0));
						  
				return criteria.list();		   
    }
	
	public List<ProvisioningTagDTO> getProvisioningTags(){
	  Criteria criteria = getSession().createCriteria(ProvisioningTagDTO.class)
						  .add(Restrictions.eq("deleted",0));
						  
						  return criteria.list();
	
	}

}
