/*
 jBilling - The Enterprise Open Source Billing System
 Copyright (C) 2003-2011 Enterprise jBilling Software Ltd. and Emiliano Conde

 This file is part of jbilling.

 jbilling is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 jbilling is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with jbilling.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.saralam.sbs.server.pricing;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.sapienter.jbilling.server.item.ItemDTOEx;
import in.saralam.sbs.server.pricing.db.PackagePriceDTO;
import in.saralam.sbs.server.pricing.db.PricePackageDTO;
import in.saralam.sbs.server.pricing.db.PackagePriceTypeDTO;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.apache.log4j.Logger;
public class PackageProductWS implements Serializable {
    private static final Logger LOG = Logger.getLogger(PackageProductWS.class);
	 private int id;
     private PricePackageDTO pricePackage;
     private BigDecimal quantity;
     private Integer productId;
     private Integer versionNum;
     private int deleted;
     private Set<PackagePriceDTO> packagePrices = new HashSet<PackagePriceDTO>(0);
     private BigDecimal  oneTimePrice;
     private BigDecimal  oneTimeDiscount;
     private BigDecimal  recureringPrice;
     private BigDecimal  recureringDiscount;
    
     private BigDecimal  cancelPrice;
     private BigDecimal  cancelDiscount;
     private int oneTimeStartOffset;
     private int oneTimeStartOffsetUnit;
     private int oneTimeEndOffset;
     private int oneTimeEndOffsetUnit; 
     private int recureringstartOffset;
     private int recureringstartOffsetUnit;
     private int recureringEndOffset;
     private int recureringEndOffsetUnit;
    
     private int cancelStartOffset;
     private int cancelStartOffsetUnit;
     private int cancelEndOffset;
     private int cancelEndOffsetUnit;
     private  Integer oneTimeCbValue;
     private  Integer recurringCbValue;
    
     private  Integer cancelCbValue;
     private Date activeSince;
     private Date activeUntil;
     private String edit;
     private String bundleName;
     private int frequency;
	 private int billingType;

    public PackageProductWS() {
    }

    public PackageProductWS(Integer id, Integer productId, PricePackageDTO pricePackage ) {
        setId(id);
        setProductId(productId);
        setDeleted(deleted);
        setVersionNum(versionNum);
        

    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

   
    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        setQuantity(new BigDecimal(quantity));
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = (quantity != null ? quantity : null);
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }
    
    public void setPricePackage(PricePackageDTO pricePackage) {
    	
    	this.pricePackage = pricePackage;
    	
    }
    
     public BigDecimal getOneTimePrice(){
               return oneTimePrice;
   }
    public void setOneTimePrice(BigDecimal  oneTimePrice){
               this.oneTimePrice=oneTimePrice;
   }
   public BigDecimal getOneTimeDiscount(){
               return oneTimeDiscount;
   }
    public void setOneTimeDiscount(BigDecimal  oneTimeDiscount){
               this.oneTimeDiscount=oneTimeDiscount;
			   LOG.debug("oneTimeDiscount"+this.oneTimeDiscount);
			    LOG.debug("oneTimeDiscount"+oneTimeDiscount);
   }
   public BigDecimal getRecurringPrice(){
               return recureringPrice;
   }
    public void setRecurringPrice(BigDecimal  recureringPrice){
               this.recureringPrice=recureringPrice;
   }
   public BigDecimal getRecurringDiscount(){
               return recureringDiscount;
   }
    public void setRecurringDiscount(BigDecimal  recureringDiscount){
               this.recureringDiscount=recureringDiscount;
   }
   
    public int getOneTimeStartOffset() {
        return this.oneTimeStartOffset;
    }
    
    public void setOneTimeStartOffset(int oneTimeStartOffset) {
        this.oneTimeStartOffset = oneTimeStartOffset;
    }
    public int getOneTimeStartOffsetUnit() {
        return this.oneTimeStartOffsetUnit;
    }
    
    public void setOneTimeStartOffsetUnit(int oneTimeStartOffsetUnit) {
        this.oneTimeStartOffsetUnit = oneTimeStartOffsetUnit;
    }
   public int getOneTimeEndOffset() {
        return this.oneTimeEndOffset;
    }
    
    public void setOneTimeEndOffset(int oneTimeEndOffset) {
        this.oneTimeEndOffset = oneTimeEndOffset;
    }
   
   public int getOneTimeEndOffsetUnit() {
        return this.oneTimeEndOffsetUnit;
    }
    
    public void setOneTimeEndOffsetUnit(int oneTimeEndOffsetUnit) {
        this.oneTimeEndOffsetUnit = oneTimeEndOffsetUnit;
    }
    
   public int getRecurringStartOffset() {
        return this.recureringstartOffset;
    }
    
    public void setRecurringStartOffset(int recureringstartOffset) {
        this.recureringstartOffset= recureringstartOffset;
    }
    public int getRecurringStartOffsetUnit() {
        return this.recureringstartOffsetUnit;
    }
    
    public void setRecurringStartOffsetUnit(int recureringstartOffsetUnit) {
        this.recureringstartOffsetUnit = recureringstartOffsetUnit;
    }
   public int getRecurringEndOffset() {
        return this.recureringEndOffset;
    }
    
    public void setRecurringEndOffset(int recureringEndOffset) {
        this.recureringEndOffset = recureringEndOffset;
    }
   
   public int getRecurringEndOffsetUnit() {
        return this.recureringEndOffsetUnit;
    }
    
    public void setRecurringEndOffsetUnit(int recureringEndOffsetUnit) {
        this.recureringEndOffsetUnit = recureringEndOffsetUnit;
    }
    
   
   public Integer getOneTimeCbValue(){
      return  this.oneTimeCbValue;
    }
   public void setOneTimeCbValue(Integer oneTimeCbValue){
     this.oneTimeCbValue=oneTimeCbValue;
}
  public Integer getRecurringCbValue(){
      return  this.recurringCbValue;
    }
   public void setRecurringCbValue(Integer recurringCbValue){
     this.recurringCbValue=recurringCbValue;
}
  
    public Integer getCancelCbValue(){
      return  this.cancelCbValue;
    }
   public void setCancelCbValue(Integer cancelCbValue){
	   
     this.cancelCbValue=cancelCbValue;
}

   public Date getActiveSince() {
        return this.activeSince;
    }
    
    public void setActiveSince(Date activeSince) {
        this.activeSince = activeSince;
    }
 public Date getActiveUntil() {
        return this.activeUntil;
    }
    
    public void setActiveUntil(Date activeUntil) {
        this.activeUntil = activeUntil;
    }
     public String getEdit() {
        return this.edit;
    }
    
    public void setEdit(String edit) {
        this.edit = edit;
    }
    
   public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }


    public PricePackageDTO getPricePackage() {
    	
    	return pricePackage;
    	
    }
     
	 public BigDecimal getCancelPrice(){
               return this.cancelPrice;
   }
    public void setCancelPrice(BigDecimal  cancelPrice){
               this.cancelPrice=cancelPrice;
   }
   public BigDecimal getCancelDiscount(){
               return this.cancelDiscount;
   }
    public void setCancelDiscount(BigDecimal  cancelDiscount){
               this.cancelDiscount=cancelDiscount;
   }

    public int getCancelStartOffset() {
        return this.cancelStartOffset;
    }
    
    public void setCancelStartOffset(int cancelStartOffset) {
        this.cancelStartOffset = cancelStartOffset;
    }
    public int getCancelStartOffsetUnit() {
        return this.cancelStartOffsetUnit;
    }
    
    public void setCancelStartOffsetUnit(int cancelStartOffsetUnit) {
        this.cancelStartOffsetUnit = cancelStartOffsetUnit;
    }
   public int getCancelEndOffset() {
        return this.cancelEndOffset;
    }
    
    public void setCancelEndOffset(int cancelEndOffset) {
        this.cancelEndOffset = cancelEndOffset;
    }
   
   public int getCancelEndOffsetUnit() {
        return this.cancelEndOffsetUnit;
    }
    
    public void setCancelEndOffsetUnit(int cancelEndOffsetUnit) {
        this.cancelEndOffsetUnit = cancelEndOffsetUnit;
    }
    
    public int getFrequency(){
      return this.frequency;
    }
    public void  setFrequency(int frequency){
    
    this.frequency=frequency;

    }
	public int getBillingType(){
      return this.billingType;
    }
    public void  setBillingType(int billingType){
    
    this.billingType=billingType;

    }
    
    @Override public String toString() {
        return "OrderLineWS{"
               + "id=" + id
               + ", quantity='" + quantity + '\''
               + ", deleted=" + deleted
			   + ", billingType=" + billingType
			   + ", frequency=" + frequency
			   + ", bundleName=" + bundleName
			   + ", productId=" + productId
               + '}';
    }
}
