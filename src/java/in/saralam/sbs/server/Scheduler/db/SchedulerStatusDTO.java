package in.saralam.sbs.server.Scheduler.db;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Transient;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractGenericStatus;


@Entity
@DiscriminatorValue("scheduler_status")
public class SchedulerStatusDTO extends  AbstractGenericStatus implements java.io.Serializable {
	
	
	public SchedulerStatusDTO() {
    }
    
    public SchedulerStatusDTO(int statusValue) {
        this.statusValue = statusValue;
    }

    @Transient
	protected String getTable() {
		return Constants.TABLE_SCHEDULER_STATUS;
	}
}
