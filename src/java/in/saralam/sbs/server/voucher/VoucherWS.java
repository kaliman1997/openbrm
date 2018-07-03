package in.saralam.sbs.server.voucher;
import java.io.Serializable;

import java.util.Date;

import in.saralam.sbs.server.voucher.db.VoucherStatusDAS;
import in.saralam.sbs.server.voucher.db.VoucherStatusDTO;
import org.apache.log4j.Logger;

public class VoucherWS implements Serializable{
	

		private Integer id;
		private Date createdDateTime;
		private Date lastModified;
		private VoucherStatusDTO voucherStatus;
		private Integer entityId;		
		private Integer serialNo;
		private String pinCode;
		private String batchId;
		private Integer productId;
		
		
		private static final Logger LOG = Logger.getLogger(VoucherWS.class);
		
		public VoucherWS(){
			
		}
		public VoucherWS(Integer id,Date createdDateTime,Date lastModified,VoucherStatusDTO voucherStatus,Integer entityId,Integer serialNo,String pinCode,String batchId,Integer productId) {
			
			setId(id);
			setCreatedDateTime(createdDateTime);
			setLastModified(lastModified);
			setVoucherStatus(voucherStatus);
			setEntityId(entityId);
			setSerialNo(serialNo);
			setPinCode(pinCode);
		    setBatchId(batchId);
			setProductId(productId);
		}
		
		public VoucherWS(Date createdDateTime,VoucherStatusDTO voucherStatus,Integer entityId,Integer serialNo,String pinCode,String batchId,Integer productId) {
			

			setCreatedDateTime(createdDateTime);
			setVoucherStatus(voucherStatus);			
			setEntityId(entityId);
			setSerialNo(serialNo);
			setPinCode(pinCode);
		    setBatchId(batchId);
			setProductId(productId);
		}
		
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		
		
		public Date getCreatedDateTime()  {
		    return createdDateTime;
		}
		public void setCreatedDateTime(Date createdDateTime)  {
		    this.createdDateTime = createdDateTime;
		}
		
	
		public Date getLastModified()  {
		    return lastModified;
		}
		public void setLastModified(Date lastModified)  {
		    this.lastModified = lastModified;
		}
		
	
		public VoucherStatusDTO getVoucherStatus()  {
		    return voucherStatus;
		}
		public void setVoucherStatus(VoucherStatusDTO voucherStatus)  {
		    this.voucherStatus = voucherStatus;
		}
		
	
		public Integer getEntityId()  {
		    return entityId;
		}
		public void setEntityId(Integer entityId)  {
		    this.entityId = entityId;
		}
		
	
		public Integer getSerialNo() {
			return serialNo;
		}
		public void setSerialNo(Integer serialNo) {
			this.serialNo = serialNo;
		}
		

		public String getPinCode() {
			return pinCode;
		}
		public void setPinCode(String pinCode) {
			this.pinCode = pinCode;
		}
		
	
		public String getBatchId() {
			return batchId;
		}
		public void setBatchId(String batchId) {
			this.batchId = batchId;
		}
		
	
		public Integer getProductId()  {
		    return productId;
		}
		public void setProductId(Integer productId)  {
		    this.productId = productId;
		}
			
	}

