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
};

eventpool.eventRegister = function() {
	this.eventId=0;
	this.ticketRegisterDTOs=[];
	this.dicountCoupon = "";
	this.paymentCurrency="USD";
	this.discountAmount = 0.0; 
	this.subCategoryId =0;
	this.organizerName=""; 
	
};

eventpool.ticketRegister = function() {
	this.ticketId=0;
	this.qty=0;
	this.price=0.0;
	this.id=0;
	this.ticketName="";
};