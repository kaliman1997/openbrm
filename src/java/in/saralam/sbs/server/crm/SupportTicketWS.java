package in.saralam.sbs.server.crm;

import in.saralam.sbs.server.crm.db.TicketStatusDTO;
import com.sapienter.jbilling.server.security.WSSecured;

import java.io.Serializable;
import java.util.Date;

import com.sapienter.jbilling.server.user.db.UserDTO;

public class SupportTicketWS implements java.io.Serializable, WSSecured {
	
	private int id;
	private String subject;
	private Integer baseUserId;
	private Integer assignedUserId;
	private TicketStatusDTO ticketStatus;
	private Date createdDate;
	private Date lastModified;
	private String ticketBody;
	
	public SupportTicketWS() {
	}
	public SupportTicketWS(Date createdDate,String ticketBody) {
		setCreatedDate(createdDate);
		setTicketBody(ticketBody);	
	}
	public SupportTicketWS(Integer id,String subject,TicketStatusDTO ticketStatus,Date createdDate,Date lastModified) {
		setId(id);
		setSubject(subject);
		setTicketStatus(ticketStatus);
		setCreatedDate(createdDate);
		setLastModified(lastModified);	
	}	
	
	//public SupportTicketWS(Integer id,String subject,TicketStatusDTO ticketStatus) {	
	public SupportTicketWS(Integer id,String subject,Integer baseUserId,TicketStatusDTO ticketStatus,Date createdDate,Date lastModified,String ticketBody) {
		setId(id);
		setSubject(subject);
		setBaseUserID(baseUserId);
		setTicketStatus(ticketStatus);
		setCreatedDate(createdDate);
		setLastModified(lastModified);
		setTicketBody(ticketBody);	
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Integer getBaseUserID() {
		return baseUserId;
	}
	public void setBaseUserID(Integer baseUserId) {
		this.baseUserId = baseUserId;
	}
	public Integer getAssignedUserID() {
		return assignedUserId;
	}
	public void setAssignedUserID(Integer assignedUserId) {
		this.assignedUserId = assignedUserId;
	}
	public TicketStatusDTO getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(TicketStatusDTO ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public String getTicketBody() {
		return ticketBody;
	}
	public void setTicketBody(String ticketBody) {
		this.ticketBody = ticketBody;
	}
	
	  @Override
    public Integer getOwningEntityId() {
        if (getBaseUserID() == null) {
            return null;
        }
        return getBaseUserID();
    }
    
    @Override
    public Integer getOwningUserId() {
        return null;
    }
}
