package in.saralam.sbs.server.PriceModel.db;

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
		name="price_GEN",
		table="jbilling_seqs",
		pkColumnName = "name",
		valueColumnName = "next_id",
		pkColumnValue = "price_model",
		allocationSize = 100
		)
@Table(name="price_model")
public class PriceModelDTO implements Serializable{
	
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
	public ItemDTO item;
	private CompanyDTO entity;
	
	
	private static final Logger LOG = Logger.getLogger(PriceModelDTO.class);
	
	public PriceModelDTO(){
		
	}
	

	public PriceModelDTO(Integer id, String priceModel, Integer qtyStep, Integer tierFrom, Integer  tierTo, BigDecimal beat, BigDecimal factor, Integer chargeBase, Integer deleted, Integer priceModelPlan, Date createdDate, Date lastUpdatedDate) {
		
		this.id = id;
		this.priceModel = priceModel;
		this.qtyStep = qtyStep;
		this.tierFrom = tierFrom;
		this.tierTo = tierTo;
		this.beat = beat;
		this.factor = factor;
	    this.chargeBase = chargeBase;
		this.deleted = deleted;
		this.priceModelPlan= priceModelPlan;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
		
		
	}
	
	public PriceModelDTO(String priceModel, Integer qtyStep, Integer tierFrom, Integer  tierTo, BigDecimal beat, BigDecimal factor, Integer chargeBase, Date createdDate, Date lastUpdatedDate) {
		
		this.priceModel = priceModel;
		this.qtyStep = qtyStep;
		this.tierFrom = tierFrom;
		this.tierTo = tierTo;
		this.beat = beat;
		this.factor = factor;
	    this.chargeBase = chargeBase;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
    public PriceModelDTO(Integer id, String priceModel, Integer qtyStep, Integer tierFrom, Integer  tierTo, BigDecimal beat, BigDecimal factor,Integer chargeBase) {
		
		this.id = id;
		this.priceModel = priceModel;
		this.qtyStep = qtyStep;
		this.tierFrom = tierFrom;
		this.tierTo = tierTo;
		this.beat = beat;
		this.factor = factor;
	    this.chargeBase = chargeBase;
		LOG.debug("PriceModelDTO constructor is created with " +id+","+priceModel+","+qtyStep+","+tierFrom+","+tierTo+","+beat+","+factor+","+chargeBase+","+priceModelPlan+","+createdDate+","+","+lastUpdatedDate);
	}
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="price_GEN")
	@Column(name="id", unique=true, nullable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    @Column(name="price_model", length=45)
	public String getPriceModel() {
		return priceModel;
	}

	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}

	@Column(name="qty_step" , nullable=false)
	public Integer getQtyStep() {
		return qtyStep;
	}

	public void setQtyStep(Integer qtyStep) {
		this.qtyStep = qtyStep;
	}
	
	@Column(name="tier_from", nullable=false)
	public Integer getTierFrom() {
		return tierFrom;
	}

	public void setTierFrom(Integer tierFrom) {
		this.tierFrom = tierFrom;
	}
	
	

	@Column(name="tier_to", nullable=false)
	public Integer getTierTo() {
		return tierTo;
	}

	public void setTierTo(Integer tierTo) {
		this.tierTo = tierTo;
	}

	
/*@Column(name="conn_charge")
	public BigDecimal getconncharge() {
		if (conncharge == null) {
			return BigDecimal.ZERO;
		}
		return conncharge;
	}

	public void setconncharge(BigDecimal conncharge) {
		this.conncharge = conncharge;
	}*/
	@Column(name="beat")
	public BigDecimal getBeat() {
		if (beat == null) {
			return BigDecimal.ZERO;
		}
		return beat;
	}

	public void setBeat(BigDecimal beat) {
		this.beat = beat;
	}
	
	@Column(name="factor")
	public BigDecimal getFactor() {
		if (factor == null) {
			return BigDecimal.ZERO;
		}
		return factor;
	}

	public void setFactor(BigDecimal factor) {
		this.factor = factor;
	}
	
	@Column(name="charge_base")
	public Integer getChargeBase() {
		return chargeBase;
	}

	public void setChargeBase(Integer chargeBase) {
		this.chargeBase = chargeBase;
	}
	
	@Column(name="deleted", nullable=false)
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	@Column(name="price_plan")
	public Integer getPriceModelPlan() {
		return priceModelPlan;
	}

	public void setPriceModelPlan(Integer priceModelPlan) {
		this.priceModelPlan = priceModelPlan;
	}
	
	@Column(name="created_date", length=19, nullable=false)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
			"priceModel=" + priceModel + "," +
			"qtyStep=" + qtyStep + "," +
			"tierFrom=" + tierFrom + "," +
			"tierTo=" + tierTo + "," +
			"beat=" + beat + "," +
			"factor=" + factor + "," +
			"chargeBase=" + chargeBase+ "," +
			"deleted=" + deleted + "," +
			"priceModelPlan=" + priceModelPlan + "," +
			"createdDate=" + createdDate + "," +
			"lastUpdatedDate=" + lastUpdatedDate
			);
		   return str.toString();
	}
	

}
