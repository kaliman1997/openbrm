package in.saralam.sbs.server.advancepricing.db;

import in.saralam.sbs.server.rating.db.RatingEventTypeDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.process.db.PeriodUnitDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDescription;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;
import in.saralam.sbs.server.openRate.destinationMap.db.DestinationMapDTO;

@Entity
@TableGenerator(
        name="product_charge_rate_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="product_charge_rate",
        allocationSize = 100
        )
@Table(name="product_charge_rate")
public class ProductChargeRateDTO implements Serializable{

	private static final Logger LOG = Logger.getLogger(ProductChargeRateDTO.class);
	
	private int id;
	private ProductChargeDTO productCharge;
	private CurrencyDTO currency;
	private int order;
	private BigDecimal fixedAmount;
	private BigDecimal scaledAmount;
	private PeriodUnitDTO unitId;
	private RateDependeeDTO rateDependee;
	private RumTypeDTO rum;
	private Date activeSince;
	private Date activeUntil;
	private int deleted;
	private int versionNum;
	private DestinationMapDTO destinationMap;
	
	private Set<RatingEventTypeDTO> ratingEvent = new HashSet<RatingEventTypeDTO>(0);
	
	public ProductChargeRateDTO(){
	}
	
	public ProductChargeRateDTO(int id){
		this.id = id;
	}

	public ProductChargeRateDTO(int id, ProductChargeDTO productCharge, CurrencyDTO currency, 
						RateDependeeDTO rateDependee, RumTypeDTO rum, BigDecimal fixedAmount, 
						BigDecimal scaledAmount, PeriodUnitDTO unitId, Date activeSince, Date activeUntil, 
						int versionNum){
		
		this.id = id;
		this.productCharge = productCharge;
		this.currency = currency;
		this.rateDependee = rateDependee;
		this.rum = rum;
		this.fixedAmount = fixedAmount;
		this.scaledAmount = scaledAmount;
		this.unitId = unitId;
		this.activeSince = activeSince;
		this.activeUntil = activeUntil;
		this.versionNum = versionNum;
	}
	
	
	@Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="product_charge_rate_GEN")
    @Column(name="id", unique=true, nullable=false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="charge_id")
	public ProductChargeDTO getProductCharge() {
		return productCharge;
	}

	public void setProductCharge(ProductChargeDTO productCharge) {
		this.productCharge = productCharge;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="currency_id", nullable=false)
	public CurrencyDTO getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyDTO currency) {
		this.currency = currency;
	}
	
	@Column(name="salience", nullable=false)
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Column(name="fixed_amount", nullable=false, precision=10, scale=0)
	public BigDecimal getFixedAmount() {
		return fixedAmount;
	}

	public void setFixedAmount(BigDecimal fixedAmount) {
		this.fixedAmount = fixedAmount;
	}

	@Column(name="scaled_amount", nullable=false, precision=10, scale=0)
	public BigDecimal getScaledAmount() {
		return scaledAmount;
	}

	public void setScaledAmount(BigDecimal scaledAmount) {
		this.scaledAmount = scaledAmount;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="unit_id", nullable=true)
	public PeriodUnitDTO getUnitId() {
		return unitId;
	}

	public void setUnitId(PeriodUnitDTO unitId) {
		this.unitId = unitId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dependee_id")
	public RateDependeeDTO getRateDependee() {
		return rateDependee;
	}

	public void setRateDependee(RateDependeeDTO rateDependee) {
		this.rateDependee = rateDependee;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="rum_id")
	public RumTypeDTO getRum() {
		return rum;
	}

	public void setRum(RumTypeDTO rum) {
		this.rum = rum;
	}

	@Column(name="active_since", length=19)
	public Date getActiveSince() {
		return activeSince;
	}

	public void setActiveSince(Date activeSince) {
		this.activeSince = activeSince;
	}

	@Column(name="active_until", length=19)
	public Date getActiveUntil() {
		return activeUntil;
	}

	public void setActiveUntil(Date activeUntil) {
		this.activeUntil = activeUntil;
	}

	@Column(name="deleted", nullable=false)
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	@Version
    @Column(name="OPTLOCK" , nullable=false)
	public int getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}
	
		@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="destination_map_id")
	public DestinationMapDTO getDestinationMap() {
		return destinationMap;
	}

	public void setDestinationMap(DestinationMapDTO destinationMap) {
		this.destinationMap = destinationMap;
	}
}
