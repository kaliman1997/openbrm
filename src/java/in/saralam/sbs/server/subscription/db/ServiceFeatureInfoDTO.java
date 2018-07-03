
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
        name="service_feature_info_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="service_feature_info",
        allocationSize = 100
        )
@Table(name="service_feature_info")
// No cache, mutable and critical
public class ServiceFeatureInfoDTO implements java.io.Serializable {

	private static Logger LOG = Logger.getLogger(ServiceFeatureInfoDTO.class);

     private Integer id;
     private ServiceFeatureDTO serviceFeature;
     private String parameter;

    public ServiceFeatureInfoDTO() {
    }
    
    public ServiceFeatureInfoDTO(ServiceFeatureInfoDTO other) {
    	init(other);
    }

    public void init(ServiceFeatureInfoDTO other) {
    	this.id = other.getId();
    	this.serviceFeature = other.getServiceFeature();
    }
    
    public ServiceFeatureInfoDTO(Integer id, ServiceFeatureDTO  serviceFeature) {
        this.id = id;
        this.serviceFeature = serviceFeature;
    }
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="service_feature_info_GEN")
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="service_feature_id", nullable=false)
    public ServiceFeatureDTO getServiceFeature() {
        return this.serviceFeature;
    }
    public void setServiceFeature(ServiceFeatureDTO serviceFeature) {
        this.serviceFeature = serviceFeature;
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
	     "id=" + id);
	return str.toString();

   }
	
}


