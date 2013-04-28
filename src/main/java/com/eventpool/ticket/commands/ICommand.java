package com.eventpool.ticket.commands;


import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({TicketOrderCommand.class})
//@XmlRootElement
public interface ICommand {

    String getId();

}
