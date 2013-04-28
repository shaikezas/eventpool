package com.eventpool.ticket.commands;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

//code-review: why not extend serializable also here?
public abstract class AbstractICommand implements ICommand, Serializable {

    private static final long serialVersionUID = 1L;
	
	private String id;

    public AbstractICommand() {
        id = UUID.randomUUID().toString();
    }

    public AbstractICommand(String id) {
        this.id = id;
    }

    @XmlElement
    public String getId() {
        return id;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}
