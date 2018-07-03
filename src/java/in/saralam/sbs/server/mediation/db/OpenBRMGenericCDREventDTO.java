package in.saralam.sbs.server.mediation.db; 

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import org.apache.log4j.Logger;
import java.math.BigDecimal;

@Entity
@TableGenerator(
        name="generic_cdr_event_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="generic_cdr_event",
        allocationSize=100
        )
@Table(name = "generic_cdr_event")
public class OpenBRMGenericCDREventDTO implements Serializable {
    private static final Logger LOG = Logger.getLogger(OpenBRMGenericCDREventDTO.class);

    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="generic_cdr_event")
    private Integer id;

    @Column(name = "processId", nullable = false)
    private Integer processId;

    @Column(name = "record_id", nullable = false)
    private String recordId;

    @Column(name = "orderId", nullable = false)
    private Integer orderId;

    @Column(name = "userId", nullable = false)
    private Integer userId;

    @Column(name = "invoiceId")
    private Integer invoiceId;

    @Column(name = "src")
    private String src;

    @Column(name = "dst")
    private String dst;
    
    @Column(name = "account_code")
    private String accountCode;

    @Column(name = "dcontext")
    private String dcontext;

    @Column(name = "clid")
    private String clid;

    @Column(name = "channel")
    private String channel;
    
    @Column(name = "lastapp")
    private String lastapp;
    
    @Column(name = "lastdata")
    private String lastdata;
    
    @Column(name = "dstchannel")
    private String dstchannel;

    @Column(name = "start_date")
    private Date  startDate;

    @Column(name = "end_date")
    private Date endDate;
    
    @Column(name = "answer")
    private Date answer;

    @Column(name = "duration")
    private Integer totalDuration;
    
    @Column(name = "billsec")
    private Integer billsec;

    @Column(name = "disposition")
    private String disposition;

    @Column(name = "amaflags")
    private String amaflags;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "record_description")
    private String description;

    @Column(name = "charge_amount")
    private BigDecimal chargeAmount;

   

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "rating_result")
    private Integer ratingResult;
	
	@Column(name = "peeraccount")
	private String peerAccount;
	
	@Column(name = "linkedid")
	private String linkedId;
	
	@Column(name = "sequence")
	private Integer sequence;
	
	@Column(name = "jbilling_timestamp")
	private Date jbillingTimeStamp;

	@Column(name = "rate_id")
	private Integer rateId;
	
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
	
	public void setRateId(Integer rateId) {
        this.rateId = rateId;
    }

    public Integer getRateId() {
        return rateId;
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
  
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration= totalDuration;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }


    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId= recordId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

   

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setRatingResult(Integer ratingResult) {
        this.ratingResult = ratingResult;
    }

    public Integer getRatingResult() {
        return ratingResult;
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
	
	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getDcontext() {
		return dcontext;
	}

	public void setDcontext(String dcontext) {
		this.dcontext = dcontext;
	}

	public String getClid() {
		return clid;
	}

	public void setClid(String clid) {
		this.clid = clid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getLastapp() {
		return lastapp;
	}

	public void setLastapp(String lastapp) {
		this.lastapp = lastapp;
	}

	public String getLastdata() {
		return lastdata;
	}

	public void setLastdata(String lastdata) {
		this.lastdata = lastdata;
	}

	public String getDstchannel() {
		return dstchannel;
	}

	public void setDstchannel(String dstchannel) {
		this.dstchannel = dstchannel;
	}

	public Date getAnswer() {
		return answer;
	}

	public void setAnswer(Date answer) {
		this.answer = answer;
	}

	public Integer getBillsec() {
		return billsec;
	}

	public void setBillsec(Integer billsec) {
		this.billsec = billsec;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public String getAmaflags() {
		return amaflags;
	}

	public void setAmaflags(String amaflags) {
		this.amaflags = amaflags;
	}

	public void setPeerAccount(String peerAccount){
		this.peerAccount = peerAccount;
	}
	
	public String getPeerAccount(){
		return peerAccount;
	}

	public void setLinkedId(String linkedId){
		this.linkedId = linkedId;
	}
	
	public String getLinkedId(){
		return linkedId;
	}


	public void setSequence(Integer sequence){
		this.sequence = sequence;
	}
	
	public Integer getSequence(){
		return sequence;
	}


	public void setJbillingTimeStamp(Date jbillingTimeStamp){
		this.jbillingTimeStamp = jbillingTimeStamp;
	}
	
	public Date getJbillingTimeStamp(){
		return jbillingTimeStamp;
	}

}
