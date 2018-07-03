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
        name="purchased_bundle_product_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="purchased_bundle_product",
        allocationSize = 100
        )
@Table(name="purchased_bundle_product")
public class PurchasedBundleProductDTO  implements Serializable  {

    private static final Logger LOG = Logger.getLogger(PurchasedBundleProductDTO.class);
     private int id;
     private int pbId;
     private int productId;
     private BigDecimal recurringCharge;
     private BigDecimal recurringDiscount;
     private Date recurringStartTime;
     private Date recurringEndTime;
     private BigDecimal oneOffCharge;
     private BigDecimal oneOffDiscount;
     private Date oneOffStartTime;
     private Date oneOffEndTime;
     private BigDecimal usageCharge;
     private BigDecimal usageDiscount;
     private Date usageStartTime;
     private Date usageEndTime;
     private BigDecimal cancelCharge;
     private BigDecimal cancelDiscount;
     private Date cancelStartTime;
     private Date cancelEndTime;
     private int recurringOrderId;
     private int oneOffOrderId;
     private int cancelOrderId;
     public PurchasedBundleProductDTO() {
    }

    public PurchasedBundleProductDTO(int id, int pbId, int productId, BigDecimal recurringCharge, BigDecimal recurringDiscount
                                     ,Date recurringStartTime, Date recurringEndTime, BigDecimal oneOffCharge,BigDecimal oneOffDiscount
                                     ,Date oneOffStartTime,Date oneOffEndTime,BigDecimal usageCharge,BigDecimal usageDiscount
                                     ,Date usageStartTime,Date usageEndTime,BigDecimal cancelCharge,BigDecimal cancelDiscount
                                     ,Date cancelStartTime,Date cancelEndTime,int recurringOrderId,int oneOffOrderId,int cancelOrderId) {
       this.id = id;
       this.pbId = pbId;
       this.productId = productId;
       this.recurringCharge = recurringCharge;
       this.recurringDiscount =recurringDiscount;
       this.recurringStartTime = recurringStartTime;
       this.recurringEndTime = recurringEndTime;
       this.oneOffCharge = oneOffCharge;
       this.oneOffDiscount = oneOffDiscount;
       this.oneOffStartTime = oneOffStartTime;
       this.oneOffEndTime = oneOffEndTime;
       this.usageCharge = usageCharge;
       this.usageDiscount = usageDiscount;
       this.usageStartTime = usageStartTime;
       this.cancelCharge = cancelCharge;
       this.cancelDiscount = cancelDiscount;
       this.cancelStartTime = cancelStartTime;
       this.cancelEndTime = cancelEndTime;
       this.recurringOrderId = recurringOrderId;
       this.oneOffOrderId = oneOffOrderId;
       this.cancelOrderId = cancelOrderId;
      
      
    }
   

    
    @Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="purchased_bundle_product_GEN")
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

     @Column(name="pb_id", nullable=false)
    public int getPbId() {
        return this.pbId;
    }
    
    public void setPbId(int pbId) {
        this.pbId = pbId;
    }

    @Column(name="product_id", nullable=false)
    public int getProductId () {
        return this.productId ;
    }
    
    public void setProductId (int productId ) {
        this.productId  =productId ;
    }

     @Column(name="recurring_charge", nullable=true, precision=10, scale=0)
    public BigDecimal getRecurringCharge() {
        return this.recurringCharge;
    }
    
    public void setRecurringCharge(BigDecimal recurringCharge) {
        this.recurringCharge = recurringCharge;
    }
     @Column(name="recurring_discount", nullable=true, precision=10, scale=0)
    public BigDecimal getRecurringDiscount() {
        return this.recurringDiscount;
    }
    
    public void setRecurringDiscount(BigDecimal recurringDiscount) {
        this.recurringDiscount = recurringDiscount;
    }

 
    @Column(name="recurring_start_time", nullable=true)
    public Date getRecurringStartTime() {
        return this.recurringStartTime;
    }
    
    public void setRecurringStartTime(Date recurringStartTime) {
        this.recurringStartTime =recurringStartTime;
    }
    @Column(name="recurring_end_time", nullable=true)
    public Date getRecurringEndTime() {
        return this.recurringEndTime;
    }
    
    public void setRecurringEndTime(Date recurringEndTime) {
        this.recurringEndTime =recurringEndTime;
    }
     @Column(name="oneoff_charge", nullable=true, precision=10, scale=0)
    public BigDecimal getOneOffCharge() {
        return this.oneOffCharge;
    }
    
    public void setOneOffCharge(BigDecimal oneOffCharge) {
        this.oneOffCharge = oneOffCharge;
    }
     @Column(name="oneoff_discount", nullable=true, precision=10, scale=0)
    public BigDecimal getOneOffDiscount() {
        return this.oneOffDiscount;
    }
    
    public void setOneOffDiscount(BigDecimal oneOffDiscount) {
        this.oneOffDiscount = oneOffDiscount;
    }

 
    @Column(name="oneoff_start_time", nullable=true)
    public Date getOneOffStartTime() {
        return this.oneOffStartTime;
    }
    
    public void setOneOffStartTime(Date oneOffStartTime) {
        this.oneOffStartTime =oneOffStartTime;
    }
    @Column(name="oneoff_end_time", nullable=true)
    public Date getOneOffEndTime() {
        return this.oneOffEndTime;
    }
    
    public void setOneOffEndTime(Date oneOffEndTime) {
        this.oneOffEndTime =oneOffEndTime;
    }
   @Column(name="usage_charge", nullable=true, precision=10, scale=0)
    public BigDecimal getUsageCharge() {
        return this.usageCharge;
    }
    
    public void setUsageCharge(BigDecimal usageCharge) {
        this.usageCharge = usageCharge;
    }
     @Column(name="usage_discount", nullable=true, precision=10, scale=0)
    public BigDecimal getUsageDiscount() {
        return this.usageDiscount;
    }
    
    public void setUsageDiscount(BigDecimal usageDiscount) {
        this.usageDiscount = usageDiscount;
    }

 
    @Column(name="usage_start_time", nullable=true)
    public Date getUsageStartTime() {
        return this.usageStartTime;
    }
    
    public void setUsageStartTime(Date usageStartTime) {
        this.usageStartTime =usageStartTime;
    }
    @Column(name="usage_end_time", nullable=true)
    public Date getUsageEndTime() {
        return this.usageEndTime;
    }
    
    public void setUsageEndTime(Date usageEndTime) {
        this.usageEndTime =usageEndTime;
    }
   
    @Column(name="cancel_charge", nullable=true, precision=10, scale=0)
    public BigDecimal getCancelCharge() {
        return this.cancelCharge;
    }
    
    public void setCancelCharge(BigDecimal cancelCharge) {
        this.cancelCharge = cancelCharge;
    }
     @Column(name="cancel_discount", nullable=true, precision=10, scale=0)
    public BigDecimal getCancelDiscount() {
        return this.cancelDiscount;
    }
    
    public void setCancelDiscount(BigDecimal cancelDiscount) {
        this.cancelDiscount = cancelDiscount;
    }

 
    @Column(name="cancel_start_time", nullable=true)
    public Date getCancelStartTime() {
        return this.cancelStartTime;
    }
    
    public void setCancelStartTime(Date cancelStartTime) {
        this.cancelStartTime =cancelStartTime;
    }
    @Column(name="cancel_end_time", nullable=true)
    public Date getCancelEndTime() {
        return this.cancelEndTime;
    }
    
    public void setCancelEndTime(Date cancelEndTime) {
        this.cancelEndTime =cancelEndTime;
    }
    @Column(name="oneoff_order_id", nullable=true)
    public int getOneOffOrderId () {
        return this.oneOffOrderId ;
    }
    
    public void setOneOffOrderId (int oneOffOrderId) {
        this.oneOffOrderId  =oneOffOrderId ;
    }
    @Column(name="recurring_order_id", nullable=true)
    public int getRecurringOrderId () {
        return this.recurringOrderId ;
    }
    
    public void setRecurringOrderId (int recurringOrderId ) {
        this.recurringOrderId  =recurringOrderId ;
    }
    
    @Column(name="cancel_order_id", nullable=true)
    public int getCancelOrderId () {
        return this.cancelOrderId ;
    }
    
    public void setCancelOrderId (int cancelOrderId ) {
        this.cancelOrderId  =cancelOrderId ;
    }



    
    
}


