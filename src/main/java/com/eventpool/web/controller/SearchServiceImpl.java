package com.eventpool.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eventpool.event.module.EventApiImpl;

/**
 
 */
@Service("SearchService")
public class SearchServiceImpl implements SearchService {
    
	private static final Logger logger = LoggerFactory.getLogger(EventApiImpl.class);
	
	@Resource
	public SearchServer searchServer;

	
    public List<EventSearchRecord> getSearchRecords(String query)
			throws Exception {
    	if(query==null || query.isEmpty()){
    		query = "*:*";
    	}
    	
		SolrQuery solrQuery = new SolrQuery();
		QueryResponse response;
		
		solrQuery.setQuery(query);
		solrQuery.setRows(10);
		solrQuery.setIncludeScore(true);
		
		logger.debug("RawQuery :" + query);
		response = searchServer.solrServer.query(solrQuery); 
		List<EventSearchRecord> searchResults = response.getBeans(EventSearchRecord.class);
    	return searchResults;
	}

}
