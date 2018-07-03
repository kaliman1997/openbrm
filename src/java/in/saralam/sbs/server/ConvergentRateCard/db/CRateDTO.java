package in.saralam.sbs.server.ConvergentRateCard.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.user.db.CompanyDTO;


import org.apache.log4j.Logger;


@Entity
@TableGenerator(
		name="c_rate_GEN",
		table="jbilling_seqs",
		pkColumnName = "name",
		valueColumnName = "next_id",
		pkColumnValue = "rate",
		allocationSize = 100
		)
@Table(name="c_rate")
public class CRateDTO implements Serializable{
	
	private Integer id;
	private String prefix;
	private String destination;
	private Integer version;
	private int deleted;
	private BigDecimal flatRate;
	private BigDecimal conncharge;
	private BigDecimal scaledRate;
	private Integer ratePlan;
	private Date createdDate;
	private Date validFrom;
	private Date validTo;
	private Date lastUpdatedDate;
	private String rateType;
	public ItemDTO item;
	private CompanyDTO entity;
	private String callType;
	
	private static final Logger LOG = Logger.getLogger(CRateDTO.class);
	
	public CRateDTO(){
		
	}
	

	public CRateDTO(Integer id, String prefix, String destination, Integer version, Integer deleted, BigDecimal flatRate, BigDecimal conncharge, BigDecimal scaledRate,Integer ratePlan, Date createdDate, Date validFrom, Date validTo, Date lastUpdatedDate,String rateType,String callType) {
		
		this.id = id;
		this.prefix= prefix;
		this.destination = destination;
		this.version = version;
		this.deleted = deleted;
		this.flatRate = flatRate;
		this.conncharge = conncharge;
	    this.scaledRate = scaledRate;
		this.ratePlan= ratePlan;
		this.createdDate = createdDate;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.lastUpdatedDate = lastUpdatedDate;
		this.rateType=rateType;
		this.callType=callType;
	}
	
	public CRateDTO(String prefix, String destination, BigDecimal flatRate,BigDecimal conncharge, BigDecimal scaledRate, Date createdDate, Date validFrom, Date validTo, Date lastUpdatedDate) {
		
		this.prefix= prefix;
		this.destination = destination;
		this.flatRate = flatRate;
		this.conncharge = conncharge;
	    this.scaledRate = scaledRate;
		this.createdDate = createdDate;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
public CRateDTO(Integer id, String prefix, String destination, BigDecimal flatRate, BigDecimal conncharge, BigDecimal scaledRate, Date validFrom, Date validTo,String rateType, String callType) {
		
		this.id = id;
		this.prefix= prefix;
		this.destination = destination;
		this.flatRate = flatRate;
		this.conncharge = conncharge;
	    this.scaledRate = scaledRate;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.rateType=rateType;
		this.callType=callType;
		LOG.debug("CRateDTO constructor is created with " +id+","+prefix+","+destination+","+version+","+flatRate+","+ratePlan+","+conncharge+","+scaledRate+","+createdDate+","+validFrom+","+validTo+","+lastUpdatedDate );
	}
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="c_rate_GEN")
	@Column(name="id", unique=true, nullable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    @Column(name="prefix", length=45)
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Column(name="destination", length=45)
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	@Column(name="version", nullable=false, length=6)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name="deleted", nullable=false)
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	@Column(name="flat_rate")
	public BigDecimal getFlatRate() {
		if (flatRate == null) {
			return BigDecimal.ZERO;
		}
		return flatRate;
	}

	public void setFlatRate(BigDecimal flatRate) {
		this.flatRate = flatRate;
	}
@Column(name="conn_charge")
	public BigDecimal getconncharge() {
		if (conncharge == null) {
			return BigDecimal.ZERO;
		}
		return conncharge;
	}

	public void setconncharge(BigDecimal conncharge) {
		this.conncharge = conncharge;
	}
	@Column(name="scaled_rate")
	public BigDecimal getscaledRate() {
		if (scaledRate == null) {
			return BigDecimal.ZERO;
		}
		return scaledRate;
	}

	public void setscaledRate(BigDecimal scaledRate) {
		this.scaledRate = scaledRate;
	}
	@Column(name="rate_plan")
	public Integer getratePlan() {
		return ratePlan;
	}

	public void setratePlan(Integer ratePlan) {
		this.ratePlan = ratePlan;
	}

	@Column(name="created_date", length=19, nullable=false)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="valid_from", length=19, nullable=false)
	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	@Column(name="valid_to", length=19)
	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	@Column(name="last_updated_date", length=19, nullable=false)
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	@Column(name="rate_type", length=45)
		public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id")
    public CompanyDTO getEntity() {
        return this.entity;
    }

    public void setEntity(CompanyDTO entity) {
        this.entity = entity;
    }
	
	@Column(name="call_type", length=40)
		public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}
	
	public String toString(){
		StringBuffer str = new StringBuffer("order = " +
	       "id=" + id + "," +
			"prefix=" + prefix + "," +
			"destination=" + destination + "," +
			"version=" + version + "," +
			"deleted=" + deleted + "," +
			"flatRate=" + flatRate + "," +
			"connectionCharge=" + conncharge + "," +
			"scaledRate=" + scaledRate + "," +
			"ratePlan=" + ratePlan + "," +
			"createdDate=" + createdDate + "," +
			"validFrom=" + validFrom + "," +
			"validTo=" + validTo + "," +
			"lastUpdatedDate=" + lastUpdatedDate
				);
		   return str.toString();
	}
	

}
