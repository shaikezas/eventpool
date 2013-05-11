if (!eventpool )
	var eventpool = {};

if ( !window.eventpool ) {
	window.eventpool = eventpool;
}

eventpool.ticket = function() {
	this.title 	= "";
	this.quantity 	= 0;
};