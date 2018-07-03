package in.saralam.sbs.server.Scheduler.db;
import org.apache.log4j.Logger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.server.util.Constants;

import com.sapienter.jbilling.server.util.db.AbstractDAS;

public class ScheduleActionDAS extends AbstractDAS<ScheduleActionDTO>{

private static final Logger LOG = Logger.getLogger(ScheduleActionDAS.class);

	public ScheduleActionDTO findByTypeId(int typeId){
		
		 Criteria criteria = getSession().createCriteria(ScheduleActionDTO.class);
	              
	                   criteria.add(Restrictions.eq("typeId", typeId));
		 return (ScheduleActionDTO) criteria.uniqueResult();
	}
	
}
