package com.eventpool.order.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class Invoice implements Serializable {
	
	private Long id;

	private Date createdDate;

	private long createdBy;

	private Date modifiedDate;

	private Boolean deleted;

	private Date invoiceDate;

	private Long orderId;

	private Date orderDate;

	private String paymentDescription;

	private DespatchInfo despatchInfo = new DespatchInfo();

	private SellerInfo sellerInfo = new SellerInfo();

	private String reversePickup;

	private Address buyer = new Address();

	private ItemInfo itemInfo = new ItemInfo();

	private Long caseNumber;

	private String bagSize;
	
	private String imei;

	 private String invoiceNumber;
	
	 private String remarks;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getPaymentDescription() {
		return paymentDescription;
	}

	public void setPaymentDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
	}

	public DespatchInfo getDespatchInfo() {
		return despatchInfo;
	}

	public void setDespatchInfo(DespatchInfo despatchInfo) {
		this.despatchInfo = despatchInfo;
	}

	public SellerInfo getSellerInfo() {
		return sellerInfo;
	}

	public void setSellerInfo(SellerInfo sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

	public String getReversePickup() {
		return reversePickup;
	}

	public void setReversePickup(String reversePickup) {
		this.reversePickup = reversePickup;
	}

	public Address getBuyer() {
		return buyer;
	}

	public void setBuyer(Address buyer) {
		this.buyer = buyer;
	}

	public ItemInfo getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
	}

	public Long getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(Long caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getBagSize() {
		return bagSize;
	}

	public void setBagSize(String bagSize) {
		this.bagSize = bagSize;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }



   public static class SellerInfo implements Serializable {
		private String legalName;

		private String localSalesTaxNumber;

		private String centralSalesTaxNumber;

		private String shipperName;

		public String getLocalSalesTaxNumber() {
			return localSalesTaxNumber;
		}

		public void setLocalSalesTaxNumber(String localSalesTaxNumber) {
			this.localSalesTaxNumber = localSalesTaxNumber;
		}

		public String getCentralSalesTaxNumber() {
			return centralSalesTaxNumber;
		}

		public void setCentralSalesTaxNumber(String centralSalesTaxNumber) {
			this.centralSalesTaxNumber = centralSalesTaxNumber;
		}

		public String getShipperName() {
			return shipperName;
		}

		public void setShipperName(String shipperName) {
			this.shipperName = shipperName;
		}

		public String getLegalName() {
			return legalName;
		}

		public void setLegalName(String legalName) {
			this.legalName = legalName;
		}

	}

	public static class DespatchInfo implements Serializable {
		private String documentNumber;

		private Date date;

		private String courier;

		private String destination;

		private String deliveryNote;

		public String getDocumentNumber() {
			return documentNumber;
		}

		public void setDocumentNumber(String documentNumber) {
			this.documentNumber = documentNumber;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public String getCourier() {
			return courier;
		}

		public void setCourier(String courier) {
			this.courier = courier;
		}

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
		}

		public String getDeliveryNote() {
			return deliveryNote;
		}

		public void setDeliveryNote(String deliveryNote) {
			this.deliveryNote = deliveryNote;
		}
	}

	public static class ItemInfo implements Serializable {
		private String description;

		private int quantity;

		private BigDecimal rate;

		private BigDecimal amount;
		
		 private BigDecimal discountAmount;
		
		private BigDecimal shippingCharge;

		private ItemInfo() {

		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public BigDecimal getRate() {
			return rate;
		}

		public void setRate(BigDecimal rate) {
			this.rate = rate;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

        public BigDecimal getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(BigDecimal discountAmount) {
            this.discountAmount = discountAmount;
        }
        
        public BigDecimal getShippingCharge() {
            return shippingCharge;
        }
        
        public void setShippingCharge(BigDecimal shippingCharge) {
            this.shippingCharge = shippingCharge;
        }

		
	}

	public static class Address implements Serializable {

		private String firstName;
		
		private String middleName;

		private String lastName;

		private String title;

		private String address;

		private String city;

		private String state;

		private String country;

		private String zip;

		private String phone;

		private String email;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

	    public String getMiddleName() {
	        return middleName;
	     }

	    public void setMiddleName(String middleName) {
	       this.middleName = middleName;
	     }
	        
		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		
		

	}
}
