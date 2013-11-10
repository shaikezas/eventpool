package com.eventpool.web.controller;

import java.util.List;
import java.util.Map;

import com.eventpool.common.dto.EventDTO;

/**
 */
public interface SearchService {

	public List<EventSearchRecord> getSearchRecords(int rows,int start,Integer cityId,Integer countryId) throws Exception;
	
	public SearchQueryResponse getSearchQueryResponse(String query,Map<String,String> filterMap,int rows,int start,Integer countryId) throws Exception;
	
}
