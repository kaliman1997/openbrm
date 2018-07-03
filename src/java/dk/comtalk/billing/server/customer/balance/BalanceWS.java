package dk.comtalk.billing.server.customer.balance;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.apache.log4j.Logger;

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

public class BalanceWS implements java.io.Serializable {

     private static Logger LOG = Logger.getLogger(BalanceWS.class);

     private Integer id;
     private UserDTO baseUserByUserId;
     private CurrencyDTO currencyDTO;
     private OrderLineDTO orderLineDTO;
     private OrderDTO orderDTO;
     private BigDecimal balance;
     private BigDecimal currentBalance;
     private Date activeSince;
     private Date activeUntil;
     private Date createDate;
     private int deleted;
     private Integer versionNum;
     
    public BalanceWS() {
    }
    
    public BalanceWS(BalanceWS other) {
    	init(other);
    }

    public void init(BalanceWS other) {

    	this.id = other.getId();
    	this.baseUserByUserId = other.getBaseUserByUserId();
    	this.currencyDTO = other.getCurrency();
    	this.orderLineDTO = other.getOrderLineDTO();
    	this.orderDTO = other.getOrderDTO();
    	this.activeSince = other.getActiveSince();
    	this.activeUntil = other.getActiveUntil();
    	this.createDate = other.getCreateDate();
	this.balance = other.getBalance();
		
    	    	
    }
    
   

    public BalanceWS(int id, 
	UserDTO baseUserByUserId, 
	CurrencyDTO currencyDTO,
	OrderLineDTO orderLineDTO, 
	OrderDTO orderDTO, 
	BigDecimal balance, 
	Date createDatetime, 
	Integer deleted) {

        this.id = id;
        this.baseUserByUserId = baseUserByUserId;
        this.currencyDTO = currencyDTO;
        this.orderLineDTO = orderLineDTO;
        this.orderDTO = orderDTO;
        this.createDate = createDatetime;
	this.balance = balance;
        this.deleted = deleted;
    }
	
    public BalanceWS(int id,
	UserDTO baseUserByUserId,
	CurrencyDTO currencyDTO,
	OrderLineDTO orderLineDTO,
	OrderDTO orderDTO,
	BigDecimal balance,
	BigDecimal currentBalance,
	Date activeSince,
	Date activeUntil,
	Date createDate,
	Integer deleted) {

	this.id = id;
        this.baseUserByUserId = baseUserByUserId;
        this.currencyDTO = currencyDTO;
        this.orderLineDTO = orderLineDTO;
        this.orderDTO = orderDTO;
	this.activeSince = activeSince;
    	this.activeUntil = activeUntil;
        this.createDate = createDate;
	this.deleted = deleted;
	this.balance = balance;
	this.currentBalance = currentBalance;
	
	}
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public UserDTO getBaseUserByUserId() {
        return this.baseUserByUserId;
    }

    public void setBaseUserByUserId(UserDTO baseUserByUserId) {
        this.baseUserByUserId = baseUserByUserId;
    }
    
    public CurrencyDTO getCurrency() {
        return this.currencyDTO;
    }
    
    public void setCurrency(CurrencyDTO currencyDTO) {
        this.currencyDTO = currencyDTO;
    }
    
    public OrderLineDTO getOrderLineDTO() {
   	return orderLineDTO;
    }

    public void setOrderLineDTO(OrderLineDTO orderLineDTO) {
  	this.orderLineDTO = orderLineDTO;
    }
   	
    public OrderDTO getOrderDTO() {
   	return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
   	this.orderDTO = orderDTO;
    }

    public Date getActiveSince() {
        return this.activeSince;
    }
    
    public void setActiveSince(Date activeSince) {
        this.activeSince = activeSince;
    }
    
    public BigDecimal getBalance() {
        if (balance == null) {
            return BigDecimal.ZERO;
        }
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
   
    public BigDecimal getBalanceInMB() {
        if (balance == null) {
            return BigDecimal.ZERO;
        }
        return balance.divide(new BigDecimal("1048576"), Constants.BIGDECIMAL_SCALE, Constants.BIGDECIMAL_ROUND);
    }


    public BigDecimal getCurrentBalance() {
        if (currentBalance == null) {
            return BigDecimal.ZERO;
        }
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getCurrentBalanceInMB() {
        if (currentBalance == null) {
            return BigDecimal.ZERO;
        }
        return currentBalance.divide(new BigDecimal("1048576"), Constants.BIGDECIMAL_SCALE, Constants.BIGDECIMAL_ROUND);
    }



    
    public Date getActiveUntil() {
        return this.activeUntil;
    }
    
    public void setActiveUntil(Date activeUntil) {
        this.activeUntil = activeUntil;
    }
   
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDatetime) {
        this.createDate = createDatetime;
    }

    public int getDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
   
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
	     "currencyDTO=" + currencyDTO + "," +
	     "activeSince=" + activeSince + "," +
	     "activeUntil=" + activeUntil + "," +
	     "createDate=" + createDate + "," +
	     "deleted=" + deleted + "," +
	     "versionNum=" + versionNum );
		
	return str.toString();

    }
	  
    
    
}



