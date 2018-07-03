package in.saralam.sbs.server.advancepricing.db;

import java.io.Serializable;
import java.math.BigDecimal;

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
import javax.persistence.Version;

import org.apache.log4j.Logger;

import com.sapienter.jbilling.server.util.db.CurrencyDTO;


@Entity
@TableGenerator(
        name="rate_dependee_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="rate_dependee",
        allocationSize = 100
        )
@Table(name="rate_dependee")
public class RateDependeeDTO implements Serializable{

	private static final Logger LOG = Logger.getLogger(RateDependeeDTO.class);
	
	
	private int id;
	private CurrencyDTO currency;
	private BigDecimal minBalance;
	private BigDecimal maxBalance;
	private RateDependencyTypeDTO dependencyType;
	private int versionNum;
	
	public RateDependeeDTO(){
	}
	
	public RateDependeeDTO(int id){
		this.id = id;
	}

	public RateDependeeDTO(int id, CurrencyDTO currency, RateDependencyTypeDTO dependencyType, 
					BigDecimal minBalance, BigDecimal maxBalance, int versionNum){
		
		this.id = id;
		this.currency = currency;
		this.dependencyType = dependencyType;
		this.minBalance = minBalance;
		this.maxBalance = maxBalance;
		this.versionNum = versionNum;
	}
	
	
	
	@Id   @GeneratedValue(strategy=GenerationType.TABLE, generator="rate_dependee_GEN")
    @Column(name="id", unique=true, nullable=false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="currency_id", nullable=true)
	public CurrencyDTO getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyDTO currency) {
		this.currency = currency;
	}

	@Column(name="min_balance", precision=10, scale=0)
	public BigDecimal getMinBalance() {
		return minBalance;
	}

	public void setMinBalance(BigDecimal minBalance) {
		this.minBalance = minBalance;
	}

	@Column(name="max_balance", precision=10, scale=0)
	public BigDecimal getMaxBalance() {
		return maxBalance;
	}

	public void setMaxBalance(BigDecimal maxBalance) {
		this.maxBalance = maxBalance;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dependency_type", nullable=true)
	public RateDependencyTypeDTO getDependencyType() {
		return dependencyType;
	}

	public void setDependencyType(RateDependencyTypeDTO dependencyType) {
		this.dependencyType = dependencyType;
	}

	@Version
    @Column(name="OPTLOCK" , nullable=false)
	public int getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}
	
	
	
	
}
