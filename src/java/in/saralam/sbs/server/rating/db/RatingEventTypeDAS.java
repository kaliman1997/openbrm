package in.saralam.sbs.server.rating.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.util.db.AbstractDAS;

public class RatingEventTypeDAS extends AbstractDAS<RatingEventTypeDTO>{

	
	public List<RatingEventTypeDTO> findByCompany(CompanyDTO company){
		
		 Criteria criteria = getSession().createCriteria(RatingEventTypeDTO.class)
	               
	                    .add(Restrictions.eq("company", company));
	                
		 return criteria.list();
	}
	
	public RatingEventTypeDTO findByEventName(String eventName){
		
		 Criteria criteria = getSession().createCriteria(RatingEventTypeDTO.class)
	               
	                    .add(Restrictions.eq("eventName", eventName));
	                
		 return findFirst(criteria);
	}
}
