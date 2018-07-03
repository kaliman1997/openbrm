package in.saralam.sbs.server.advancepricing.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "rate_dependency_type")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class RateDependencyTypeDTO implements Serializable{

private static final Logger LOG = Logger.getLogger(RateDependencyTypeDTO.class);
	
	private int id;
	private String type;
	
	public RateDependencyTypeDTO(){
	}
	
	public RateDependencyTypeDTO(int id){
		this.id = id;
	}
	
	public RateDependencyTypeDTO(int id, String type){
		this.id = id;
		this.type = type;
	}
	
	@Id   
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
