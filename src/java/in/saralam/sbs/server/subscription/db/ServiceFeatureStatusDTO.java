package in.saralam.sbs.server.subscription.db;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.sapienter.jbilling.server.util.Constants;
import in.saralam.sbs.server.subscription.SubscriptionConstants;
import com.sapienter.jbilling.server.util.db.AbstractGenericStatus;


@Entity
@DiscriminatorValue("service_feature_status")
public class ServiceFeatureStatusDTO  extends AbstractGenericStatus implements java.io.Serializable {


     private Set<ServiceFeatureDTO> serviceFeatures = new HashSet<ServiceFeatureDTO>(0);

    public ServiceFeatureStatusDTO() {
    }

	
    public ServiceFeatureStatusDTO(int statusValue) {
        this.statusValue = statusValue;
    }
    public ServiceFeatureStatusDTO(int statusValue, Set<ServiceFeatureDTO> serviceFeatures) {
       this.statusValue = statusValue;
       this.serviceFeatures = serviceFeatures;
    }

    @Transient
    protected String getTable() {
    	return SubscriptionConstants.TABLE_SERVICE_FEATURE_STATUS;
    }

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="serviceFeatureStatus")
    public Set<ServiceFeatureDTO> getServiceFeature() {
        return this.serviceFeatures;
    }
    
    public void setServiceFeature(Set<ServiceFeatureDTO> serviceFeatures) {
        this.serviceFeatures = serviceFeatures;
    }
}


