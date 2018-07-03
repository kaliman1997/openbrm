package in.saralam.sbs.server.Scheduler.db;


import java.util.List;
import org.apache.log4j.Logger;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sapienter.jbilling.server.util.db.AbstractDAS;
import com.sapienter.jbilling.server.util.Constants;
import in.saralam.sbs.server.Scheduler.db.ScheduleDTO;

public class ScheduleDAS extends AbstractDAS<ScheduleDTO>{

private static final Logger LOG = Logger.getLogger(ScheduleDAS.class);


@SuppressWarnings("unchecked")

	public List<ScheduleDTO> findByschedule(Integer scheduleId){
		
		 Criteria criteria = getSession().createCriteria(ScheduleDTO.class)
	                .createAlias("schedule", "t")
	                    .add(Restrictions.eq("t.id", scheduleId))
	                    .addOrder( Order.desc("createdDate"));
		 return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ScheduleDTO> findAllByEntity(Integer entityId)
	{
		Criteria criteria = getSession().createCriteria(ScheduleDTO.class)
				.createAlias("statusId", "s")
					.add(Restrictions.eq("s.id", Constants.SCHEDULER_STATUS_NEW))
				 .createAlias("entity", "e")
				.add(Restrictions.eq("e.id", entityId));
		
		return criteria.list();
				
	}
	
	public List<ScheduleDTO> findByUser(String userName){
		
		 Criteria criteria = getSession().createCriteria(ScheduleDTO.class)
	                .createAlias("baseUser", "b")
	                    .add(Restrictions.eq("b.userName", userName));
		 return criteria.list();
	}
}