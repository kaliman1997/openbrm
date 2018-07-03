 package in.saralam.sbs.server.notification.db;

 import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDescription;
import com.sapienter.jbilling.server.util.db.AbstractGenericStatus;

@Entity
@Table(name="notification_type")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
 public class NotificationTypeDTO extends  AbstractDescription implements java.io.Serializable {	
 	
 	private int id;

 	public NotificationTypeDTO() {
     }
     
     public NotificationTypeDTO(int id) {
         this.id = id;
     }
    
 	@Transient
 	protected String getTable() {
 		return Constants.TABLE_NOTIFICATION_TYPE;
 	}

 	@Id
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

}


