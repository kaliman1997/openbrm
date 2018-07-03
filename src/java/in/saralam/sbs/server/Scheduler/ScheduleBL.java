package in.saralam.sbs.server.Scheduler;

import java.util.Date;

import in.saralam.sbs.server.Scheduler.db.ScheduleActionDAS;
import in.saralam.sbs.server.Scheduler.db.ScheduleActionDTO;
import in.saralam.sbs.server.Scheduler.db.ScheduleDAS;
import in.saralam.sbs.server.Scheduler.db.ScheduleDTO;
import in.saralam.sbs.server.Scheduler.db.SchedulerStatusDAS;
import in.saralam.sbs.server.Scheduler.db.SchedulerStatusDTO;
import in.saralam.sbs.server.Scheduler.db.SchedulerTypeDAS;
import in.saralam.sbs.server.Scheduler.db.SchedulerTypeDTO;
import in.saralam.sbs.server.crm.event.CRMEvent;
import com.sapienter.jbilling.server.util.WebServicesSessionSpringBean;
import com.sapienter.jbilling.server.user.UserWS;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.user.db.UserDAS;
import com.sapienter.jbilling.server.user.db.UserDTO;

import in.saralam.sbs.server.notification.OpenBRMNotificationBL;
//import in.saralam.sbs.server.voucher.VoucherWS;
//import in.saralam.sbs.server.voucher.db.VoucherDTO;

import com.sapienter.jbilling.server.system.event.EventManager;
import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.util.Constants;

public class ScheduleBL extends ResultList {

		

			private static final Logger LOG = Logger.getLogger(ScheduleBL.class);
			
			private ScheduleDTO         scheduledto = null;
			private SchedulerStatusDTO  schedulerStatusDTO = null;
			private SchedulerTypeDTO    schedulerTypeDTO = null;
			private ScheduleActionDTO   scheduleActionDTO = null;
			private ScheduleDAS         scheduleDAS = null;
			private SchedulerStatusDAS  schedulerStatusDAS = null;
			private SchedulerTypeDAS    schedulerTypeDAS = null;
			private ScheduleActionDAS   scheduleActionDAS = null;
			
			WebServicesSessionSpringBean webServicesSession = new  WebServicesSessionSpringBean();
		
			
			public ScheduleBL(){
				init();
			}
			
			public ScheduleBL(Integer scheduleId){
				init();
				set(scheduleId);
			}
			public ScheduleBL(ScheduleDTO scheduledto) {
				init();
				this.scheduledto = scheduledto;
			}
			public void set(Integer scheduleId) {
				scheduledto = scheduleDAS.find(scheduleId);
			}

			public void init() {
				
				scheduledto = new ScheduleDTO();
				scheduleDAS = new ScheduleDAS();
			}
			
			public Integer create(ScheduleWS scheduleWS){
				LOG.debug("in create schedule method in ScheduletBL....");
				ScheduleDTO scheduleDTO = new ScheduleDTO();
				LOG.debug("scheduleWS.getActiveSince() in ScheduletBL...." + scheduleWS.getActiveSince());
				LOG.debug("scheduleWS.getDoe() in ScheduletBL...." + scheduleWS.getDoe());
				try{
				Integer  entityId=webServicesSession.getCallerCompanyId();
                 CompanyDTO cdto = new CompanyDAS().find(entityId);
               UserDTO  user = new UserDAS().findByUserName(scheduleWS.getUserName(), cdto.getId());
					LOG.debug("user in ScheduletBL...." + user);
					scheduleDTO.setSubject(scheduleWS.getSubject());
					scheduleDTO.setUserName(scheduleWS.getUserName());
					scheduleDTO.setBaseUser(user);
						LOG.debug("scheduleWS.getBaseUser()  in ScheduletBL...." + scheduleWS.getBaseUser());
					SchedulerStatusDTO schedulerStatus = new SchedulerStatusDAS().find(Constants.SCHEDULER_STATUS_NEW);
				scheduleDTO.setScheduleStatus(schedulerStatus);
				scheduleDTO.setPeriodId(scheduleWS.getPeriodId());
				scheduleDTO.setEntityId(cdto);
				scheduleDTO.setActiveSince(scheduleWS.getActiveSince());
				scheduleDTO.setActiveUntil(scheduleWS.getActiveUntil());
				scheduleDTO.setCreatedDate(scheduleWS.getCreatedDate());
				scheduleDTO.setDoe(scheduleWS.getDoe());
				scheduledto = scheduleDAS.save(scheduleDTO);
				
				
				}catch (Exception e) {
					LOG.debug("error creating scheduler...."+e);
				}
				return scheduledto.getId();
			}
			
			
			
			public  ScheduleWS getScheduleWS(ScheduleDTO dto){
				ScheduleWS scheduleWS = new ScheduleWS();
				Integer  entityId=webServicesSession.getCallerCompanyId();
                CompanyDTO cdto = new CompanyDAS().find(entityId);
	  
				scheduleWS.setActiveSince(dto.getActiveSince());
				scheduleWS.setActiveUntil(dto.getActiveUntil());
				scheduleWS.setCreatedDate (dto.getCreatedDate ());
				scheduleWS.setSubject(dto.getSubject());
				scheduleWS.setUserName(dto.getUserName());
				scheduleWS.setBaseUser(dto.getBaseUser());
				scheduleWS.setPeriodId(dto.getPeriodId());
				scheduleWS.setEntityId(cdto);
				scheduleWS.setId(dto.getId());
				scheduleWS.setDoe(scheduleWS.getDoe());
  

				return scheduleWS;

				}

			}


