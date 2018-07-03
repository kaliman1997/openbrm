package in.saralam.sbs.server.Exclusions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.sapienter.jbilling.server.user.db.CompanyDTO;

import org.apache.log4j.Logger;

public class ExclusionWS implements Serializable{
	
	private Integer id;
	private String prefix;
	private String destination;
	private String field1;
	private String field2;
	private Integer version;
	private int deleted;
	private Integer ratePlan;
	private Date createdDate;
	private Date validFrom;
	private Date validTo;
	private Date lastUpdatedDate;
	private CompanyDTO entityId;
	private static final Logger LOG = Logger.getLogger(ExclusionWS.class);

	public ExclusionWS() {
		
	}
	
	public ExclusionWS(Integer id, String prefix, String destination, Integer version,String field1,String field2, Integer ratePlan, Date createdDate, Date validFrom, Date validTo, Date lastUpdatedDate, CompanyDTO entityId) {
		LOG.debug("ExclusionWS class....");
		setId(id);
		setPrefix(prefix);
		setDestination(destination);
		setVersion(version);
		setField1(field1);
		setField2(field2);
		setratePlan(ratePlan);
		setCreatedDate(createdDate);
		setValidFrom(validFrom);
		setValidTo(validTo);
		setLastUpdatedDate(lastUpdatedDate);
		setEntity(entityId);
	}
	
	public ExclusionWS(String prefix, String destination,String field1,String field2, Integer ratePlan, Date createdDate, Date validFrom, Date validTo, Date lastUpdatedDate, CompanyDTO entityId) {
		
		LOG.debug("ratews class....");
		
		setPrefix(prefix);
		setDestination(destination);
		setField1(field1);
		setField2(field2);
		setratePlan(ratePlan);
		setCreatedDate(createdDate);
		setValidFrom(validFrom);
		setValidTo(validTo);
		setLastUpdatedDate(lastUpdatedDate);
		setEntity(entityId);
		
		LOG.debug("values are : " + prefix + destination+  field1+ field2 + ratePlan+ createdDate + validFrom + lastUpdatedDate);
	}
	
	
    
    public ExclusionWS(String prefix, Date validFrom, Date validTo) {
		
		
		setPrefix(prefix);
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

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}
	
	public String getField2() {
		return field1;
	}

	public void setField2(String field2) {
		this.field2 = field2;
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
	
	public CompanyDTO getEntity() {
                return entityId;
        }

        public void setEntity(CompanyDTO entityId) {
                this.entityId = entityId;
        }
	
	
}
