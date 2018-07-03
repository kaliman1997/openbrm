package in.saralam.sbs.server.openRate.worldZoneMap;
import java.util.Date;

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
import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.invoice.InvoiceBL;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.process.db.BillingProcessDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Util;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;
import org.apache.log4j.Logger;
import java.util.ArrayList;



public class WorldZoneMapWS  implements Serializable  {

    private static final Logger LOG = Logger.getLogger( WorldZoneMapWS.class);
     private int id;
    private String mapGroup;
     private String tierCode;
     private String worldZone;
    
   
    
    public WorldZoneMapWS() {
    }

    public WorldZoneMapWS(int id, String mapGroup,String tierCode, String worldZone) {
       this.id = id;
       this.mapGroup = mapGroup;
       this.tierCode = tierCode;
       this.worldZone = worldZone;
       
      
      
    }
     public WorldZoneMapWS(String mapGroup,String tierCode, String worldZone) {
      
       this.mapGroup = mapGroup;
       this.tierCode = tierCode;
       this.worldZone = worldZone;
       
      
      
    }
   


    
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

       
   
    public String getMapGroup() {
        return this.mapGroup;
    }
    
    public void setMaxGroup(String mapGroup) {
        this.mapGroup = mapGroup;
    }
   
    public String getTierCode() {
        return this.tierCode ;
    }
    
    public void setTierCode(String tierCode ) {
        this.tierCode =tierCode ;
    }
   
    public String getWorldZone() {
        return this.worldZone ;
    }
    
    public void setWorldZone(String worldZone) {
        this.worldZone =worldZone ;
    }
    
    
  
    
    
}


