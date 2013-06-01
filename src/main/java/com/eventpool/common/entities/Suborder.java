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
@Table(name = "SUBORDER")
public class Suborder extends AuditableIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1737813513553962187L;

	@OneToOne(fetch = FetchType.LAZY,cascade=CascadeType.PERSIST)
	@JoinColumn(name="TICKET_SNAPSHOT_ID",insertable=true,updatable=false)
	private TicketSnapShot ticketSnapShot;
	
/*	@Column(name = "TICKET_SNAPSHOT_ID", nullable = false,insertable=false)
	private Long ticketSnapShotId;
*/	
	@Column(name = "TICKET_NAME", nullable = false)
	private String ticketName;

	@Column(name = "TICKET_ID", nullable = false)
	private Long ticketId;

	@Column(name="TICKET_PRICE")
	private Double ticketPrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;
	
	@Column(name = "SUB_CATEGORY_ID")
	private Integer subCategoryId;

	@Column(name="GROSS_AMOUNT")
	private Double grossAmount;
	
	@Column(name="NET_AMOUNT")
	private Double netAmount;
	
	@Column(name="DISCOUNT_AMOUNT")
	private Double discountAmount;
	
	@Column(name="DISCOUNT_COUPON")
	private String dicountCoupon;
	
	@Column(name="STATUS")
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Column(name = "ORGANIZER_NAME")
	private String organizerName;
	
	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="SUBORDER_ID",referencedColumnName="ID")
	private List<Registration> registrations;


	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

/*	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
*/
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

	
	public String getDicountCoupon() {
		return dicountCoupon;
	}

	public void setDicountCoupon(String dicountCoupon) {
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

	public TicketSnapShot getTicketSnapShot() {
		return ticketSnapShot;
	}

	public void setTicketSnapShot(TicketSnapShot ticketSnapShot) {
		this.ticketSnapShot = ticketSnapShot;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTickerId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

}
