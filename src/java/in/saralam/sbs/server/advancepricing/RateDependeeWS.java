package in.saralam.sbs.server.advancepricing;

import in.saralam.sbs.server.advancepricing.db.RateDependencyTypeDTO;

import java.io.Serializable;
import java.math.BigDecimal;

import com.sapienter.jbilling.server.util.db.CurrencyDTO;

public class RateDependeeWS implements Serializable{

	private int id;
	private CurrencyDTO currency;
	private BigDecimal minBalance;
	private BigDecimal maxBalance;
	private RateDependencyTypeDTO dependencyType;
	private int versionNum;
	
	public RateDependeeWS(){
	}
	
	public RateDependeeWS(int id, CurrencyDTO currency, RateDependencyTypeDTO dependencyType, 
					BigDecimal minBalance, BigDecimal maxBalance, int versionNum){
		
		setId(id);
		setCurrency(currency);
		setDependencyType(dependencyType);
		setMinBalance(minBalance);
		setMaxBalance(maxBalance);
		setVersionNum(versionNum);
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CurrencyDTO getCurrency() {
		return currency;
	}
	public void setCurrency(CurrencyDTO currency) {
		this.currency = currency;
	}
	public BigDecimal getMinBalance() {
		return minBalance;
	}
	public void setMinBalance(BigDecimal minBalance) {
		this.minBalance = minBalance;
	}
	public BigDecimal getMaxBalance() {
		return maxBalance;
	}
	public void setMaxBalance(BigDecimal maxBalance) {
		this.maxBalance = maxBalance;
	}
	public RateDependencyTypeDTO getDependencyType() {
		return dependencyType;
	}
	public void setDependencyType(RateDependencyTypeDTO dependencyType) {
		this.dependencyType = dependencyType;
	}
	public int getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}
	
	
}
