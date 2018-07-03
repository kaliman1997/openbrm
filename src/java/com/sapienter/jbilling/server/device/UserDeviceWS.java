package com.sapienter.jbilling.server.device;
	
import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
	
import com.sapienter.jbilling.server.device.db.DeviceTypeDTO;
import com.sapienter.jbilling.server.device.db.UserDeviceDTO;
import com.sapienter.jbilling.server.device.db.DeviceDTO;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.device.db.UserDeviceStatusDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.db.AbstractGenericStatus;
import com.sapienter.jbilling.server.device.db.DeviceTypeDTO;
import com.sapienter.jbilling.server.device.db.DeviceStatusDTO;
import com.sapienter.jbilling.server.device.db.DeviceTypeDAS;
	 
	public class UserDeviceWS implements Serializable {
	
	    private Integer id; 
	    private DeviceDTO device;
	    private UserDTO baseUser;
	    private UserDeviceStatusDTO userDeviceStatus;
	    private Date createdDate;
	    private Date lastUpdatedDate;
	    private Integer deleted;
            private int optlock;
	    private Integer orderId;
	    private Integer orderLineId;
	    private Integer itemId;
	    private String telephoneNumber;
	    private String ip;
	    private String extId1;
	    private String icc;
	    
	  public UserDeviceWS(Integer id,
			DeviceDTO device,
			UserDTO baseUser,
			UserDeviceStatusDTO userDeviceStatus,
			Date createdDate,
			Integer deleted,
			int optlock) {


			this.setId(id);
                        this.setBaseUser(baseUser);
                        this.setDevice(device);
                        this.setUserDeviceStatus(userDeviceStatus);
                        this.setCreatedDate(createdDate);
                        this.setDeleted(deleted);
                        this.setOptlock(optlock);
	}

		  public UserDeviceWS(Integer id,
			DeviceDTO device,
			UserDTO baseUser,
			UserDeviceStatusDTO userDeviceStatus,
			Date createdDate,
			Integer deleted,
			int optlock,
        	String TelephoneNumber,
        	String icc) {


			this.setId(id);
                        this.setBaseUser(baseUser);
                        this.setDevice(device);
                        this.setUserDeviceStatus(userDeviceStatus);
                        this.setCreatedDate(createdDate);
                        this.setDeleted(deleted);
                        this.setOptlock(optlock);
                        this.setTelephoneNumber(TelephoneNumber);
                        this.setIcc(icc);
	}

	public UserDeviceWS(Integer id,
                        DeviceDTO device,
                        UserDTO baseUser,
                        UserDeviceStatusDTO userDeviceStatus,
                        Date createdDate,
                        Integer deleted,
                        int optlock,
			OrderDTO orderId,
			OrderLineDTO orderLineId,
			String telephone,
			String ip,
			String icc) {


                        this.setId(id);
                        this.setBaseUser(baseUser);
                        this.setDevice(device);
                        this.setUserDeviceStatus(userDeviceStatus);
                        this.setCreatedDate(createdDate);
                        this.setDeleted(deleted);
                        this.setOptlock(optlock);
                        this.setOrderId(orderId.getId());
                        this.setOrderLineId(orderLineId.getId());
			this.setTelephoneNumber(telephone);
			this.setIP(ip);
			this.setIcc(icc);
        }

	public String getIcc() {
		return icc;
	}

	public void setIcc(String icc) {
		this.icc = icc;
	}    
	
	
		public Integer getId() {
			return id;
		}
	
		public void setId(Integer id) {
			this.id = id;
		}

	public void setBaseUser(UserDTO baseUser) {
		this.baseUser = baseUser;
	}

	public UserDTO getBaseUser() {
		return this.baseUser;
	}
	
	public DeviceDTO getDevice()
		{
		return this.device;
		}
	public void setDevice(DeviceDTO device) {

		this.device = device;
	}

	 public void setUserDeviceStatus(UserDeviceStatusDTO userDeviceStatus)
		{
      this.userDeviceStatus = userDeviceStatus;
		}

	public UserDeviceStatusDTO getUserDeviceStatus()
		{
		return userDeviceStatus;
		}
		
		public Date getCreatedDate() {
			return createdDate;
		}
	
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public Date getLastUpdatedDate() {
			return lastUpdatedDate;
		}
	
		public void setLastUpdatedDate(Date lastUpdatedDate) {
			this.lastUpdatedDate = lastUpdatedDate;
		}
		
		public void setDeleted(Integer deleted) {
			this.deleted = deleted;
		}
		
		public Integer  getDeleted() {

			return this.deleted;
		}

		public void setOptlock(int optlock) {

			this.optlock = optlock;
		}

		public int getOptlock() {

			return this.optlock;
		}
	    
	    public UserDeviceWS() {
	    }

	    public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	    }

	    public void setOrderLineId(Integer orderLineId) {
                this.orderLineId = orderLineId;
            }

	    public void setItemId(Integer itemId) {
                this.itemId = itemId;
            }

	    public void setTelephoneNumber(String telephone) {
                this.telephoneNumber = telephone;
            }

	    public void setIP(String ip) {
                this.ip = ip;
            }

	    public Integer getOrderId() {
                return this.orderId;
            }

	    public Integer getOrderLineId() {
                return this.orderLineId;
            }

	    public Integer getItemId() {
               return this.itemId;
            }
	
	    public String getTelephoneNumber(){
                return this.telephoneNumber;
            }
	    public  String getIP(){
                return this.ip;
            }

	    public  String getExtId1(){
                return this.extId1;
            }

	    public void setExtId1(String extId1) {
		this.extId1 = extId1;
	    }







	
	}
