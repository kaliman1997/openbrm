
package in.saralam.sbs.server.subscription.db;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
//import org.hibernate.annotations.ServiceBy;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.invoice.InvoiceBL;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.process.db.BillingProcessDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Util;
import in.saralam.sbs.server.subscription.db.ServiceStatusDTO;
import java.util.ArrayList;

@Entity
@TableGenerator(
        name="service_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="service",
        allocationSize = 100
        )
@Table(name="service")
// No cache, mutable and critical
public class ServiceDTO implements java.io.Serializable {

     private static Logger LOG = Logger.getLogger(ServiceDTO.class);

     private Integer id;
     private UserDTO baseUserByUserId;
     private String serviceType;
     private String login;
     private String password;
     private Date lastStatusChange;
     private Date createDate;
     private int deleted;
     private String notes;
     private String name;

     // other non-persitent fields
     private String serviceTypeStr = null;
     private String statusStr = null;
     private ServiceStatusDTO serviceStatus;
     private OrderDTO orderDTO;
     private OrderLineDTO orderLineDTO;
     
    

    public ServiceDTO() {
    }
    
    public ServiceDTO(ServiceDTO other) {
    	init(other);
    }

    public void init(ServiceDTO other) {
    	this.id = other.getId();
    	this.baseUserByUserId = other.getBaseUserByUserId();
      	this.serviceStatus = other.getServiceStatus(); 
    	this.createDate = other.getCreateDate();
    	
    	this.deleted = other.getDeleted();
    	//this.notes = other.getNotes();
    }
    
    public ServiceDTO(int id, ServiceStatusDTO serviceStatusDTO, Date createDatetime, Integer deleted) {
        this.id = id;
        this.serviceStatus = serviceStatusDTO;
        this.createDate = createDatetime;
        this.deleted = deleted;
    }
    public ServiceDTO(int id, UserDTO baseUserByUserId, ServiceStatusDTO serviceStatusDTO, Integer deleted, String notes) {
       this.id = id;
       this.baseUserByUserId = baseUserByUserId;
       this.serviceStatus = serviceStatusDTO;
       this.deleted = deleted;
       this.notes = notes;
      
    }
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="service_GEN")
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    public UserDTO getBaseUserByUserId() {
        return this.baseUserByUserId;
    }
    public void setBaseUserByUserId(UserDTO baseUserByUserId) {
        this.baseUserByUserId = baseUserByUserId;
    }
    
   
    @Column(name="login")
    public String getLogin() {
        return this.login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    @Column(name="password", nullable=false)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
   @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="status_id", nullable=false)
    public ServiceStatusDTO getServiceStatus() {
        return this.serviceStatus;
    }
    
    public void setServiceStatus(ServiceStatusDTO serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id", nullable=false)
    public OrderDTO getOrderDTO() {
        return this.orderDTO;
    }
    
    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_line_id", nullable=false)
    public OrderLineDTO getOrderLineDTO() {
        return this.orderLineDTO;
    }
    
    public void setOrderLineDTO(OrderLineDTO orderLineDTO) {
        this.orderLineDTO = orderLineDTO;
    }
 
    @Column(name="create_datetime", nullable=false, length=29)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDatetime) {
        this.createDate = createDatetime;
    }
   
    @Column(name="deleted", nullable=false)
    public int getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Column(name="name", nullable=false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    } 
	@Column(name="service_type", nullable=false)
    public String getServiceType() {
        return this.serviceType;
    }
    
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    } 
  
	@Transient
	public Integer getStatusId() {
		return getServiceStatus() == null ? null : getServiceStatus().getId();
	}
	public void setStatusId(Integer statusId) {
		if (statusId == null) {
			setServiceStatus(null);
			return;
		}
		ServiceStatusDTO dto = new ServiceStatusDTO();
		dto.setId(statusId);
		setServiceStatus(dto);
	}
      
    @Transient
    public UserDTO getUser() {
    	return getBaseUserByUserId();
    }
      
    @Transient
    public String getStatusStr() {
    	return statusStr;
    }
  
    @Transient
    public Integer getUserId() {
    	return (getBaseUserByUserId() == null) ? null : getBaseUserByUserId().getId();
    }

	/**
	 * Makes sure that all the proxies are loaded, so no session is needed to
	 * use the pojo
	 */
	public String toString() {
		StringBuffer str = new StringBuffer("Service = " +
	     "id=" + id + "," + 
	     "baseUserByUserId=" + ((baseUserByUserId == null) ? null : baseUserByUserId.getId()) + "," +
	     "serviceStatusDTO=" + ((serviceStatus == null) ? null : serviceStatus) + "," +
	     "createDate=" + createDate + "," +
	     "deleted=" + deleted + "," +
	     "notes=" + notes);
		
		return str.toString();

	}
	
    // default values
    @Transient
    public void setDefaults() {
        if (getCreateDate() == null) {
            setCreateDate(Calendar.getInstance().getTime());
            setDeleted(0);
        }
        if (getServiceStatus() == null) {
            setServiceStatus(new ServiceStatusDAS().find(
                    Constants.ORDER_STATUS_ACTIVE));
        }
       
    }
    
    

}


