package in.saralam.sbs.server.ConvergentRateCard;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import com.sapienter.jbilling.server.user.db.CompanyDTO;

public class CRateWS implements Serializable{
	
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
	private CompanyDTO entityId;
	private String callType;
	private static final Logger LOG = Logger.getLogger(CRateWS.class);

	public CRateWS() {
		
	}
	
	public CRateWS(Integer id, String prefix, String destination, Integer version, BigDecimal flatRate, BigDecimal conncharge, BigDecimal scaledRate, Integer ratePlan, Date createdDate, Date validFrom, Date validTo, Date lastUpdatedDate,String rateType,CompanyDTO entityId,String callType) {
		LOG.debug("cratews class....");
		setId(id);
		setPrefix(prefix);
		setDestination(destination);
		setVersion(version);
		setFlatRate(flatRate);
		setconncharge(conncharge);
		setscaledRate(scaledRate);
		setratePlan(ratePlan);
		setCreatedDate(createdDate);
		setValidFrom(validFrom);
		setValidTo(validTo);
		setLastUpdatedDate(lastUpdatedDate);
		setRateType(rateType);
		setEntity(entityId);
		setCallType(callType);
	}
	
	public CRateWS(String prefix, String destination, BigDecimal flatRate, BigDecimal conncharge, BigDecimal scaledRate, Integer ratePlan, Date createdDate, Date validFrom, Date validTo, Date lastUpdatedDate,String rateType,CompanyDTO entityId,String callType) {
		
		LOG.debug("cratews class....");
		
		setPrefix(prefix);
		setDestination(destination);
		setFlatRate(flatRate);
		setconncharge(conncharge);
		setscaledRate(scaledRate);
		setratePlan(ratePlan);
		setCreatedDate(createdDate);
		setValidFrom(validFrom);
		setValidTo(validTo);
		setLastUpdatedDate(lastUpdatedDate);
		setRateType(rateType);
		setEntity(entityId);
		setCallType(callType);
		LOG.debug("values are : " + prefix + destination + flatRate + conncharge + scaledRate + createdDate + validFrom + lastUpdatedDate);
	}
	
	/*public RateWS(String prefix, String destination, BigDecimal flatRate) {
		
		setPrefix(prefix);
		setDestination(destination);
		setFlatRate(flatRate);
	}*/
	
   /* public RateWS(Integer id, String prefix, String destination, BigDecimal flatRate) {
		
		setId(id);
		setPrefix(prefix);
		setDestination(destination);
		setFlatRate(flatRate);
	}*/
    
    public CRateWS(String prefix, BigDecimal flatRate, Date validFrom, Date validTo) {
		
		
		setPrefix(prefix);
		setFlatRate(flatRate);
		setValidFrom(validFrom);
		setValidTo(validTo);
	}


	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public BigDecimal getFlatRate() {
		return flatRate;
	}

	public void setFlatRate(BigDecimal flatRate) {
		this.flatRate = flatRate;
	}
	public BigDecimal getconncharge() {
		return conncharge;
	}

	public void setconncharge(BigDecimal conncharge) {
		this.conncharge = conncharge;
	}
	public BigDecimal getscaledRate() {
		return scaledRate;
	}

	public void setscaledRate(BigDecimal scaledRate) {
		this.scaledRate = scaledRate;
	}

	public Integer getratePlan() {
		return ratePlan;
	}

	public void setratePlan(Integer ratePlan) {
		this.ratePlan = ratePlan;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	
	public CompanyDTO getEntity() {
		return entityId;
	}
	
	public void setEntity(CompanyDTO entityId) {
		this.entityId = entityId;
	}
	
	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}
}
