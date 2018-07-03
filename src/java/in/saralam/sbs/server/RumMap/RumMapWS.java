package in.saralam.sbs.server.RumMap;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import com.sapienter.jbilling.server.user.db.CompanyDTO;

public class RumMapWS implements Serializable{
	
	 private Integer id;
	private String priceGroup;
	private Integer step;
	private String priceModel;
	private String rum;
	private String resource;
	private Integer resourceId;
	private String rumType;
	private Integer consumeFlag;
	private int deleted;
	private Integer rumMapPlan;
	private Date createdDate;
	private Date lastUpdatedDate;
	private CompanyDTO entityId;   
	
	private static final Logger LOG = Logger.getLogger(RumMapWS.class);

	public RumMapWS() {
		
	}
	
	public RumMapWS(Integer id, String priceGroup, Integer step, String priceModel, String rum, String resource, Integer resourceId, String rumType, Integer consumeFlag, Integer rumMapPlan, Date createdDate,  Date lastUpdatedDate, CompanyDTO entityId) {
		LOG.debug("rummapws class....");
		setId(id);
		setPriceGroup(priceGroup);
		setStep(step);
		setPriceModel(priceModel);
		setRum(rum);
		setResource(resource);
		setResourceId(resourceId);
		setRumType(rumType);
		setConsumeFlag(consumeFlag);
		setRumMapPlan(rumMapPlan);
		setCreatedDate(createdDate);
		setLastUpdatedDate(lastUpdatedDate);
		setEntity(entityId);
	}
	
	public RumMapWS(String priceGroup, Integer step, String priceModel, String rum, String resource, Integer resourceId, String rumType, Integer consumeFlag, Integer rumMapPlan, Date createdDate,  Date lastUpdatedDate, CompanyDTO entityId) {
		
		LOG.debug("rummapws class....");
		
		
		setPriceGroup(priceGroup);
		setStep(step);
		setPriceModel(priceModel);
		setRum(rum);
		setResource(resource);
		setResourceId(resourceId);
		setRumType(rumType);
		setConsumeFlag(consumeFlag);
		setRumMapPlan(rumMapPlan);
		setCreatedDate(createdDate);
		setLastUpdatedDate(lastUpdatedDate);
		setEntity(entityId);
		
		
		LOG.debug("values are : " + priceGroup + step + priceModel + rum + resource + resourceId + rumType + consumeFlag + rumMapPlan + createdDate +  lastUpdatedDate);
	}
	
	/*public RumMapWS(String prefix, String destination, BigDecimal flatRate) {
		
		setPrefix(prefix);
		setDestination(destination);
		setFlatRate(flatRate);
	}*/
	
   /* public RumMapWS(Integer id, String prefix, String destination, BigDecimal flatRate) {
		
		setId(id);
		setPrefix(prefix);
		setDestination(destination);
		setFlatRate(flatRate);
	}*/
    
    public RumMapWS(String priceGroup, Integer step, String priceModel, Integer resourceId, Integer consumeFlag) {
		
		
		setPriceGroup(priceGroup);
		setStep(step);
		setPriceModel(priceModel);
		setResourceId(resourceId);
		setConsumeFlag(consumeFlag);
	}


	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPriceGroup() {
		return priceGroup;
	}

	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public String getPriceModel() {
		return priceModel;
	}

	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}

	public String getRum() {
		return rum;
	}

	public void setRum(String rum) {
		this.rum = rum;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getRumType() {
		return rumType;
	}

	public void setRumType(String rumType) {
		this.rumType = rumType;
	}
	
	public Integer getConsumeFlag() {
		return consumeFlag;
	}

	public void setConsumeFlag(Integer consumeFlag) {
		this.consumeFlag = consumeFlag;
	}
	
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public Integer getRumMapPlan() {
		return rumMapPlan;
	}

	public void setRumMapPlan(Integer rumMapPlan) {
		this.rumMapPlan = rumMapPlan;
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
	
	
	
	public CompanyDTO getEntity() {
		return entityId;
	}
	
	public void setEntity(CompanyDTO entityId) {
		this.entityId = entityId;
	}
}
