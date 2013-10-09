package com.eventpool.web.domain;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eventpool.common.entities.TicketRegister;
import com.eventpool.common.repositories.TicketRegisterRepository;
import com.eventpool.ticket.service.TicketInventoryUnblockedService;

public class ContextLoaderListener
{
    private static final Logger logger = LoggerFactory.getLogger( ContextLoaderListener.class );
    
    @Resource
    private TicketRegisterRepository ticketRegisterRepository;
    
    @Resource
	TicketInventoryUnblockedService unBlockedService;
    
    public void start()
    {
    	List<TicketRegister> ticketRegisters = ticketRegisterRepository.findAll();
    	if(ticketRegisters!=null){
    	for(TicketRegister ticketReg :ticketRegisters ){
    		unBlockedService.registerTask(ticketReg, ticketReg.getRegistrationLimit()*60);
    	}
    	}
    }
}