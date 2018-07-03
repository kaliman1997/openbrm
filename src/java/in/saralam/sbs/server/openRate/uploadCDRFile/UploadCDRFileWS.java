package in.saralam.sbs.server.openRate.uploadCDRFile;
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

public class UploadCDRFileWS implements Serializable{

    private static final Logger LOG = Logger.getLogger(UploadCDRFileWS.class);
    
     
     private String name;
     int  id;
     private Date date;
     private String type;
	 private String status;
    public UploadCDRFileWS () {
    }

	 public UploadCDRFileWS(int id,String name,Date date,String status,String type) {
       this.id=id;
       this.name=name;
       this.date=date;
	   this.status=status;
	   this.type=type;
      }

    public UploadCDRFileWS(String name,Date date,String status,String type) {
       this.name = name;
	   this.date = date;
	   this.status=status;
	   this.type=type;
      
      }
   

    
  
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
   

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}


