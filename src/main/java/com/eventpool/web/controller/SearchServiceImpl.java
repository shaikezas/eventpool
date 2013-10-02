package com.eventpool.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    
	private static final String COUNTRYID = "countryId";

	private static final String EVENTDATE = "eventDate";

	private static final String EVENTTYPE = "eventType";

	private static final String CITYID = "cityId";

	private static final String SUBCATEGORYID = "subCategoryId";

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
	
	@Value("$EVENT_POOL{image.host}")
	private String imageHostUrl ;//= "C://Event//source";

	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
    public List<EventSearchRecord> getSearchRecords(int rows,int start,Integer cityId,Integer countryId)
			throws Exception {
    
    	String fq=null;
    	if(cityId!=null){
    		fq = fq+"cityId:"+cityId;
    	}
    	if(countryId!=null){
    		if(fq!=null) fq = fq+" AND "; 
    		fq=fq+"countryId:"+countryId;
    	}
    	if(fq==null){
    		fq = "eventDate:("+sdf.format(new Date())+" TO * )";
    	}else{
    		fq = fq+"eventDate:("+sdf.format(new Date())+" TO * )";
    	}
    	QueryResponse response = getSolrResponse("",fq, rows,start); 
		List<EventSearchRecord> searchResults = response.getBeans(EventSearchRecord.class);
		addImageHostUrl(searchResults);
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
		solrQuery.addFacetField(SUBCATEGORYID);
		solrQuery.addFacetField(EVENTDATE);
		solrQuery.addFacetField(EVENTTYPE);
		solrQuery.addFacetField(CITYID);
		solrQuery.addFacetField(COUNTRYID);
		
		solrQuery.setIncludeScore(true);
		//String fq="cityId:6453";
		if(filterQuery!=null && filterQuery.contains(":")){
			solrQuery.setFilterQueries(filterQuery);
		}
		logger.debug("RawQuery :" + query);
		response = searchServer.solrServer.query(solrQuery);
		return response;
	}


	public SearchQueryResponse getSearchQueryResponse(String query,Map<String,String> listOfFilters, int rows,int start,Integer countryId)
			throws Exception {
		String fq=""; 
		if(listOfFilters!=null){
			for(String key:listOfFilters.keySet()){
				String filterQuery = listOfFilters.get(key);
		    	if(filterQuery!=null && key.equalsIgnoreCase(SUBCATEGORYID)){
		    		fq=fq+" AND "+SUBCATEGORYID+":"+filterQuery;
		    	}
		    	if(filterQuery!=null && key.equalsIgnoreCase(CITYID)){
		    		fq=fq+" AND "+CITYID+":"+filterQuery;
		    	}
	
		    	if(filterQuery!=null && key.equalsIgnoreCase(EVENTTYPE)){
		    		fq=fq+" AND "+EVENTTYPE+":"+filterQuery;
		    	}
	
		    	if(filterQuery!=null && key.equalsIgnoreCase(EVENTDATE)){
		    		fq=fq+" AND "+EVENTDATE+":"+filterQuery;
		    	}
			}
		}
		if(fq!=null && fq.length()>4){
			fq = fq.substring(5);
		}
		if(countryId!=null){
			fq=COUNTRYID+":"+countryId+" AND "+fq;
		}
		
		QueryResponse response = getSolrResponse(query,fq, rows,start*rows); 
		

		SearchQueryResponse searchQueryResponse = new SearchQueryResponse();
		searchQueryResponse.setEventSearchRecords(response.getBeans(EventSearchRecord.class));
		addImageHostUrl(searchQueryResponse.getEventSearchRecords());
		searchQueryResponse.setQuery(query);
		searchQueryResponse.setNoOfresults(response.getResults().getNumFound());
		
		List<FilterItem> subCategoryFilterItems = searchQueryResponse.getSubCategoryFilterItems();
		if(subCategoryFilterItems==null) {
			subCategoryFilterItems = new ArrayList<FilterItem>();
			searchQueryResponse.setSubCategoryFilterItems(subCategoryFilterItems);
		}
		
		List<Count> facetValues = response.getFacetField(SUBCATEGORYID).getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				if(facet == null) continue;
				if(facet.getName()!=null){
					CategoryNode node = categoryTree.getNode(Long.parseLong(facet.getName()));
					if(node!=null && facet.getCount()>0){
						String subcategoryFilter = listOfFilters.get(SUBCATEGORYID);
						if(subcategoryFilter!=null){
							if(!subcategoryFilter.contains(node.getId().toString())){
								subcategoryFilter = subcategoryFilter.replace("(", "").replace(")", "");
								subcategoryFilter = "("+subcategoryFilter + " OR " + node.getId()+")";
							}else{
								subcategoryFilter = removeOldFilter(node.getId().toString(),subcategoryFilter);
							}
						}else{
							subcategoryFilter = node.getId().toString();
						}
						Map<String,String> newFilterMap = new HashMap<String, String>(listOfFilters);
						String filterFacetQuery = SUBCATEGORYID+"="+subcategoryFilter;
						newFilterMap.put(SUBCATEGORYID, subcategoryFilter);
						FilterItem filterItem = getFilterItem(facet.getCount(), node.getName(),filterFacetQuery,newFilterMap);
						subCategoryFilterItems.add(filterItem);
					}
				}
			}
		}
		
		getDateFilters(response, searchQueryResponse,listOfFilters);

		List<FilterItem> eventTypeFilterItems = searchQueryResponse.getEventTypeFilterItems();
		if(eventTypeFilterItems==null){
			eventTypeFilterItems = new ArrayList<FilterItem>();
			searchQueryResponse.setEventTypeFilterItems(eventTypeFilterItems);
		}
		
		
		facetValues = response.getFacetField(EVENTTYPE).getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				if(facet.getCount()>0){
					
					String eventTypeFilter = listOfFilters.get(EVENTTYPE);
					if(eventTypeFilter!=null){
						if(!eventTypeFilter.contains(facet.getName())){
							eventTypeFilter = eventTypeFilter.replace("(", "").replace(")", "");
							eventTypeFilter = "("+eventTypeFilter + " OR " + facet.getName()+")";
						}else{
							eventTypeFilter = removeOldFilter(facet.getName(), eventTypeFilter);
						}
					}else{
						eventTypeFilter = facet.getName();
					}
					String filterFacetQuery = EVENTTYPE+"="+eventTypeFilter;
					Map<String,String> newFilterMap = new HashMap<String, String>(listOfFilters);
					newFilterMap.put(EVENTTYPE, eventTypeFilter);
					FilterItem filterItem = getFilterItem(facet.getCount(), facet.getName(), filterFacetQuery,newFilterMap);
					eventTypeFilterItems.add(filterItem);
				}
			}
		}
		
		Map<Integer, String> cityMap = entityUtilities.getCityMap();
		List<FilterItem> cityIdFilterItems = searchQueryResponse.getCityIdFilterItems();
		if(cityIdFilterItems==null){
			cityIdFilterItems = new ArrayList<FilterItem>();
			searchQueryResponse.setCityIdFilterItems(cityIdFilterItems);
		}
		
		facetValues = response.getFacetField(CITYID).getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				String facetName = facet.getName();
				String cityName = null;
				if(cityMap!=null){
					try {
						cityName = cityMap.get(Integer.parseInt(facetName));
						if(cityName!=null && facet.getCount()>0){
							
							String cityIdFilter = listOfFilters.get(CITYID);
							if(cityIdFilter!=null){
								if(!cityIdFilter.contains(facetName)){
									cityIdFilter = cityIdFilter.replace("(", "").replace(")", "");
									cityIdFilter = "("+cityIdFilter + " OR " + facetName+")";
								}else{
									cityIdFilter = removeOldFilter(facetName, cityIdFilter);
								}
							}else{
								cityIdFilter = facetName;
							}
							String filterFacetQuery = CITYID+"="+cityIdFilter;
							Map<String,String> newFilterMap = new HashMap<String, String>(listOfFilters);
							newFilterMap.put(CITYID, cityIdFilter);		
							FilterItem filterItem = getFilterItem(facet.getCount(), cityName,filterFacetQuery,newFilterMap);
							cityIdFilterItems.add(filterItem);
						}
					} catch (NumberFormatException e) {
						logger.info("parsing error for city");
					}
				}
			}
		}
		
		facetValues = response.getFacetField(COUNTRYID).getValues();
		if(facetValues!=null && facetValues.size()>0){
			Map<Integer, String> countryMap = entityUtilities.getCountryMap();
			long otherCountriesCount = 0;
			for(Count facet:facetValues){
				String facetName = facet.getName();
				try {
					int country = Integer.parseInt(facetName);
					if(countryId.compareTo(country)==0){
						facet.getCount();
					}else{
						otherCountriesCount+=facet.getCount();
					}
				} catch (Exception e) {
					logger.info("not able to parse country id from seach results "+facetName);
				}
			}
			FilterItem filterItem = getFilterItem(otherCountriesCount, "Other Countries",COUNTRYID+"=-"+countryId,null);
			List<FilterItem> otherCountryFilterList = new ArrayList<FilterItem>();
			otherCountryFilterList.add(filterItem);
			searchQueryResponse.setOtherCountries(otherCountryFilterList);
		}
		return searchQueryResponse;
	}


	private void addImageHostUrl(List<EventSearchRecord> eventSearchRecords) {
		if(eventSearchRecords!=null && eventSearchRecords.size()>0){
			for(EventSearchRecord eventSearchRecord:eventSearchRecords){
				if(eventSearchRecord==null) continue;
				if(eventSearchRecord.getPromotionLogoUrl()!=null && !eventSearchRecord.getPromotionLogoUrl().startsWith(imageHostUrl)){
					eventSearchRecord.setPromotionLogoUrl(imageHostUrl+eventSearchRecord.getPromotionLogoUrl());
				}
			}
		}
		
	}


	private String removeOldFilter(String oldFilter, String filter) {
		filter = filter.replace("(", "").replace(")", "");
		filter = filter.replace(oldFilter, "");
		if(filter.startsWith(" OR ")){
			filter = filter.substring(4);
		}
		if(filter.endsWith(" OR ")){
			filter = filter.substring(0,filter.length()-5);
		}
		if(filter!=null && !filter.isEmpty()){
			filter = "("+filter + ")";
		}else{
			filter = null;
		}
		return filter;
	}


	private void getDateFilters(QueryResponse response,
			SearchQueryResponse searchQueryResponse,Map<String,String> listOfFilters) throws ParseException {
		List<Count> facetValues;
		List<FilterItem> eventDateFilterItems = searchQueryResponse.getEventDateFilterItems();
		if(eventDateFilterItems==null){
			eventDateFilterItems = new ArrayList<FilterItem>();
			searchQueryResponse.setEventDateFilterItems(eventDateFilterItems);
		}
		
		facetValues = response.getFacetField(EVENTDATE).getValues();
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

		//Today filter
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date date = cal.getTime();
		String dateFormat = sdf.format(date);
		
		String eventDateFilter = listOfFilters.get(EVENTDATE);
		if(eventDateFilter!=null){
			if(!eventDateFilter.contains(dateFormat)){
				eventDateFilter = eventDateFilter.replace("(", "").replace(")", "");
				eventDateFilter = "("+eventDateFilter + " OR " + dateFormat+")";
			}
		}else{
			eventDateFilter = dateFormat;
		}
		String filterFacetQuery = EVENTDATE+"="+eventDateFilter;
		Map<String,String> newFilterMap = new HashMap<String, String>(listOfFilters);
		newFilterMap.put(EVENTDATE, eventDateFilter);		
		FilterItem filterItem = getFilterItem(todayCount,"Today", filterFacetQuery,newFilterMap);
		eventDateFilterItems.add(filterItem);
		
		//Tomorrrow filter
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		date = cal.getTime();
		dateFormat = sdf.format(date);
		
		eventDateFilter = listOfFilters.get(EVENTDATE);
		if(eventDateFilter!=null){
			if(!eventDateFilter.contains(dateFormat)){
				eventDateFilter = eventDateFilter.replace("(", "").replace(")", "");
				eventDateFilter = "("+eventDateFilter + " OR " + dateFormat+")";
			}else{
				eventDateFilter = removeOldFilter(dateFormat, eventDateFilter);
			}
		}else{
			eventDateFilter = dateFormat;
		}
		filterFacetQuery = EVENTDATE+"="+eventDateFilter;
		newFilterMap = new HashMap<String, String>(listOfFilters);
		newFilterMap.put(EVENTDATE, eventDateFilter);		
		filterItem = getFilterItem(tomorrowCount,"Tommorrow", filterFacetQuery,newFilterMap);
		eventDateFilterItems.add(filterItem);

		//This week filter
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 0);
		date = cal.getTime();
		dateFormat = sdf.format(date);
		
		int dayOftheWeek = cal.get(Calendar.DAY_OF_WEEK);
		int i = Calendar.SATURDAY - dayOftheWeek;
		
		cal.add(Calendar.DAY_OF_MONTH, i+1);
		date = cal.getTime();
		String endDateFormat = sdf.format(date);
		
		eventDateFilter = listOfFilters.get(EVENTDATE);
		if(eventDateFilter!=null){
			if(!eventDateFilter.contains(dateFormat+" TO "+endDateFormat)){
				eventDateFilter = eventDateFilter.replace("(", "").replace(")", "");
				eventDateFilter = "("+eventDateFilter + " OR " + dateFormat+" TO "+endDateFormat+")";
			}else{
				eventDateFilter = removeOldFilter(dateFormat+" TO "+endDateFormat, eventDateFilter);
			}
		}else{
			eventDateFilter = "("+dateFormat+" TO "+endDateFormat+")";
		}
		filterFacetQuery = EVENTDATE+"="+eventDateFilter;
		newFilterMap = new HashMap<String, String>(listOfFilters);
		newFilterMap.put(EVENTDATE, eventDateFilter);		
		filterItem = getFilterItem(currentWeekCount,"This Week", filterFacetQuery,newFilterMap);
		eventDateFilterItems.add(filterItem);

	
		//Next week filter
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

		eventDateFilter = listOfFilters.get(EVENTDATE);
		if(eventDateFilter!=null){
			if(!eventDateFilter.contains(dateFormat+" TO "+endDateFormat)){
				eventDateFilter = eventDateFilter.replace("(", "").replace(")", "");
				eventDateFilter = "("+eventDateFilter + " OR " + dateFormat+" TO "+endDateFormat+")";
			}else{
				eventDateFilter = removeOldFilter(dateFormat+" TO "+endDateFormat, eventDateFilter);
			}
		}else{
			eventDateFilter = "("+dateFormat+" TO "+endDateFormat+")";
		}
		filterFacetQuery = EVENTDATE+"="+eventDateFilter;
		newFilterMap = new HashMap<String, String>(listOfFilters);
		newFilterMap.put(EVENTDATE, eventDateFilter);		
		filterItem = getFilterItem(nextWeekCount,"Next Week", filterFacetQuery,newFilterMap);
		eventDateFilterItems.add(filterItem);

		//Other dates filter
		cal.setTime(new Date());
		cal.add(Calendar.WEEK_OF_MONTH, 2);
		cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		date = cal.getTime();
		dateFormat = sdf.format(date);

		eventDateFilter = listOfFilters.get(EVENTDATE);
		if(eventDateFilter!=null){
			if(!eventDateFilter.contains(dateFormat+" TO *")){
				eventDateFilter = eventDateFilter.replace("(", "").replace(")", "");
				eventDateFilter = "("+eventDateFilter + " OR " + dateFormat+" TO *)";
			}else{
				eventDateFilter = removeOldFilter(dateFormat+" TO *", eventDateFilter);
			}
		}else{
			eventDateFilter = "("+dateFormat+" TO *)";
		}
		filterFacetQuery = EVENTDATE+"="+eventDateFilter;
		newFilterMap = new HashMap<String, String>(listOfFilters);
		newFilterMap.put(EVENTDATE, eventDateFilter);		
		filterItem = getFilterItem(otherDatesCount,"Other Dates", EVENTDATE+"=("+dateFormat+" TO *)",newFilterMap);
		eventDateFilterItems.add(filterItem);
	}


	private FilterItem getFilterItem(Long count, String  name,
			String filterQuery,Map<String,String> filterMap) {
		FilterItem filterItem = new FilterItem();
		filterItem.setCount(count);
		filterItem.setName(name);
		
		filterQuery = "";
		if(filterMap!=null){
			for(String key:filterMap.keySet()){
				String fq = filterMap.get(key);
		    	if(fq!=null && !fq.isEmpty() && key.equalsIgnoreCase(SUBCATEGORYID)){
		    		filterQuery=filterQuery+"&"+SUBCATEGORYID+"="+fq;
		    	}
		    	if(fq!=null && !fq.isEmpty() && key.equalsIgnoreCase(CITYID)){
		    		filterQuery=filterQuery+"&"+CITYID+"="+fq;
		    	}
	
		    	if(fq!=null && !fq.isEmpty() && key.equalsIgnoreCase(EVENTTYPE)){
		    		filterQuery=filterQuery+"&"+EVENTTYPE+"="+fq;
		    	}
	
		    	if(fq!=null && !fq.isEmpty() && key.equalsIgnoreCase(EVENTDATE)){
		    		filterQuery=filterQuery+"&"+EVENTDATE+"="+fq;
		    	}
			}
		}
		if(filterQuery!=null && !filterQuery.isEmpty() && filterQuery.startsWith("&") && filterQuery.length()>1){
			filterQuery = filterQuery.substring(1);
		}
		if(filterQuery!=null && !filterQuery.isEmpty() && filterQuery.endsWith("&") && filterQuery.length()>1){
			filterQuery = filterQuery.substring(0,filterQuery.length()-1);;
		}
		if(filterQuery!=null && !filterQuery.isEmpty() && filterQuery.equals("&")){
			filterQuery = "";
		}
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
