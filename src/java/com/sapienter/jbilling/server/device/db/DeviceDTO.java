package com.sapienter.jbilling.server.device.db;

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


import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.device.db.DeviceTypeDTO;
import com.sapienter.jbilling.server.device.db.UserDeviceDTO;
import com.sapienter.jbilling.server.device.db.DeviceStatusDTO;
import com.sapienter.jbilling.server.user.db.CompanyDTO;




@Entity
@TableGenerator(
        name="device_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="device",
        allocationSize = 100
        )
@Table(name="device")
public class DeviceDTO  implements java.io.Serializable {	


     private Integer id;
     private DeviceTypeDTO deviceType;
     private DeviceStatusDTO deviceStatus;
     private String serialNum;
     private String deviceCode;
     private String vendorCode;
     private Date createdDate;
     private Date lastUpdatedDate;
     private Integer deleted;
     private int optlock;
     private String statusStr;
     private String icc;
     private String imsi;
     private Integer puk1;
     private Integer puk2;
     private String pin1;
     private String pin2;

     //private Set<UserDeviceDTO> userDeviceDTOs = new HashSet<UserDeviceDTO>(0);
     private CompanyDTO entity;

	 private static final Logger LOG = Logger.getLogger(DeviceDTO.class);

    public DeviceDTO() {
    }

    public DeviceDTO(Integer id) {
    	this.id=id;
    }
    
    
    public DeviceDTO(Integer id, DeviceTypeDTO deviceType, DeviceStatusDTO deviceStatus, Date createdDate, Integer deleted, int optlock) {
        this.id = id;
        this.deviceType = deviceType;
        this.deviceStatus = deviceStatus;
        this.createdDate = createdDate;
        this.deleted = deleted;
        this.optlock = optlock;
    }
    
    public DeviceDTO(String icc,String imsi, Integer puk1, Integer puk2) {
        this.icc = icc;
        this.imsi = imsi;
        this.puk1 = puk1;
        this.puk2 = puk2;
        
    }
      public DeviceDTO(Integer id, DeviceTypeDTO deviceType, DeviceStatusDTO deviceStatus, String serialNum, String deviceCode, String vendorCode, Date createdDate, Date lastUpdatedDate, Integer deleted, int optlock, CompanyDTO entity,String icc,String imsi, Integer puk1, Integer puk2 ) {
       this.id = id;
       this.deviceType = deviceType;
       this.deviceStatus = deviceStatus;
       this.serialNum = serialNum;
       this.deviceCode = deviceCode;
       this.vendorCode = vendorCode;
       this.createdDate = createdDate;
       this.lastUpdatedDate = lastUpdatedDate;
       this.deleted = deleted;
       this.optlock = optlock;
       this.entity = entity;
       this.icc = icc;
       this.imsi = imsi;
       this.puk1 = puk1;
       this.puk2 = puk2;
       
       //this.userDeviceDTOs = userDeviceDTOs;
    }
    
    public DeviceDTO(Integer id, DeviceTypeDTO deviceType, DeviceStatusDTO deviceStatus, String serialNum, String deviceCode, String vendorCode, Date createdDate, Date lastUpdatedDate, Integer deleted, int optlock, CompanyDTO entity,String icc,String imsi, Integer puk1, Integer puk2 ,String pin1, String pin2) {
       this.id = id;
       this.deviceType = deviceType;
       this.deviceStatus = deviceStatus;
       this.serialNum = serialNum;
       this.deviceCode = deviceCode;
       this.vendorCode = vendorCode;
       this.createdDate = createdDate;
       this.lastUpdatedDate = lastUpdatedDate;
       this.deleted = deleted;
       this.optlock = optlock;
       this.entity = entity;
       this.icc = icc;
       this.imsi = imsi;
       this.puk1 = puk1;
       this.puk2 = puk2;
       this.pin1 = pin1;
       this.pin2 = pin2; 
       //this.userDeviceDTOs = userDeviceDTOs;
    }



	 public DeviceDTO(Integer id, DeviceTypeDTO deviceType, DeviceStatusDTO deviceStatus, String serialNum, String deviceCode, String vendorCode, Date createdDate, Date lastUpdatedDate) {
       this.id = id;
       this.deviceType = deviceType;
       this.deviceStatus = deviceStatus;
       this.serialNum = serialNum;
       this.deviceCode = deviceCode;
       this.vendorCode = vendorCode;
       this.createdDate = createdDate;
       this.lastUpdatedDate = lastUpdatedDate;
	   LOG.debug("DeviceDTO constructor is created" +id+","+deviceType+","+deviceStatus+","+serialNum+","+deviceCode+","+vendorCode+","+createdDate+","+lastUpdatedDate);
  
    }
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="device_GEN")
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="type_id", nullable=false)
    public DeviceTypeDTO getDeviceType() {
        return this.deviceType;
    }
    
    public void setDeviceType(DeviceTypeDTO deviceType) {
        this.deviceType = deviceType;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="status_id", nullable=false)   
    public DeviceStatusDTO getDeviceStatus() {
        return this.deviceStatus;
    }
    
    public void setDeviceStatus(DeviceStatusDTO deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    
    @Column(name="serial_num", length=100)
    public String getSerialNum() {
        return this.serialNum;
    }
    
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    
    @Column(name="device_code", length=100)
    public String getDeviceCode() {
        return this.deviceCode;
    }
    
    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    
    @Column(name="vendor_code", length=100)
    public String getVendorCode() {
        return this.vendorCode;
    }
    
    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    @Column(name="created_date", nullable=false, length=19)
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name="last_updated_date", length=19)
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

    
    @Column(name="OPTLOCK", nullable=false)
    public int getOptlock() {
        return this.optlock;
    }
    
    public void setOptlock(int optlock) {
        this.optlock = optlock;
    }
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id")
    public CompanyDTO getEntity() {
        return this.entity;
    }

    public void setEntity(CompanyDTO entity) {
        this.entity = entity;
    }

    @Transient
    public String getStatusStr() {
        return statusStr;
    }

	 @Column(name="icc", unique=true, length=100)
	 public void setIcc(String icc) {
	        this.icc = icc;
	    }
	 public String getIcc()
	 {
		 return this.icc;
	 }
	 @Column(name="imsi",  unique=true,length=100)
	 public void setImsi(String imsi) {
	        this.imsi = imsi;
	    }
	 public String getImsi()
	 {
		 return this.imsi;
	 }

	 @Column(name="puk1", nullable=false)
	 public void setPuk1(Integer puk1) {
	        this.puk1 = puk1;
	 }

	 public Integer getPuk1() {

		 return this.puk1;
	 }

	 @Column(name="puk2", nullable=false)
	 public void setPuk2(Integer puk2) {
	        this.puk2 = puk2;
	 }

	 public Integer getPuk2() {

	 	return this.puk2;
	 }
          @Column(name="pin1", nullable=false)
	 public void setPin1(String pin1) {
	        this.pin1 = pin1;
	 }

	 public String getPin1() {

		 return this.pin1;
	 }

	  @Column(name="pin2", nullable=false)
	 public void setPin2(String pin2) {
	        this.pin2 = pin2;
//           LOG.debug(" pin2 "+pin2);
	 }

	 public String getPin2() {

		 return this.pin2;
	 }
/*@OneToMany(fetch=FetchType.LAZY, mappedBy="device")
    public Set<UserDeviceDTO> getUserDeviceDTOs() {
        return this.userDeviceDTOs;
    }
    
    public void setUserDeviceDTOs(Set<UserDeviceDTO> userDeviceDTOs) {
        this.userDeviceDTOs = userDeviceDTOs;
    }*/


	public String toString() {		
		
		StringBuffer str = new StringBuffer("Service = " +
	     "id=" + id + "," +
	     "," + "serial_num=" + serialNum + "," +
	     "device_code=" + deviceCode + "," +
	     "DeviceTypeDTO=" + deviceType + ","+
	     "created_datetime=" + createdDate + "," +
	     "modified_datetime=" + lastUpdatedDate +","+"vendorCode=" + vendorCode+","+
	     "icc"+icc+","+"imsi"+imsi+","+"puk1"+puk1+","+"puk2"+puk2+","+"pin1"+pin1+","+"pin2"+pin2);
		
		return str.toString();

	}




}


