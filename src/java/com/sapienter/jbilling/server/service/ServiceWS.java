package com.sapienter.jbilling.server.service;

import java.io.Serializable;
import java.util.Date;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;
import com.sapienter.jbilling.server.service.db.ServiceStatusDTO;

public class ServiceWS implements Serializable {

    private Integer id;
    private String name;
    private Integer createdBy;
    private Integer statusId;
    private Date createDate;
    private int deleted;
    private Integer versionNum;
    private String statusStr = null;
    private Integer userId = null; // who is buying ?
    private OrderDTO orderDTO;
    private OrderLineDTO orderLineDTO;
    private ServiceStatusDTO serviceStatus;

    public ServiceWS() {
    }

    public ServiceWS(Integer id, Date createDate, Integer statusId, 
    		Integer deleted, Integer userId) {
    	setId(id);

    	setCreateDate(createDate);
    	setStatusId(statusId);
    	setDeleted(deleted.shortValue());
    	setUserId(userId);
    }

    public ServiceWS(Integer id, String name, Date createDate, Integer statusId,
		OrderDTO order, OrderLineDTO orderLine, Integer deleted, Integer userId) {
        setId(id);
	setName(name);
        setCreateDate(createDate);
        setStatusId(statusId);
        setDeleted(deleted.shortValue());
        setUserId(userId);
	setOrderDTO(order);
	setOrderLineDTO(orderLine);
    }

    public ServiceWS(Integer id, String name, Date createDate, ServiceStatusDTO serviceStatus,
                OrderDTO order, OrderLineDTO orderLine, Integer deleted, Integer userId) {
        setId(id);
        setName(name);
        setCreateDate(createDate);
        setServiceStatus(serviceStatus);
        setDeleted(deleted.shortValue());
        setUserId(userId);
        setOrderDTO(order);
        setOrderLineDTO(orderLine);
    }



    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setName(String name) {
       this.name = name;
    } 

    public String getName() {
       return this.name;
    }

    public void setServiceStatus(ServiceStatusDTO serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public ServiceStatusDTO getServiceStatus() {
      return this.serviceStatus;
   
    }



    public void setOrderDTO(OrderDTO order) {
        this.orderDTO = order;
    }

    public void setOrderLineDTO(OrderLineDTO orderLine) {
        this.orderLineDTO = orderLine;
    }

    public OrderDTO getOrderDTO() {
	return this.orderDTO;

    }

    public OrderLineDTO getOrderLineDTO() {
        return this.orderLineDTO;

    }

    public Integer getOrderLineId() {
	return this.orderLineDTO.getId();

    }

    public Integer getOrderId() {
        return this.orderDTO.getId();

    }



    public String toString() {
		StringBuffer str = new StringBuffer(super.toString() + " currencyId= " );
		str.append("lines=");
		str.append("]");
		return str.toString();

    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

}
