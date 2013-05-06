package com.eventpool.event.module;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.exceptions.EventNotFoundException;

@Component
public interface EventApi {

    @Transactional
    public EventDTO saveEventDTO(EventDTO eventDTO);
    
    @Transactional(readOnly = true)
    public EventDTO getEvenDTO(Long id) throws EventNotFoundException;
}
