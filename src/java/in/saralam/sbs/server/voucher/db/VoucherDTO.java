package in.saralam.sbs.server.voucher.db;

import in.saralam.sbs.server.voucher.VoucherBL;
import in.saralam.sbs.server.voucher.VoucherWS;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;
import org.apache.log4j.Logger;
import javax.persistence.FetchType;
import com.sapienter.jbilling.server.util.csv.Exportable;
@Entity
@TableGenerator(
		name="voucher_GEN",
		table="jbilling_seqs",
		pkColumnName = "name",
		valueColumnName = "next_id",
		pkColumnValue = "voucher",
		allocationSize = 100
		)
		
@Table(name="voucher")
public class VoucherDTO implements Serializable,Exportable{
	
	private Integer id;
	private Date createdDateTime;
	private Date lastModified;
	private VoucherStatusDTO voucherStatus;
	private Integer entityId;	
	private Integer serialNo;
	private String pinCode;
	private String batchId;
	private Integer productId;
	
	
	private static final Logger LOG = Logger.getLogger(VoucherDTO.class);
	
	public VoucherDTO(){
		
	}
	public VoucherDTO(Integer id,Date createdDateTime,Date lastModified,VoucherStatusDTO voucherStatus,Integer entityId,Integer serialNo,String pinCode,String batchId,Integer productId) {
		
		this.id = id;
		this.createdDateTime = createdDateTime;
		this.lastModified = lastModified;
		this.voucherStatus = voucherStatus;
		this.entityId = entityId;
		this.serialNo = serialNo;
		this.pinCode = pinCode;
	    this.batchId = batchId;
		this.productId = productId;
	}
	
	public VoucherDTO(Date createdDateTime,VoucherStatusDTO voucherStatus,Integer entityId,Integer serialNo,String pinCode,String batchId,Integer productId) {
				
		
		this.createdDateTime = createdDateTime;
		this.voucherStatus = voucherStatus;
		this.entityId = entityId;
		this.serialNo = serialNo;
		this.pinCode = pinCode;
	    this.batchId = batchId;
		this.productId = productId;
	}
	
	public VoucherDTO(VoucherWS voucherWS) {
		
		setId(voucherWS.getId());
		setCreatedDateTime(voucherWS.getCreatedDateTime());
		setLastModified(voucherWS.getLastModified());
		setVoucherStatus(voucherWS.getVoucherStatus());
		setEntityId(voucherWS.getEntityId());
		setSerialNo(voucherWS.getSerialNo());
		setPinCode(voucherWS.getPinCode());
		setBatchId(voucherWS.getBatchId());
		setProductId(voucherWS.getProductId());
	}
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="voucher_GEN")
	
	
	@Column(name="id", unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="created_datetime",length=25, nullable=false)
	public Date getCreatedDateTime()  {
	    return this.createdDateTime;
	}
	public void setCreatedDateTime(Date createdDateTime)  {
	    this.createdDateTime = createdDateTime;
	}
	
	@Column(name="last_modified",length=25)
	public Date getLastModified()  {
	    return this.lastModified;
	}
	public void setLastModified(Date lastModified)  {
	    this.lastModified = lastModified;
	}
	
	 @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    public VoucherStatusDTO getVoucherStatus() {
        return this.voucherStatus;
    }

    public void setVoucherStatus(VoucherStatusDTO voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    @Transient
    public VoucherStatusDTO getStatus() {
        return this.getVoucherStatus();
    }
	
	@Column(name="entity_id")
	public Integer getEntityId()  {
	    return this.entityId;
	}
	public void setEntityId(Integer entityId)  {
	    this.entityId = entityId;
	}
	
	@Column(name="serial_no", length=45)
	public Integer getSerialNo() {
		return this.serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	
	@Column(name="pin_code", length=45)
	public String getPinCode() {
		return this.pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	
	@Column(name="batch_id", length=45)
	public String getBatchId() {
		return this.batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	@Column(name="product_id")
	public Integer getProductId()  {
	    return this.productId;
	}
	public void setProductId(Integer productId)  {
	    this.productId = productId;
	}
	
	public String toString(){
		StringBuffer str = new StringBuffer("voucher = " +
	       "id=" + id + "," +
			"createdDateTime=" + createdDateTime + "," +
			"lastModified=" + lastModified + "," +
			"voucherStatus=" + voucherStatus + "," +
			"entityId=" + entityId + "," +
			"serialNo=" + serialNo + "," +
			"pinCode=" + pinCode + "," +
			"batchId=" + batchId + "," +
			"productId=" + productId 
				);
		   return str.toString();
	}
	 @Transient
	    public String[] getFieldNames() {
	        return new String[] {
	                "id",
	                "createdDateTime",
	                "lastModified",
	                "voucherStatus",
	                
	                "entityId",
	                "serialNo",
	                "pinCode",
	                "batchId",
	                "productId",
	                
	        };
	    }
	
 @Transient
	    public Object[][] getFieldValues() {
	        VoucherBL voucherBL = new VoucherBL();
	        voucherBL.set(id);

	        VoucherDTO voucher = voucherBL.getEntity();

	        return new Object[][] {
	            {
	                id,
	                createdDateTime,
	                lastModified,
	                voucherStatus.getDescription(),
				
	                entityId,
	                serialNo,
	                pinCode,
	                batchId,
	                productId,
	                
	               	            }
	        };
	    }



}