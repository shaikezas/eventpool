package com.eventpool.ticket.commands;


import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({TicketOrderedCommand.class,TicketBlockedCommand.class,TicketUnBlockedCommand.class})
//@XmlRootElement
public interface ICommand {

    String getId();

}
