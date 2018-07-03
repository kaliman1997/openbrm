package in.saralam.sbs.server.Scheduler.db;

import java.io.Serializable;                                                     
import java.math.BigDecimal;
import java.util.Date;
import com.sapienter.jbilling.server.util.csv.Exportable;
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
import com.sapienter.jbilling.server.user.db.UserDTO;
import in.saralam.sbs.server.Scheduler.db.SchedulerStatusDTO;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
@Entity
@TableGenerator(
		name="schedule_GEN",
		table="jbilling_seqs",
		pkColumnName = "name",
		valueColumnName = "next_id",
		pkColumnValue = "schedule",
		allocationSize = 100
		)
@Table(name="schedule")

public class ScheduleDTO implements Serializable {
	
	private int                id;
	private String             subject;
	private int                periodId;
	private String             userName;
	private UserDTO            baseUser;
	private SchedulerStatusDTO statusId;
	private Date               createdDateTime;
	private Date               activeSince;
    private Date               activeUntil;
	private Date               doe;
    private Date               lastModified;
    private CompanyDTO         entityId;
    
    @Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="schedule_GEN")
    @Column(name="id", unique=true, nullable=false)
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="subject")
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	 @Column(name="period_id")
		public int getPeriodId() {
			return this.periodId;
		}
		public void setPeriodId(int periodId) {
			this.periodId = periodId;
		}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
	public UserDTO getBaseUser() {
		return this.baseUser;
	}
	public void setBaseUser(UserDTO baseUser) {
		this.baseUser = baseUser;
	}
	
	@Column(name="user_name")
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
		
	 @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="status_id")
	public SchedulerStatusDTO getScheduleStatus() {
		return this.statusId;
	}
	public void setScheduleStatus(SchedulerStatusDTO statusId) {
		this.statusId = statusId;
	}
	
	@Column(name="created_datetime", length=19)
	public Date getCreatedDate() {
		return this.createdDateTime;
	}
	public void setCreatedDate(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
	@Column(name="active_since",  length=19)
	public Date getActiveSince() {
		return this.activeSince;
	}
	public void setActiveSince(Date activeSince) {
		this.activeSince = activeSince;
	}
	
	@Column(name="active_until", length=19)
	public Date getActiveUntil() {
		return this.activeUntil;
	}
	public void setActiveUntil(Date activeUntil) {
		this.activeUntil = activeUntil;
	}
	
	@Column(name="date_of_event", length=19)
	public Date getDoe() {
		return this.doe;
	}
	public void setDoe(Date doe) {
		this.doe = doe;
	}
	
	@Column(name="last_update_time",  length=19)
	public Date getLastModified() {
		return this.lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id")
    public CompanyDTO getEntityId() {
        return this.entityId;
    }

    public void setEntityId(CompanyDTO entityId) {
        this.entityId = entityId;
    }
	
		public String toString(){
		StringBuffer str = new StringBuffer("Scheduler = " +
	        "id=" + id + "," +
			"createdDateTime=" + createdDateTime + "," +
			"lastModified=" + lastModified + "," +
			"subject=" + subject + "," +
			"periodId=" + periodId + "," +
			"userName=" + userName + "'" + 
			"activeSince=" + activeSince + "," +
			"activeUntil=" + activeUntil + "," +
			"doe=" + doe
				);
		   return str.toString();
	}
	
	 @Transient
    public String[] getFieldNames() {
        return new String[] {
                "id",
				"subject",
				"periodId",
                "baseUser",
                "userName",
                "activeSince",
                "activeUntil",
                "createdDateTime",
				"doe"
        };
    }
    
    @Transient
    public Object[][] getFieldValues() {
          return new Object[][] {
	            {
	            id,
				subject,
				periodId,
                baseUser,
                userName,             
                activeSince,
                activeUntil,
                createdDateTime,
				doe
	                
	            }
	        };
	    }

 
	
}
