package com.sapienter.jbilling.server.device.db;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.sapienter.jbilling.server.user.db.UserDTO;
//import com.sapienter.jbilling.server.device.db.DeviceTypeDTO;
import com.sapienter.jbilling.server.device.db.DeviceDTO;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import in.saralam.sbs.server.subscription.db.ServiceDTO;

@Entity
@TableGenerator(
        name="user_device_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="user_device",
        allocationSize = 100
        )
@Table(name="user_device")
public class UserDeviceDTO  implements java.io.Serializable {


     private Integer id;
     private UserDTO baseUser;
     private DeviceDTO device;
     private Date createdDate;
     private Date lastUpdatedDate;
     private Integer deleted;
     private Integer optlock;
     private UserDeviceStatusDTO userDeviceStatus;
     private OrderDTO orderId;
     private OrderLineDTO orderLineId;
     private String telephoneNumber;
     private String ip;
     private String extId1;
     private String icc;

     public UserDeviceDTO() {

     }

    public UserDeviceDTO(Integer id, UserDTO baseUser, DeviceDTO device, Date createdDate, Date lastUpdatedDate, Integer deleted, int optlock, UserDeviceStatusDTO userDeviceStatus) {
       this.id = id;
       this.baseUser = baseUser;
       this.device = device;
       //this.service = service;
       this.createdDate = createdDate;
	   this.lastUpdatedDate = lastUpdatedDate;
       this.deleted = deleted;
       this.optlock = optlock;
       this.userDeviceStatus=userDeviceStatus;
    }

    public UserDeviceDTO(Integer id, UserDTO baseUser, DeviceDTO device, Date createdDate, Date lastUpdatedDate, Integer deleted, int optlock,
		UserDeviceStatusDTO userDeviceStatus, OrderDTO orderId, OrderLineDTO orderLineId, String telephoneNumber, String ip, String icc) {
       this.id = id;
       this.baseUser = baseUser;
       this.device = device;
       //this.service = service;
       this.createdDate = createdDate;
       this.lastUpdatedDate = lastUpdatedDate;
       this.deleted = deleted;
       this.optlock = optlock;
       this.userDeviceStatus = userDeviceStatus;
       this.orderId = orderId;
       this.orderLineId = orderLineId;
       this.telephoneNumber = telephoneNumber;
       this.ip = ip;
       this.icc = icc;
    }
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="user_device_GEN")
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer  id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    public UserDTO getBaseUser() {
        return this.baseUser;
    }
    
    public void setBaseUser(UserDTO baseUser) {
        this.baseUser = baseUser;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="device_id", nullable=false)
    public DeviceDTO getDevice() {
        return this.device;
    }
    
    public void setDevice(DeviceDTO device) {
        this.device = device;
    }


    /*@Temporal(TemporalType.TIMESTAMP) */
    @Column(name="created_date", nullable=false, length=19)
    public Date getCreatedDate() {
        return this.createdDate;
    }
    

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

	
	@Column(name="last_updated_date", nullable=true, length=19)
    public Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }
    
    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    
    @Column(name="deleted", nullable=false)
    public Integer getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    
    @Version
    @Column(name="OPTLOCK")
    public Integer getOptlock() {
        return this.optlock;
    }
    
    public void setOptlock(Integer optlock) {
        this.optlock = optlock;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="status_id", nullable=false) 
    public UserDeviceStatusDTO getUserDeviceStatus() {
    	return this.userDeviceStatus;
    }

    public  void setUserDeviceStatus(UserDeviceStatusDTO userDeviceStatus) {
    	 this.userDeviceStatus=userDeviceStatus;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id", nullable=false) 
    public OrderDTO getOrderId() {
        return this.orderId;
    }
  
    public void setOrderId(OrderDTO orderId) {
        this.orderId = orderId;

    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_line_id", nullable=false) 
    public OrderLineDTO getOrderLineId() {
        return this.orderLineId;
    }

    public void setOrderLineId(OrderLineDTO orderLineId) {
	this.orderLineId = orderLineId;

    }

    public void setTelephoneNumber(String telephone) {
       this.telephoneNumber = telephone;

    }
   
    @Column(name="telephone_number", nullable=true)
    public String getTelephoneNumber() {

	return this.telephoneNumber;
    }

    @Column(name="ip", nullable=true)
    public String getIP() {
       return this.ip;

    }

    public void setIP(String ip) {
       this.ip = ip;

    }

    @Column(name="ext_id1", nullable=true)
    public String getExtId1() {
        return this.extId1;
    }

    public void setExtId1(String extId1) {
        this.extId1 = extId1;
    }

    @Transient
   	public String getIcc() {
   		return this.icc;
   	}

   	public void setIcc(String icc) {
   		this.icc = icc;
   	}
    

}


