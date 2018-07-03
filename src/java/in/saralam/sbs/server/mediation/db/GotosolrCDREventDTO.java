package in.saralam.sbs.server.mediation.db; 

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import org.apache.log4j.Logger;
import java.math.BigDecimal;

@Entity
@TableGenerator(
        name="ob_rated_cdr_record_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="ob_rated_cdr_record",
        allocationSize=100
        )
@Table(name = "ob_rated_cdr_record")
public class GotosolrCDREventDTO implements Serializable {
    private static final Logger LOG = Logger.getLogger(GotosolrCDREventDTO.class);

    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="ob_rated_cdr_record_GEN")
    private Integer id;

    @Column(name = "process_id", nullable = false)
    private Integer processId;

    @Column(name = "record_id", nullable = false)
    private String recordId;

    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "invoice_id")
    private Integer invoiceId;

    @Column(name = "calling_number")
    private String src;

    @Column(name = "destination_number")
    private String dst;
    
   @Column(name = "call_start_date")
    private Date  callStartDate;

    @Column(name = "call_end_date")
    private Date callEndDate;
    
   @Column(name = "duration")
    private Integer duration;
    
    
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name="product_id")
    private Integer productId;

    @Column(name="destination_descr")
    private String destinationDescr;

    @Column(name="rate_id")
    private Integer rateId;
	
	@Column(name = "call_type")
    private String callType;
    
	@Column(name = "cdr_source")
    private String cdrSource;
	
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
	
	

   public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getProcessId() {
        return processId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }
  
    public void setCallStartDate(Date callStartDate) {
        this.callStartDate = callStartDate;
    }

    public Date getCallStartDate() {
        return callStartDate;
    }
    public void setCallEndDate(Date callEndDate) {
        this.callEndDate =callEndDate;
    }

    public Date getCallEndDate() {
        return callEndDate;
    }
    public void setDuration(Integer duration) {
        this.duration= duration;
    }

    public Integer Duration() {
        return duration;
    }


    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId= recordId;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getCost() {
        return cost;
    }

    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

   
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public void setDestinationDescr(String descr) {
		this.destinationDescr = descr;

 	}

	public String getDestinationDescr() {
		return this.destinationDescr;

	}
	

	public void setRateId (Integer id) {
		this.rateId = id;

	}

	public Integer getRateId () {

		return this.rateId;
	}
	
	public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType= callType;
    }
	
	public String getCdrSource() {
        return cdrSource;
    }

    public void setCdrSource(String cdrSource) {
        this.cdrSource= cdrSource;
    }
   
	
}

