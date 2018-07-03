package in.saralam.sbs.server.notification;

import java.io.Serializable;

public class NotificationsConfigurationWS implements Serializable {

  private int id;	
  private Integer eventName;
  private Integer message;
  private Integer notifyType;
  private Integer status;
  private int deleted;
  
  public int getId(){
	    return this.id;
	  }
	  
	  public void setId(int id){
	    this.id = id;
	  }
  
  public Integer getEventName(){
    return this.eventName;
  }
  
  public void setEventName(Integer eventName){
    this.eventName = eventName;
  }
  
  public Integer getMessage(){
    return this.message;
  }
  
  public void setMessage(Integer message){
    this.message = message;
  }
  
  public Integer getNotifyType(){
    return this.notifyType;
  }
  
  public void setNotifyType(Integer notifyType){
    this.notifyType = notifyType;
  }
  
  public Integer getStatus(){
    return this.status;
  }
  
  public void setStatus(Integer status){
    this.status = status;
  }
  
  public Integer getDeleted(){
	    return this.deleted;
	  }
	  
	  public void setDeleted(Integer deleted){
	    this.deleted = deleted;
	  }

}