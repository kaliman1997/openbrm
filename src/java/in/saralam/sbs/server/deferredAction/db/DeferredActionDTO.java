package in.saralam.sbs.server.deferredAction.db;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
import in.saralam.sbs.server.common.SBSConstants;
import in.saralam.sbs.server.deferredAction.db.DeferredActionStatusDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
@Entity
@TableGenerator(
        name="deferred_action_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="deferred_action",
        allocationSize = 100
        )
@Table(name="deferred_action")
public class DeferredActionDTO  implements java.io.Serializable {	


     private Integer id;
     private CompanyDTO entity;
     private DeferredActionStatusDTO deferredActionStatus;
     private Date createdDate;
     private Date whenDate;
     private byte[] object;
     private int deleted;
     private UserDTO baseUser;
     private String statusStr;

    private static final Logger LOG = Logger.getLogger(DeferredActionDTO.class);

    public DeferredActionDTO() {
    }

    public DeferredActionDTO(Integer id) {
    	this.id=id;
    }
    
    
    public DeferredActionDTO(Integer id, DeferredActionStatusDTO deferredActionStatus, Date createdDate, Integer deleted,UserDTO baseUser) {
        this.id = id;
        this.deferredActionStatus = deferredActionStatus;
        this.createdDate = createdDate;
        this.deleted = deleted;
        this.baseUser =baseUser;
      
    }
    
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="deferred_action_GEN")
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="status_id", nullable=false)   
    public DeferredActionStatusDTO getDeferredActionStatus() {
        return this.deferredActionStatus;
    }
    
    public void setDeferredActionStatus(DeferredActionStatusDTO deferredActionStatus) {
        this.deferredActionStatus = deferredActionStatus;
    }

    @Column(name="when_date", nullable=false, length=19)
    public Date getWhenDate() {
        return this.whenDate;
    }

    public void setWhenDate(Date whenDate) {
        this.whenDate = whenDate;
    }

   @Column(columnDefinition = "longblob", name = "object", nullable = true)
    public byte[] getObject() {
      return this.object;
    }
    
    public void setObject(byte[] object) {
      this.object = object;
    }

    @Column(name="created_date", nullable=false, length=19)
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name="deleted", nullable=false)
    public Integer getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enitity_id")
    public CompanyDTO getEntity() {
        return this.entity;
    }

    public void setEntity(CompanyDTO entity) {
        this.entity = entity;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    public UserDTO getBaseUser() {
        return this.baseUser;
    }
    
    public void setBaseUser(UserDTO baseUser) {
        this.baseUser = baseUser;
    }

    @Transient
    public String getStatusStr() {
        return statusStr;
    }

    public String toString() {		
		
	StringBuffer str = new StringBuffer("Service = " +
	     "id=" + id + "," +
	     "created_datetime=" + createdDate);
		
	return str.toString();

   }




}


