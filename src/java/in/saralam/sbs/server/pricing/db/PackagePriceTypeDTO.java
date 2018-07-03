package in.saralam.sbs.server.pricing.db;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.db.AbstractDescription;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.TableGenerator;
@Entity
@TableGenerator(
        name="package_price_type_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="package_price_type",
        allocationSize = 100
        )
@Table(name="package_price_type")
//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)

public class PackagePriceTypeDTO implements java.io.Serializable {


     private int id;
     private String type;
     private int deleted;
     private Set<PackagePriceDTO> packagePrices = new HashSet<PackagePriceDTO>(0);

    public PackagePriceTypeDTO() {
    }
     public PackagePriceTypeDTO(int id){
     this.id = id;
}
	
    public PackagePriceTypeDTO(int id, String type) {
        this.id = id;
        this.type = type;
    }
    
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="package_price_type_GEN")  
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    
    @Column(name="type", nullable=false, length=50)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

 }


