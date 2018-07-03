package in.saralam.sbs.server.Exclusions.db;

import in.saralam.sbs.server.Exclusions.ExclusionBL;

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
import javax.persistence.Transient;
import org.apache.log4j.Logger;

import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.user.db.CompanyDTO;



@Entity
@TableGenerator(
		name="ex_rate_GEN",
		table="jbilling_seqs",
		pkColumnName = "name",
		valueColumnName = "next_id",
		pkColumnValue = "ex_rate",
		allocationSize = 100
		)
@Table(name="ex_rate")
public class ExclusionDTO implements Serializable{
	
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
	private CompanyDTO entity;
	
	
	private static final Logger LOG = Logger.getLogger(ExclusionDTO.class);
	
	public ExclusionDTO(){
		
	}
	

	public ExclusionDTO(Integer id, String prefix, String destination,String field1,String field2, Integer version, Integer deleted, Integer ratePlan, Date createdDate, Date validFrom, Date validTo, Date lastUpdatedDate) {
		
		this.id = id;
		this.prefix= prefix;
		this.destination = destination;
		this.version = version;
		this.deleted = deleted;
		this.field1= field1;
		this.field2= field2;
		this.ratePlan= ratePlan;
		this.createdDate = createdDate;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.lastUpdatedDate = lastUpdatedDate;
		
		
	}
	
	public ExclusionDTO(String prefix, String destination, Date createdDate, Date validFrom, Date validTo, Date lastUpdatedDate) {
		
		this.prefix= prefix;
		this.destination = destination;
		this.createdDate = createdDate;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
public ExclusionDTO(Integer id, String prefix,String destination, Date validFrom, Date validTo) {
		
		this.id = id;
		this.prefix= prefix;
		this.destination = destination;
		this.validFrom = validFrom;
		this.validTo = validTo;
		LOG.debug("RateDTO constructor is created with " +id+","+prefix+","+destination+","+validFrom+","+validTo);
	}
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="ex_rate_GEN")
	
	@Column(name="id", unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    @Column(name="prefix", length=45 ,nullable=false)
	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Column(name="destination", length=45,nullable=false)
	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	@Column(name="field1", length=45,nullable=false)
	public String getField1() {
		return this.field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}
	
	@Column(name="field2", length=45,nullable=false)
	public String getField2() {
		return this.field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}
	
	@Column(name="version", nullable=false, length=6)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name="deleted", nullable=false)
	public int getDeleted() {
		return this.deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	@Column(name="rate_plan")
	public Integer getratePlan() {
		return this.ratePlan;
	}

	public void setratePlan(Integer ratePlan) {
		this.ratePlan = ratePlan;
	}

	@Column(name="created_date", length=19, nullable=false)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="valid_from", length=19, nullable=false)
	public Date getValidFrom() {
		return this.validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	@Column(name="valid_to", length=19)
	public Date getValidTo() {
		return this.validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	@Column(name="last_updated_date", length=19, nullable=false)
	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
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
			"prefix=" + prefix + "," +
			"destination=" + destination + "," +
			"field1="  + field1 + "'" +
			"field2="  + field2 + "'" + 
			"version=" + version + "," +
			"deleted=" + deleted + "," +
			"ratePlan=" + ratePlan + "," +
			"createdDate=" + createdDate + "," +
			"validFrom=" + validFrom + "," +
			"validTo=" + validTo + "," +
			"lastUpdatedDate=" + lastUpdatedDate
				);
		   return str.toString();
	}
	 @Transient
	    public String[] getFieldNames() {
	        return new String[] {
	                "id",
	                "prefix",
	                "lastModified",	           
	                "destination",
	                "field1",
	                "field2",
	                "ratePlan",	                
					"createdDate",
	                
	        };
	    }
	
 @Transient
	    public Object[][] getFieldValues() {
	   

	        return new Object[][] {
	            {
	                id,
	                prefix,
	                lastUpdatedDate,
	                destination,
	                field1,
	                field2,
	                ratePlan,
	                createdDate,
	                
	               	            }
	        };
	    }



}
