package in.saralam.sbs.server.advancepricing.db;


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
        name="charge_type_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="charge_type",
        allocationSize = 100
        )
@Table(name="charge_type")
public class ChargeTypeDTO implements Serializable {
	
	  private int id;
	     private String type;
	     private int deleted;
	     private Set<ChargeTypeDTO> packagePrices = new HashSet<ChargeTypeDTO>(0);

	    public ChargeTypeDTO() {
	    }
	     public ChargeTypeDTO(int id){
	     this.id = id;
	}
		
	    public ChargeTypeDTO(int id, String type) {
	        this.id = id;
	        this.type = type;
	    }
	    
	   
	    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="charge_type_GEN")  
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
