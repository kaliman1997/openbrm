package in.saralam.sbs.server.advancepricing;

import in.saralam.sbs.server.advancepricing.db.ProductChargeDTO;
import in.saralam.sbs.server.advancepricing.db.RateDependeeDTO;
import in.saralam.sbs.server.advancepricing.db.RumTypeDTO;
import in.saralam.sbs.server.rating.db.RatingEventTypeDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import in.saralam.sbs.server.openRate.destinationMap.db.DestinationMapDTO;
import com.sapienter.jbilling.server.process.db.PeriodUnitDTO;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;

public class ProductChargeRateWS implements Serializable{

	private int id;
	
	private ProductChargeDTO productCharge;
	private CurrencyDTO currency;
	private int order;
	private BigDecimal fixedAmount;
	private BigDecimal scaledAmount;
	private PeriodUnitDTO unitId;
	private int unit;
	private RateDependeeDTO rateDependee;
	private RumTypeDTO rum;
	private Date activeSince;
	private Date activeUntil;
	private int deleted;
	private int versionNum;
	private RateDependeeWS rateDependeeWS;
	private RatingEventTypeDTO ratingEvent;
	private DestinationMapDTO destinationMap;
       
	
	public ProductChargeRateWS(){
	}
	
	public ProductChargeRateWS(int id, ProductChargeDTO productCharge, CurrencyDTO currency, 
						RateDependeeDTO rateDependee, RumTypeDTO rum, BigDecimal fixedAmount, 
						BigDecimal scaledAmount, PeriodUnitDTO unitId, Date activeSince, Date activeUntil, 
						int versionNum, int deleted){
		
		setId(id);
		setProductCharge(productCharge);
		setCurrency(currency);
		setRateDependee(rateDependee);
		setRum(rum);
		setFixedAmount(fixedAmount);
		setScaledAmount(scaledAmount);
		setUnitId(unitId);
		setActiveSince(activeSince);
		setActiveUntil(activeUntil);
		setVersionNum(versionNum);
		setDeleted(deleted);
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ProductChargeDTO getProductCharge() {
		return productCharge;
	}
	public void setProductCharge(ProductChargeDTO productCharge) {
		this.productCharge = productCharge;
	}
	public CurrencyDTO getCurrency() {
		return currency;
	}
	public void setCurrency(CurrencyDTO currency) {
		this.currency = currency;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public BigDecimal getFixedAmount() {
		return fixedAmount;
	}
	public void setFixedAmount(BigDecimal fixedAmount) {
		this.fixedAmount = fixedAmount;
	}
	public BigDecimal getScaledAmount() {
		return scaledAmount;
	}
	public void setScaledAmount(BigDecimal scaledAmount) {
		this.scaledAmount = scaledAmount;
	}
	public PeriodUnitDTO getUnitId() {
		return unitId;
	}
	public void setUnitId(PeriodUnitDTO unitId) {
		this.unitId = unitId;
	}
	public void setUnitId(int unitId) {
		this.unit = unit;
	}
	public RateDependeeDTO getRateDependee() {
		return rateDependee;
	}
	public void setRateDependee(RateDependeeDTO rateDependee) {
		this.rateDependee = rateDependee;
	}
	public RumTypeDTO getRum() {
		return rum;
	}
	public void setRum(RumTypeDTO rum) {
		this.rum = rum;
	}
	public Date getActiveSince() {
		return activeSince;
	}
	public void setActiveSince(Date activeSince) {
		this.activeSince = activeSince;
	}
	public Date getActiveUntil() {
		return activeUntil;
	}
	public void setActiveUntil(Date activeUntil) {
		this.activeUntil = activeUntil;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public int getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}

	public RateDependeeWS getRateDependeeWS() {
		return rateDependeeWS;
	}

	public void setRateDependeeWS(RateDependeeWS rateDependeeWS) {
		this.rateDependeeWS = rateDependeeWS;
	}

	public RatingEventTypeDTO getRatingEvent() {
		return ratingEvent;
	}

	public void setRatingEvent(RatingEventTypeDTO ratingEvent) {
		this.ratingEvent = ratingEvent;
	}
        
	public DestinationMapDTO getDestinationMap() {
		return destinationMap;
	}

	public void setDestinationMap(DestinationMapDTO destinationMap) {
		this.destinationMap = destinationMap;
	}
	
}
