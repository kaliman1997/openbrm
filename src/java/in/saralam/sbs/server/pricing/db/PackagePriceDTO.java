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

@Entity
@TableGenerator(
        name="package_price_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="package_price",
        allocationSize = 100
        )
@Table(name="package_price")
public class PackagePriceDTO  implements Serializable, Exportable  {

    private static final Logger LOG = Logger.getLogger(PackagePriceDTO.class);
     private int id;
     private PackagePriceTypeDTO packagePriceType;
     private PackageProductDTO packageProduct;
     private BigDecimal amount;
     private BigDecimal discount;
     private Date startDate;
     private Date endDate;
     private Integer startOffset;
     private Integer startOffsetUnit;
     private int endOffset;
     private int endOffsetUnit;
     private Integer versionNum;
     private int deleted;
     private int frequency;
     private int billingType;
    public PackagePriceDTO() {
    }

    public PackagePriceDTO(int id, PackagePriceTypeDTO packagePriceType, PackageProductDTO packageProduct, BigDecimal amount, BigDecimal discount, Date startDate, Date endDate, Integer startOffset, Integer startOffsetUnit, int endOffset, int endOffsetUnit) {
       this.id = id;
       this.packagePriceType = packagePriceType;
       this.packageProduct = packageProduct;
       this.amount = amount;
       this.discount = discount;
       this.startDate = startDate;
       this.endDate = endDate;
       this.startOffset = startOffset;
       this.startOffsetUnit = startOffsetUnit;
       this.endOffset = endOffset;
       this.endOffsetUnit = endOffsetUnit;
    }
   

    public PackagePriceDTO(PackageProductWS packageProductWS){
          this.id = packageProductWS.getId();
          this.amount= packageProductWS.getOneTimePrice();
          this.discount =packageProductWS.getOneTimeDiscount();
          this.startOffset = packageProductWS.getOneTimeStartOffset();
          this.startOffsetUnit = packageProductWS.getOneTimeStartOffsetUnit();
          this.endOffset = packageProductWS.getOneTimeEndOffset();
          this.endOffsetUnit = packageProductWS.getOneTimeEndOffsetUnit() ;
         }    
    @Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="package_price_GEN")
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="type_id", nullable=true)
    public PackagePriceTypeDTO getPackagePriceType() {
        return this.packagePriceType;
    }
    
    public void setPackagePriceType(PackagePriceTypeDTO packagePriceType) {
        this.packagePriceType = packagePriceType;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="pkg_prod_id", nullable=true)
    public PackageProductDTO getPackageProduct() {
        return this.packageProduct;
    }
    
    public void setPackageProduct(PackageProductDTO packageProduct) {
        this.packageProduct = packageProduct;
    }

    
    @Column(name="amount", nullable=false, precision=10, scale=0)
    public BigDecimal getAmount() {
        return this.amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    
    @Column(name="discount", nullable=false, precision=10, scale=0)
    public BigDecimal getDiscount() {
        return this.discount;
    }
    
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

   @Column(name="start_date", nullable=false, length=19)
    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name="end_date", nullable=true, length=19)
    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    
    @Column(name="start_offset", nullable=false)
    public Integer getStartOffset() {
        return this.startOffset;
    }
    
    public void setStartOffset(Integer startOffset) {
        this.startOffset = startOffset;
    }

    
    @Column(name="start_offset_unit", nullable=false)
    public Integer getStartOffsetUnit() {
        return this.startOffsetUnit;
    }
    
    public void setStartOffsetUnit(Integer startOffsetUnit) {
        this.startOffsetUnit = startOffsetUnit;
    }

    
    @Column(name="end_offset", nullable=false)
    public int getEndOffset() {
        return this.endOffset;
    }
    
    public void setEndOffset(int endOffset) {
        this.endOffset = endOffset;
    }

    
    @Column(name="end_offset_unit", nullable=false)
    public int getEndOffsetUnit() {
        return this.endOffsetUnit;
    }
    
    public void setEndOffsetUnit(int endOffsetUnit) {
        this.endOffsetUnit = endOffsetUnit;
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
        return this.deleted;
    }
    
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
      @Column(name="period_id", nullable=false)
     public int getFrequency(){
      return this.frequency;
    }
    public void  setFrequency(int frequency){
    
    this.frequency=frequency;

    }
	 @Column(name="billing_type_id", nullable=false)
    public int getBillingType(){
      return this.billingType;
    }
    public void  setBillingType(int billingType){
    
    this.billingType=billingType;

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


