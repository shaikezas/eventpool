package com.eventpool.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
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

	
    public List<EventSearchRecord> getSearchRecords(String query,int rows)
			throws Exception {
    	QueryResponse response = getSolrResponse(query, rows); 
		List<EventSearchRecord> searchResults = response.getBeans(EventSearchRecord.class);
    	return searchResults;
	}


	private QueryResponse getSolrResponse(String query, int rows)
			throws SolrServerException {
		if(query==null || query.isEmpty()){
    		query = "*:*";
    	}
    	
		SolrQuery solrQuery = new SolrQuery();
		QueryResponse response;
		
		solrQuery.setQuery(query);
		if(rows==0){
			rows = 10;
		}
		solrQuery.setRows(rows);
		solrQuery.addFacetField("subCategoryId");
		solrQuery.addFacetField("eventDate");
		solrQuery.addFacetField("eventType");
		solrQuery.addFacetField("cityId");
		
		solrQuery.setIncludeScore(true);
		
		logger.debug("RawQuery :" + query);
		response = searchServer.solrServer.query(solrQuery);
		return response;
	}


	public SearchQueryResponse getSearchQueryResponse(String query, int rows)
			throws Exception {
		QueryResponse response = getSolrResponse(query, rows); 

		SearchQueryResponse searchQueryResponse = new SearchQueryResponse();
		searchQueryResponse.setEventSearchRecords(response.getBeans(EventSearchRecord.class));
		
		Map<String, Long> subCategoryIdMap = searchQueryResponse.getSubCategoryIdMap();
		List<Count> facetValues = response.getFacetField("subCategoryId").getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				subCategoryIdMap.put(facet.getName(), facet.getCount());
			}
		}
		
		Map<String, Long> eventDateMap = searchQueryResponse.getEventDateMap();
		facetValues = response.getFacetField("eventDate").getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				eventDateMap.put(facet.getName(), facet.getCount());
			}
		}
		
		Map<String, Long> eventTypeMap = searchQueryResponse.getEventTypeMap();
		facetValues = response.getFacetField("eventType").getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				eventTypeMap.put(facet.getName(), facet.getCount());
			}
		}
		
		Map<String, Long> cityIdMap = searchQueryResponse.getCityIdMap();
		facetValues = response.getFacetField("cityId").getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				cityIdMap.put(facet.getName(), facet.getCount());
			}
		}
		return searchQueryResponse;
	}

}
