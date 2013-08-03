package com.eventpool.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    	QueryResponse response = getSolrResponse(query,null, rows,start); 
		List<EventSearchRecord> searchResults = response.getBeans(EventSearchRecord.class);
    	return searchResults;
	}


	private QueryResponse getSolrResponse(String query, String filterQuery,int rows,int start)
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
		//String fq="cityId:6453";
		//solrQuery.setFilterQueries(fq);
		logger.debug("RawQuery :" + query);
		response = searchServer.solrServer.query(solrQuery);
		return response;
	}


	public SearchQueryResponse getSearchQueryResponse(String query,String filterQuery, int rows,int start)
			throws Exception {
		QueryResponse response = getSolrResponse(query,filterQuery, rows,start); 

		SearchQueryResponse searchQueryResponse = new SearchQueryResponse();
		searchQueryResponse.setEventSearchRecords(response.getBeans(EventSearchRecord.class));
		
		List<FilterItem> subCategoryFilterItems = searchQueryResponse.getSubCategoryFilterItems();
		if(subCategoryFilterItems==null) {
			subCategoryFilterItems = new ArrayList<FilterItem>();
			searchQueryResponse.setSubCategoryFilterItems(subCategoryFilterItems);
		}
		
		List<Count> facetValues = response.getFacetField("subCategoryId").getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				if(facet == null) continue;
				if(facet.getName()!=null){
					CategoryNode node = categoryTree.getNode(Long.parseLong(facet.getName()));
					if(node!=null && facet.getCount()>0){
						String filterFacetQuery = "subCategoryId:"+node.getId();
						FilterItem filterItem = getFilterItem(facet.getCount(), node.getName(),filterFacetQuery);
						subCategoryFilterItems.add(filterItem);
					}
				}
			}
		}
		
		getDateFilters(response, searchQueryResponse);

		List<FilterItem> eventTypeFilterItems = searchQueryResponse.getEventTypeFilterItems();
		if(eventTypeFilterItems==null){
			eventTypeFilterItems = new ArrayList<FilterItem>();
			searchQueryResponse.setEventTypeFilterItems(eventTypeFilterItems);
		}
		
		facetValues = response.getFacetField("eventType").getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				if(facet.getCount()>0){
					getFilterItem(facet.getCount(), facet.getName(), "eventType:"+facet.getName());
				}
			}
		}
		
		Map<Integer, String> cityMap = entityUtilities.getCityMap();
		List<FilterItem> cityIdFilterItems = searchQueryResponse.getCityIdFilterItems();
		if(cityIdFilterItems==null){
			cityIdFilterItems = new ArrayList<FilterItem>();
			searchQueryResponse.setCityIdFilterItems(cityIdFilterItems);
		}
		
		facetValues = response.getFacetField("cityId").getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				String facetName = facet.getName();
				String cityName = null;
				if(cityMap!=null){
					try {
						cityName = cityMap.get(Integer.parseInt(facetName));
						if(cityName!=null && facet.getCount()>0){
							getFilterItem(facet.getCount(), cityName, "cityId:"+facetName);
						}
					} catch (NumberFormatException e) {
						logger.info("parsing error for city");
					}
				}
			}
		}
		return searchQueryResponse;
	}


	private void getDateFilters(QueryResponse response,
			SearchQueryResponse searchQueryResponse) throws ParseException {
		List<Count> facetValues;
		List<FilterItem> eventDateFilterItems = searchQueryResponse.getEventDateFilterItems();
		if(eventDateFilterItems==null){
			eventDateFilterItems = new ArrayList<FilterItem>();
			searchQueryResponse.setEventDateFilterItems(eventDateFilterItems);
		}
		
		facetValues = response.getFacetField("eventDate").getValues();
		long todayCount =0L;
		long tomorrowCount=0L;
		long currentWeekCount=0L;
		long nextWeekCount=0L;
		long currentMonthCount=0L;
		long otherDatesCount=0L;
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				Date eventDate = sdf.parse(facet.getName());
				int dateFilter = getDayFilter(eventDate);
				if(facet.getCount()>0){
					if(dateFilter == TODAY){
						long count = facet.getCount();
						todayCount = count + todayCount;
					}else
					if(dateFilter == TOMORROW){
						long count = facet.getCount();
						tomorrowCount = count + tomorrowCount;
					}else
					if(dateFilter == CURRENT_WEEK){
						long count = facet.getCount();
						currentWeekCount = currentWeekCount + count;
					}else
					if(dateFilter == NEXT_WEEK){
						long count = facet.getCount();
						nextWeekCount = nextWeekCount + count;
					}else
					if(dateFilter == CURRENT_MONTH){
						long count = facet.getCount();
						currentMonthCount = currentMonthCount +count;
					}else{
						long count = facet.getCount();
						otherDatesCount = otherDatesCount +count;
					}

				}
			}
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date date = cal.getTime();
		String dateFormat = sdf.format(date);
		FilterItem filterItem = getFilterItem(todayCount,"Today", "eventDate:"+dateFormat);
		eventDateFilterItems.add(filterItem);
		
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		date = cal.getTime();
		dateFormat = sdf.format(date);
		filterItem = getFilterItem(tomorrowCount,"Tommorrow", "eventDate:"+dateFormat);
		eventDateFilterItems.add(filterItem);

		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 0);
		date = cal.getTime();
		dateFormat = sdf.format(date);
		
		int dayOftheWeek = cal.get(Calendar.DAY_OF_WEEK);
		int i = Calendar.SATURDAY - dayOftheWeek;
		
		cal.add(Calendar.DAY_OF_MONTH, i+1);
		date = cal.getTime();
		String endDateFormat = sdf.format(date);

		filterItem = getFilterItem(currentWeekCount,"This Week", "eventDate:["+dateFormat+" TO "+endDateFormat+"]");
		eventDateFilterItems.add(filterItem);

	
		cal.setTime(new Date());
		cal.add(Calendar.WEEK_OF_MONTH, 1);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		date = cal.getTime();
		dateFormat = sdf.format(date);

		cal.setTime(new Date());
		cal.add(Calendar.WEEK_OF_MONTH, 2);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);

		date = cal.getTime();
		endDateFormat = sdf.format(date);

		filterItem = getFilterItem(nextWeekCount,"Next Week", "eventDate:["+dateFormat+" TO "+endDateFormat+"]");
		eventDateFilterItems.add(filterItem);

		cal.setTime(new Date());
		cal.add(Calendar.WEEK_OF_MONTH, 2);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		date = cal.getTime();
		dateFormat = sdf.format(date);
		filterItem = getFilterItem(otherDatesCount,"Other Dates", "eventDate:["+dateFormat+" TO *]");
		eventDateFilterItems.add(filterItem);
	}


	private FilterItem getFilterItem(Long count, String  name,
			String filterQuery) {
		FilterItem filterItem = new FilterItem();
		filterItem.setCount(count);
		filterItem.setName(name);
		filterItem.setQuery(filterQuery);
		return filterItem;
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
