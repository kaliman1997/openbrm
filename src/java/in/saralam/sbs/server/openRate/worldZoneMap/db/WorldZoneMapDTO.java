package in.saralam.sbs.server.openRate.worldZoneMap.db;
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
        name="world_zone_map_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="world_zone_map",
        allocationSize = 100
        )
@Table(name="world_zone_map")
public class WorldZoneMapDTO  implements Serializable {

    private static final Logger LOG = Logger.getLogger(WorldZoneMapDTO.class);
     private int id;
     private String mapGroup;
     private String tierCode;
     private String worldZone;
   
    
   
    
    public WorldZoneMapDTO() {
    }

    public WorldZoneMapDTO(int id, String mapGroup,String tierCode, String worldZone) {
       this.id = id;
       this.mapGroup = mapGroup;
       this.tierCode = tierCode;
       this.worldZone = worldZone;
       
       
      
      
    }
   

    
    @Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="world_zone_map_GEN")
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

       
    @Column(name="map_group", nullable=false)
    public String getMapGroup() {
        return this.mapGroup;
    }
    
    public void setMapGroup(String mapGroup) {
        this.mapGroup = mapGroup;
    }
    @Column(name="tier_code ", nullable=false)
    public String getTierCode() {
        return this.tierCode ;
    }
    
    public void setTierCode(String tierCode ) {
        this.tierCode =tierCode ;
    }
    @Column(name="world_zone ", nullable=false)
    public String getWorldZone() {
        return this.worldZone ;
    }
    
    public void setWorldZone(String worldZone) {
        this.worldZone =worldZone ;
    }
    
    
  
    
    
}


