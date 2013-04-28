package com.eventpool.ticket.commands;


import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({TicketSoldCommand.class})
//@XmlRootElement
public interface ICommand {

    String getId();

}
