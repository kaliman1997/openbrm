package in.saralam.sbs.server.rating;

import com.sapienter.jbilling.server.rule.Result;
import java.math.BigDecimal;
import org.apache.log4j.Logger;

public class CRatingResult extends Result {

    private static final Logger LOG = Logger.getLogger(CRatingResult.class);

	private Integer rateId;
    private Integer itemId;
    private Integer currencyId;
    private int ratingResult;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal discount;
    private BigDecimal connectCharge;
    private BigDecimal netAmount;
    private BigDecimal ratedQuantity;
    private BigDecimal taxTotal = new BigDecimal("0");
    private String description;
    private boolean taxable = false;

    public CRatingResult(Integer itemId, Integer currencyId, BigDecimal amount) {
        this.itemId = itemId;
        this.currencyId = currencyId;
		this.amount = amount;
    }

    public CRatingResult() {

    }

	public Integer getRateId(){
	 return rateId;
	 }
	 
	 public void setRateId(Integer rateId){
		this.rateId = rateId;
	 }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
	this.currencyId = currencyId;

    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
	this.itemId = itemId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
	
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setAmount(String amount) {
        setAmount(new BigDecimal(amount));
    }
	
    public void setRatedQuantity(BigDecimal qty) {
        this.ratedQuantity = qty;
    }

    public void setRatedQuantity(int qty) {
        this.ratedQuantity = new BigDecimal(qty);
    }

	
    public BigDecimal getRatedQuantity() {
	return  this.ratedQuantity;
    }

    public BigDecimal getConnectCharge() {
		return connectCharge;
	}

	public void setConnectCharge(BigDecimal connectCharge) {
		this.connectCharge = connectCharge;
	}

	public BigDecimal getPrice() {
        return  this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return  this.discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getNetAmount() {
        return  this.netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public void setTaxTotal(BigDecimal taxTotal) {

	this.taxTotal = taxTotal;
    }

   public BigDecimal getTaxTotal() {
	return this.taxTotal;
   }

    public String getDescription() {
        return  this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int  getRatingResult() {
        return  this.ratingResult;
    }

    public void  setRatingResult(int ratingResult) {
        this.ratingResult = ratingResult;;
    }

    public boolean getTaxable() {
	return this.taxable;
    }

    public void setTaxable(boolean taxable) {
	this.taxable = taxable;
    }


	
    public String toString() {
        return  "CRatingResult:" +
                "itemId=" + itemId + " " +
                "currencyId=" + currencyId + " " +
                "price=" + amount + " " + super.toString();
    }

}


