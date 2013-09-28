package com.eventpool.event.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.MediaDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.common.image.ImageType;
import com.eventpool.common.image.SaveImage;
import com.eventpool.event.command.SaveEventCommand;
import com.eventpool.event.module.EventApi;
import com.eventpool.ticket.command.handler.CommandHandler;
import com.eventpool.ticket.command.handler.CommandHandlerAnnotation;
import com.eventpool.ticket.commands.TicketUpdatedCommand;
import com.eventpool.ticket.service.TicketInventoryService;



@CommandHandlerAnnotation
public class SaveEventCommandHandler implements CommandHandler<SaveEventCommand, Boolean>{
	
	@Resource
	EventApi eventApi;
	
	@Resource 
	SaveImage saveImage;

	@Value("$EVENT_POOL{image.location}")
	private String imageBasePathForDb;// = "eventpool//images//";

	@Resource
	TicketInventoryService ticketInventoryService;

	private static final Logger logger = LoggerFactory.getLogger(SaveEventCommandHandler.class);
	
	
	public Boolean handle(SaveEventCommand command)
			throws Exception {
		List<Long> ticketIds = new ArrayList<Long>();
		EventDTO eventDTO = command.getEventDTO();
		preprocess(eventDTO);
		eventApi.saveEventDTO(eventDTO);
	   	List<TicketDTO> tickets = eventDTO.getTickets();
		if(tickets!=null && tickets.size()>0){
			for(TicketDTO ticketDTO:tickets){
				TicketUpdatedCommand ticketUpdatedCommand = new TicketUpdatedCommand();
				ticketUpdatedCommand.setMaxQty(ticketDTO.getQuantity());
				ticketUpdatedCommand.setTicketId(ticketDTO.getId());
				TicketInventoryDetails ticketInventoryDetails = (TicketInventoryDetails) ticketInventoryService.executeCommand(ticketUpdatedCommand);
				if(!ticketInventoryDetails.isMaxQtyUpdated()){
					ticketIds.add(ticketDTO.getId());
				}
			}
		}
		if(ticketIds.size()>0){
			logger.error("could not update Max qty for tickets {}",ticketIds);
		}
		return true;
	}

	public void preprocess(EventDTO eventDTO) throws Exception{
		MediaDTO media = eventDTO.getMedia();
		if(media!=null){
			String bannerUrl = media.getBannerUrl();
			if(!bannerUrl.contains(imageBasePathForDb)){
				Map<ImageType, String> imageMap = saveImage.saveImageOnDisk(bannerUrl);
				if(imageMap!=null){
					media.setBannerUrl(imageMap.get(ImageType.MEDIUM));
				}
			}
		}
	}
}
