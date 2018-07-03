package in.saralam.sbs.server.PriceMap;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import com.sapienter.jbilling.server.user.db.CompanyDTO;




public class PriceMapWS implements Serializable{
	
	private Integer id;
	private String mapGroup;
	private String originZone;
	private String destZone;
	private int deleted;
	private String zoneResult;
	private String timeResult;
	private String priceGroup;
	private String description;
	private BigDecimal ratePrice;
	private BigDecimal setUpPrice;
	private String ratingType;
	private Integer priceMapPlan;
	private Date createdDate;
	private Date startDate;
	private Date endDate;
	private Date lastUpdatedDate;
	
	private CompanyDTO entityId;
	private static final Logger LOG = Logger.getLogger(PriceMapWS.class);

	public PriceMapWS() {
		
	}
	
	
	
	public PriceMapWS(Integer id, String mapGroup, String originZone, String destZone, String zoneResult, String timeResult, String priceGroup, String description, BigDecimal ratePrice, BigDecimal setUpPrice,String ratingType, Integer priceMapPlan, Date createdDate, Date startDate, Date endDate, Date lastUpdatedDate,CompanyDTO entityId) {
		LOG.debug("PriceMapws class1....");
		setId(id);
		setMapGroup(mapGroup);
		setOriginZone(originZone);
		setDestZone(destZone);
		setZoneResult(zoneResult);
		setTimeResult(timeResult);
		setPriceGroup(priceGroup);
		setDescription(description);
		setRatePrice(ratePrice);
		setSetUpPrice(setUpPrice);
		setRatingType(ratingType);
		setPriceMapPlan(priceMapPlan);
		setCreatedDate(createdDate);
		setStartDate(startDate);
		setEndDate(endDate);
		setLastUpdatedDate(lastUpdatedDate);
		setEntity(entityId);
	}
	
	public PriceMapWS(String mapGroup, String originZone, String destZone, String zoneResult, String timeResult, String priceGroup, String description, BigDecimal ratePrice, BigDecimal setUpPrice, String ratingType, Integer priceMapPlan, Date createdDate, Date startDate, Date endDate, Date lastUpdatedDate,CompanyDTO entityId) {
		
		LOG.debug("PriceMapws class2....");
		
		setMapGroup(mapGroup);
		setOriginZone(originZone);
		setDestZone(destZone);
		setZoneResult(zoneResult);
		setTimeResult(timeResult);
		setPriceGroup(priceGroup);
		setDescription(description);
		setRatePrice(ratePrice);
		setSetUpPrice(setUpPrice);
		setRatingType(ratingType);
		setPriceMapPlan(priceMapPlan);
		setCreatedDate(createdDate);
		setStartDate(startDate);
		setEndDate(endDate);
		setLastUpdatedDate(lastUpdatedDate);
		setEntity(entityId);
		LOG.debug("values are : " + mapGroup + originZone + destZone + zoneResult + timeResult +  description + ratePrice + setUpPrice + ratingType + createdDate + startDate + lastUpdatedDate);
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
    
    public PriceMapWS(String mapGroup, BigDecimal ratePrice, Date startDate, Date endDate) {
		
		
		setMapGroup(mapGroup);
		setRatePrice(ratePrice);
		setStartDate(startDate);
		setEndDate(endDate);
	}


	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    
	public String getMapGroup() {
		return mapGroup;
	}

	public void setMapGroup(String mapGroup) {
		this.mapGroup = mapGroup;
	}
	
	
	public String getOriginZone() {
		return originZone;
	}

	public void setOriginZone(String originZone) {
		this.originZone = originZone;
	}
	
	
	public String getDestZone() {
		return destZone;
	}

	public void setDestZone(String destZone) {
		this.destZone = destZone;
	}
	
	
	public String getZoneResult() {
		return zoneResult;
	}

	public void setZoneResult(String zoneResult) {
		this.zoneResult = zoneResult;
	}
	
	
	public String getTimeResult() {
		return timeResult;
	}

	public void setTimeResult(String timeResult) {
		this.timeResult = timeResult;
	}
	
	
	public String getPriceGroup() {
		return priceGroup;
	}

	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	
	
	public BigDecimal getRatePrice() {
		return ratePrice;
	}

	public void setRatePrice(BigDecimal ratePrice) {
		this.ratePrice = ratePrice;
	}
	
	
	public BigDecimal getSetUpPrice() {
		return setUpPrice;
	}

	public void setSetUpPrice(BigDecimal setUpPrice) {
		this.setUpPrice = setUpPrice;
	}
	
	
	
	
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	public String getRatingType() {
		return ratingType;
	}

	public void setRatingType(String ratingType) {
		this.ratingType = ratingType;
	}
	public Integer getPriceMapPlan() {
		return priceMapPlan;
	}

	public void setPriceMapPlan(Integer priceMapPlan) {
		this.priceMapPlan = priceMapPlan;
	}
	
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
