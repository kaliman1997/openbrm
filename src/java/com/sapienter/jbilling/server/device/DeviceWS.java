package com.sapienter.jbilling.server.device;
	
import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
	
import com.sapienter.jbilling.server.device.db.DeviceTypeDTO;
import com.sapienter.jbilling.server.util.db.AbstractGenericStatus;
import com.sapienter.jbilling.server.device.db.DeviceTypeDTO;
import com.sapienter.jbilling.server.device.db.DeviceStatusDTO;
import com.sapienter.jbilling.server.device.db.DeviceTypeDAS;
public class DeviceWS implements Serializable {
	
	private Integer id;
    private Integer statusId;
    private int deleted;
    private DeviceTypeDTO deviceType;
    private DeviceStatusDTO deviceStatus;
    private String serialNum;
    private String deviceCode;
    private String vendorCode;
    private Date createdDate;
    private Date lastUpdatedDate;  
	    
    private String icc;
    private String imsi;
    private int puk1;
    private int puk2;
    private String pin1;
    private String pin2;

    private String statusStr = null;
	
    private static final Logger LOG = Logger.getLogger(DeviceWS.class);

    public Integer getId() {
	return id;
    }
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSerialNum() {
		return serialNum;
	}
		
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	
	}
	
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	
	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceType(DeviceTypeDTO deviceType) {
	      this.deviceType = deviceType;
	}

	public DeviceTypeDTO getDeviceType() {
		return deviceType;
	}
	
	public void setDeviceStatus(DeviceStatusDTO deviceStatus) {
	      this.deviceStatus = deviceStatus;
	}

	public DeviceStatusDTO getDeviceStatus() {
		return deviceStatus;
	}
		
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	    
	    
	public String getVendorCode() {
		return vendorCode;
	}
	
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
		
 	public void setIcc(String icc) {
	        this.icc = icc;
        }
	public String getIcc() {
		 return this.icc;
	}
		 
	public void setImsi(String imsi) {
	        this.imsi = imsi;
	}
	public String getImsi() {
		 return this.imsi;
 	}
		 
	public void setPuk1(int puk1) {
	        this.puk1 = puk1;
	}
	public int getPuk1() {
		 return this.puk1;
	}
	public void setPuk2(int puk2) {
	        this.puk2 = puk2;
	}
	public int getPuk2() {
		 return this.puk2;
	}
        public void setPin1(String pin1) {
	        this.pin1 = pin1;
	}
	public String getPin1() {
		 return this.pin1;
	}
	public void setPin2(String pin2) {
	        this.pin2 = pin2;
	}
	public String getPin2() {
		 return this.pin2;
	}
		
  	public DeviceWS() {
	}
	
	/*    public DeviceWS(String serialNum, String deviceCode, String vendorCode, Date createdDate, Date lastUpdatedDate) {
	    
	    	setSerialNum(serialNum);
	        setDeviceCode(deviceCode);
	        setCreatedDate(createdDate);      
	        setLastUpdatedDate(lastUpdatedDate);
	        setVendorCode(vendorCode);
	        LOG.debug("we are in device ws consructor" ); 
	      // DeviceTypeDTO deviceType=new DeviceTypeDAS().find(deviceTyp);
	       //LOG.debug("deviceType" +deviceType);
	       //setDeviceType(deviceType);
	        
	    	
	    }*/
	    
	    public DeviceWS(Integer id, String serialNum, String deviceCode, String vendorCode, DeviceStatusDTO deviceStatus, DeviceTypeDTO deviceType,
			String icc, String imsi, int puk1,int puk2,Date createdDate, Date lastUpdatedDate) {
		    
		setId(id);
                setSerialNum(serialNum);
                setDeviceCode(deviceCode);
		setVendorCode(vendorCode);
		setDeviceStatus(deviceStatus);
		setDeviceType(deviceType);
	    	setIcc(icc);
	    	setImsi(imsi);
	    	setPuk1(puk1);
	    	setPuk2(puk2);
                setCreatedDate(createdDate);      
	        setLastUpdatedDate(lastUpdatedDate);
                
	       
	    }
             public DeviceWS(Integer id, String serialNum, String deviceCode, String vendorCode, DeviceStatusDTO deviceStatus, DeviceTypeDTO deviceType,
			String icc, String imsi, int puk1,int puk2, String pin1,String pin2,Date createdDate, Date lastUpdatedDate) {
		    
		setId(id);
                setSerialNum(serialNum);
                setDeviceCode(deviceCode);
		setVendorCode(vendorCode);
		setDeviceStatus(deviceStatus);
		setDeviceType(deviceType);
	    	setIcc(icc);
	    	setImsi(imsi);
	    	setPuk1(puk1);
	    	setPuk2(puk2);
                setPin1(pin1);
                setPin2(pin2);
	        setCreatedDate(createdDate);      
	        setLastUpdatedDate(lastUpdatedDate);
                
	       
	    }

            public DeviceWS(String icc, String imsi, int puk1, int puk2, Date createdDate, Date lastUpdatedDate) {

                setIcc(icc);
                setImsi(imsi);
		setImsi(imsi);
                setPuk1(puk1);
                setPuk2(puk2);
                setCreatedDate(createdDate);
                setLastUpdatedDate(lastUpdatedDate);


	    }
            public DeviceWS(String icc, String imsi, int puk1, int puk2,String pin1,String pin2, Date createdDate, Date lastUpdatedDate) {
               
                setIcc(icc);
                setImsi(imsi);
		  setImsi(imsi);
                setPuk1(puk1);
                setPuk2(puk2);
                setPin1(pin1);
                setPin2(pin2);
                setCreatedDate(createdDate);
                setLastUpdatedDate(lastUpdatedDate);


	    }



	    

		public DeviceWS(Integer id, String serialNum, String deviceCode, String vendorCode, DeviceStatusDTO deviceStatus, DeviceTypeDTO deviceType, Date createdDate, Date lastUpdatedDate) {
	    	setId(id);	    	
	    	setSerialNum(serialNum);
	        setDeviceCode(deviceCode);
	        setCreatedDate(createdDate);      
	        setLastUpdatedDate(lastUpdatedDate);
	        setVendorCode(vendorCode);
	    	setCreatedDate(createdDate);	    
		setDeviceStatus(deviceStatus);
		setDeviceType(deviceType);
			
	    }
	
		
		public Date getCreatedDate() {
			return createdDate;
		}
	
		public void setCreatedDate(Date createDate) {
			this.createdDate = createDate;
		}
	
		public int getDeleted() {
			return deleted;
		}
	
		public void setDeleted(int deleted) {
			this.deleted = deleted;
		}
	
		public String getStatusStr() {
			return statusStr;
		}
	
		public void setStatusStr(String statusStr) {
			this.statusStr = statusStr;
		}
	
	}
