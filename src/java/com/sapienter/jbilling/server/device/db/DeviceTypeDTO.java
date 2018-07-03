package com.sapienter.jbilling.server.device.db;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.sapienter.jbilling.server.util.db.AbstractDescription;
import com.sapienter.jbilling.server.util.Constants;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.Transient;

@Entity
@Table(name="device_type")

public class DeviceTypeDTO extends AbstractDescription implements java.io.Serializable {


    private int id;
     private Set<DeviceDTO> devices = new HashSet<DeviceDTO>(0);

    public DeviceTypeDTO() {
    }

	
    public DeviceTypeDTO(int id) {
        this.id = id;
    }

    public DeviceTypeDTO(int id, Set<DeviceDTO> devices) {
       this.id = id;
       this.devices = devices;
    }
   
    @Id 
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="deviceType")
    public Set<DeviceDTO> getDeviceDTOs() {
        return this.devices;
    }
    
    public void setDeviceDTOs(Set<DeviceDTO> devices) {
        this.devices = devices;
    }

    @Transient
    protected String getTable() {
        return Constants.TABLE_DEVICE_TYPE;
    }

    @Transient
    public String getTitle(Integer languageId) {
        return getDescription(languageId, "description");
    }

	    

}


