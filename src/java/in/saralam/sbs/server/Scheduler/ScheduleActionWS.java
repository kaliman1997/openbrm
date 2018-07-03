
/**
 * @author swagathp
 *
 */
package in.saralam.sbs.server.Scheduler;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskTypeDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;

import in.saralam.sbs.server.Scheduler.db.ScheduleDTO;
import in.saralam.sbs.server.Scheduler.db.SchedulerStatusDTO;
import in.saralam.sbs.server.Scheduler.db.SchedulerTypeDTO;
import org.apache.log4j.Logger;

public class ScheduleActionWS implements Serializable{
	
	private Integer                 id;
	private Integer             	scheduleId;
	private SchedulerStatusDTO      statusId;
	private Integer    		    	typeId;
	private Integer                 actionPeriodId;
    private Integer                 pluginId;

  
    private static final Logger LOG = Logger.getLogger(ScheduleActionWS.class);
    
    public ScheduleActionWS(){
    }
    
    public ScheduleActionWS(Integer id,Integer scheduleId,SchedulerStatusDTO statusId,Integer typeId,
    		Integer actionPeriodId,Integer pluginId ){
    	setId(id);
    	setScheduleId(scheduleId);
    	setStatusId(statusId);
    	setTypeId(typeId);
    	setActionPeriodId(actionPeriodId);
    	setPluginId(pluginId);
		
    }
   
   public ScheduleActionWS(Integer scheduleId,SchedulerStatusDTO statusId,Integer typeId,
    		Integer actionPeriodId,Integer pluginId,Integer entityId ){
    	
    	setScheduleId(scheduleId);
    	setStatusId(statusId);
    	setTypeId(typeId);
    	setActionPeriodId(actionPeriodId);
    	setPluginId(pluginId);
		
    }
   
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	

	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	
		public SchedulerStatusDTO getStatusId() {
			return statusId;
		}
		public void setStatusId(SchedulerStatusDTO statusId) {
			this.statusId = statusId;
		}
	
	
	
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	
	
	public Integer getActionPeriodId() {
		return actionPeriodId;
	}
	public void setActionPeriodId(Integer actionPeriodId) {
		this.actionPeriodId = actionPeriodId;
	}
	
	
	public Integer getPluginId() {
		return pluginId;
	}
	public void setPluginId(Integer pluginId) {
		this.pluginId = pluginId;
	}
	
	
		
}
