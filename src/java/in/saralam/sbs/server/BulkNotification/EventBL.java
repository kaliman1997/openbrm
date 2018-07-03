package in.saralam.sbs.server.BulkNotification;

import java.util.Date;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;

import com.sapienter.jbilling.server.user.db.CompanyDTO;

import com.sapienter.jbilling.server.util.audit.EventLogger;
import com.sapienter.jbilling.server.util.audit.db.EventLogDAS;
import com.sapienter.jbilling.server.util.audit.db.EventLogDTO;
import com.sapienter.jbilling.server.util.audit.db.EventLogMessageDTO;
import com.sapienter.jbilling.server.util.audit.db.EventLogModuleDTO;



public class EventBL extends ResultList{

	 
    private EventLogDTO eventlogdto = null;  
	private EventLogDAS eventlogdas = null;  
	private static final Logger LOG = Logger.getLogger(EventBL.class );
	private EventLogger eLogger = null;
	
	public EventBL(Integer eventId) {    //voucherId
		init();
		set(eventId);
	}
	
	
	public EventBL() {
		init();
	}
	
	public EventBL(EventLogDTO eventlogdto) {
		init();
		this.eventlogdto = eventlogdto;
	}
	
	private void init() {
		eLogger = EventLogger.getInstance();
		eventlogdas = new EventLogDAS();
	}
	
	public EventLogDTO getEntity() {
		return eventlogdto;
	}
	
	
	public void set(Integer id) {
		eventlogdto = eventlogdas.find(id);
	}
	
	
	public void set(EventLogDTO neweventlogdto) {
		eventlogdto = neweventlogdto;
	}
	
	public EventLogDTO getDTO() {
		return eventlogdto;
	}

	public Integer create(EventLogMessageDTO eventlog,CompanyDTO entityId) 	{
	
		int foreignId = 100;
		int levelField = 2;
		EventLogModuleDTO eventLogModule = new EventLogModuleDTO();
		int versionNum =0;
		
		 Date now = new Date();
		
		EventLogDTO neweventlogdto = new EventLogDTO();
			LOG.debug("Entity Id:" + entityId );
	
		eventlogdas = new EventLogDAS();
		neweventlogdto.setCompany(entityId);
		neweventlogdto.setForeignId(foreignId);
		neweventlogdto.setCreatedDateTime(now);
		neweventlogdto.setLevelField(levelField);
		neweventlogdto.setEventLogModule(eventLogModule);
		neweventlogdto.setEventLogMessage(eventlog);
		neweventlogdto.setVersionNum(versionNum);
				
		eventlogdto = eventlogdas.save(neweventlogdto);
		LOG.debug("Event is created(BulkNotification)" + eventlogdto);
		return eventlogdto.getId();
		
	}
	
}