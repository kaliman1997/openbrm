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
		name="schedule_action_param_GEN",
		table="jbilling_seqs",
		pkColumnName = "name",
		valueColumnName = "next_id",
		pkColumnValue = "schedule_action_param",
		allocationSize = 100
		)
@Table(name="schedule_action_param")

public class ScheduleActionParamDTO {
	
	private Integer   id;
	private Integer   scheduleActionId;
	private String    value;
	private String    name;
	
	@Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="schedule_action_param_GEN")
    @Column(name="id", unique=true, nullable=false)
	
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
    
    @Column(name="value" )
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	 @Column(name="schedule_action_id" )
	public Integer getScheduleActionId() {
		return this.scheduleActionId;
	}
	public void setScheduleActionId(Integer scheduleActionId) {
		this.scheduleActionId = scheduleActionId;
	}
	
	@Column(name="name")
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		StringBuffer str = new StringBuffer("SchedulerActionParam = " +
	       "id=" + id + "," +
		   "scheduleActionId=" + scheduleActionId + "," +
			"name=" + name + "," +
			"value=" + value 
				);
		   return str.toString();
	}
	
	 @Transient
    public String[] getFieldNames() {
        return new String[] {
                "id",
				"scheduleActionId",
				"name",
				"value"
        };
    }
    
    @Transient
    public Object[][] getFieldValues() {
          return new Object[][] {
	            {
	                id,
					scheduleActionId,
				name,
				value
   	            }
	        };
	    }

 
			
}
