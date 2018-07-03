package in.saralam.sbs.server.rating.db;

import in.saralam.sbs.server.advancepricing.db.ProductChargeDTO;
import in.saralam.sbs.server.advancepricing.db.ProductChargeRateDTO;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.server.invoice.db.InvoiceDAS;
import com.sapienter.jbilling.server.order.db.OrderDAS;
import com.sapienter.jbilling.server.order.db.OrderLineDAS;
import com.sapienter.jbilling.server.util.db.AbstractDAS;
import java.util.List;

public class EventTypeRateMapDAS extends AbstractDAS<EventTypeRateMapDTO>{

	public EventTypeRateMapDTO findByRate(ProductChargeRateDTO chargeRateId){
		
		 Criteria criteria = getSession().createCriteria(EventTypeRateMapDTO.class)
	                    .add(Restrictions.eq("productChargeRateDTO", chargeRateId));
		 return findFirst(criteria);
	}
	
	
	public List<Integer> findProductChargeRateByeventType(String ratingEventType){
			
		final String GET_SUBS_SQL =
				"SELECT e.id from event_type_rate_map e join product_charge_rate p join rating_event_type r on e.charge_rate_id = p.id and e.event_type_id=r.id  where r.event_name = :ratingEventType order by p.salience asc;";
		
		Query query = getSession()
				.createSQLQuery(GET_SUBS_SQL)
				.setParameter("ratingEventType", ratingEventType);
		
		return query.list();
	}
}
