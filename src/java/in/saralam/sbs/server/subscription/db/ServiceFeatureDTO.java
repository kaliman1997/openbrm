
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
        name="service_feature_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="service_feature",
        allocationSize = 100
        )
@Table(name="service_feature")
// No cache, mutable and critical
public class ServiceFeatureDTO implements java.io.Serializable {

     private static Logger LOG = Logger.getLogger(ServiceFeatureDTO.class);

     private Integer id;
     private ServiceDTO serviceId;
     private ServiceFeatureStatusDTO serviceFeatureStatus;
     private ProvisioningTagMapDTO provisioningTagMap;
     private List<ServiceFeatureInfoDTO> infos = new ArrayList<ServiceFeatureInfoDTO>(0);
     private Integer deleted;
     private Integer level;
     private ServiceFeatureDTO parent;
     private Set<ServiceFeatureDTO> children = new HashSet<ServiceFeatureDTO>(0);


    public ServiceFeatureDTO() {
    }
    
    public ServiceFeatureDTO(ServiceFeatureDTO other) {
    	init(other);
    }

    public void init(ServiceFeatureDTO other) {
    	this.id = other.getId();
    	this.serviceId = other.getServiceId();
      	this.provisioningTagMap = other.getProvisioningTagMap(); 
      	this.serviceFeatureStatus = other.getServiceFeatureStatus(); 
    }
    
    public ServiceFeatureDTO(int id, ProvisioningTagMapDTO provisioningTagMap) {
        this.id = id;
        this.provisioningTagMap = provisioningTagMap;
    }
    public ServiceFeatureDTO(int id, ServiceDTO serviceId, ProvisioningTagMapDTO provisioningTagMap) {
       this.id = id;
       this.serviceId = serviceId;
       this.provisioningTagMap = provisioningTagMap;
      
    }
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="service_feature_GEN")
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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="status_id", nullable=false)
    public ServiceFeatureStatusDTO getServiceFeatureStatus() {
        return this.serviceFeatureStatus;
    }

    public void setServiceFeatureStatus(ServiceFeatureStatusDTO serviceFeatureStatus) {
        this.serviceFeatureStatus = serviceFeatureStatus;
    }


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="prov_tag_map_id", nullable=false)
    public ProvisioningTagMapDTO getProvisioningTagMap() {
        return this.provisioningTagMap;
    }
    
    public void setProvisioningTagMap(ProvisioningTagMapDTO provisioningTagMap) {
        this.provisioningTagMap = provisioningTagMap;
    }

    @Column(name="deleted", nullable=false)
    public Integer getDeleted() {
        return this.deleted;
    }
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

       @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    public Set<ServiceFeatureDTO> getChildren() {
        return children;
    }

    public void setChildren(Set<ServiceFeatureDTO> children) {
        this.children = children;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    public ServiceFeatureDTO getParent() {
        return this.parent;
    }

    public void setParent(ServiceFeatureDTO parent) {
        this.parent = parent;
    }

    @Column(name = "level")
    public Integer getLevel() {
      return level;
    }

    public void setLevel(Integer level) {
      this.level = level;
    }


    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="serviceFeature")
    @OrderBy(clause="id")
    public List<ServiceFeatureInfoDTO> getInfos() {
        return this.infos;
    }

    public void setInfos(List<ServiceFeatureInfoDTO> infos) {
        this.infos = infos;
    }

    public String toString() {
	StringBuffer str = new StringBuffer("Service = " +
	     "id=" + id + "," + 
	     "serviceId=" + ((serviceId == null) ? null : serviceId.getId()) + "," +
	     "provisioningTagMap=" + ((provisioningTagMap == null) ? null : provisioningTagMap)) ;
		
	return str.toString();

   }
	
}


