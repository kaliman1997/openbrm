package in.saralam.sbs.server.crm.db;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.server.util.db.AbstractDAS;

public class TicketDetailsDAS extends AbstractDAS<TicketDetailsDTO>{

	public List<TicketDetailsDTO> findByticket(Integer ticketId){
		
		 Criteria criteria = getSession().createCriteria(TicketDetailsDTO.class)
	                .createAlias("ticket", "t")
	                    .add(Restrictions.eq("t.id", ticketId))
	                    .addOrder( Order.desc("createdDate"));
		 return criteria.list();
	}
}
