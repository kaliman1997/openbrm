package in.saralam.sbs.server.Scheduler;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import org.apache.log4j.Logger;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ScheduleActionParamsWS implements Serializable{

   private 	Integer 	   id;
	private Integer        scheduleActionId;
	private String        value;
	private String         name ;
	
	 private static final Logger LOG = Logger.getLogger(ScheduleActionParamsWS.class);
	    
	  
	    public ScheduleActionParamsWS(){
	    }
		
		
		 public ScheduleActionParamsWS(Integer id,Integer scheduleActionId, String value,String name){
		    setId(id);
			setScheduleActionId(scheduleActionId);
	    	setValue(value);
	    	setName(name);
	    }
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		
		
		public Integer getScheduleActionId() {
			return scheduleActionId;
		}
		public void setScheduleActionId(Integer scheduleActionId) {
			this.scheduleActionId = scheduleActionId;
		}
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		}