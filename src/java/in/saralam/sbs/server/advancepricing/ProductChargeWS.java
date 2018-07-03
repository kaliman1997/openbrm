package in.saralam.sbs.server.advancepricing;

import in.saralam.sbs.server.advancepricing.db.ChargeTypeDTO;
import in.saralam.sbs.server.rating.db.RatingEventTypeDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapienter.jbilling.server.item.db.ItemDTO;
import in.saralam.sbs.server.taxCode.db.TaxCodeDTO;
import in.saralam.sbs.server.taxCode.db.TaxCodeDAS;

public class ProductChargeWS implements Serializable{

	private int id;
	private ItemDTO item;
	private Date createdDate;
	private ChargeTypeDTO chargeType;
	private Integer versionNum;
	private int deleted;
	private List<ProductChargeRateWS> rates = null;
	private RatingEventTypeDTO ratingEvent;
	private Map<Integer, ProductChargeWS> charges = new HashMap<Integer, ProductChargeWS>();
         private TaxCodeDTO taxCode;
	
	public ProductChargeWS(){
	}
	

	public ProductChargeWS(int id, ItemDTO item, Date createdDate, ChargeTypeDTO chargeType, int versionNum, int deleted){
		setId(id);
		setItem(item);
		setCreatedDate(createdDate);
		setChargeType(chargeType);
		setVersionNum(versionNum);
		setDeleted(deleted);
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ItemDTO getItem() {
		return item;
	}
	public void setItem(ItemDTO item) {
		this.item = item;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public ChargeTypeDTO getChargeType() {
		return chargeType;
	}
	public void setChargeType(ChargeTypeDTO chargeType) {
		this.chargeType = chargeType;
	}
	public Integer getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	public List<ProductChargeRateWS> getRates() {
		return rates;
	}

	public void setRates(List<ProductChargeRateWS> rates) {
		this.rates = rates;
	}
	
	public Map<Integer, ProductChargeWS> getCharges() {
		return charges;
	}

	public void setCharges(Map<Integer, ProductChargeWS> charges) {
		this.charges = charges;
	}


	public RatingEventTypeDTO getRatingEvent() {
		return ratingEvent;
	}


	public void setRatingEvent(RatingEventTypeDTO ratingEvent) {
		this.ratingEvent = ratingEvent;
	}
	public  TaxCodeDTO getTaxCode(){
		  return this.taxCode;

	 }
	  public  void setTaxCode(TaxCodeDTO  taxCode){
		  this.taxCode=taxCode;
	  }
	
	
}
