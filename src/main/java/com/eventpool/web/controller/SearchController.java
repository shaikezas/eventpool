package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventpool.common.module.EntityUtilities;


@Controller
@RequestMapping("/search")
public class SearchController {
	

    @Resource
    private EntityUtilities utilities;
    
    TreeMap<String, String> citySearch = null;
	
    @RequestMapping("citysearch")
    public @ResponseBody List<String> getCitiesWithStateAndCountry(@RequestParam("query") String query) {
    	List<String> searchResults = new ArrayList<String>();
    	if(query==null || query.length()<=0){
    		return searchResults;
    	}
    	query = query.toLowerCase();
    	
    	if(citySearch==null){
    		citySearch =  new TreeMap<String, String>();
    	
    	Map<Integer, String> citiesWithStateAndCountry = utilities.getCitiesWithStateAndCountry();
    	for(Integer key : citiesWithStateAndCountry.keySet()){
    		
    		citySearch.put(citiesWithStateAndCountry.get(key).toLowerCase(),citiesWithStateAndCountry.get(key)+utilities.SEPARATOR+key);
    	}
    	}
    	SortedMap<String, String> myValues = citySearch.subMap(query, getNextKey(query));
        for(Entry<String, String> entry : myValues.entrySet()){
          searchResults.add(entry.getValue());
        }
    	
        return searchResults;
    }
    
    public static String getNextKey(String key1){
        int len = key1.length();
        String allButLast = key1.substring(0, len - 1);
        String newTitleSearch = allButLast + (char)(key1.charAt(len - 1) + 1);
        return newTitleSearch;
      }

}
