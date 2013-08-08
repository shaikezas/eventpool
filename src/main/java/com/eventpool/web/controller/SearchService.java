package com.eventpool.web.controller;

import java.util.List;
import java.util.Map;

import com.eventpool.common.dto.EventDTO;

/**
 */
public interface SearchService {

	public List<EventSearchRecord> getSearchRecords(String query,int rows,int start) throws Exception;
	
	public SearchQueryResponse getSearchQueryResponse(String query,Map<String,String> filterMap,int rows,int start) throws Exception;
  
}
