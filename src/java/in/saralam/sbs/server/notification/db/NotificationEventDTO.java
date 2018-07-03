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

@Entity
@TableGenerator(
        name="notification_event_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="notification_event ",
        allocationSize = 100
        )
@Table(name="notification_event ")
public class NotificationEventDTO implements java.io.Serializable {	

     private Integer id;
     private String subject ;
     private Integer statusId;
     private Date createDateTime;	 
     
     private static final Logger LOG = Logger.getLogger(NotificationEventDTO.class);

    public NotificationEventDTO() {
    }

    public NotificationEventDTO(Integer id) {
    	this.id=id;
    }
    
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="notification_event_GEN")
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="subject ", nullable=true)
    public String getSubject() {
	    return this.subject;
    }
    public void setSubject(String subject) {
	        this.subject =subject;
    }
	
    @Column(name = "created_datetime")
    public  Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }


       /*public String toString() {		
		
	  StringBuffer str = new StringBuffer("Service = " +
	  "id=" + id + "," +
	  "," + "resource_id=" + currency + "," +
	  "threshold=" + threshold+ "," +
          "entityId=" + entityId);
	   return str.toString();

	}*/




}


