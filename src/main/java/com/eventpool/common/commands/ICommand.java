package com.eventpool.common.commands;


import javax.xml.bind.annotation.XmlSeeAlso;

import com.eventpool.event.command.SaveEventCommand;
import com.eventpool.ticket.commands.TicketBlockedCommand;
import com.eventpool.ticket.commands.TicketOrderedCommand;
import com.eventpool.ticket.commands.TicketUnBlockedCommand;
import com.eventpool.ticket.commands.TicketUpdatedCommand;

@XmlSeeAlso({TicketOrderedCommand.class,TicketBlockedCommand.class,TicketUnBlockedCommand.class,SaveEventCommand.class,TicketUpdatedCommand.class})
//@XmlRootElement
public interface ICommand {

    String getId();

}
