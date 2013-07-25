package com.eventpool.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    
	private static final int REST = 6;

	private static final int CURRENT_MONTH = 5;

	private static final int NEXT_WEEK = 4;

	private static final int TOMORROW = 2;

	private static final int TODAY = 1;

	private static final Logger logger = LoggerFactory.getLogger(EventApiImpl.class);

	private static final int CURRENT_WEEK = 0;
	
	@Resource
	public SearchServer searchServer;

	@Resource
	CategoryTree categoryTree;
	
	@Resource
	EntityUtilities entityUtilities;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
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
					if(node!=null && facet.getCount()>0){
						subCategoryIdMap.put(node.getName(), facet.getCount());
					}
				}
			}
		}
		
		Map<String, Long> eventDateMap = searchQueryResponse.getEventDateMap();
		facetValues = response.getFacetField("eventDate").getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				Date eventDate = sdf.parse(facet.getName());
				int dateFilter = getDayFilter(eventDate);
				if(facet.getCount()>0){
					if(dateFilter == TODAY){
						long count = facet.getCount();
						Long todayCount = eventDateMap.get("Today");
						if(todayCount==null)todayCount=0L;
						eventDateMap.put("Today", count+todayCount);
					}else
					if(dateFilter == TOMORROW){
						long count = facet.getCount();
						Long todayCount = eventDateMap.get("Tommorrow");
						if(todayCount==null)todayCount=0L;
						eventDateMap.put("Tommorrow", count+todayCount);
					}else
					if(dateFilter == CURRENT_WEEK){
						long count = facet.getCount();
						Long todayCount = eventDateMap.get("This Week");
						if(todayCount==null)todayCount=0L;
						eventDateMap.put("This Week", count+todayCount);
					}else
					if(dateFilter == NEXT_WEEK){
						long count = facet.getCount();
						Long todayCount = eventDateMap.get("Next Week");
						if(todayCount==null)todayCount=0L;
						eventDateMap.put("Next Week", count+todayCount);
					}else
					if(dateFilter == CURRENT_MONTH){
						long count = facet.getCount();
						Long todayCount = eventDateMap.get("This month");
						if(todayCount==null)todayCount=0L;
						eventDateMap.put("This month", count+todayCount);
					}else{
						long count = facet.getCount();
						Long todayCount = eventDateMap.get("Rest");
						if(todayCount==null)todayCount=0L;
						eventDateMap.put("Rest", count+todayCount);
					}

				}
			}
		}
		
		Map<String, Long> eventTypeMap = searchQueryResponse.getEventTypeMap();
		facetValues = response.getFacetField("eventType").getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				if(facet.getCount()>0){
					eventTypeMap.put(facet.getName(), facet.getCount());
				}
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
				if(cityName!=null && facet.getCount()>0){
					cityIdMap.put(cityName, facet.getCount());
				}
			}
		}
		return searchQueryResponse;
	}


	private int getDayFilter(Date eventDate) {
		Calendar cal = Calendar.getInstance();
	    
		cal.setTime(eventDate);
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
	    int date = cal.get(Calendar.DATE);
	    int day = cal.get(Calendar.DAY_OF_MONTH);
	    int week = cal.get(Calendar.WEEK_OF_MONTH);
	    
	    cal.setTime(new Date());
	    int currentYear = cal.get(Calendar.YEAR);
	    int currentMonth = cal.get(Calendar.MONTH);
	    int currentDay = cal.get(Calendar.DAY_OF_MONTH);
	    int currentDate = cal.get(Calendar.DATE);
	    int currentWeek = cal.get(Calendar.WEEK_OF_MONTH);
	    
	    if(currentDate == date && currentMonth == month && currentYear == year){
	    	return TODAY; // today
	    }
	    if(currentDate == date-1 && currentMonth == month && currentYear == year ){
	    	return TOMORROW;//tomorrow
	    }
	    if(week == currentWeek && currentMonth == month && currentYear == year){
			return CURRENT_WEEK;//this week
	    }
	    if(week-1 == currentWeek && currentMonth == month && currentYear == year){
	    	return NEXT_WEEK;//next week
	    }
	    if(currentMonth == month && currentYear == year){
	    	return CURRENT_MONTH;//this month
	    }
	    return REST;//Rest 
	}

}
