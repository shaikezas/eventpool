package com.eventpool.common.exceptions;
public class NoTicketInventoryBlockedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1769225591910016749L;

	
	public NoTicketInventoryBlockedException(String exception){
		super(exception);
	}
}