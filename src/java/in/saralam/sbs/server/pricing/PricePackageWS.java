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
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.sapienter.jbilling.server.invoice.InvoiceWS;
import com.sapienter.jbilling.server.order.OrderLineWS;
import com.sapienter.jbilling.server.order.validator.DateRange;
import in.saralam.sbs.server.pricing.db.PackageProductDTO;
import com.sapienter.jbilling.server.security.WSSecured;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.user.db.CustomerDAS;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.sapienter.jbilling.server.metafields.EntityType;
import com.sapienter.jbilling.server.metafields.db.MetaField;
import com.sapienter.jbilling.server.metafields.db.MetaFieldDAS;
import com.sapienter.jbilling.server.metafields.db.MetaFieldValue;
import com.sapienter.jbilling.server.metafields.MetaFieldValueWS;


public class PricePackageWS implements WSSecured, Serializable {

	 private int id;
     private CompanyDTO entity;
     private String packageCode;
     private String bundleName;
     private Date createdDate;
     private Date activeSince;
     private Date activeUntil;
     private Integer category;
     private Integer versionNum;
     private int deleted;
     private PackageProductWS packageProducts[] = null;
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
     private Integer oneTimeCbValue;
     private Integer recurringCbValue;
   
	 private  Integer cancelCbValue;
     private Integer productId;
     private BigDecimal quantity ;
     private String edit;
	 private Integer mbgDays;
	 private  String mbgCheck;

	 @Valid
	 private MetaFieldValueWS[] metaFields;

    public PricePackageWS() {
    }

    public PricePackageWS(Integer id, CompanyDTO entity, Date activeSince, Date activeUntil,
                   Date createDate, Integer deleted,
                   Integer version) {
    	setEntity(entity);
        setId(id);
        setActiveSince(activeSince);
        setActiveUntil(activeUntil);
        setCreatedDate(createDate);
        setDeleted(deleted.shortValue());
        setVersionNum(version);
     }
   
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate (Date createdDate) {
    	this.createdDate = createdDate;
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

 public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName= bundleName;
    }
    
 public Integer getMbgDays() {
        return mbgDays;
    }

    public void setMbgDays(Integer mbgDays) {
        this.mbgDays= mbgDays;
    }

    public String getMbgCheck() {
        return mbgCheck;
    }

    public void setMbgCheck(String mbgCheck) {
        this.mbgCheck= mbgCheck;
    }

    public void setEntity(CompanyDTO entity) {
    	this.entity = entity;
    }

    public CompanyDTO getEntity() {
    	return entity;
    }
    
    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
  
    public PackageProductWS[] getPackageProducts() {
        return packageProducts;
    }

   
    public void setPackageProducts(PackageProductWS[] packageProducts) {
        this.packageProducts = packageProducts;
    }

    
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getOwningEntityId() {
        return null;
    }
    
    public Integer getOwningUserId() {
        return entity.getId();
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
        return this.oneTimeStartOffset;
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
    
  
   public Integer getOneTimeCBValue(){
               return this.oneTimeCbValue;
   }
    public void setOneTimeCBValue(Integer  oneTimeCbValue){
               this.oneTimeCbValue=oneTimeCbValue;
   } 
  public  Integer getRecurringCBValue(){
               return this.recurringCbValue;
   }
    public void setRecurringCBValue(Integer  recurringCbValue){
               this.recurringCbValue=recurringCbValue;
   } 
 public Integer getCancelCbValue(){
      return  this.cancelCbValue;
    }
   public void setCancelCbValue(Integer cancelCbValue){
     this.cancelCbValue=cancelCbValue;
}


     public String getEdit() {
        return this.edit;
    }
    
    public void setEdit(String edit) {
        this.edit = edit;
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
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

     public BigDecimal getCancelPrice(){
               return  this.cancelPrice;
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
    

	
	public MetaFieldValueWS[] getMetaFields() {
        return metaFields;
    }

    public void setMetaFields(MetaFieldValueWS[] metaFields) {
		
        this.metaFields = metaFields;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        sb.append("OrderWS");
        sb.append("{id=").append(id);
        //sb.append(", userId=").append(userId);
        sb.append(", lines=");
        sb.append('}');
        return sb.toString();
    }
}
