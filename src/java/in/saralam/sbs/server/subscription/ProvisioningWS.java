package in.saralam.sbs.server.subscription;
	
import java.io.Serializable;
import java.util.Date;
import org.apache.log4j.Logger;	
import com.sapienter.jbilling.server.util.db.AbstractGenericStatus;

public class ProvisioningWS implements Serializable {
	
	private Integer id;
    private String tagCode = null;
	private Integer tagLevel = null;
	private String tagParent = null;
	private Boolean inUse = null;
	
	
    private static final Logger LOG = Logger.getLogger(ProvisioningWS.class);

    public Integer getId() {
	return id;
    }
	
	public void setId(int id) {
		this.id = id;
	}
	
			
  	public ProvisioningWS() {
	}
		
		public ProvisioningWS(String tagCode, Integer tagLevel, String tagParent) {		    
		  this.tagCode = tagCode;
		  this.tagLevel = tagLevel;
		  this.tagParent = tagParent;
	    }
	    
	    public ProvisioningWS(Integer id, String tagCode) {		    
		setId(id);               
	    setTagCode(tagCode);
	    }             
	
		public String getTagCode() {
			return tagCode;
		}
	
		public void setTagCode(String tagCode) {
			this.tagCode = tagCode;
		}
		
		public Integer getTagLevel() {
			return tagLevel;
		}
	
		public void setTagLevel(Integer tagLevel) {
			this.tagLevel = tagLevel;
		}
		
		public String getTagParent() {
			return tagParent;
		}
	
		public void setTagParent(String tagParent) {
			this.tagParent = tagParent;
		}
		
		public Boolean getInUse(){
		 return inUse;
		}
		
		public void setInUse(Boolean inUse){
		 this.inUse = inUse;
		}
	
	}
