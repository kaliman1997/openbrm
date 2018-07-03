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
		name="schedule_action_type_GEN",
		table="jbilling_seqs",
		pkColumnName = "name",
		valueColumnName = "next_id",
		pkColumnValue = "schedule_action_type",
		allocationSize = 100
		)
@Table(name="schedule_action_type")

public class ScheduleActionTypeDTO {
	
	private int  id;
	
    @Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="schedule_action_type_GEN")
    @Column(name="id", unique=true, nullable=false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
		
}
