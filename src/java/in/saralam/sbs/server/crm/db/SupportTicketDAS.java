package in.saralam.sbs.server.crm.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.server.util.db.AbstractDAS;

public class SupportTicketDAS extends AbstractDAS<SupportTicketDTO>{

	public List<SupportTicketDTO> findByUser(String userName){
		
		 Criteria criteria = getSession().createCriteria(SupportTicketDTO.class)
	                .createAlias("baseUser", "b")
	                    .add(Restrictions.eq("b.userName", userName));
		 return criteria.list();
	}
}
