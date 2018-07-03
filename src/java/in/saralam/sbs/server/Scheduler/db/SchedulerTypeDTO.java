/*
 * JBILLING CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise jBilling Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise jBilling Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise jBilling Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */
package in.saralam.sbs.server.Scheduler.db;

import com.sapienter.jbilling.server.util.db.AbstractGenericStatus;
import com.sapienter.jbilling.server.util.Constants;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

@Entity
@DiscriminatorValue("scheduler_type")
public class SchedulerTypeDTO extends AbstractGenericStatus implements Serializable {

    private Set<ScheduleDTO> schedulerDTOs = new HashSet<ScheduleDTO>(0);

    public SchedulerTypeDTO() { }

    public SchedulerTypeDTO(Integer id) {
        this.id = id;
    }

    public SchedulerTypeDTO(Integer id, Set<ScheduleDTO> schedulerDTOs) {
        this.id = id;
        this.schedulerDTOs = schedulerDTOs;
				
    }
	
	public SchedulerTypeDTO(int  statusValue) {
        this.statusValue = statusValue;
    }

    public SchedulerTypeDTO(int statusValue, Set<ScheduleDTO> schedulerDTOs) {
        this.statusValue = statusValue;
        this.schedulerDTOs = schedulerDTOs;
				
    }   

    @Transient
    protected String getTable() {
        return Constants.TABLE_SCHEDULER_TYPE;
    }
}
