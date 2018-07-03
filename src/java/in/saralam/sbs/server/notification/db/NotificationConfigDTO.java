 package in.saralam.sbs.server.notification.db;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import javax.persistence.*;
import com.sapienter.jbilling.server.notification.db.NotificationMessageLineDTO;

@Entity
@TableGenerator(
        name="notification_config_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="notification_config ",
        allocationSize = 100
        )
@Table(name="notification_config ")
public class NotificationConfigDTO implements java.io.Serializable {	

     private Integer id;
     private NotificationEventDTO subject ;
     private Date createDateTime;
	 private NotificationMessageLineDTO messageId;
	 private NotificationTypeDTO notifyType;
	 private int deleted;
	 private int version;
     
     private static final Logger LOG = Logger.getLogger(NotificationConfigDTO.class);

    public NotificationConfigDTO() {
    }

    public NotificationConfigDTO(Integer id) {
    	this.id=id;
    }
    
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="notification_config_GEN")
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    public NotificationEventDTO getSubject() {
	    return this.subject;
    }
    public void setSubject(NotificationEventDTO subject) {
	        this.subject = subject;
    }

    @Column(name = "created_datetime")
    public  Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
	public NotificationMessageLineDTO getMessageId(){
	  return this.messageId;
	}
	
	public void setMessageId(NotificationMessageLineDTO messageId){
	  this.messageId = messageId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notify_type")
	public NotificationTypeDTO getNotifyType(){
	  return this.notifyType;
	}
	
	public void setNotifyType(NotificationTypeDTO notifyType){
	  this.notifyType = notifyType;
	}
	
	@Column(name="deleted")
	public int getDeleted(){
	  return this.deleted;
	}
	
	public void setDeleted(int deleted){
	  this.deleted = deleted;
	}
	
	@Column(name="optlock")
	public int getVersion(){
	  return this.version;
	}
	
	public void setVersion(int version){
	  this.version = version;
	}


      /* public String toString() {		
		
	  StringBuffer str = new StringBuffer("Service = " +
	  "id=" + id + "," +
	  "," + "resource_id=" + currency + "," +
	  "threshold=" + threshold+ "," +
          "entityId=" + entityId);
	   return str.toString();

	}*/

}


