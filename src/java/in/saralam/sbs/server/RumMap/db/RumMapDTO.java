package in.saralam.sbs.server.RumMap.db;

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
		name="rum_map_GEN",
		table="jbilling_seqs",
		pkColumnName = "name",
		valueColumnName = "next_id",
		pkColumnValue = "rum_map",
		allocationSize = 100
		)
@Table(name="rum_map")
public class RumMapDTO implements Serializable{
	
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
	public ItemDTO item;
	private CompanyDTO entity;
	
	private static final Logger LOG = Logger.getLogger(RumMapDTO.class);
	
	public RumMapDTO(){
		
	}
	

	public RumMapDTO(Integer id, String priceGroup, Integer step, String priceModel, String rum, String resource, Integer resourceId, String rumType,Integer consumeFlag, Integer deleted, Integer rumMapPlan, Date createdDate,  Date lastUpdatedDate) {
		
		this.id = id;
		this.priceGroup = priceGroup;
		this.step = step;
		this.priceModel = priceModel;
		this.rum = rum;
		this.resource = resource;
	    this.resourceId = resourceId;
		this.rumType = rumType;
		this.consumeFlag = consumeFlag;
		this.deleted = deleted;
		this.rumMapPlan = rumMapPlan;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
		
		
	}
	
	public RumMapDTO(String priceGroup, Integer step, String priceModel, String rum, String resource, Integer resourceId, String rumType,Integer consumeFlag,  Date createdDate,  Date lastUpdatedDate) {
		
		this.priceGroup = priceGroup;
		this.step = step;
		this.priceModel = priceModel;
		this.rum = rum;
		this.resource = resource;
	    this.resourceId = resourceId;
		this.rumType = rumType;
		this.consumeFlag = consumeFlag;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
    public RumMapDTO(Integer id, String priceGroup, Integer step, String priceModel, String rum, String resource, Integer resourceId, String rumType,Integer consumeFlag) {
		
		this.id = id;
		this.priceGroup = priceGroup;
		this.step = step;
		this.priceModel = priceModel;
		this.rum = rum;
		this.resource = resource;
	    this.resourceId = resourceId;
		this.rumType = rumType;
		this.consumeFlag = consumeFlag;
		LOG.debug("RumMapDTO constructor is created with " +id+","+priceGroup+","+step+","+priceModel+","+rum+","+resource+","+resourceId+","+rumType+","+consumeFlag+","+createdDate+","+lastUpdatedDate );
	}
	
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="rum_map_GEN")
	@Column(name="id", unique=true, nullable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    @Column(name="price_group", length=45)
	public String getPriceGroup() {
		return priceGroup;
	}

	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}
	
	@Column(name="step", nullable=false)
	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	@Column(name="price_model", length=45)
	public String getPriceModel() {
		return priceModel;
	}

	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}
	@Column(name="rum", length=45)
	public String getRum() {
		return rum;
	}

	public void setRum(String rum) {
		this.rum = rum;
	}
	
	@Column(name="resource", length=45)
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	@Column(name="resource_id", nullable=false)
	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	
	@Column(name="rum_type", length=45)
	public String getRumType() {
		return rumType;
	}

	public void setRumType(String rumType) {
		this.rumType = rumType;
	}
	
	@Column(name="consume_flag", nullable=false, length=6)
	public Integer getConsumeFlag() {
		return consumeFlag;
	}

	public void setConsumeFlag(Integer consumeFlag) {
		this.consumeFlag = consumeFlag;
	}
	

	@Column(name="deleted", nullable=false)
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	@Column(name="rummap_plan")
	public Integer getRumMapPlan() {
		return rumMapPlan;
	}

	public void setRumMapPlan(Integer rumMapPlan) {
		this.rumMapPlan = rumMapPlan;
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
			"priceGroup=" + priceGroup + "," +
			"step=" + step + "," +
			"priceModel=" + priceModel + "," +
			"rum=" + rum +","+
			"resource=" + resource + "," +
			"resourceId=" + resourceId + "," +
			"rumType=" + rumType + "," +
			"consumeFlag=" + consumeFlag + "," +
			"deleted=" + deleted + "," +
			"rumMapPlan=" + rumMapPlan + "," +
			"createdDate=" + createdDate + "," +
			"lastUpdatedDate=" + lastUpdatedDate
				);
		   return str.toString();
	}
	

}
