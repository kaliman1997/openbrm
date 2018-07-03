package in.saralam.sbs.server.PriceMap.db;

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
		name="price_map_GEN",
		table="jbilling_seqs",
		pkColumnName = "name",
		valueColumnName = "next_id",
		pkColumnValue = "price_map",
		allocationSize = 100
		)
@Table(name="price_map")
public class PriceMapDTO implements Serializable{
	
	private Integer id;
	private String mapGroup;
	private String originZone;
	private String destZone;
	private String zoneResult;
	private String timeResult;
	private String priceGroup;
	private String description;
	private BigDecimal ratePrice;
	private BigDecimal setUpPrice;
	private int deleted;
	private String ratingType;
	private Integer priceMapPlan;
	private Date createdDate;
	private Date startDate;
	private Date endDate;
	private Date lastUpdatedDate;
	public ItemDTO item;
	private CompanyDTO entity;
	
	
	private static final Logger LOG = Logger.getLogger(PriceMapDTO.class);
	
	public PriceMapDTO(){
		
	}
	

	public PriceMapDTO(Integer id, String mapGroup, String originZone, String destZone, String zoneResult, String timeResult, String priceGroup, String description, BigDecimal ratePrice, BigDecimal setUpPrice, Integer deleted, String ratingType, Integer priceMapPlan, Date createdDate, Date startDate, Date endDate, Date lastUpdatedDate) {
		
		this.id = id;
		this.mapGroup = mapGroup;
		this.originZone = originZone;
		this.destZone = destZone;
		this.zoneResult = zoneResult;
		this.timeResult = timeResult;
		this.priceGroup = priceGroup;
		this.description = description;
	    this.ratePrice = ratePrice;
		this.setUpPrice = setUpPrice;
		this.deleted = deleted;
		this.ratingType= ratingType;
		this.priceMapPlan = priceMapPlan;
		this.createdDate = createdDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.lastUpdatedDate = lastUpdatedDate;
		
		
	}
	
	public PriceMapDTO(String mapGroup, String originZone, String destZone, String zoneResult, String timeResult, String priceGroup, String description, BigDecimal ratePrice, BigDecimal setUpPrice, String ratingType, Date createdDate, Date startDate, Date endDate, Date lastUpdatedDate) {
		
		this.mapGroup = mapGroup;
		this.originZone = originZone;
		this.destZone = destZone;
		this.zoneResult = zoneResult;
		this.timeResult = timeResult;
		this.priceGroup = priceGroup;
		this.description = description;
	    this.ratePrice = ratePrice;
		this.setUpPrice = setUpPrice;
		this.ratingType= ratingType;
		this.createdDate = createdDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
    public PriceMapDTO(Integer id, String mapGroup, String originZone, String destZone, String zoneResult, String timeResult, String priceGroup, String description, BigDecimal ratePrice, BigDecimal setUpPrice,  String ratingType, Date startDate, Date endDate) {
		
		this.id = id;
		this.mapGroup = mapGroup;
		this.originZone = originZone;
		this.destZone = destZone;
		this.zoneResult = zoneResult;
		this.timeResult = timeResult;
		this.priceGroup = priceGroup;
		this.description = description;
	    this.ratePrice = ratePrice;
		this.setUpPrice = setUpPrice;
		this.ratingType= ratingType;
		this.startDate = startDate;
		this.endDate = endDate;
		
		LOG.debug("PriceMapDTO constructor is created with " +id+","+mapGroup+","+originZone+","+destZone+","+zoneResult+","+timeResult+","+ priceGroup + ","+description+","+ratePrice+","+setUpPrice+","+ratingType+","+createdDate+","+","+startDate+","+endDate+","+lastUpdatedDate);
	}
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="price_map_GEN")
	@Column(name="id", unique=true, nullable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    @Column(name="map_group", length=24)
	public String getMapGroup() {
		return mapGroup;
	}

	public void setMapGroup(String mapGroup) {
		this.mapGroup = mapGroup;
	}
	
	@Column(name="origin_zone", length=10)
	public String getOriginZone() {
		return originZone;
	}

	public void setOriginZone(String originZone) {
		this.originZone = originZone;
	}
	
	@Column(name="dest_zone", length=10)
	public String getDestZone() {
		return destZone;
	}

	public void setDestZone(String destZone) {
		this.destZone = destZone;
	}
	
	@Column(name="zone_result", length=64)
	public String getZoneResult() {
		return zoneResult;
	}

	public void setZoneResult(String zoneResult) {
		this.zoneResult = zoneResult;
	}
	
	@Column(name="time_result", length=24)
	public String getTimeResult() {
		return timeResult;
	}

	public void setTimeResult(String timeResult) {
		this.timeResult = timeResult;
	}
	
	@Column(name="price_group", length=64)
	public String getPriceGroup() {
		return priceGroup;
	}

	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}
	
	@Column(name="description", length=64)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	
	@Column(name="rate_price")
	public BigDecimal getRatePrice() {
		if (ratePrice == null) {
			return BigDecimal.ZERO;
		}
		return ratePrice;
	}

	public void setRatePrice(BigDecimal ratePrice) {
		this.ratePrice = ratePrice;
	}
	
	@Column(name="setup_price")
	public BigDecimal getSetUpPrice() {
		if (setUpPrice == null) {
			return BigDecimal.ZERO;
		}
		return setUpPrice;
	}

	public void setSetUpPrice(BigDecimal setUpPrice) {
		this.setUpPrice = setUpPrice;
	}
	
	
	
	@Column(name="deleted", nullable=false)
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	@Column(name="rating_type", length=64)
	public String getRatingType() {
		return ratingType;
	}

	public void setRatingType(String ratingType) {
		this.ratingType = ratingType;
	}
	
	@Column(name="price_map_plan")
	public Integer getPriceMapPlan() {
		return priceMapPlan;
	}

	public void setPriceMapPlan(Integer priceMapPlan) {
		this.priceMapPlan = priceMapPlan;
	}
	
	
	
	@Column(name="created_date", length=19, nullable=false)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name="start_date", length=19, nullable=false)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name="end_date", length=19)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Column(name="last_updated_date", length=19, nullable=false)
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id")
    public CompanyDTO getEntity() {
        return this.entity;
    }

    public void setEntity(CompanyDTO entity) {
        this.entity = entity;
    }

	
	public String toString(){
		StringBuffer str = new StringBuffer("order = " +
	       "id=" + id + "," +
			"mapGroup=" + mapGroup + "," +
			"originZone=" + originZone + "," +
			"destZone=" + destZone + "," +
			"zoneResult+=" + zoneResult+ "," +
			"timeResult=" +timeResult + "," +
			"description=" + description + "," +
			"ratePrice=" + ratePrice+ "," +
			"setupPrice=" + setUpPrice + "," +
			"ratingType =" + ratingType + "," +
			"deleted=" + deleted + "," +
			"priceMapPlan=" + priceMapPlan + "," +
			"createdDate=" + createdDate + "," +
			"startDate=" + startDate + "," +
			"endDate=" + endDate + ","+
			"lastUpdatedDate=" + lastUpdatedDate
			);
		   return str.toString();
	}
	

}
