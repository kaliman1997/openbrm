package in.saralam.sbs.server.Scheduler;

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
import in.saralam.sbs.server.Scheduler.db.SchedulerTypeDTO;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO;

public class ScheduleWS implements Serializable{

		
		private int             id;
		private String          subject;
		private String          userName;
		private int             periodId;
		private UserDTO         baseUser;
		private SchedulerStatusDTO statusId;
		private Date            createdDateTime;
		private Date            activeSince;
	    private Date            activeUntil;
		private Date 			doe;
	    private Date            lastModified;
	    private CompanyDTO      entityId;
	    
	    private static final Logger LOG = Logger.getLogger(ScheduleWS.class);
	    
	  
	    public ScheduleWS(){
	    }
	    
	    public ScheduleWS(int id,String subject,UserDTO baseUser,int periodId,SchedulerStatusDTO statusId,
	    		Date createdDateTime,Date activeSince,Date activeUntil,Date doe,Date lastModified, CompanyDTO entityId ,String userName){
	    	setId(id);
	    	setSubject(subject);
	    	setBaseUser(baseUser);
	    	setPeriodId(periodId);
	    	setSchedulerStatus(statusId);
	    	setCreatedDate(createdDateTime);
	    	setActiveSince(activeSince);
	    	setActiveUntil(activeUntil);
			setDoe(doe);
	    	setLastModified(lastModified);
	    	setEntityId(entityId);
			setUserName(userName);
	    }
	    
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
	
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		

		public int getPeriodId() {
			return periodId;
		}
		public void setPeriodId(int periodId) {
			this.periodId = periodId;
		}
		
		
		
		public UserDTO getBaseUser() {
			return baseUser;
		}
		public void setBaseUser(UserDTO baseUser) {
			this.baseUser = baseUser;
		}
			

		public SchedulerStatusDTO getSchedulerStatus() {
			return statusId;
		}
		public void setSchedulerStatus(SchedulerStatusDTO statusId) {
			this.statusId = statusId;
		}
		

		public Date getCreatedDate() {
			return createdDateTime;
		}
		public void setCreatedDate(Date createdDateTime) {
			this.createdDateTime = createdDateTime;
		}
		

		public Date getActiveSince() {
			return activeSince;
		}
		public void setActiveSince(Date activeSince) {
			this.activeSince = activeSince;
		}
		
		
		public Date getActiveUntil() {
			return activeUntil;
		}
		public void setActiveUntil(Date activeUntil) {
			this.activeUntil = activeUntil;
		}
		
		public Date getDoe() {
			return doe;
		}
		public void setDoe(Date doe) {
			this.doe = doe;
		}
		
		public Date getLastModified() {
			return lastModified;
		}
		public void setLastModified(Date lastModified) {
			this.lastModified = lastModified;
		}
		
	
		public CompanyDTO getEntityId() {
			return entityId;
		}
		public void setEntityId(CompanyDTO entityId) {
			this.entityId = entityId;
		}
		
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		
	}


