package com.eventpool.web.controller;

import java.util.List;

import com.eventpool.common.dto.EventDTO;

/**
 */
public interface SearchService {

	public List<EventSearchRecord> getSearchRecords(String query) throws Exception;

  
}