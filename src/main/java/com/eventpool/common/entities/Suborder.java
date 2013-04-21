package com.eventpool.common.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;

import com.eventpool.common.type.OrderStatus;

@Entity
@Table(name = "suborder")
public class Suborder extends AuditableIdEntity {

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="ticket_id",nullable = false)
	private Ticket ticket;
	
	@Column(name = "quantity", length = 11)
	private Integer quantity;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
	
	@Column(name = "categoryId")
	private Integer categoryId;

	@Column(name = "sub_category_id")
	private Integer subCategoryId;
	
	@Column(name="ticket_price")
	private Double ticketPrice;

	@Column(name="gross_amount")
	private Double grossAmount;
	
	@Column(name="net_amount")
	private Double netAmount;
	
	@Column(name="discount_amount")
	private Double discountAmount;
	
	@Column(name="discount_coupon")
	private Integer dicountCoupon;
	
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Column(name = "organizer_name")
	private String organizerName;
	
	@OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="suborder_id",referencedColumnName="id")
	private List<Registration> registrations;

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(Double grossAmount) {
		this.grossAmount = grossAmount;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Integer getDicountCoupon() {
		return dicountCoupon;
	}

	public void setDicountCoupon(Integer dicountCoupon) {
		this.dicountCoupon = dicountCoupon;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getOrganizerName() {
		return organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}

	public List<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
	}
	
	@PostUpdate
	@PostPersist
	public void onPersist(){
		List<Registration> listOfRegistrations = this.getRegistrations();
		if ( listOfRegistrations!= null && listOfRegistrations.size()>0){
			for ( Registration registration : listOfRegistrations){
				registration.setSuborderId(this.getId());
			}
		}
	}
	
}
