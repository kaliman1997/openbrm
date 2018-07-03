package in.saralam.sbs.server.PriceModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import com.sapienter.jbilling.server.user.db.CompanyDTO;

public class PriceModelWS implements Serializable{
	
	private Integer id;
	private String priceModel;
	private Integer qtyStep;
	private Integer tierFrom;
	private Integer tierTo;
	private BigDecimal beat;
	private BigDecimal factor;
	private Integer chargeBase;
	private int deleted;
	private Integer priceModelPlan;
	private Date createdDate;
	private Date lastUpdatedDate;
	private CompanyDTO entityId;
	private static final Logger LOG = Logger.getLogger(PriceModelWS.class);

	public PriceModelWS() {
		
	}
	
	public PriceModelWS(Integer id, String priceModel, Integer qtyStep, Integer tierFrom, Integer  tierTo, BigDecimal beat, BigDecimal factor, Integer chargeBase, Integer priceModelPlan, Date createdDate, Date lastUpdatedDate, CompanyDTO entityId) {
		LOG.debug("pricemodelws class....");
		setId(id);
		setPriceModel(priceModel);
		setQtyStep(qtyStep);
		setTierFrom(tierFrom);
		setTierTo(tierTo);
		setBeat(beat);
		setFactor(factor);
		setChargeBase(chargeBase);
		setPriceModelPlan(priceModelPlan);
		setCreatedDate(createdDate);
		setLastUpdatedDate(lastUpdatedDate);
		setEntity(entityId);
	}
	
	public PriceModelWS(String priceModel, Integer qtyStep, Integer tierFrom, Integer  tierTo, BigDecimal beat, BigDecimal factor, Integer chargeBase, Integer priceModelPlan, Date createdDate, Date lastUpdatedDate, CompanyDTO entityId) {
		
		LOG.debug("pricemodelws class....");
		
		
		setPriceModel(priceModel);
		setQtyStep(qtyStep);
		setTierFrom(tierFrom);
		setTierTo(tierTo);
		setBeat(beat);
		setFactor(factor);
		setChargeBase(chargeBase);
		setPriceModelPlan(priceModelPlan);
		setCreatedDate(createdDate);
		setLastUpdatedDate(lastUpdatedDate);
		setEntity(entityId);
		
		
		LOG.debug("values are : " + priceModel + qtyStep + tierFrom + tierTo + beat + factor + chargeBase + createdDate +  lastUpdatedDate);
	}
	
	/*public PriceModelWS(String prefix, String destination, BigDecimal flatRate) {
		
		setPrefix(prefix);
		setDestination(destination);
		setFlatRate(flatRate);
	}*/
	
   /* public PriceModelWS(Integer id, String prefix, String destination, BigDecimal flatRate) {
		
		setId(id);
		setPrefix(prefix);
		setDestination(destination);
		setFlatRate(flatRate);
	}*/
    
    public PriceModelWS(String priceModel, Integer qtyStep, BigDecimal factor, Integer tierFrom, Integer tierTo) {
		
		
		setPriceModel(priceModel);
		setQtyStep(qtyStep);
		setFactor(factor);
		setTierFrom(tierFrom);
		setTierTo(tierTo);
	}


	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPriceModel() {
		return priceModel;
	}

	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}

	public Integer getQtyStep() {
		return qtyStep;
	}

	public void setQtyStep(Integer qtyStep) {
		this.qtyStep = qtyStep;
	}

	public Integer getTierFrom() {
		return tierFrom;
	}

	public void setTierFrom(Integer tierFrom) {
		this.tierFrom = tierFrom;
	}

	public Integer getTierTo() {
		return tierTo;
	}

	public void setTierTo(Integer tierTo) {
		this.tierTo = tierTo;
	}

	public BigDecimal getBeat() {
		return beat;
	}

	public void setBeat(BigDecimal beat) {
		this.beat = beat;
	}
	public BigDecimal getFactor() {
		return factor;
	}

	public void setFactor(BigDecimal factor) {
		this.factor = factor;
	}
	public Integer getChargeBase() {
		return chargeBase;
	}

	public void setChargeBase(Integer chargeBase) {
		this.chargeBase = chargeBase;
	}
	
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public Integer getPriceModelPlan() {
		return priceModelPlan;
	}

	public void setPriceModelPlan(Integer priceModelPlan) {
		this.priceModelPlan = priceModelPlan;
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
