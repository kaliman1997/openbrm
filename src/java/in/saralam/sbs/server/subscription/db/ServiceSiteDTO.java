
package in.saralam.sbs.server.subscription.db;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.persistence.CascadeType;
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

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
//import org.hibernate.annotations.ServiceBy;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.invoice.InvoiceBL;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.process.db.BillingProcessDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Util;
import in.saralam.sbs.server.subscription.db.ServiceStatusDTO;
import java.util.ArrayList;

@Entity
@TableGenerator(
        name="service_site_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="service_site",
        allocationSize = 100
        )
@Table(name="service_site")
// No cache, mutable and critical
public class ServiceSiteDTO implements java.io.Serializable {

     private static Logger LOG = Logger.getLogger(ServiceSiteDTO.class);

     private Integer id;
     private ServiceDTO serviceId;
     private String siteAddr;
     private Date createdDate;
     private Date lastUpdatedDate;
     private int deleted;
      
     

    public ServiceSiteDTO() {
    }
    
    public ServiceSiteDTO(ServiceSiteDTO other) {
    	init(other);
    }

    public void init(ServiceSiteDTO other) {
    	this.id = other.getId();
		this.serviceId = other.getServiceId();
    	this.siteAddr = other.getSiteAddr();
    	this.createdDate = other.getCreatedDate();
		this.lastUpdatedDate = lastUpdatedDate;
    	
    	this.deleted = other.getDeleted();
    	
    }
    
    public ServiceSiteDTO(int id, ServiceDTO serviceId, String siteAddr, Date createdDate, Integer deleted) {
        this.id = id;
        this.serviceId = serviceId;
	this.siteAddr = siteAddr;
        this.createdDate = createdDate;
        this.deleted = deleted;
    }
    public ServiceSiteDTO(int id, ServiceDTO serviceId, String siteAddr, Date createdDate, Date lastUpdatedDate, Integer deleted) {
       	this.id = id;
   	this.serviceId = serviceId;
       	this.siteAddr = siteAddr;
	this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
	this.deleted = deleted;
	               
    }
	
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE, generator="service_site_GEN")
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="service_id", nullable=false)
    public ServiceDTO getServiceId() {
        return this.serviceId;
    }
    public void setServiceId(ServiceDTO serviceId) {
        this.serviceId = serviceId;
    }
	
	 @Column(name="site_addr", nullable=true)
    public String getSiteAddr() {
       return this.siteAddr;

    }

    public void setSiteAddr(String siteAddr) {
       this.siteAddr = siteAddr;

    }
	
	
	@Column(name="created_date", nullable=true, length=19)
    public Date getCreatedDate() {
        return this.createdDate;
    }
    

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

	
	@Column(name="last_updated_date", nullable=true, length=19)
    public Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }
    
    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
	 
	 @Column(name="deleted", nullable=false)
    public Integer getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
	
	 

}


