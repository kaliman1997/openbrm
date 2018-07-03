package in.saralam.sbs.server.mediation.db; 
import com.sapienter.jbilling.server.util.db.AbstractDAS;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class  GotosolrCDREventDAS extends AbstractDAS< GotosolrCDREventDTO> {
	public void saveEvent(GotosolrCDREventDTO newEvent) {
		super.save(newEvent);
	}

	public List<GotosolrCDREventDTO> getEvent(Integer userId, Date cdrDateFrom,
			Date cdrDateTo, Integer startIndex, Integer pageValue) {
		if (cdrDateFrom == null || cdrDateTo == null) {
			Criteria criteria = getSession().createCriteria(GotosolrCDREventDTO.class).add(
							Restrictions.eq("userId", userId));
			return criteria.list();
		} else {
			Criteria criteria = getSession().createCriteria(GotosolrCDREventDTO.class).add(
							Restrictions.eq("userId", userId)).add(							
							Restrictions.between("startDate", cdrDateFrom,
									cdrDateTo));
			return criteria.list();
		}
	}

   /**
    * Get the events belonging to a given order
    *
    * @param orderId The order
    * @return The list of events
    */
  	public List<GotosolrCDREventDTO> getEventByOrder(Integer orderId) {
			Criteria criteria = getSession().createCriteria(GotosolrCDREventDTO.class).add(
							Restrictions.eq("orderId", orderId));
			return criteria.list();
	}
    
   /**
    * Get if the eventis already in the database
    *
    * @param orderId The order
    * @return The list of events
    */
  	public boolean getEventByRecordId(String recordId) {
			Criteria criteria = getSession().createCriteria(GotosolrCDREventDTO.class).add(
							Restrictions.eq("recordId", recordId));
			return criteria.list().size() > 0;
	}

}
