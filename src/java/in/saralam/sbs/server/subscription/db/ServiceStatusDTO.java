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
import com.sapienter.jbilling.server.util.db.AbstractGenericStatus;


@Entity
@DiscriminatorValue("service_status")
public class ServiceStatusDTO  extends AbstractGenericStatus implements java.io.Serializable {


     private Set<ServiceDTO> serviceDTOs = new HashSet<ServiceDTO>(0);

    public ServiceStatusDTO() {
    }

	
    public ServiceStatusDTO(int statusValue) {
        this.statusValue = statusValue;
    }
    public ServiceStatusDTO(int statusValue, Set<ServiceDTO> serviceDTOs) {
       this.statusValue = statusValue;
       this.serviceDTOs = serviceDTOs;
    }

    @Transient
    protected String getTable() {
    	return Constants.TABLE_SERVICE_STATUS;
    }

@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="serviceStatus")
    public Set<ServiceDTO> getServices() {
        return this.serviceDTOs;
    }
    
    public void setServices(Set<ServiceDTO> serviceDTOs) {
        this.serviceDTOs = serviceDTOs;
    }
}


