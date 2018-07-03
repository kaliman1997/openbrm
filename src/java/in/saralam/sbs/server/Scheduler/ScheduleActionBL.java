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


import in.saralam.sbs.server.notification.OpenBRMNotificationBL;
import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.util.Constants;
public class ScheduleActionBL extends ResultList{
	
	private static final Logger LOG = Logger.getLogger(ScheduleActionBL.class);
		
		private ScheduleDTO         scheduleDTO = null;
		private SchedulerStatusDTO  schedulerStatusDTO = null;
		private SchedulerTypeDTO    schedulerTypeDTO = null;
		private ScheduleActionDTO   scheduleActionDTO = null;
		private ScheduleDAS         scheduleDAS = null;
		private SchedulerStatusDAS  schedulerStatusDAS = null;
		private SchedulerTypeDAS    schedulerTypeDAS = null;
		private ScheduleActionDAS   scheduleActionDAS = null;
		
		public ScheduleActionBL(){
			init();
		}
		
		public ScheduleActionBL(Integer scheduleId){
			init();
			set(scheduleId);
		}

		public void set(Integer scheduleId) {
			scheduleActionDTO = scheduleActionDAS.find(scheduleId);
		}

		public void init() {
			scheduleActionDTO = new ScheduleActionDTO();
			scheduleActionDAS = new ScheduleActionDAS();
			scheduleDTO = new ScheduleDTO();
			scheduleDAS = new ScheduleDAS();
		}
		
				
		public Integer create(ScheduleActionWS scheduleActionWS){
				LOG.debug("in create schedule method in ScheduletBL....");
				ScheduleActionDTO scheduleactionDTO = new ScheduleActionDTO();
				try{
				
                scheduleactionDTO.setScheduleId(scheduleActionWS.getScheduleId());
				scheduleactionDTO.setTypeId(scheduleActionWS.getTypeId());
				SchedulerStatusDTO schedulerStatus = new SchedulerStatusDAS().find(Constants.SCHEDULER_STATUS_NEW);
				scheduleactionDTO.setStatusId(schedulerStatus);
				scheduleactionDTO.setActionPeriodId(scheduleActionWS.getActionPeriodId());
				scheduleactionDTO.setTypeId(scheduleActionWS.getTypeId());
				scheduleactionDTO.setPluginId(scheduleActionWS.getPluginId());
				scheduleActionDTO = scheduleActionDAS.save(scheduleactionDTO);
							
				}catch (Exception e) {
					LOG.debug("error creating scheduler...."+e);
				}
				return scheduleActionDTO.getId();
			}		
		}
