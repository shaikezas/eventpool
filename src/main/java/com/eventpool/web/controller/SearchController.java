package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventpool.common.dto.Region;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.module.EntityUtilities;
import com.eventpool.web.forms.SearchResponse;


@Controller
@RequestMapping("/search")
public class SearchController {
	

    @Resource
    private EntityUtilities utilities;
    
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
    public @ResponseBody List<SearchResponse> getDefaultResults() throws Exception {
    	List<SearchResponse> srList = new ArrayList<SearchResponse>();
    	SearchResponse sr = new SearchResponse();
    	sr.setvName("VN001");
    	sr.setvAddress("VAdd001");
    	sr.setStartDate("11-11-11");
    	sr.setEndDate("12-12-12");
    	sr.setEventTitle("ETitle001");
    	srList.add(sr);
    	sr = new SearchResponse();
    	sr.setvName("VN002");
    	sr.setvAddress("VAdd002");
    	sr.setStartDate("10-10-10");
    	sr.setEndDate("11-11-11");
    	sr.setEventTitle("ETitle002");
    	srList.add(sr);
    	return srList;
    }
    
    
    public static String getNextKey(String key1){
        int len = key1.length();
        String allButLast = key1.substring(0, len - 1);
        String newTitleSearch = allButLast + (char)(key1.charAt(len - 1) + 1);
        return newTitleSearch;
      }

}
