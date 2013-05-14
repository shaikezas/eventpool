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