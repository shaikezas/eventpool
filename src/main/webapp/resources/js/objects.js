if (!eventpool )
	var eventpool = {};

if ( !window.eventpool ) {
	window.eventpool = eventpool;
}

eventpool.ticket = function() {
	this.name 	= "";
	this.quantity 	= 0;
};