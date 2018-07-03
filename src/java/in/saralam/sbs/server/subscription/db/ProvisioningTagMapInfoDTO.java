
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
        name="provisioning_tag_map_info_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="provisioning_tag_map_info",
        allocationSize = 100
        )
@Table(name="provisioning_tag_map_info")
// No cache, mutable and critical
public class ProvisioningTagMapInfoDTO implements java.io.Serializable {

	private static Logger LOG = Logger.getLogger(ProvisioningTagMapInfoDTO.class);

     private Integer id;
     private String parameter;
     private ProvisioningTagMapDTO  provisioningTagMap;
     
    

    public ProvisioningTagMapInfoDTO() {
    }
    
    public ProvisioningTagMapInfoDTO(ProvisioningTagMapInfoDTO other) {
    	init(other);
    }

    public void init(ProvisioningTagMapInfoDTO other) {
    	this.id = other.getId();
    	this.provisioningTagMap = other.getProvisioningTagMap();
	this.parameter = this.getParameter();
    }
    
    public ProvisioningTagMapInfoDTO(int id, ProvisioningTagMapDTO provisioningTagMap) {
        this.id = id;
        this.provisioningTagMap = provisioningTagMap;
    }
    public ProvisioningTagMapInfoDTO(int id, ProvisioningTagMapDTO provisioningTagMap, Integer order, String parameter) {
       this.id = id;
       this.provisioningTagMap = provisioningTagMap;
       this.parameter = parameter;
      
    }
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="provisioning_tag_map_info_GEN")
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="map_id", nullable=false)
    public ProvisioningTagMapDTO getProvisioningTagMap() {
        return this.provisioningTagMap;
    }
    public void setProvisioningTagMap(ProvisioningTagMapDTO provisioningTagMap) {
        this.provisioningTagMap = provisioningTagMap;
    }

    @Column(name="parameter", nullable=false)
    public String getParameter() {
        return this.parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }


    public String toString() {
	StringBuffer str = new StringBuffer("Service = " +
	     "id=" + id + "," + 
	     "provisioningTagMap=" + ((provisioningTagMap == null) ? null : provisioningTagMap)) ;
		
		return str.toString();

    }
	
    

}


