package in.saralam.sbs.server.pricing.db;


import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractGenericStatus;



import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Transient;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;



@Entity
@DiscriminatorValue("bundle_status")
public class BundleStatusDTO extends AbstractGenericStatus implements java.io.Serializable {


    public BundleStatusDTO() {
    }
	
	public BundleStatusDTO(Integer statusvalue) {
        this.statusValue = statusValue;
    }
	
     public BundleStatusDTO(int id) {
        this.id = id;
    }

	// @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="userStatus")
    // public Set<UserDTO> getBaseUsers() {
        // return this.baseUsers;
    // }
    @Transient
    protected String getTable() {
        return Constants.TABLE_BUNDLE_STATUS;
    }
    
}


