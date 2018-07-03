package in.saralam.sbs.server.crm.db;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(
        name="ticket_status_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="ticket_status",
        allocationSize = 100
        )
@Table(name="ticket_status")
public class TicketStatusDTO implements Serializable {
	
	  private int id;
	     private String type;
	     private int deleted;
	     private Set<TicketStatusDTO> packagePrices = new HashSet<TicketStatusDTO>(0);

	    public TicketStatusDTO() {
	    }
	     public TicketStatusDTO(int id){
	     this.id = id;
	}
		
	    public TicketStatusDTO(int id, String type) {
	        this.id = id;
	        this.type = type;
	    }
	    
	   
	    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="ticket_status_GEN")  
	    @Column(name="id", unique=true, nullable=false)
	    public int getId() {
	        return this.id;
	    }
	    
	    public void setId(int id) {
	        this.id = id;
	    }

	    
	    @Column(name="type", nullable=false, length=50)
	    public String getType() {
	        return this.type;
	    }
	    
	    public void setType(String type) {
	        this.type = type;
	    }

}
