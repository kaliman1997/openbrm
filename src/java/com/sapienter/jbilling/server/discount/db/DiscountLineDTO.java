package com.sapienter.jbilling.server.discount.db;

import java.io.Serializable;
import java.math.BigDecimal;

import com.sapienter.jbilling.server.item.db.ItemDTO;
import com.sapienter.jbilling.server.order.db.OrderDTO;
import com.sapienter.jbilling.server.order.db.OrderLineDTO;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@TableGenerator(
	name = "discount_line_GEN",
	table = "jbilling_seqs",
	pkColumnName = "name",
	valueColumnName = "next_id",
	pkColumnValue = "discount_line",
	allocationSize = 100
)
@Table(name = "discount_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DiscountLineDTO implements Serializable {

	private Integer id;
	private DiscountDTO discount;
	private ItemDTO item;
	private OrderDTO order;
	private OrderLineDTO discountOrderLine;	// Order Line created by this discount line.
	
	private BigDecimal orderLineAmount;		// Order Line amount which will be used in product level discounts.
	private String description;				// description which will be used in invoice for discount line.
	
	public DiscountLineDTO() {
		
	}
	
	public DiscountLineDTO(DiscountLineDTO other) {
        this.id = other.id;
        this.discount = other.getDiscount();
        this.item = other.getItem();
        this.order = other.getPurchaseOrder();
        this.orderLineAmount = other.getOrderLineAmount();
        this.description = other.getDescription();
        this.discountOrderLine = other.getDiscountOrderLine();
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "discount_line_GEN")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="discount_id", nullable=false)
	public DiscountDTO getDiscount() {
		return discount;
	}
	
	public void setDiscount(DiscountDTO discount) {
		this.discount = discount;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id", nullable=true)
	public ItemDTO getItem() {
		return item;
	}
	
	public void setItem(ItemDTO item) {
		this.item = item;
	}
	
	@Transient
	public boolean hasItem() {
		return getItem() != null;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id", nullable=false)
	public OrderDTO getPurchaseOrder() {
		return order;
	}
	
	public void setPurchaseOrder(OrderDTO order) {
		this.order = order;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="discount_order_line_id", nullable=true)
	public OrderLineDTO getDiscountOrderLine() {
		return discountOrderLine;
	}

	public void setDiscountOrderLine(OrderLineDTO discountOrderLine) {
		this.discountOrderLine = discountOrderLine;
	}
	
	@Column(name="order_line_amount", nullable=true, precision=17, scale=17)
	public BigDecimal getOrderLineAmount() {
		return orderLineAmount;
	}

	public void setOrderLineAmount(BigDecimal orderLineAmount) {
		this.orderLineAmount = orderLineAmount;
	}

	@Column(name="description", length=1000, nullable=false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public boolean isProductLevelDiscount() {
		return (hasItem());
	}
	
	@Transient
	public boolean isOrderLevelDiscount() {
		return (!hasItem());
	}

	@Override
	public String toString() {
		return "DiscountLineDTO id: " + this.id +
			   ", Discount: " + this.discount +
			   ", Item: " + this.item +
			   ", Order: " + (this.order != null ? this.order.getId() : "") +
			   ", Discount SubOrder: " + (this.discountOrderLine != null ? this.discountOrderLine.getId() : "");		
	}
}
