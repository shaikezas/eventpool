package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.kaha.impl.container.BaseContainerImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.net.util.IPAddressUtil;

import com.eventpool.common.dto.Region;
import com.eventpool.common.entities.Country;
import com.eventpool.common.module.EntityUtilities;
import com.eventpool.common.module.EventPoolConstants;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.common.module.IPLocation;
import com.eventpool.web.forms.SearchResponse;


@Controller
@RequestMapping("/search")
public class SearchController extends BaseController{
	

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
    @Resource
    private EntityUtilities utilities;
    
    @Resource
    private SearchService searchService;
    
    @Resource
    IPLocation ipLocation;
    
    TreeMap<String, Region> citySearch = null;
	
    @RequestMapping("citysearch")
    public @ResponseBody List<Region> getCitiesWithStateAndCountry(@RequestParam("query") String query) {
    	List<Region> searchResults = new ArrayList<Region>();
    	if(query==null || query.length()<=0){
    		return searchResults;
    	}
    	query = query.toLowerCase();
    	
    	if(citySearch==null){
    		citySearch =  new TreeMap<String, Region>();
    	
    	Map<Integer, Region> citiesWithStateAndCountry = utilities.getCitiesWithStateAndCountry();
    	for(Integer key : citiesWithStateAndCountry.keySet()){
    		String keyName = citiesWithStateAndCountry.get(key).getCityName() +"-"+citiesWithStateAndCountry.get(key).getStateName()+"-"+citiesWithStateAndCountry.get(key).getCountryName();
    		citySearch.put(keyName.toLowerCase(),citiesWithStateAndCountry.get(key));
    	}
    	}
    	SortedMap<String, Region> myValues = citySearch.subMap(query, getNextKey(query));
        for(Entry<String, Region> entry : myValues.entrySet()){
          searchResults.add(entry.getValue());
        }
    	
        return searchResults;
    }
    
    @RequestMapping(value = "/getDefaultResults", method = RequestMethod.GET)
    public @ResponseBody SearchQueryResponse getDefaultResults(HttpServletRequest request) throws Exception {

    	String subCategoryId = request.getParameter("subCategoryId");
    	String cityId = request.getParameter("cityId");
    	String eventType = request.getParameter("eventType");
    	String eventDate = request.getParameter("eventDate");
    	String countryParam = request.getParameter("countryId");
    	String webinar = request.getParameter("webinar");
    	
    	
    	Integer countryId = null;
    	if(countryParam==null || countryParam.isEmpty() || checkIfUndefined(countryParam)==null){
        	String ip=getRemoteIp(request);
        	 countryId=ipLocation.getCountryId(ip);
        	 logger.info("IP address is:"+ip+" country id:"+countryId);
    	}else{
	    	try {
				countryId = Integer.parseInt(countryParam);
			} catch (Exception e1) {
				logger.info("country is not parsable"+countryParam);
			}
    	}
    	String fq = null;
    	
    	Map<String,String> filterMap = new HashMap<String, String>();
    	subCategoryId = checkIfUndefined(subCategoryId);
    	if(subCategoryId!=null && !subCategoryId.isEmpty()){
    		filterMap.put("subCategoryId", subCategoryId);
    	}

    	cityId = checkIfUndefined(cityId);
    	if(cityId!=null && !cityId.isEmpty()){
    		filterMap.put("cityId", cityId);
    	}

    	eventType = checkIfUndefined(eventType);
    	if(eventType!=null && !eventType.isEmpty()){
    		filterMap.put("eventType", eventType);
    	}

    	eventDate = checkIfUndefined(eventDate);
    	if(eventDate!=null && !eventDate.isEmpty()){
    		filterMap.put("eventDate", eventDate);
    	}

    	webinar = checkIfUndefined(webinar);
    	if(webinar!=null && !webinar.isEmpty()){
    		filterMap.put("webinar", webinar);
    	}

    	String q = request.getParameter("q");
    	q = checkIfUndefined(q);
    	int start=0;
    	String startParam = request.getParameter("start");
    	if(startParam!=null){
    		try {
				start = Integer.parseInt(startParam.trim());
			} catch (Exception e) {
				logger.info("Parsing error for start parameter "+startParam);
			}
    	}
    	
		try {
			return searchService.getSearchQueryResponse(q,filterMap, EventPoolConstants.MAX_SEARCH_RESULTS, start,countryId);
		} catch (Exception e) {
			logger.info("Exception whil getting results from solr",e);
			throw e;
		}
    }
    
    
    private String checkIfUndefined(String q) {
    	if(q!=null && !q.isEmpty() && q.equalsIgnoreCase("undefined")){
    		return null;
    	}
    	return q;
	}

/*	@RequestMapping(value = "/fetchResultsByFilterType/{filterType}/{searchType}/{loc}", method = RequestMethod.GET)
    public @ResponseBody SearchQueryResponse getSearchResultsByFilterType(@PathVariable("filterType") String filterType,@PathVariable("searchType") String searchType,@PathVariable("loc") String loc) throws Exception {
    	String query = filterType+","+searchType + "," + loc;
    	
    	System.out.println("User entered query from the ui...:::" + query);
    	return searchService.getSearchQueryResponse(searchType, null,10, 0);
      }
*/    
    
    public static String getNextKey(String key1){
        int len = key1.length();
        String allButLast = key1.substring(0, len - 1);
        String newTitleSearch = allButLast + (char)(key1.charAt(len - 1) + 1);
        return newTitleSearch;
      }

    @RequestMapping(value = "/getSearchResults", method = RequestMethod.GET)
    public @ResponseBody SearchQueryResponse getHomepageResults(HttpServletRequest httpServletRequest) throws Exception {
    	String ip=getRemoteIp(httpServletRequest);
    	logger.info("IP address is:"+ip);
		Integer countryId = ipLocation.getCountryId(ip);
    	List<EventSearchRecord> eventSearchRecords = searchService.getSearchRecords(EventPoolConstants.HOMEPAGE_SEARCH_RESULTS, 0, null, countryId);
    	SearchQueryResponse searchQueryResponse = new SearchQueryResponse();
    	searchQueryResponse.setEventSearchRecords(eventSearchRecords);
    	return searchQueryResponse;
    }
    

	
	@RequestMapping(value = "/getactivecountries", method = RequestMethod.GET)
	public  @ResponseBody LinkedHashMap<Integer,String> getActiveCountries(HttpServletRequest request)  throws Exception {
		Map<Integer, Country> activeCountryMap = utilities.getActiveCountryMap();
		Collection<Country> values = activeCountryMap.values();
		LinkedHashMap<Integer,String> activeCountries = new LinkedHashMap<Integer,String>();
		for(Country country:values){
			activeCountries.put(country.getId(), country.getName());
			//activeCountries.add(country.getName());
		}
		//Collections.sort(activeCountries);
		return activeCountries;
	}
	
	@RequestMapping(value = "/getdefaultcountrynameid", method = RequestMethod.GET)
	public  @ResponseBody Map getDefaultCountryNameId(HttpServletRequest request)  throws Exception {
		String ip=getRemoteIp(request);
    	logger.info("IP address is:"+ip);
		return ipLocation.getCountryNameIdMap(ip);
	}
}
