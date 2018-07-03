package dk.comtalk.billing.server.customer.balance.db;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;

import com.sapienter.jbilling.common.SessionInternalError;
import com.sapienter.jbilling.server.invoice.InvoiceBL;
import com.sapienter.jbilling.server.invoice.db.InvoiceDTO;
import com.sapienter.jbilling.server.item.PricingField;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.process.db.BillingProcessDTO;
import com.sapienter.jbilling.server.user.db.UserDTO;
import com.sapienter.jbilling.server.util.Constants;
import com.sapienter.jbilling.server.util.Util;
import com.sapienter.jbilling.server.util.db.CurrencyDTO;
import java.util.ArrayList;

@Entity
@TableGenerator(
        name="user_balance_GEN",
        table="jbilling_seqs",
        pkColumnName = "name",
        valueColumnName = "next_id",
        pkColumnValue="user_balance",
        allocationSize = 100
        )
@Table(name="user_balance")

public class BalanceDTO implements java.io.Serializable {

	private static Logger LOG = Logger.getLogger(BalanceDTO.class);

     private Integer id;
     private UserDTO baseUserByUserId;
     private CurrencyDTO currency;
     private OrderLineDTO orderLineDTO;
     private OrderDTO orderDTO;
     private BigDecimal balance;
     private Date activeSince;
     private Date activeUntil;
     private Date createDate;
     private int deleted;
	 private Integer versionNum;
     
    public BalanceDTO() {
    }
    
    public BalanceDTO(BalanceDTO other) {
    	init(other);
    }

    public void init(BalanceDTO other) {
    	this.id = other.getId();
    	this.baseUserByUserId = other.getBaseUserByUserId();
    	this.currency = other.getCurrency();
    	this.orderLineDTO = other.getOrderLineDTO();
    	this.orderDTO = other.getOrderDTO();
    	this.activeSince = other.getActiveSince();
    	this.activeUntil = other.getActiveUntil();
    	this.createDate = other.getCreateDate();
		this.balance = other.getBalance();
		
    	    	
    }
    
   

	public BalanceDTO(int id, UserDTO baseUserByUserId, CurrencyDTO currencyDTO, OrderLineDTO orderLineDTO, OrderDTO orderDTO, BigDecimal balance, Date createDatetime, Integer deleted) {
        this.id = id;
        this.baseUserByUserId = baseUserByUserId;
        this.currency = currencyDTO;
        this.orderLineDTO = orderLineDTO;
        this.orderDTO = orderDTO;
        this.createDate = createDatetime;
		this.balance = balance;
        this.deleted = deleted;
    }
	
	public BalanceDTO(int id, UserDTO baseUserByUserId, CurrencyDTO currencyDTO, OrderLineDTO orderLineDTO, OrderDTO orderDTO, BigDecimal balance, Date activeSince, Date activeUntil, Date createDate, Integer deleted) {
		this.id = id;
        this.baseUserByUserId = baseUserByUserId;
        this.currency = currencyDTO;
        this.orderLineDTO = orderLineDTO;
        this.orderDTO = orderDTO;
		this.activeSince = activeSince;
    	this.activeUntil = activeUntil;
        this.createDate = createDate;
		this.deleted = deleted;
		this.balance = balance;
	
	}
   
    @Id @GeneratedValue(strategy=GenerationType.TABLE, generator="user_balance_GEN")
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    public UserDTO getBaseUserByUserId() {
        return this.baseUserByUserId;
    }
    public void setBaseUserByUserId(UserDTO baseUserByUserId) {
        this.baseUserByUserId = baseUserByUserId;
    }
    
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="currency_id", nullable=false)
    public CurrencyDTO getCurrency() {
        return this.currency;
    }
    
    public void setCurrency(CurrencyDTO currencyDTO) {
        this.currency = currencyDTO;
    }

    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_line_id", nullable=false)
   	public OrderLineDTO getOrderLineDTO() {
   		return orderLineDTO;
   	}

   	public void setOrderLineDTO(OrderLineDTO orderLineDTO) {
   		this.orderLineDTO = orderLineDTO;
   	}
   	
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id", nullable=false)
   	public OrderDTO getOrderDTO() {
   		return orderDTO;
   	}

   	public void setOrderDTO(OrderDTO orderDTO) {
   		this.orderDTO = orderDTO;
   	}

    
   
    @Column(name="active_since", length=13)
    public Date getActiveSince() {
        return this.activeSince;
    }
    
    public void setActiveSince(Date activeSince) {
        this.activeSince = activeSince;
    }
    
	@Column(name="balance")
    public BigDecimal getBalance() {
        if (balance == null) {
            return BigDecimal.ZERO;
        }
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    @Column(name="active_until", length=13)
    public Date getActiveUntil() {
        return this.activeUntil;
    }
    
    public void setActiveUntil(Date activeUntil) {
        this.activeUntil = activeUntil;
    }
   
    @Column(name="create_datetime", nullable=false, length=29)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDatetime) {
        this.createDate = createDatetime;
    }
    @Column(name="deleted", nullable=false)
    public int getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
   
	
    @Version
    @Column(name="OPTLOCK")
	public Integer getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public String toString() {
		StringBuffer str = new StringBuffer("Order = " +
	     "id=" + id + "," + 
	     "baseUserByUserId=" + ((baseUserByUserId == null) ? null : baseUserByUserId.getId()) + "," +
	     "currencyDTO=" + currency + "," +
	     "activeSince=" + activeSince + "," +
	     "activeUntil=" + activeUntil + "," +
	     "createDate=" + createDate + "," +
	     "deleted=" + deleted + "," +
	     "versionNum=" + versionNum );
		
		
		return str.toString();

	}
	  
    
    
}



