package in.saralam.sbs.server.Scheduler.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import org.apache.log4j.Logger;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sapienter.jbilling.server.order.db.OrderPeriodDTO;
import com.sapienter.jbilling.server.pluggableTask.admin.PluggableTaskTypeDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import in.saralam.sbs.server.Scheduler.db.SchedulerStatusDTO;
@Entity
@TableGenerator(
		name="schedule_action_GEN",
		table="jbilling_seqs",
		pkColumnName = "name",
		valueColumnName = "next_id",
		pkColumnValue = "schedule_action",
		allocationSize = 100
		)
@Table(name="schedule_action")

public class ScheduleActionDTO {
	
	private Integer                 id;
	private Integer                 scheduleId;
	private SchedulerStatusDTO      statusId;
	private Integer                 typeId;
	private Integer                 actionPeriodId;
    private Integer	                pluginId;

  
    
    @Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="schedule_action_GEN")
    @Column(name="id", unique=true, nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="schedule_id")
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	 @JoinColumn(name="status_id")
		public SchedulerStatusDTO getStatusId() {
			return statusId;
		}
		public void setStatusId(SchedulerStatusDTO statusId) {
			this.statusId = statusId;
		}
	
	
    @Column(name="type_id", nullable=true)
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	
    @Column(name="action_period_id", nullable=true)
	public Integer getActionPeriodId() {
		return actionPeriodId;
	}
	public void setActionPeriodId(Integer actionPeriodId) {
		this.actionPeriodId = actionPeriodId;
	}
	
	@Column(name="plugin_id", nullable=true)
	public Integer getPluginId() {
		return pluginId;
	}
	public void setPluginId(Integer pluginId) {
		this.pluginId = pluginId;
	}
	
	public String toString(){
		StringBuffer str = new StringBuffer("Scheduler = " +
	        "id=" + id + "," +
			"scheduleId=" + scheduleId + "," +
			"statusId=" + statusId + "," +
			"typeId=" + typeId + "," +
			"actionPeriodId=" + actionPeriodId + "," +
			"pluginId=" + pluginId 
				);
		   return str.toString();
	}
	
	 @Transient
    public String[] getFieldNames() {
        return new String[] {
                "id",
				"scheduleId",
				"statusId",
                "typeId",
                "actionPeriodId",
                "pluginId"
                
        };
    }
    
    @Transient
    public Object[][] getFieldValues() {
          return new Object[][] {
	            {
	            id,
				scheduleId,
				statusId,
                typeId,
                actionPeriodId,             
                pluginId
                	                
	            }
	        };
	    }
		
}
