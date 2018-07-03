package in.saralam.sbs.server.Scheduler;

import java.util.Date;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.list.ResultList;
import com.sapienter.jbilling.server.util.Constants;

import in.saralam.sbs.server.Scheduler.db.ScheduleActionParamDAS;
import in.saralam.sbs.server.Scheduler.db.ScheduleActionParamDTO;

public class ScheduleActionParamsBL extends ResultList{
	
	private static final Logger LOG = Logger.getLogger(ScheduleActionParamsBL.class);
	
	private ScheduleActionParamDAS   scheduleActionParamDAS = null;
	private ScheduleActionParamDTO   scheduleActionParamDTO = null;
	
	public ScheduleActionParamsBL(){
			init();
		}
		
		public ScheduleActionParamsBL(Integer scheduleId){
			init();
			set(scheduleId);
		}

		public void set(Integer scheduleId) {
			scheduleActionParamDTO = scheduleActionParamDAS.find(scheduleId);
		}

		public void init() {
			scheduleActionParamDTO = new ScheduleActionParamDTO();
			scheduleActionParamDAS = new ScheduleActionParamDAS();
			
		}

		public Integer create(ScheduleActionParamsWS scheduleActionParamsWS){
				LOG.debug("in create schedule method in ScheduletBL....");
				ScheduleActionParamDTO scheduleactionParamDTO = new ScheduleActionParamDTO();
				
				try{
				 scheduleactionParamDTO.setScheduleActionId(scheduleActionParamsWS.getScheduleActionId());
                 scheduleactionParamDTO.setName(scheduleActionParamsWS.getName());
				 scheduleactionParamDTO.setValue(scheduleActionParamsWS.getValue());				
				 scheduleActionParamDTO = scheduleActionParamDAS.save(scheduleactionParamDTO);
											
				}catch (Exception e) {
					LOG.debug("error creating scheduler...."+e);
				}
				return scheduleActionParamDTO.getId();
			}		
		}