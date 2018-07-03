package in.saralam.sbs.server.openRate.holidayMap;
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

import java.util.ArrayList;
import org.apache.log4j.Logger;

public class HolidayMapWS implements Serializable{

    private static final Logger LOG = Logger.getLogger(HolidayMapWS.class);
    
     private String mapGroup;
     private int day;
     private int month;
     private int year;
     private String description;
     int  id;
    
    
    public HolidayMapWS () {
    }

	 public HolidayMapWS(int id,String mapGroup,int day,int month,int year,String description) {
       this.id=id;
       this.mapGroup=mapGroup;
       this.day=day;
       this.month=month;
       this.year=year;
       this.description=description;
      
      }

    public HolidayMapWS(String mapGroup,int day,int month,int year,String description) {
      
       this.mapGroup = mapGroup;
       this.day = day;
       this.month = month;
       this.year = year;
       this.description = description;
      
      }
   

    public String getMapGroup() {
        return this.mapGroup;
    }
    
    public void setMapGroup(String mapGroup) {
        this.mapGroup = mapGroup;
    }
    
    public int getDay () {
        return this.day ;
    }
    
    public void setDay (int day ) {
        this.day  = day ;
    }
   
    public int getMonth() {
        return this.month;
    }
    
    public void setMonth(int month) {
        this.month = month;
    }
    
    public int getYear() {
        return this.year;
    }
    
    public void setYear(int year) {
        this.year =year;
    }
  
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
   
    
    
    
}


