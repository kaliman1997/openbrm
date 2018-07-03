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

import com.sapienter.jbilling.server.user.db.UserDTO;


@Entity
@TableGenerator(
        name="support_ticket_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="support_ticket",
        allocationSize = 100
        )
@Table(name="support_ticket")

public class SupportTicketDTO implements Serializable{

	private int id;
	private String subject;
	private UserDTO baseUser;
	private UserDTO assignedUser;
	private TicketStatusDTO ticketStatus;
	private Date createdDate;
	private Date lastModified;
	
	
	@Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="support_ticket_GEN")
    @Column(name="id", unique=true, nullable=false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="subject")
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
	public UserDTO getBaseUser() {
		return baseUser;
	}
	public void setBaseUser(UserDTO baseUser) {
		this.baseUser = baseUser;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="assigned_user_id", nullable=false)
	public UserDTO getAssignedUser() {
		return assignedUser;
	}
	public void setAssignedUser(UserDTO assignedUser) {
		this.assignedUser = assignedUser;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="status_id", nullable=false)
	public TicketStatusDTO getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(TicketStatusDTO ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	
	@Column(name="created_datetime", nullable=false, length=19)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="last_modified", nullable=false, length=19)
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	
	
}
