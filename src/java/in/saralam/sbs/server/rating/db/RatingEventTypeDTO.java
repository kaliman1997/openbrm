package in.saralam.sbs.server.rating.db;

//import in.saralam.sbs.server.advancepricing.db.ProductChargeDTO;
import in.saralam.sbs.server.advancepricing.db.ProductChargeRateDTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.sapienter.jbilling.server.user.db.CompanyDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDescription;

@Entity
@TableGenerator(
        name = "rating_event_type_GEN",
        table = "jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue = "rating_event_type",
        allocationSize = 100
)
@Table(name = "rating_event_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

public class RatingEventTypeDTO implements Serializable {


    private int id;
	private String 	eventName;
    private CompanyDTO company;
    private Integer versionNum;
    private Set<ProductChargeRateDTO> productCharge = new HashSet<ProductChargeRateDTO>(0);
    
    public RatingEventTypeDTO() {
    }

    public RatingEventTypeDTO(int id) {
        this.id = id;
    }

    public RatingEventTypeDTO(int id, CompanyDTO entity, String eventName) {
       this.id = id;
       this.company = entity;
       this.eventName = eventName;
    }
    
   
    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "rating_event_type_GEN")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id")
    public CompanyDTO getCompany() {
        return this.company;
    }

    public void setCompany(CompanyDTO entity) {
        this.company = entity;
    }

    @Column(name = "event_name")
    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
   

    @Version
    @Column(name = "OPTLOCK")
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }
    
   

    @Override
    public String toString() {
        return "RatingEventTypeDTO:[" +
               " id=" + id +
               " company=" + company +
               " name =" + eventName +
               " versionNum=" + versionNum + "]";
    }



	

  }


