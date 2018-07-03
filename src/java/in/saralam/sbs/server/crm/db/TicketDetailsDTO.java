package in.saralam.sbs.server.crm.db;

import java.io.Serializable;
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

@Entity
@TableGenerator(
        name="ticket_details_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="ticket_details",
        allocationSize = 100
        )
@Table(name="ticket_details")

public class TicketDetailsDTO implements Serializable{

	private int id;
	private SupportTicketDTO ticket;
	private Date createdDate;
	private String ticketBody;
	
	
	@Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="ticket_details_GEN")
    @Column(name="id", unique=true, nullable=false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ticket_id", nullable=false)
	public SupportTicketDTO getTicket() {
		return ticket;
	}
	public void setTicket(SupportTicketDTO ticket) {
		this.ticket = ticket;
	}
	
	@Column(name="created_datetime", nullable=false, length=19)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="ticket_body", length=200)
	public String getTicketBody() {
		return ticketBody;
	}
	public void setTicketBody(String ticketBody) {
		this.ticketBody = ticketBody;
	}
	
	
	
}
