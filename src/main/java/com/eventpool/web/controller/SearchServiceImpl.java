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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.eventpool.common.module.CategoryNode;
import com.eventpool.common.module.CategoryTree;
import com.eventpool.common.module.EntityUtilities;
import com.eventpool.event.module.EventApiImpl;

/**
 
 */
@Service("SearchService")
public class SearchServiceImpl implements SearchService {
    
	private static final Logger logger = LoggerFactory.getLogger(EventApiImpl.class);
	
	@Resource
	public SearchServer searchServer;

	@Resource
	CategoryTree categoryTree;
	
	@Resource
	EntityUtilities entityUtilities;
	
	
    public List<EventSearchRecord> getSearchRecords(String query,int rows,int start)
			throws Exception {
    	QueryResponse response = getSolrResponse(query, rows,start); 
		List<EventSearchRecord> searchResults = response.getBeans(EventSearchRecord.class);
    	return searchResults;
	}


	private QueryResponse getSolrResponse(String query, int rows,int start)
			throws SolrServerException {
		if(query==null || query.isEmpty()){
    		query = "*:*";
    	}
    	
		SolrQuery solrQuery = new SolrQuery();
		QueryResponse response;
		
		solrQuery.setStart(start);
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


	public SearchQueryResponse getSearchQueryResponse(String query, int rows,int start)
			throws Exception {
		QueryResponse response = getSolrResponse(query, rows,start); 

		SearchQueryResponse searchQueryResponse = new SearchQueryResponse();
		searchQueryResponse.setEventSearchRecords(response.getBeans(EventSearchRecord.class));
		
		Map<String, Long> subCategoryIdMap = searchQueryResponse.getSubCategoryIdMap();
		List<Count> facetValues = response.getFacetField("subCategoryId").getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				if(facet == null) continue;
				if(facet.getName()!=null){
					CategoryNode node = categoryTree.getNode(Long.parseLong(facet.getName()));
					if(node!=null){
						subCategoryIdMap.put(node.getName(), facet.getCount());
					}
				}
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
		
		Map<Integer, String> cityMap = entityUtilities.getCityMap();
		Map<String, Long> cityIdMap = searchQueryResponse.getCityIdMap();
		facetValues = response.getFacetField("cityId").getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				String facetName = facet.getName();
				String cityName = null;
				if(cityIdMap!=null) {
					cityName = cityMap.get(Integer.parseInt(facetName));
				}
				if(cityName!=null){
					cityIdMap.put(cityName, facet.getCount());
				}
			}
		}
		return searchQueryResponse;
	}

}
