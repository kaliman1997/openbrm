
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
import in.saralam.sbs.server.subscription.ProvisioningWS;
import java.util.ArrayList;

@Entity
@TableGenerator(
        name="provisioning_tag_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="provisioning_tag",
        allocationSize = 100
        )
@Table(name="provisioning_tag")
// No cache, mutable and critical
public class ProvisioningTagDTO implements java.io.Serializable {

	private static Logger LOG = Logger.getLogger(ProvisioningTagDTO.class);
	 private Integer id;
     private String code;
	 private Integer level;
	 private Integer parentId;
     private Integer deleted;
	 private Boolean inUse = null;

    public ProvisioningTagDTO() {
    }
    
    public ProvisioningTagDTO(ProvisioningTagDTO other) {
    	init(other);
    }

    public void init(ProvisioningTagDTO other) {
    	this.code = other.getCode();
    	this.deleted = other.getDeleted();
    	//this.notes = other.getNotes();
    }
    
    public ProvisioningTagDTO(String code, Integer deleted) {
        this.code = code;
        this.deleted = deleted;
    }
	
	public ProvisioningTagDTO(String code, Integer level, Integer parentId) {
      this.code = code;
      this.level = level;
	  this.parentId = parentId;
    }
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="provisioning_tag_GEN")
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
	    public void setId(Integer id) {
        this.id = id;
    }
    
     @Column(name="code", unique=true, nullable=false)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
	
	@Column(name="level", nullable=true)
    public Integer getLevel() {
        return this.level;
    }    
    public void setLevel(Integer level) {
        this.level = level;
    }
	
   @Column(name="parent_id",nullable=true)
    public Integer getParentId() {
        return this.parentId;
    }    
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Column(name="deleted", nullable=false)
    public Integer getDeleted() {
        return this.deleted;
    }
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
	
	@Transient
	public Boolean getInUse() {
        return this.inUse;
    }
	@Transient
    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }


	/**
	 * Makes sure that all the proxies are loaded, so no session is needed to
	 * use the pojo
	 */
	public String toString() {
		StringBuffer str = new StringBuffer("Service = " +
	     "code=" + code + "," + 
	     "deleted=" +deleted) ;
		
		return str.toString();

	}

}


