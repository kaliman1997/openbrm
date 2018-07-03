package in.saralam.sbs.server.openRate.destinationMap.db;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import com.sapienter.jbilling.server.invoice.db.InvoiceLineDTO;
import com.sapienter.jbilling.server.util.csv.Exportable;
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
import com.sapienter.jbilling.server.process.db.BillingProcessDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Util;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;

import java.util.ArrayList;

@Entity
@TableGenerator(
        name="destination_map_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="destination_map",
        allocationSize = 100
        )
@Table(name="destination_map")
public class DestinationMapDTO  implements Serializable {

    private static final Logger LOG = Logger.getLogger(DestinationMapDTO.class);
     private int id;
     private String mapGroup;
     private String prefix;
     private String tierCode;
     private String description;
     private String category;
     private int rank;
     private int deleted;
    public DestinationMapDTO() {
    }

    public DestinationMapDTO(int id, String mapGroup,String prefix, String tierCode, String description, String category, int rank) {
       this.id = id;
       this.mapGroup = mapGroup;
       this.prefix = prefix;
       this.tierCode = tierCode;
       this.description = description;
       this.category = category;
       
       this.rank = rank;
      
    }
   

    
    @Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="destination_map_GEN")
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

       
    @Column(name="map_group", nullable=true)
    public String getMapGroup() {
        return this.mapGroup;
    }
    
    public void setMapGroup(String mapGroup) {
        this.mapGroup = mapGroup;
    }
    @Column(name="prefix ", nullable=true)
    public String getPrefix () {
        return this.prefix ;
    }
    
    public void setPrefix (String prefix ) {
        this.prefix  = prefix ;
    }
    @Column(name="tier_code", nullable=true)
    public String getTierCode() {
        return this.tierCode;
    }
    
    public void setTierCode(String tierCode) {
        this.tierCode = tierCode;
    }
    @Column(name="description", nullable=true)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name="category", nullable=true)
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category =category;
    }
    @Column(name="rank", nullable=false)
    public int getRank() {
        return this.rank;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
   
     @Column(name="deleted", nullable=false)
    public int getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
    
}


