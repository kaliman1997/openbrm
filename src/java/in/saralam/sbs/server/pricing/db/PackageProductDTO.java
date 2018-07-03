package in.saralam.sbs.server.pricing.db;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
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
import java.util.ArrayList;
import in.saralam.sbs.server.pricing.PricePackageWS;
import in.saralam.sbs.server.pricing.PackageProductWS;

@Entity
@TableGenerator(
        name="package_product_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="package_product",
        allocationSize = 100
        )
@Table(name="package_product")
public class PackageProductDTO  implements Serializable, Exportable {

private static final Logger LOG = Logger.getLogger(PackageProductDTO.class);
     private int id;
     private PricePackageDTO pricePackage;
     private BigDecimal quantity;
     private Integer productId;
     private Integer versionNum;
     private int deleted;
     private Set<PackagePriceDTO> packagePrices = new HashSet<PackagePriceDTO>(0);
     private PackageProductWS packageProducts[] = null;
     private BigDecimal price;

    public PackageProductDTO() {
    }

	
    public PackageProductDTO(int id, PricePackageDTO pricePackage, BigDecimal quantity, int productId) {
        this.id = id;
        this.pricePackage = pricePackage;
        this.quantity = quantity;
        this.productId = productId;
    }
    public PackageProductDTO(int id, PricePackageDTO pricePackage, BigDecimal quantity, int productId, Set<PackagePriceDTO> packagePrices) {
       this.id = id;
       this.pricePackage = pricePackage;
       this.quantity = quantity;
       this.productId = productId;
       this.packagePrices = packagePrices;
    }
   
  
      public PackageProductDTO(PackageProductWS packageProductWS){
          this.id = packageProductWS.getId();
          this.price= packageProductWS.getOneTimePrice();
          this.pricePackage=packageProductWS.getPricePackage();
          this.quantity = packageProductWS.getQuantity();
          this.productId =packageProductWS.getProductId();
        
}    


    @Id  @GeneratedValue(strategy=GenerationType.TABLE, generator="package_product_GEN")
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="package_id", nullable=false)
    public PricePackageDTO getPricePackage() {
        return this.pricePackage;
    }
    
    public void setPricePackage(PricePackageDTO pricePackage) {
        this.pricePackage = pricePackage;
    }

    
    @Column(name="quantity", nullable=false)
    public BigDecimal getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    
    @Column(name="product_id", nullable=false)
    public Integer getProductId() {
        return this.productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="packageProduct")
    public Set<PackagePriceDTO> getPackagePrices() {
        return this.packagePrices;
    }
    
    public void setPackagePrices(Set<PackagePriceDTO> packagePrices) {
        this.packagePrices = packagePrices;
    }
    @Version
    @Column(name="OPTLOCK")
    public Integer getVersionNum() {
        return versionNum;
    }
    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }
    
    @Column(name="deleted", nullable=false)
    public int getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Transient
    public String[] getFieldNames() {
        return new String[] {
                "id",
                "userId",
                "userName",
                "status",
                "period",
                "billingType",
                "currency",
                "total",
                "activeSince",
                "activeUntil",
                "cycleStart",
                "createdDate",
                "nextBillableDay",
                "isMainSubscription",
                "notes",

                // order lines
                "lineItemId",
                "lineProductCode",
                "lineQuantity",
                "linePrice",
                "lineAmount",
                "lineDescription"
        };
    }
    
    @Transient
    public Object[][] getFieldValues() {
        List<Object[]> values = new ArrayList<Object[]>();
       
        return values.toArray(new Object[values.size()][]);
    }




}


