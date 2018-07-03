package in.saralam.sbs.server.rating.db;

import in.saralam.sbs.server.advancepricing.db.ProductChargeDTO;
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
        name = "event_type_rate_map_GEN",
        table = "jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue = "event_type_rate_map",
        allocationSize = 100
)
@Table(name = "event_type_rate_map")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

public class EventTypeRateMapDTO implements Serializable {

	private int id;
    private ProductChargeRateDTO productChargeRateDTO;
    private RatingEventTypeDTO ratingEventTypeDTO;
    
    public EventTypeRateMapDTO() {
    }

    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "event_type_rate_map_GEN")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_rate_id")
	public ProductChargeRateDTO getProductChargeRateDTO() {
		return productChargeRateDTO;
	}

	public void setProductChargeRateDTO(ProductChargeRateDTO productChargeRateDTO) {
		this.productChargeRateDTO = productChargeRateDTO;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_type_id")
	public RatingEventTypeDTO getRatingEventTypeDTO() {
		return ratingEventTypeDTO;
	}

	public void setRatingEventTypeDTO(RatingEventTypeDTO ratingEventTypeDTO) {
		this.ratingEventTypeDTO = ratingEventTypeDTO;
	}

  
    
   
   

 /*   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id")*/
   

  }


