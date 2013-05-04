package com.eventpool.event.module;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.exceptions.EventNotFoundException;

@SuppressWarnings("rawtypes")
@Component
public interface EventApi {

    @Transactional
    public void saveEventDTO(EventDTO eventDTO);
    
    @Transactional(readOnly = true)
    public EventDTO getEvenDTO(Long id) throws EventNotFoundException;
}
