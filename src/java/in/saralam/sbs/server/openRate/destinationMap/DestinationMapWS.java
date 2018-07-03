package in.saralam.sbs.server.openRate.destinationMap;
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
import org.apache.log4j.Logger;
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


public class DestinationMapWS  implements Serializable  {

    private static final Logger LOG = Logger.getLogger(DestinationMapWS.class);
     private int id;
     private String  mapGroup;
     private String prefix;
     private String tierCode;
     private String description;
     private String category;
     private int rank;
    
    public DestinationMapWS() {
    }

    public DestinationMapWS(int id, String  mapGroup,String prefix, String tierCode, String description, String category, int rank) {
       this.id = id;
       this. mapGroup = mapGroup;
       this.prefix = prefix;
       this.tierCode = tierCode;
       this.description = description;
       this.category = category;
       
       this.rank = rank;
      
    }
	public DestinationMapWS( String mapGroup,String prefix, String tierCode, String description, String category, int rank) {
     
       this.mapGroup = mapGroup;
	  
       this.prefix = prefix;
       this.tierCode = tierCode;
       this.description = description;
       this.category = category;
       
       this.rank = rank;
      
    }
   
    public String getMapGroup() {
        return this.mapGroup;
    }
    
    public void setMapGroup(String mapGroup) {
        this.mapGroup = mapGroup;
    }
   
    public String getPrefix () {
        return this.prefix ;
    }
    
    public void setPrefix (String prefix ) {
        this.prefix  = prefix ;
    }
   
   
    
    public void setTierCode(String tierCode) {
        this.tierCode = tierCode;
    }
  
   public String getTierCode() {
        return this.tierCode;
    }
	 public String getDescription() {
        return this.description ;
    }
   
    public void setDescription(String description) {
        this.description = description;
    }
   
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String categoryp) {
        this.category =category;
    }
    
    public int getRank() {
        return this.rank;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
   
    
    
}


