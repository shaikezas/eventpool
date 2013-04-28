package com.eventpool.ticket.command;
public interface Gate {

	public abstract Object dispatch(Object command) throws Exception;

}