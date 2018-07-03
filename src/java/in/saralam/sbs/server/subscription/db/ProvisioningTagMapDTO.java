
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
import org.hibernate.annotations.OrderBy;


import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.invoice.InvoiceBL;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.item.db.ItemDTO;
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
        name="provisioning_tag_map_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="provisioning_tag_map",
        allocationSize = 100
        )
@Table(name="provisioning_tag_map")
// No cache, mutable and critical
public class ProvisioningTagMapDTO implements java.io.Serializable {

     private static Logger LOG = Logger.getLogger(ProvisioningTagMapDTO.class);

     private Integer id;
     private ItemDTO itemId;
     private Integer level;
     private Integer deleted;
     private ProvisioningTagDTO parent;
     private Set<ProvisioningTagMapDTO> children = new HashSet<ProvisioningTagMapDTO>(0);
     private ProvisioningTagDTO provisioningTag;
     private List<ProvisioningTagMapInfoDTO> infos = new ArrayList<ProvisioningTagMapInfoDTO>(0);
     
    

    public ProvisioningTagMapDTO() {
    }
    
    public ProvisioningTagMapDTO(ProvisioningTagMapDTO other) {
    	init(other);
    }

    public void init(ProvisioningTagMapDTO other) {
    	this.id = other.getId();
    	this.itemId = other.getItemId();
      	this.provisioningTag = other.getProvisioningTag(); 
    	//this.notes = other.getNotes();
    }
    
    public ProvisioningTagMapDTO(int id, ProvisioningTagDTO provisioningTag) {
        this.id = id;
        this.provisioningTag = provisioningTag;
    }
    public ProvisioningTagMapDTO(int id, ItemDTO itemId, ProvisioningTagDTO provisioningTag) {
       this.id = id;
       this.itemId = itemId;
       this.provisioningTag = provisioningTag;
      
    }
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="provisioning_tag_map_GEN")
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id", nullable=false)
    public ItemDTO getItemId() {
        return this.itemId;
    }
    public void setItemId(ItemDTO itemId) {
        this.itemId = itemId;
    }
 

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="tag_id", nullable=false)
    public ProvisioningTagDTO getProvisioningTag() {
        return this.provisioningTag;
    }
    
    public void setProvisioningTag(ProvisioningTagDTO provisioningTag) {
        this.provisioningTag = provisioningTag;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    public Set<ProvisioningTagMapDTO> getChildren() {
        return children;
    }

    public void setChildren(Set<ProvisioningTagMapDTO> children) {
        this.children = children;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    public ProvisioningTagDTO getParent() {
        return this.parent;
    }

    public void setParent(ProvisioningTagDTO parent) {
        this.parent = parent;
    }
	
    @Column(name = "level")
    public Integer getLevel() {
      return level;
    }

    public void setLevel(Integer level) {
      this.level = level;
    }


    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="provisioningTagMap")
    @OrderBy(clause="id")
    public List<ProvisioningTagMapInfoDTO> getInfos() {
        return this.infos;
    }
    
    public void setInfos(List<ProvisioningTagMapInfoDTO> infos) {
        this.infos = infos;
    }


    @Column(name="deleted", nullable=true)
    public Integer getDeleted() {
        return this.deleted;
    }
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }


    public String toString() {
		StringBuffer str = new StringBuffer("Service = " +
	     "id=" + id + "," + 
	     "serviceId=" + ((itemId == null) ? null : itemId.getId()) + "," +
	     "provisioningTag=" + ((provisioningTag == null) ? null : provisioningTag)) ;
		
		return str.toString();

	}
	
    // default values
    @Transient
    public void setDefaults() {

       
    }
    
    

}


