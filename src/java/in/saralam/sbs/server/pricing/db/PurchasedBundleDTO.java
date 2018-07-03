package in.saralam.sbs.server.pricing.db;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import com.sapienter.jbilling.server.invoice.db.InvoiceLineDTO;
import com.sapienter.jbilling.server.util.csv.Exportable;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.invoice.InvoiceBL;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.process.db.BillingProcessDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Util;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;
import in.saralam.sbs.server.pricing.PricePackageWS;
import in.saralam.sbs.server.pricing.PackageProductWS;
import java.util.ArrayList;
import in.saralam.sbs.server.pricing.db.BundleStatusDTO;

@Entity
@TableGenerator(
        name="purchased_bundle_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="purchased_bundle",
        allocationSize = 100
        )
@Table(name="purchased_bundle")
public class PurchasedBundleDTO  implements Serializable  {

    private static final Logger LOG = Logger.getLogger(PackagePriceDTO.class);
     private int id;
     private int bundleId;
     //private int statusId;
      private BundleStatusDTO bundleStatus;
     private Date  validFrom;
     private Date  validTo;
     private Date  updateDateTime;
     private Date  createdDateTime;
     private UserDTO  userDto;
     
    public PurchasedBundleDTO() {
    }

    public PurchasedBundleDTO(int id, int bundleId, BundleStatusDTO bundleStatus, Date  validFrom, Date  validTo, Date  updateDateTime, Date createdDateTime, UserDTO  userDto) {
       this.id = id;
       this.bundleId = bundleId;
       this.bundleStatus =bundleStatus;
       this.validFrom = validFrom;
       this.validTo = validTo;
       this.updateDateTime = updateDateTime;
       this.createdDateTime = createdDateTime;
       this.userDto = userDto;
      
    }
   

    
    @Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="purchased_bundle_GEN")
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

     @Column(name="bundle_id", nullable=false)
    public int getBundleId() {
        return this.bundleId;
    }
    
    public void setBundleId(int bundleId) {
        this.bundleId = bundleId;
    }

   // @Column(name="status_id", nullable=true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    public BundleStatusDTO getStatusId() {
        return this.bundleStatus;
    }
    
    public void setStatusId(BundleStatusDTO bundleStatus) {
        this.bundleStatus =bundleStatus;
    }
 
    @Column(name="valid_from", nullable=false)
    public Date getValidFrom() {
        return this.validFrom;
    }
    
    public void setValidFrom(Date validFrom) {
        this.validFrom =validFrom;
    }
    @Column(name="valid_to", nullable=true)
    public Date getValidTo() {
        return this.validTo;
    }
    
    public void setValidTo(Date validTo) {
        this.validTo =validTo;
    }
    @Column(name="update_datetime", nullable=true)
    public Date getUpdateDateTime() {
        return this.updateDateTime;
    }
    
    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
    @Column(name="created_datetime", nullable=false)
    public Date getCreatedDateTime() {
        return this.createdDateTime;
    }
    
    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=true)
    public UserDTO getUserDto() {
        return this.userDto;
    }
    
    public void setUserDto(UserDTO userDto) {
        this.userDto = userDto;
    }

    
    
}


