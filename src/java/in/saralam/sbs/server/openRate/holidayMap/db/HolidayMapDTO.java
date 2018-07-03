package in.saralam.sbs.server.openRate.holidayMap.db;
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
import org.apache.log4j.Logger;
import java.util.ArrayList;

@Entity
@TableGenerator(
        name="holiday_map_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="holiday_map",
        allocationSize = 100
        )
@Table(name="holiday_map")
public class HolidayMapDTO  implements Serializable  {

    private static final Logger LOG = Logger.getLogger(HolidayMapDTO.class);
    
     private String mapGroup;
     private int day;
     private int month;
     private int year;
     private String description;
     
    private  int  id;
    
    public HolidayMapDTO () {
    }

    public HolidayMapDTO(  int  id,String mapGroup,int day,int month,int year,String description ) {
        this.id = id;
       this.mapGroup = mapGroup;
       this.day = day;
       this.month = month;
       this.year = year;
       this.description = description;
      
       
       
      
    }
   
     @Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="holiday_map_GEN")
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
    @Column(name="day ", nullable=false)
    public int getDay () {
        return this.day ;
    }
    
    public void setDay (int day ) {
        this.day  = day ;
    }
    @Column(name="month", nullable=false)
    public int getMonth() {
        return this.month;
    }
    
    public void setMonth(int month) {
        this.month = month;
    }
     @Column(name="year", nullable=false)
    public int getYear() {
        return this.year;
    }
    
    public void setYear(int year) {
        this.year =year;
    }
    @Column(name="description", nullable=false)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
   
    
    
    
}


