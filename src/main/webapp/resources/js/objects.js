if (!eventpool )
	var eventpool = {};

if ( !window.eventpool ) {
	window.eventpool = eventpool;
}

eventpool.ticket = function() {
	this.name 	= "";
	this.quantity 	= 0;
	this.price = 0;
	this.showsettings = false;
	this.ticketType = "";
	this.showPrice = false;
	this.showFree = false;
	this.ticketOrder =0;
	this.minQty = 1;
    this.maxQty = 5;
    this.saleStart = moment().format("DD-MM-YYYY hh:mm A");
};

eventpool.eventRegister = function() {
	this.eventId=0;
	this.ticketRegisterDTOs=[];
	this.dicountCoupon = "";
	this.paymentCurrency="USD";
	this.discountAmount = 0.0; 
	this.subCategoryId =0;
	this.organizerName=""; 
	this.registrationLimit = 15;
	
};

eventpool.ticketRegister = function() {
	this.ticketId=0;
	this.qty=0;
	this.price=0.0;
	this.id=0;
	this.ticketName="";
};