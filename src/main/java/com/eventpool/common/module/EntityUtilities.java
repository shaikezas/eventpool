package com.eventpool.common.module;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.eventpool.common.dto.Region;
import com.eventpool.common.entities.City;
import com.eventpool.common.entities.Country;
import com.eventpool.common.entities.State;

@Component
public class EntityUtilities {

	@Resource
	private RepositoryPool repositoryPool;
	
	private Map<Integer,String> cityMap = new HashMap<Integer,String>(39000);
	private Map<Integer,Integer> cityStateMap = new HashMap<Integer,Integer>(39000);
	private Map<Integer,String> cityTimeZoneMap = new HashMap<Integer,String>(39000);
	private Map<Integer,String> stateMap =new HashMap<Integer,String>();
	private Map<Integer,Integer> stateCountryMap =new HashMap<Integer,Integer>();
	private Map<Integer,String> countryMap = new HashMap<Integer,String>();
	private Map<String,Integer> countryNameMap = new HashMap<String,Integer>();
	private Map<Integer,String> countryFlagMap = new HashMap<Integer,String>();
	private Map<Integer,Country> activeCountryMap = new LinkedHashMap<Integer,Country>();

	private boolean initAllMaps=true;
	
	public static final String SEPARATOR = "-";
	
	@PostConstruct
	public void initMaps(){
		if(initAllMaps){
			List<City> allCities = repositoryPool.getCityRepository().findAll();
			if(allCities!=null && allCities.size()>0){
				for(City city:allCities){
					cityMap.put(city.getId(), city.getName());
					cityStateMap.put(city.getId(), city.getStateId());
					cityTimeZoneMap.put(city.getId(), city.getTimeZone());
				}
			}else{
				cityMap.clear();
				cityStateMap.clear();
				cityTimeZoneMap.clear();
			}
			List<State> allStates = repositoryPool.getStateRepository().findAll();
			if(allStates!=null && allStates.size()>0){
				for(State state:allStates){
					stateMap.put(state.getId(), state.getName());
					stateCountryMap.put(state.getId(), state.getCountryId());
				}
			}else{
				stateMap.clear();
				stateCountryMap.clear();
			}
			List<Country> allCountries = repositoryPool.getCountryRepository().findAll();
			if(allCountries!=null && allCountries.size()>0){
				for(Country country:allCountries){
					countryMap.put(country.getId(), country.getName());
					countryNameMap.put(country.getName().toLowerCase(), country.getId());
					countryFlagMap.put(country.getId(), country.getCode());
					if(country.getActive()!=null && country.getActive()){
						activeCountryMap.put(country.getId(), country);
					}
				}
			}else{
				countryMap.clear();
			}

		}
	}
	
	public Map<Integer,String> getCitiesForState(Integer stateId){
		HashMap<Integer,String> cityMap = new HashMap<Integer, String>();
		List<City> citiesForState = repositoryPool.getCityRepository().getCitiesForState(stateId);
		if(citiesForState!=null && citiesForState.size()>0){
			for(City city:citiesForState){
				cityMap.put(city.getId(), city.getName());
			}
		}
		return cityMap;
	}

	public Map<Integer,String> getCitiesForCountry(Integer countryId){
		HashMap<Integer,String> cityMap = new HashMap<Integer, String>();
		List<City> citiesForCountry = repositoryPool.getCityRepository().getCitiesForCountry(countryId);
		if(citiesForCountry!=null && citiesForCountry.size()>0){
			for(City city:citiesForCountry){
				cityMap.put(city.getId(), city.getName());
			}
		}
		return cityMap;
		
	}

	public Map<Integer,String> getStatesForCountry(Integer countryId){
		HashMap<Integer,String> stateMap = new HashMap<Integer, String>();
		List<State> statesForCountry = repositoryPool.getStateRepository().getStatesForCountry(countryId);
		if(statesForCountry!=null && statesForCountry.size()>0){
			for(State state:statesForCountry){
				stateMap.put(state.getId(), state.getName());
			}
		}
		return stateMap;
	}

	public Map<Integer,Region> getCitiesWithStateAndCountry(){
		HashMap<Integer,Region> cityMap = new HashMap<Integer, Region>();
		for(Integer cityId:this.cityMap.keySet()){
			Integer stateId = this.cityStateMap.get(cityId);
			Integer countryId = this.stateCountryMap.get(stateId);
			
			String cityName  = this.cityMap.get(cityId);
			String stateName  = this.stateMap.get(stateId);
			String countryName  = this.countryMap.get(countryId);
			String countryFlag = this.countryFlagMap.get(countryId);
			String timeZone = this.cityTimeZoneMap.get(cityId);		
			
			Region region = new Region();
				region.setCityId(cityId);
				region.setCityName(cityName);
				region.setStateId(stateId);
				region.setStateName(stateName);
				region.setCountryId(countryId);
				region.setCountryName(countryName);
				region.setFlag(countryFlag);
				region.setTimeZone(timeZone);
			
			cityMap.put(cityId, region);
		}
		return cityMap;
	}
	
	public Map<Integer, String> getCityMap() {
		return cityMap;
	}

	public void setCityMap(Map<Integer, String> cityMap) {
		this.cityMap = cityMap;
	}

	public Map<Integer, String> getStateMap() {
		return stateMap;
	}

	public void setStateMap(Map<Integer, String> stateMap) {
		this.stateMap = stateMap;
	}

	public Map<Integer, String> getCountryMap() {
		return countryMap;
	}

	public void setCountryMap(Map<Integer, String> countryMap) {
		this.countryMap = countryMap;
	}

	public RepositoryPool getRepositoryPool() {
		return repositoryPool;
	}

	public void setRepositoryPool(RepositoryPool repositoryPool) {
		this.repositoryPool = repositoryPool;
	}

	public Map<String, Integer> getCountryNameMap() {
		return countryNameMap;
	}

	public void setCountryNameMap(Map<String, Integer> countryNameMap) {
		this.countryNameMap = countryNameMap;
	}

	public Map<Integer, Country> getActiveCountryMap() {
		return activeCountryMap;
	}

	public void setActiveCountryMap(Map<Integer, Country> activeCountryMap) {
		this.activeCountryMap = activeCountryMap;
	}
	
}
