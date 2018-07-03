
package in.saralam.sbs.server.rating;
import  in.saralam.sbs.server.rating.CRatingResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import in.saralam.sbs.server.rating.db.RatingEventTypeDTO;

public abstract class  IRatingEvent {

	/* Basic details */
	private	Integer userId;
	private Integer	serviceId;
	private String description;
	/*Event Type */
	private RatingEventTypeDTO	eventType;
	/* Time of event */
	private	Date	eventStartDate;
	private Date	eventEndDate;
	private	TimeZone	eventTimeZone;
	/* Event quantities */
	private BigDecimal	quantity;
	/* Tax details  */
	private	String	taxCode;
	private double	taxRate;
	private String serviceAlias;
	private Integer result;
	/* post raing:  ba;lance impacts */
	
	private List<CRatingResult> ratingResults ;
	
	
	/* Setter and getters */
	
	 public void setUserId(Integer userId) {
		this.userId = userId;
	}
    public Integer  getUserId(){
		return this.userId;
	}
	
	public void setServiceId(Integer serviceId){
		this.serviceId=serviceId;
    }

	public Integer  getServiceId(){
		return this.serviceId;
	} 

	public void setDescription(String description) {
	    this.description=description;
	}

	public String getDescription(){
		return this.description;
    }
	
	public  void setEventType(RatingEventTypeDTO eventType){

		this.eventType=eventType;
    }

	public RatingEventTypeDTO	getEventType(){
		return this.eventType;
    }
    public void setEventStartDate(Date eventStartDate){
		this.eventStartDate=eventStartDate;
    }

    public Date getEventStartDate(){
		return this.eventStartDate;
    }
	public void setEventEndDate(Date eventEndDate){
		this.eventEndDate=eventEndDate;
    }

    public Date getEventEndDate(){
		return this.eventEndDate;
    }

	public void setEventTimeZone(TimeZone eventTimeZone){
		this.eventTimeZone=eventTimeZone;
    }

    public TimeZone getEventTimeZone(){
		return this.eventTimeZone;
    }
	
	public void setQuantity(BigDecimal quantity){
		this.quantity=quantity;
    }

	public BigDecimal  getQuantity(){
		return this.quantity;
	} 
	
	public void setTaxCode(String taxCode) {
	    this.taxCode=taxCode;
	}

	public String getTaxCode(){
		return this.taxCode;
    }

	public void setTaxRate(double taxRate) {
	    this.taxRate=taxRate;
	}

	public Double getTaxRate(){
		return this.taxRate;
    }
		
	public void setServiceAlias(String serviceAlias) {
	   this.serviceAlias=serviceAlias;
	}

	public String getServiceAlias(){
	  return this.serviceAlias;
	}
	public void setResult(Integer result) {
	   this.result=result;
	}

	public Integer getResult(){
	  return this.result;
	}
	public void setRatingResults(List<CRatingResult> ratingResults){
	   this.ratingResults=ratingResults;
	}
        public List<CRatingResult> getRatingResults(){
	return this.ratingResults;
		}
}
	
	
