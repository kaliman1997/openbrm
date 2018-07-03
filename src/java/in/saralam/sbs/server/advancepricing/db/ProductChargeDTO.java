package in.saralam.sbs.server.advancepricing.db;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.util.db.AbstractDescription;
import in.saralam.sbs.server.taxCode.db.TaxCodeDTO;

@Entity
@TableGenerator(
        name="product_charge_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="product_charge",
        allocationSize = 100
        )
@Table(name="product_charge")
public class ProductChargeDTO implements Serializable{

	private static final Logger LOG = Logger.getLogger(ProductChargeDTO.class);
	
	
	private int id;
	private ItemDTO item;
	private Date createdDate;
	private ChargeTypeDTO chargeType;
	private Integer versionNum;
	private int deleted;
	private Set<ProductChargeRateDTO> rates = new HashSet<ProductChargeRateDTO>(0);
	private TaxCodeDTO taxCode;
	
	public ProductChargeDTO(){
	}
	
	public ProductChargeDTO(int id){
		this.id = id;
	}
	
	public ProductChargeDTO( ItemDTO item, Date createdDate, int deleted){
		this.item = item;
		this.createdDate = createdDate;
		this.deleted = deleted;
	}

	@Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="product_charge_GEN")
    @Column(name="id", unique=true, nullable=false)
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id", nullable=false)
	public ItemDTO getItem() {
		return item;
	}


	public void setItem(ItemDTO item) {
		this.item = item;
	}

	 @Column(name="created_date", nullable=false, length=19)
	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="charge_type", nullable=false)
	public ChargeTypeDTO getChargeType() {
		return chargeType;
	}


	public void setChargeType(ChargeTypeDTO chargeType) {
		this.chargeType = chargeType;
	}

	@Version
    @Column(name="OPTLOCK" , nullable=false)
	public Integer getVersionNum() {
		return versionNum;
	}


	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Column(name="deleted", nullable=false)
	public int getDeleted() {
		return deleted;
	}


	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	 @OneToMany(fetch=FetchType.LAZY, mappedBy="productCharge")
	public Set<ProductChargeRateDTO> getRates() {
		return rates;
	}

	public void setRates(Set<ProductChargeRateDTO> rates) {
		this.rates = rates;
	}
       @ManyToOne(fetch=FetchType.LAZY)
         @JoinColumn(name="tax_code", nullable=true)
         public  TaxCodeDTO getTaxCode(){
		  return this.taxCode;

	 }
	  public  void setTaxCode(TaxCodeDTO  taxCode){
		  this.taxCode=taxCode;
	  }


}
