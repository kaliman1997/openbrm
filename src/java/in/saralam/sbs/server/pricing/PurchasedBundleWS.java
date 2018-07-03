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
import in.saralam.sbs.server.pricing.db.BundleStatusDTO;
import com.sapienter.jbilling.server.security.WSSecured;
import com.sapienter.jbilling.server.user.db.CompanyDAS;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.user.db.CustomerDAS;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.user.db.UserStatusDTO;
public class PurchasedBundleWS implements  Serializable {
     private int id;
     private int bundleId;
     private BundleStatusDTO statusId;
     private Date  validFrom;
     private Date  validTo;
     private Date  updateDateTime;
     private Date  createdDateTime;
     private UserDTO  userDto; 
     PackageProductWS[] packageProducts;  
     String bundleName;
     public PurchasedBundleWS(){
     }
     public PurchasedBundleWS(int id, int bundleId, BundleStatusDTO statusId, Date  validFrom, Date  validTo, Date  updateDateTime, Date createdDateTime, UserDTO  userDto, String bundleName) {
       this.id = id;
       this.bundleId = bundleId;
       this.statusId = statusId;
       this.validFrom = validFrom;
       this.validTo = validTo;
       this.updateDateTime = updateDateTime;
       this.createdDateTime = createdDateTime;
       this.userDto = userDto;
	   this.bundleName = bundleName;
      
    }

    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
     
    public int getBundleId() {
        return this.bundleId;
    }
    
    public void setBundleId(int bundleId) {
        this.bundleId = bundleId;
    }
    
     public String getBundleName() {
        return this.bundleName;
    }
    
    public void setBundleName(String bundleName) {
        this.bundleName=bundleName;
    }
    
    public BundleStatusDTO getStatusId() {
        return this.statusId;
    }
    
    public void setStatusId(BundleStatusDTO statusId) {
        this.statusId =statusId;
    }
 
   
    public Date getValidFrom() {
        return this.validFrom;
    }
    
    public void setValidFrom(Date validFrom) {
        this.validFrom =validFrom;
    }
   
    public Date getValidTo() {
        return this.validTo;
    }
    
    public void setValidTo(Date validTo) {
        this.validTo =validTo;
    }
    
    public Date getUpdateDateTime() {
        return this.updateDateTime;
    }
    
    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
    
    public Date getCreatedDateTime() {
        return this.createdDateTime;
    }
    
    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

   
    public UserDTO getUserDto() {
        return this.userDto;
    }
    
    public void setUserDto(UserDTO userDto) {
        this.userDto = userDto;
    }

   public PackageProductWS[] getPackageProducts() {
        return packageProducts;
    }

   
    public void setPackageProducts(PackageProductWS[] packageProducts) {
        this.packageProducts = packageProducts;
    }



}
