package in.saralam.sbs.server.crm;

import in.saralam.sbs.server.crm.db.SupportTicketDTO;

import java.io.Serializable;
import java.util.Date;

public class TicketDetailsWS implements Serializable{

	private int id;
	private SupportTicketDTO ticket;
	private Date createdDate;
	private String ticketBody;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SupportTicketDTO getTicket() {
		return ticket;
	}
	public void setTicket(SupportTicketDTO ticket) {
		this.ticket = ticket;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getTicketBody() {
		return ticketBody;
	}
	public void setTicketBody(String ticketBody) {
		this.ticketBody = ticketBody;
	}
	
	
}
