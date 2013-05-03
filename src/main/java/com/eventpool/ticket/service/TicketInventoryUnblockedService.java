package com.eventpool.ticket.service;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.common.entities.TicketRegister;
import com.eventpool.common.repositories.TicketRegisterRepository;
import com.eventpool.order.service.OrderServiceImpl;
import com.eventpool.ticket.commands.TicketUnBlockedCommand;

@Service
public class TicketInventoryUnblockedService {
	
	Timer timer;
	
	@Resource
	TicketInventoryService inventoryService;
	
	
	
	@Resource
	TicketRegisterRepository ticketRegisterRepository;
	
	private static final Logger log = LoggerFactory.getLogger(TicketInventoryUnblockedService.class);
	
	public TicketInventoryUnblockedService(){
		timer = new Timer();
		System.out.println("TicketInventoryUnblockedService object created");
	   
	}
	public void registerTask(TicketRegister ticketReg,int seconds){
	timer.schedule(new UnblockTask(ticketReg), seconds * 1000);
	}
	
	class UnblockTask extends TimerTask {
		TicketRegister ticketRegister;
		public UnblockTask(TicketRegister ticketReg){
			ticketRegister = ticketReg;
		}
	    public void run() {
	      System.out.println("Time's up! "+ticketRegister.getId());
	      TicketUnBlockedCommand unblockcmd  = new TicketUnBlockedCommand();
	      unblockcmd.setUnBlockingQty(ticketRegister.getQty());
	      unblockcmd.setTicketId(ticketRegister.getTicketId());
	      try {
			TicketInventoryDetails ticketInventoryDetails = (TicketInventoryDetails) inventoryService.executeCommand(unblockcmd);
			if(ticketInventoryDetails.isInvUnBlocked()){
				ticketRegisterRepository.delete(ticketRegister.getId());
			}else{
				log.error("Unable unblock the ticket inventory for ticket register id "+ticketRegister.getId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	      
	      timer.cancel(); 
	    }
	  }
}
