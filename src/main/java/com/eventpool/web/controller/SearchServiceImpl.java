package com.eventpool.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.eventpool.common.module.CategoryNode;
import com.eventpool.common.module.CategoryTree;
import com.eventpool.common.module.DateCustomConverter;
import com.eventpool.common.module.EntityUtilities;
import com.eventpool.event.module.EventApiImpl;

/**
 
 */
@Service("SearchService")
public class SearchServiceImpl implements SearchService {
    
	private static final String RIGHT_PARENTHESIS = ")";
	private static final String LEFT_PARENTHESIS = "(";
	private static final String COUNTRYID = "countryId";
	private static final String WEBINAR = "isWebinar";
	private static final String EVENTDATE = "eventDate";
	private static final String START_DATE = "startDate";
	private static final String END_DATE="endDate";
	private static final String EVENTTYPE = "eventType";
	private static final String CITYID = "cityId";
	private static final String SUBCATEGORYID = "subCategoryId";
	private static final int REST = 6;
	private static final int CURRENT_MONTH = 5;
	private static final int NEXT_WEEK = 4;
	private static final int CURRENT_WEEK = 3;
	private static final int TOMORROW = 2;
	private static final int TODAY = 1;
	private static final int PAST = 0;
	private static final Logger logger = LoggerFactory.getLogger(EventApiImpl.class);
	
	private static final String EXCLUDE ="{!ex=dt}";
	private static final String TAG ="{!tag=dt}";
	@Resource
	public SearchServer searchServer;

	@Resource
	CategoryTree categoryTree;
	
	@Resource
	EntityUtilities entityUtilities;
	
	@Value("$EVENT_POOL{image.host}")
	private String imageHostUrl ;//= "C://Event//source";

	//2013-07-17T00:00:00Z HH:mm:ss
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@PostConstruct
	public void init(){
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
    public List<EventSearchRecord> getSearchRecords(int rows,int start,Integer cityId,Integer countryId)
			throws Exception {
    
    	String fq=null;
    	Map<String,String> listOfFilters = new HashMap<String, String>();
    	if(cityId!=null){
    		listOfFilters.put(CITYID,String.valueOf(cityId));
    	}
    	if(countryId!=null){
    		listOfFilters.put("countryId",String.valueOf(countryId));
    	}
    	QueryResponse response = getSolrResponse("",listOfFilters, rows,start,null); 
		List<EventSearchRecord> searchResults = response.getBeans(EventSearchRecord.class);
		addImageHostUrl(searchResults);
    	return searchResults;
	}

    @Resource
    private DateCustomConverter dateCustomConverter;


	private QueryResponse getSolrResponse(String query, Map<String,String> listOfFilters,int rows,int start,Integer countryId)
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
		
		if(listOfFilters.get(SUBCATEGORYID)==null){
			solrQuery.addFacetField(SUBCATEGORYID);
			//solrQuery.addFacetField(EXCLUDE+SUBCATEGORYID);
		}
		if(listOfFilters.get(EVENTTYPE)==null){
			//solrQuery.addFacetField(EXCLUDE+EVENTTYPE);
			solrQuery.addFacetField(EVENTTYPE);
		}
		if(listOfFilters.get(CITYID)==null){
			//solrQuery.addFacetField(EXCLUDE+CITYID);
			solrQuery.addFacetField(CITYID);
		}
		if(listOfFilters.get("webinar")==null){
			solrQuery.addFacetField(EXCLUDE+WEBINAR);
		}
		
		solrQuery.addFacetField(COUNTRYID);
		solrQuery.addFacetField(WEBINAR);
		
		solrQuery.addFacetPivotField(EXCLUDE+START_DATE+","+END_DATE);
		solrQuery.setIncludeScore(true);
		
		solrQuery.addFilterQuery(END_DATE+":["+sdf.format(getCurrentZeroHourDate().getTime())+" TO * ]");
		
		if(listOfFilters!=null){
			for(String key:listOfFilters.keySet()){
				String filterQuery = listOfFilters.get(key);
		    	if(filterQuery!=null && key.equalsIgnoreCase(SUBCATEGORYID)){
		    		solrQuery.addFilterQuery(TAG+SUBCATEGORYID+":"+filterQuery);
		    		solrQuery.addFacetField(EXCLUDE+SUBCATEGORYID);
		    	}
		    	if(filterQuery!=null && key.equalsIgnoreCase(CITYID)){
		    		solrQuery.addFilterQuery(TAG+CITYID+":"+filterQuery);
		    		solrQuery.addFacetField(EXCLUDE+CITYID);
		    	}
	
		    	if(filterQuery!=null && key.equalsIgnoreCase(EVENTTYPE)){
		    		solrQuery.addFilterQuery(TAG+EVENTTYPE+":"+filterQuery);
		    		solrQuery.addFacetField(EXCLUDE+EVENTTYPE);
		    	}
		    	
		    	if(filterQuery!=null && key.equalsIgnoreCase("webinar")){
		    		solrQuery.addFilterQuery(TAG+WEBINAR+":"+filterQuery);
		    		solrQuery.addFacetField(EXCLUDE+WEBINAR);
		    	}

		    	if(filterQuery!=null && key.equalsIgnoreCase(EVENTDATE)){
		    		
		    		try {
						int eventDate = Integer.parseInt(filterQuery);
						Date date = getCurrentZeroHourDate().getTime();
						String dateFormat = sdf.format(date);

						Calendar cal = getCurrentZeroHourDate();
						cal.add(Calendar.DAY_OF_MONTH, 1);
						
						date = cal.getTime();
						String endDateFormat = sdf.format(date);
						
						if(eventDate==TOMORROW){
							cal = getCurrentZeroHourDate();
							cal.add(Calendar.DAY_OF_MONTH, 1);
							date = cal.getTime();
							dateFormat = sdf.format(date);

							cal = getCurrentZeroHourDate();
							cal.add(Calendar.DAY_OF_MONTH, 2);
							date = cal.getTime();
							endDateFormat = sdf.format(date);
						}
						if(eventDate==TODAY || eventDate==TOMORROW){
							solrQuery.addFilterQuery(TAG+LEFT_PARENTHESIS+START_DATE+":[ * TO "+endDateFormat+"] AND "+END_DATE+":["+dateFormat+" TO *]"+RIGHT_PARENTHESIS);
						}else{
							if(eventDate==CURRENT_WEEK){
								//This week filter
								cal = getCurrentZeroHourDate();	
								int dayOftheWeek = cal.get(Calendar.DAY_OF_WEEK);
								
								cal.add(Calendar.DAY_OF_MONTH, 1);
								date = cal.getTime();
								dateFormat = sdf.format(date);
								
								int i = Calendar.SATURDAY - dayOftheWeek;
								
								cal.add(Calendar.DAY_OF_MONTH, i+1);
								date = cal.getTime();
								endDateFormat = sdf.format(date);
								solrQuery.addFilterQuery(TAG+LEFT_PARENTHESIS+START_DATE+":"+"[ * TO "+endDateFormat+"]"+" AND "+END_DATE+":["+dateFormat+" TO *]"+RIGHT_PARENTHESIS);
							}
							if(eventDate==NEXT_WEEK){
								//Next week filter
								cal = getCurrentZeroHourDate();
								cal.add(Calendar.WEEK_OF_MONTH, 1);
								cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
								date = cal.getTime();
								dateFormat = sdf.format(date);

								cal = getCurrentZeroHourDate();
								cal.add(Calendar.WEEK_OF_MONTH, 2);
								cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);

								date = cal.getTime();
								endDateFormat = sdf.format(date);
								solrQuery.addFilterQuery(TAG+LEFT_PARENTHESIS+START_DATE+":"+"[ * TO "+endDateFormat+"]"+" AND "+END_DATE+":["+dateFormat+" TO *]"+RIGHT_PARENTHESIS);
							}
							if(eventDate==CURRENT_MONTH){
								//Next week filter
								cal = getCurrentZeroHourDate();
								dateFormat = sdf.format(cal.getTime());

								cal = getCurrentZeroHourDate();
								cal.add(Calendar.MONTH, 1);
								cal.set(Calendar.DAY_OF_MONTH,1);

								date = cal.getTime();
								endDateFormat = sdf.format(date);
								solrQuery.addFilterQuery(TAG+LEFT_PARENTHESIS+START_DATE+":"+"[ * TO "+endDateFormat+"]"+" AND "+END_DATE+":["+dateFormat+" TO *]"+RIGHT_PARENTHESIS);
							}
							if(eventDate==REST){
								//Other dates filter
								cal =getCurrentZeroHourDate();
								cal.add(Calendar.MONTH, 1);
								cal.set(Calendar.DAY_OF_MONTH,1);
								date = cal.getTime();
								dateFormat = sdf.format(date);
								solrQuery.addFilterQuery(TAG+LEFT_PARENTHESIS+START_DATE+":"+"[ * TO * ]"+" AND "+END_DATE+":["+dateFormat+" TO *]"+RIGHT_PARENTHESIS);
								//fq=fq+" AND ("+START_DATE+":"+"["+dateFormat+" TO *]"+" OR "+END_DATE+":["+dateFormat+" TO *])";
							}
						}
					} catch (Exception e) {
						logger.info(" event date parsable error"+filterQuery);
					}
		    		
		    	}else{
					solrQuery.addFilterQuery(END_DATE+":["+sdf.format(getCurrentZeroHourDate().getTime())+" TO * ]");
				}
			}
		}
		
		if(countryId!=null){
			if(countryId<0){
				solrQuery = getOtherCountrySolrQuery(query, rows, start,countryId);
			}
			else{
				solrQuery.addFilterQuery(COUNTRYID+":"+countryId+" OR isWebinar:true");
			}
		}
		return returnSolrResponse(solrQuery);
	}
	private Calendar getCurrentZeroHourDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal;
	}


	private QueryResponse returnSolrResponse(SolrQuery solrQuery)
			throws SolrServerException {
		QueryResponse response;
		logger.info("RawQuery :" + solrQuery);
		response = searchServer.solrServer.query(solrQuery);
		return response;
	}


	public SearchQueryResponse getSearchQueryResponse(String query,Map<String,String> listOfFilters, int rows,int start,Integer countryId)
			throws Exception {
		
		QueryResponse response = getSolrResponse(query,listOfFilters, rows,start*rows,countryId); 

		if(countryId!=null){
			if(countryId<0){
				return getOtherCountrySearchResponse(response, query);
			}
		}
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

		addWebinarCount(response, searchQueryResponse);
		
		/*long webinarCount = 0;
		List<Count> facetValues = response.getFacetField(WEBINAR).getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				if(facet == null) continue;
				if(facet.getName()!=null && facet.getName().equals("true")){
					webinarCount+=facet.getCount();
				}
			}
		}
		
		List<FilterItem> webinarFilterItems = searchQueryResponse.getWebinarFilterItems();
		if(webinarFilterItems==null) {
			webinarFilterItems = new ArrayList<FilterItem>();
			searchQueryResponse.setWebinarFilterItems(webinarFilterItems);
		}
		
		if(webinarCount>0){
			FilterItem filterItem = new FilterItem();
			filterItem.setName("Webinar");
			filterItem.setCount(webinarCount);
			webinarFilterItems.add(filterItem);
		}*/
		
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
								subcategoryFilter = subcategoryFilter.replace(LEFT_PARENTHESIS, "").replace(RIGHT_PARENTHESIS, "");
								
								//this is for OR condtion
								//subcategoryFilter = LEFT_PARENTHESIS+subcategoryFilter + " OR " + node.getId()+RIGHT_PARENTHESIS;
								
								//this is for AND condtion
								subcategoryFilter = node.getId().toString();
								
							}else{
								subcategoryFilter = removeOldFilter(node.getId().toString(),subcategoryFilter);
							}
						}else{
							subcategoryFilter = node.getId().toString();
						}
						Map<String,String> newFilterMap = new HashMap<String, String>(listOfFilters);
						String filterFacetQuery = SUBCATEGORYID+"="+subcategoryFilter;
						newFilterMap.put(SUBCATEGORYID, subcategoryFilter);
						FilterItem filterItem = getFilterItem(facet.getCount(), node.getName(),filterFacetQuery,newFilterMap,countryId);
						subCategoryFilterItems.add(filterItem);
					}
				}
			}
		}
		
		getDateFilters(response, searchQueryResponse,listOfFilters,countryId);

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
							eventTypeFilter = eventTypeFilter.replace(LEFT_PARENTHESIS, "").replace(RIGHT_PARENTHESIS, "");
							
							//this is for OR condition
							//eventTypeFilter = LEFT_PARENTHESIS+eventTypeFilter + " OR " + facet.getName()+RIGHT_PARENTHESIS;
							
							//this is for AND condtion
							eventTypeFilter = facet.getName();
						}else{
							eventTypeFilter = removeOldFilter(facet.getName(), eventTypeFilter);
						}
					}else{
						eventTypeFilter = facet.getName();
					}
					String filterFacetQuery = EVENTTYPE+"="+eventTypeFilter;
					Map<String,String> newFilterMap = new HashMap<String, String>(listOfFilters);
					newFilterMap.put(EVENTTYPE, eventTypeFilter);
					FilterItem filterItem = getFilterItem(facet.getCount(), facet.getName(), filterFacetQuery,newFilterMap,countryId);
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
									
									cityIdFilter = cityIdFilter.replace(LEFT_PARENTHESIS, "").replace(RIGHT_PARENTHESIS, "");
									
									//this is for OR condition
									//cityIdFilter = LEFT_PARENTHESIS+cityIdFilter + " OR " + facetName+RIGHT_PARENTHESIS;
									
									//this is for AND condition
									cityIdFilter = facetName;
									
								}else{
									cityIdFilter = removeOldFilter(facetName, cityIdFilter);
								}
							}else{
								cityIdFilter = facetName;
							}
							String filterFacetQuery = CITYID+"="+cityIdFilter;
							Map<String,String> newFilterMap = new HashMap<String, String>(listOfFilters);
							newFilterMap.put(CITYID, cityIdFilter);		
							FilterItem filterItem = getFilterItem(facet.getCount(), cityName,filterFacetQuery,newFilterMap,countryId);
							cityIdFilterItems.add(filterItem);
						}
					} catch (NumberFormatException e) {
						logger.info("parsing error for city");
					}
				}
			}
		}
		
		FilterItem filterItem = new FilterItem();
		if(countryId!=null){
			filterItem = getFilterItem(0L, "Other Countries",COUNTRYID+"=-"+countryId,null,null);
			searchQueryResponse.setOtherCountries(filterItem);
		}
		return searchQueryResponse;
	}


	private void addImageHostUrl(List<EventSearchRecord> eventSearchRecords) {
		if(eventSearchRecords!=null && eventSearchRecords.size()>0){
			for(EventSearchRecord eventSearchRecord:eventSearchRecords){
				if(eventSearchRecord==null) continue;
				String timeZone = eventSearchRecord.getTimeZone();
				if(eventSearchRecord.getStartDate()!=null){
					try {
						eventSearchRecord.setStartDate(dateCustomConverter.getTimeZoneDate(timeZone, eventSearchRecord.getStartDate()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(eventSearchRecord.getStartDate()!=null){
					try {
						eventSearchRecord.setEndDate(dateCustomConverter.getTimeZoneDate(timeZone, eventSearchRecord.getEndDate()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(eventSearchRecord.getPromotionLogoUrl()!=null && !eventSearchRecord.getPromotionLogoUrl().startsWith(imageHostUrl)){
					eventSearchRecord.setPromotionLogoUrl(imageHostUrl+eventSearchRecord.getPromotionLogoUrl());
				}
				if(eventSearchRecord.getBanner()!=null && !eventSearchRecord.getBanner().startsWith(imageHostUrl)){
					eventSearchRecord.setBanner(imageHostUrl+eventSearchRecord.getBanner());
				}
			}
		}
		
	}


	private String removeOldFilter(String oldFilter, String filter) {
		filter = filter.replace(LEFT_PARENTHESIS, "").replace(RIGHT_PARENTHESIS, "");
		filter = filter.replace(oldFilter, "");
		if(filter.startsWith(" OR ")){
			filter = filter.substring(4);
		}
		if(filter.endsWith(" OR ")){
			filter = filter.substring(0,filter.length()-5);
		}
		if(filter!=null && !filter.isEmpty()){
			filter = LEFT_PARENTHESIS+filter + RIGHT_PARENTHESIS;
		}else{
			filter = null;
		}
		return filter;
	}


	private void getDateFilters(QueryResponse response,
			SearchQueryResponse searchQueryResponse,Map<String,String> listOfFilters,Integer countryId) throws ParseException {
		List<FilterItem> eventDateFilterItems = searchQueryResponse.getEventDateFilterItems();
		if(eventDateFilterItems==null){
			eventDateFilterItems = new ArrayList<FilterItem>();
			searchQueryResponse.setEventDateFilterItems(eventDateFilterItems);
		}

/*		Long todayCount =0L;
		Long tomorrowCount=0L;
		Long currentWeekCount=0L;
		Long nextWeekCount=0L;
		Long currentMonthCount=0L;
		Long otherDatesCount=0L;
*/		
		DateCount dateCount = new DateCount();
		
		NamedList<List<PivotField>> facetPivot = response.getFacetPivot();
		List<List<PivotField>> pivotList = facetPivot.getAll(START_DATE+","+END_DATE);
		for(List<PivotField> pivotInnerList:pivotList){
			for(PivotField pivotField:pivotInnerList){
				String field = pivotField.getField();
				int count = pivotField.getCount();
				Date pivotValue = (Date)pivotField.getValue();
				List<PivotField> subPivotList = pivotField.getPivot();
				
				//Date eventDate = dateSdf.parse(pivotValue);
				int dateFilter = getDayFilter(pivotValue);
				for(PivotField innerPivotField:subPivotList){
					//Date endDate = dateSdf.parse((Date)innerPivotField.getValue());
					Date endDatePivot = (Date)innerPivotField.getValue();
					int endDateFilter = getDayFilter(endDatePivot);
					count=innerPivotField.getCount();
					calculateCount(dateCount, count, endDateFilter);
					switch(dateFilter){
							case PAST:
							case TODAY:
								if(endDateFilter>TODAY){
									dateCount.todayCount+=count;
								}
							case TOMORROW:
								if(endDateFilter>TOMORROW){
									dateCount.tomorrowCount+=count;
								}
							case CURRENT_WEEK:
								if(endDateFilter>CURRENT_WEEK){
									dateCount.currentWeekCount+=count;
								}
							case NEXT_WEEK:
								if(endDateFilter>NEXT_WEEK){
									dateCount.nextWeekCount+=count;
								}
							case CURRENT_MONTH:
								if((isCurrentMonth(pivotValue) || isCurrentMonth(endDatePivot) || isCurrentMonth(pivotValue,endDatePivot)) ){
									if(endDateFilter!=CURRENT_MONTH)
										dateCount.currentMonthCount+=count;
								}
								if(!isCurrentMonth(endDatePivot) && endDateFilter!=REST){
									dateCount.otherDatesCount+=count;
								}
					}
					//System.out.println("start date"+pivotValue+" end date"+endDatePivot+"date filter"+dateFilter+"end filter "+
						//	endDateFilter+" datecount:"+dateCount.toString()+"   count:"+count);					
				}
			}
		}
		
		String valueOf = String.valueOf(TODAY);
		Map<String, String> newFilterMap = getFilterData(listOfFilters, valueOf);		
		FilterItem filterItem = getFilterItem(dateCount.todayCount,"Today", "",newFilterMap,countryId);
		eventDateFilterItems.add(filterItem);

		valueOf = String.valueOf(TOMORROW);
		newFilterMap = getFilterData(listOfFilters, valueOf);		
		filterItem = getFilterItem(dateCount.tomorrowCount,"Tommorrow", "",newFilterMap,countryId);
		eventDateFilterItems.add(filterItem);

		valueOf = String.valueOf(CURRENT_WEEK);
		newFilterMap = getFilterData(listOfFilters, valueOf);		
		filterItem = getFilterItem(dateCount.currentWeekCount,"This Week", "",newFilterMap,countryId);
		eventDateFilterItems.add(filterItem);

		valueOf = String.valueOf(NEXT_WEEK);
		newFilterMap = getFilterData(listOfFilters, valueOf);		
		filterItem = getFilterItem(dateCount.nextWeekCount,"Next Week", "",newFilterMap,countryId);
		eventDateFilterItems.add(filterItem);

		valueOf = String.valueOf(CURRENT_MONTH);
		newFilterMap = getFilterData(listOfFilters, valueOf);		
		filterItem = getFilterItem(dateCount.currentMonthCount,"This month", "",newFilterMap,countryId);
		eventDateFilterItems.add(filterItem);

		valueOf = String.valueOf(REST);
		newFilterMap = getFilterData(listOfFilters, valueOf);		
		filterItem = getFilterItem(dateCount.otherDatesCount,"Other Dates", "",newFilterMap,countryId);
		eventDateFilterItems.add(filterItem);
	}


	private boolean isCurrentMonth(Date startDate, Date endDate) {
		Calendar cal = getCurrentZeroHourDate();
	    Date date = cal.getTime();
	    if(startDate.before(date) && endDate.after(date)) return true;
		return false;
	}
	private boolean isCurrentMonth(Date startDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
	    
	    cal.setTime(new Date());
		int currentYear = cal.get(Calendar.YEAR);
	    int currentMonth = cal.get(Calendar.MONTH);
	    
	    if(month==currentMonth) return true;
	    return false;
	    
	}
	private void calculateCount(DateCount dateCount, int count,
			int endDateFilter) {
		switch(endDateFilter){
			case PAST:
				break;
			case TODAY:
				dateCount.todayCount+=count;
				break;
			case TOMORROW:
				dateCount.tomorrowCount+=count;
				break;
			case CURRENT_WEEK:
				dateCount.currentWeekCount+=count;
				break;
			case NEXT_WEEK:
				dateCount.nextWeekCount+=count;
				break;
			case CURRENT_MONTH:
				dateCount.currentMonthCount+=count;
				break;
			case REST:
				dateCount.otherDatesCount+=count;
				break;
		}
	}


	private Map<String, String> getFilterData(
			Map<String, String> listOfFilters, String valueOf) {
	//	String eventDateFilter = getEventDateFilter(listOfFilters, valueOf);
	//	String filterFacetQuery = EVENTDATE+"="+valueOf;
		Map<String,String> newFilterMap = new HashMap<String, String>(listOfFilters);
		
		//TODO: need to add later
		if(!valueOf.equals(listOfFilters.get(EVENTDATE))){
			newFilterMap.put(EVENTDATE, valueOf);	
		}else{
			newFilterMap.remove(EVENTDATE);
		}
		//newFilterMap.put(EVENTDATE, eventDateFilter);
		return newFilterMap;
	}


	private String getEventDateFilter(Map<String, String> listOfFilters,
			String valueOf) {
		String eventDateFilter = listOfFilters.get(EVENTDATE);
		if(eventDateFilter!=null){
			if(!eventDateFilter.contains(valueOf)){
				eventDateFilter = eventDateFilter.replace(LEFT_PARENTHESIS, "").replace(RIGHT_PARENTHESIS, "");
				eventDateFilter = LEFT_PARENTHESIS+eventDateFilter + " OR " + valueOf+RIGHT_PARENTHESIS;
			}
		}else{
			eventDateFilter = valueOf;
		}
		return eventDateFilter;
	}


	private FilterItem getFilterItem(Long count, String  name,
			String filterQuery,Map<String,String> filterMap,Integer countryId) {
		FilterItem filterItem = new FilterItem();
		filterItem.setCount(count);
		filterItem.setName(name);
		
		if(filterMap==null){
			filterItem.setQuery(filterQuery);
			return filterItem;
		}
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
		if(countryId!=null && countryId>0){
			filterQuery = filterQuery+"&countryId="+countryId;
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
	    
	    cal = getCurrentZeroHourDate();
		int currentYear = cal.get(Calendar.YEAR);
	    int currentMonth = cal.get(Calendar.MONTH);
	    int currentDay = cal.get(Calendar.DAY_OF_MONTH);
	    int currentDate = cal.get(Calendar.DATE);
	    int currentWeek = cal.get(Calendar.WEEK_OF_MONTH);
	    
	    long eventMilli = eventDate.getTime();
	    long currentTimeMilli = cal.getTime().getTime();
	    
	    if(currentTimeMilli>eventMilli) return PAST;
	    long delayMilli = eventMilli-currentTimeMilli;
	    long hrs = delayMilli/(1000*60*60);
	    if(hrs<24) {
	    	return TODAY;
	    }
	    if(hrs<48){
	    	return TOMORROW;
	    }
	    
	    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    currentTimeMilli = cal.getTime().getTime();
	    if(currentTimeMilli<=eventMilli){
	    	delayMilli = eventMilli-currentTimeMilli;
	    	hrs = delayMilli/(1000*60*60);
	    	if(hrs<7*24) {
	    		return CURRENT_WEEK;
	    	}
	    	if(hrs<14*24){ 
	    		return NEXT_WEEK;
	    	}
	    }
	    
	    if(currentMonth == month ){
	    	return CURRENT_MONTH;//this month
	    }
	    return REST;//Rest 
	}

	public SearchQueryResponse getOtherCountrySearchResponse(QueryResponse response,String query)
			throws Exception {
		
		SearchQueryResponse searchQueryResponse = new SearchQueryResponse();
		searchQueryResponse.setEventSearchRecords(response.getBeans(EventSearchRecord.class));
		addImageHostUrl(searchQueryResponse.getEventSearchRecords());
		searchQueryResponse.setQuery(query);
		searchQueryResponse.setNoOfresults(response.getResults().getNumFound());
		
		
		Map<Integer, String> countryMap = entityUtilities.getCountryMap();
		List<FilterItem> countryFilterItems = searchQueryResponse.getCountryFilterItems();
		if(countryFilterItems==null){
			countryFilterItems = new ArrayList<FilterItem>();
			searchQueryResponse.setCityIdFilterItems(countryFilterItems);
		}
		
		List<Count> facetValues = response.getFacetField(COUNTRYID).getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				String facetName = facet.getName();
				String countryName = null;
				if(countryMap!=null){
					try {
						countryName = countryMap.get(Integer.parseInt(facetName));
						if(countryName!=null && facet.getCount()>0){
							
							String filterFacetQuery = COUNTRYID+"="+facetName;
							FilterItem filterItem = new FilterItem();
							filterItem.setCount(facet.getCount());
							filterItem.setName(countryName);
							filterItem.setQuery(filterFacetQuery);
							countryFilterItems.add(filterItem);
						}
					} catch (NumberFormatException e) {
						logger.info("parsing error for city");
					}
				}
			}
		}
		
		addWebinarCount(response, searchQueryResponse);

		return searchQueryResponse;
	}
	private void addWebinarCount(QueryResponse response,
			SearchQueryResponse searchQueryResponse) {
		List<Count> facetValues;
		long webinarCount = 0;
		facetValues = response.getFacetField(WEBINAR).getValues();
		if(facetValues!=null && facetValues.size()>0){
			for(Count facet:facetValues){
				if(facet == null) continue;
				if(facet.getName()!=null && facet.getName().equals("true")){
					webinarCount+=facet.getCount();
				}
			}
		}

		List<FilterItem> webinarFilterItems = searchQueryResponse.getWebinarFilterItems();
		if(webinarFilterItems==null) {
			webinarFilterItems = new ArrayList<FilterItem>();
			searchQueryResponse.setWebinarFilterItems(webinarFilterItems);
		}

		if(webinarCount>0){
			FilterItem filterItem = new FilterItem();
			filterItem.setName("Webinar");
			filterItem.setCount(webinarCount);
			filterItem.setQuery("webinar=true");
			webinarFilterItems.add(filterItem);
		}
	}


	private SolrQuery getOtherCountrySolrQuery(String query, int rows,
			int start,Integer countryId) throws SolrServerException {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setStart(start);
		solrQuery.setQuery(query);
		if(rows==0){
			rows = 10;
		}
		solrQuery.setRows(rows);
		
		solrQuery.addFacetField(COUNTRYID);
		solrQuery.addFacetField(WEBINAR);
		solrQuery.setIncludeScore(true);
		solrQuery.addFilterQuery(END_DATE+":["+sdf.format(getCurrentZeroHourDate().getTime())+" TO * ]");
		solrQuery.addFilterQuery("-"+COUNTRYID+":"+countryId*-1/*+" OR isWebinar:true"*/);
		return solrQuery;
	}
}
